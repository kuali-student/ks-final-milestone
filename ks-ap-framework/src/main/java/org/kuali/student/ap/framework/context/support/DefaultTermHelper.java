package org.kuali.student.ap.framework.context.support;

import static org.kuali.rice.core.api.criteria.PredicateFactory.like;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.infc.Atp;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.course.infc.Course;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Default implementation of {@link TermHelper} for use with applications that
 * implement all ATP logic by following the {@link AcademicCalendarService}
 * contract.
 * 
 * <p>
 * It is expected that the implementing institution provide support within
 * {@link AtpService#searchForAtps(QueryByCriteria, ContextInfo)} for searching
 * a &quot;query&quot; for the following values. This behavior may be deprecated
 * or moved to the core AtpService implementation in a future version of the KS
 * contract.
 * </p>
 * <dl>
 * <dt>{@link PlanConstants#INPROGRESS}</dt>
 * <dd>Terms that have started, but have not yet ended.</dd>
 * <dt>{@link PlanConstants#PUBLISHED}</dt>
 * <dd>Terms that have not ended for which course offerings have been published.
 * </dd>
 * <dt>{@link PlanConstants#PLANNING}</dt>
 * <dd>Published terms that have not started, and for which registration has not
 * ended.</dd>
 * </dl>
 */
public class DefaultTermHelper implements TermHelper {

	private static final Logger LOG = Logger.getLogger(DefaultTermHelper.class);


    private int numberOfTermInAcademicYear = -1;

    private List<Term> termsInAcademicYear = null;


	@Override
	public Term getTerm(String atpId) {
		try {
			return KsapFrameworkServiceLocator.getAcademicCalendarService()
					.getTerm(
							atpId,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		} 
	}

	@Override
	public YearTerm getYearTerm(String atpId) {
		return getYearTerm(getTerm(atpId));
	}

	@Override
	public List<Term> getCurrentTerms() {
		try {
			QueryByCriteria query = QueryByCriteria.Builder
					.fromPredicates(like("atpStatus", PlanConstants.INPROGRESS));
			List<TermInfo> rv = KsapFrameworkServiceLocator
					.getAcademicCalendarService().searchForTerms(
							query,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			if (rv == null || rv.isEmpty())
				throw new IllegalStateException(
						"AcademicCalendarService did not return any current terms");
			return new java.util.ArrayList<Term>(rv);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		}
	}

	@Override
	public Term getLastScheduledTerm() {
		Term rv = null;
		for (Term t : getPublishedTerms())
			if (rv == null || t.getEndDate().after(rv.getEndDate()))
				rv = t;
		assert rv != null;
		return rv;
	}

	@Override
	public Term getFirstTermOfAcademicYear(YearTerm yearTerm) {
		try {
			List<AcademicCalendarInfo> acl = KsapFrameworkServiceLocator
					.getAcademicCalendarService().getAcademicCalendarsForTerm(
							getTerm(yearTerm).getId(),
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			if (acl == null || acl.isEmpty())
				throw new IllegalStateException(
						"AcademicCalendarService did not return an academic calendar for year/term "
								+ yearTerm);
			AcademicCalendarInfo ac = acl.get(0);
			List<TermInfo> rl;
			try {
				rl = KsapFrameworkServiceLocator.getAcademicCalendarService()
						.getTermsForAcademicCalendar(
								ac.getId(),
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
			} catch (DoesNotExistException e) {
				throw new IllegalStateException(
						"AcademicCalendarService did not return any terms for academic calendar "
								+ ac.getId(), e);
			}
			if (rl == null || rl.isEmpty())
				throw new IllegalStateException(
						"AcademicCalendarService did not return any terms for academic calendar "
								+ ac.getId());
			Term rv = null;
			for (TermInfo t : rl)
				if (rv == null || t.getStartDate().before(rv.getStartDate()))
					rv = t;
			assert rv != null;
			return rv;
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		}
	}

    @Override
    public int getNumberOfTermsInAcademicYear(YearTerm yearTerm) {
        try {
            List<AcademicCalendarInfo> acl = KsapFrameworkServiceLocator
                    .getAcademicCalendarService().getAcademicCalendarsForTerm(
                            getTerm(yearTerm).getId(),
                            KsapFrameworkServiceLocator.getContext()
                                    .getContextInfo());
            if (acl == null || acl.isEmpty())
                throw new IllegalStateException(
                        "AcademicCalendarService did not return an academic calendar for year/term "
                                + yearTerm);
            AcademicCalendarInfo ac = acl.get(0);
            List<TermInfo> rl;
            try {
                rl = KsapFrameworkServiceLocator.getAcademicCalendarService()
                        .getTermsForAcademicCalendar(
                                ac.getId(),
                                KsapFrameworkServiceLocator.getContext()
                                        .getContextInfo());
            } catch (DoesNotExistException e) {
                throw new IllegalStateException(
                        "AcademicCalendarService did not return any terms for academic calendar "
                                + ac.getId(), e);
            }
            if (rl == null || rl.isEmpty())
                throw new IllegalStateException(
                        "AcademicCalendarService did not return any terms for academic calendar "
                                + ac.getId());
            return rl.size();
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Acal lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("Acal lookup failure", e);
        }
    }

    @Override
    public List<Term> getTermsInAcademicYear(YearTerm yearTerm) {
        try {
            List<AcademicCalendarInfo> acl = KsapFrameworkServiceLocator
                    .getAcademicCalendarService().getAcademicCalendarsForTerm(
                            getTerm(yearTerm).getId(),
                            KsapFrameworkServiceLocator.getContext()
                                    .getContextInfo());
            if (acl == null || acl.isEmpty())
                throw new IllegalStateException(
                        "AcademicCalendarService did not return an academic calendar for year/term "
                                + yearTerm);
            AcademicCalendarInfo ac = acl.get(0);
            List<TermInfo> rl;
            try {
                rl = KsapFrameworkServiceLocator.getAcademicCalendarService()
                        .getTermsForAcademicCalendar(
                                ac.getId(),
                                KsapFrameworkServiceLocator.getContext()
                                        .getContextInfo());
            } catch (DoesNotExistException e) {
                throw new IllegalStateException(
                        "AcademicCalendarService did not return any terms for academic calendar "
                                + ac.getId(), e);
            }
            if (rl == null || rl.isEmpty())
                throw new IllegalStateException(
                        "AcademicCalendarService did not return any terms for academic calendar "
                                + ac.getId());
            List<Term> rv = new ArrayList<Term>();
            for (TermInfo t : rl)
                    rv.add(t);

            Collections.sort(rv, new Comparator<Term>() {
                @Override
                public int compare(Term term1,
                                   Term term2) {
                    return KsapFrameworkServiceLocator
                            .getTermHelper()
                            .getYearTerm(term1)
                            .compareTo(
                                    KsapFrameworkServiceLocator.getTermHelper()
                                            .getYearTerm(term2));
                    // return
                    // plannedTerm1.getAtpId().compareTo(plannedTerm2.getAtpId());
                }
            });

            return rv;
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Acal lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("Acal lookup failure", e);
        }
    }

    @Override
    public int getNumberOfTermsInAcademicYear() {
        if(this.numberOfTermInAcademicYear==-1){
            List<Term> currentTerms = this.getCurrentTerms();
            if(!currentTerms.isEmpty()){
                numberOfTermInAcademicYear = this.getNumberOfTermsInAcademicYear(this.getYearTerm(currentTerms.get(0)));
            }else{
                numberOfTermInAcademicYear = 4;
            }
        }
        return  numberOfTermInAcademicYear;
    }

    @Override
    public List<Term> getTermsInAcademicYear() {
       if(true){//termsInAcademicYear==null){
           List<Term> currentTerms = this.getCurrentTerms();
           if(!currentTerms.isEmpty()){
               termsInAcademicYear = this.getTermsInAcademicYear(this.getYearTerm(currentTerms.get(0)));
           }else{
               termsInAcademicYear = new ArrayList<Term>();
           }

       }
        return termsInAcademicYear;
    }

    @Override
    public String getTermNameInAcadmicYear(int index){
        if(index>=getNumberOfTermsInAcademicYear())return "";
        Term temp = getTermsInAcademicYear().get(index);
        YearTerm yearTerm = getYearTerm(temp);
        return yearTerm.getTermName();
    }

	@Override
	public boolean isPlanning(String atpId) {
		try {
			QueryByCriteria query = QueryByCriteria.Builder
					.fromPredicates(like("atpStatus", PlanConstants.PLANNING));
			List<TermInfo> rl = KsapFrameworkServiceLocator
					.getAcademicCalendarService().searchForTerms(
							query,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			if (rl == null || rl.isEmpty())
				throw new IllegalStateException(
						"AcademicCalendarService did not return any planning terms");
			for (TermInfo t : rl)
				if (t.getId().equals(atpId))
					return true;
			return false;
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		}
	}

	@Override
	public boolean isCompleted(String atpId) {
		return getTerm(atpId).getEndDate().before(new Date());
	}

	@Override
	public boolean isCourseOffered(Term term, Course course) {
		try {
			List<CourseOfferingInfo> cos = KsapFrameworkServiceLocator
					.getCourseOfferingService()
					.getCourseOfferingsByCourseAndTerm(
							course.getId(),
							term.getId(),
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			return cos != null && !cos.isEmpty();
		} catch (DoesNotExistException e) {
			return false;
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CO lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CO lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CO lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CO lookup failure", e);
		}
	}

	@Override
	public List<Term> getPublishedTerms() {
		try {
			QueryByCriteria query = QueryByCriteria.Builder
					.fromPredicates(like("atpStatus", PlanConstants.PUBLISHED));
			List<TermInfo> rl = KsapFrameworkServiceLocator
					.getAcademicCalendarService().searchForTerms(
							query,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			if (rl == null || rl.isEmpty())
				throw new IllegalStateException(
						"AcademicCalendarService did not return any in-progress terms");
			return new java.util.ArrayList<Term>(rl);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		}
	}

	private static class TermByYearTermKey {
		private final YearTerm yearTerm;

		private TermByYearTermKey(YearTerm yearTerm) {
			this.yearTerm = yearTerm;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((yearTerm == null) ? 0 : yearTerm.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TermByYearTermKey other = (TermByYearTermKey) obj;
			if (yearTerm == null) {
				if (other.yearTerm != null)
					return false;
			} else if (!yearTerm.equals(other.yearTerm))
				return false;
			return true;
		}
	}

	@Override
	public Term getTerm(YearTerm yearTerm) {
		TermByYearTermKey k = new TermByYearTermKey(yearTerm);
		Term rv = (Term) TransactionSynchronizationManager.getResource(k);
		if (rv == null)
			try {
				Calendar c = new GregorianCalendar(yearTerm.getYear() - 1,
						Calendar.NOVEMBER, 1);
				Date d1 = c.getTime();
				c.add(Calendar.YEAR, 1);
				Date d2 = c.getTime();
				List<AtpInfo> atps = KsapFrameworkServiceLocator
						.getAtpService().getAtpsByStartDateRangeAndType(
								d1,
								d2,
								yearTerm.getTermType(),
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
				if (atps == null || atps.isEmpty())
					throw new IllegalArgumentException(
							"AtpService did not return any results for "
									+ yearTerm);
				for (Atp atp : atps)
					try {
						rv = KsapFrameworkServiceLocator
								.getAcademicCalendarService().getTerm(
										atp.getId(),
										KsapFrameworkServiceLocator
												.getContext().getContextInfo());
					} catch (DoesNotExistException e) {
						LOG.warn("ATP has term type, but is not a term " + atp,
								e);
					}
				if (rv == null)
					throw new IllegalArgumentException(
							"AtpService did not return any valid results for "
									+ yearTerm);
				else
					TransactionSynchronizationManager.bindResource(k, rv);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("Acal lookup failure", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("Acal lookup failure", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("Acal lookup failure", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("Acal lookup failure", e);
			}
		return rv;
	}

	@Override
	public YearTerm getYearTerm(Term term) {
		Calendar c = Calendar.getInstance();
		c.setTime(term.getStartDate());
		return new DefaultYearTerm(term.getTypeKey(), c.get(Calendar.YEAR));
	}



}
