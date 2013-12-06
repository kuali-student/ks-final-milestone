package org.kuali.student.ap.framework.context.support;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.lessThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.course.infc.Course;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
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

	private static final MarkerKey MARKER_KEY = new MarkerKey();
    private List<String> termTypes;
    private String[] defaultTerms = {"kuali.atp.type.Fall","kuali.atp.type.Winter",
            "kuali.atp.type.Spring","kuali.atp.type.Summer1"};
    private static final int NUMBER_OF_FUTRUE_TERMS = 4;

    /**
     * Find TermId by termName and that contains the specified begin/end dates
     * @param termBeginDate begin date
     * @param termEndDate end date
     * @param termName term name
     * @return termId
     */
    @Override
    public String findTermIdByNameAndContainingDates
            (Date termBeginDate, Date termEndDate, String termName) {
        String matchTermId = null;
        try {
            QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", PlanConstants.PUBLISHED),
                    or(getTermPredicates()), lessThanOrEqual("startDate",termBeginDate),greaterThanOrEqual("endDate",termEndDate),
                    equal("name",termName));
            List<TermInfo> terms = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(query,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
            for (TermInfo term : terms) {
                if (term.getName().equals(termName)) {
                    if (matchTermId != null)
                        throw new RuntimeException("multiple terms found with name=" + termName+" and start/end="+termBeginDate+"/"+termEndDate);
                    matchTermId = term.getId();
                }
            }
        } catch (Exception e) {
            String errMsg = "exception retrieving termId by dates/type for termName=" + termName +
                    " and start/end="+termBeginDate+"/"+termEndDate+"; exception: " + e.getMessage();
            throw new RuntimeException(errMsg, e);
        }
        return matchTermId;
    }

    private static class MarkerKey {
		@Override
		public int hashCode() {
			return MarkerKey.class.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof MarkerKey;
		}
	}

	private class TermMarker {
		private Map<String, Term> termMap = new java.util.HashMap<String, Term>();
		private Map<String, YearTerm> yearTermMap = new java.util.HashMap<String, YearTerm>();
		private Map<String, List<AcademicCalendarInfo>> acalMap = new java.util.HashMap<String, List<AcademicCalendarInfo>>();
		private Map<String, List<Term>> acalTermMap = new java.util.HashMap<String, List<Term>>();

		private void cache(Term t) {
			termMap.put(t.getId(), t);
			yearTermMap.put(t.getId(), getYearTerm(t));
		}

		private List<Term> cache(Collection<? extends Term> terms) {
			List<Term> rv = new java.util.ArrayList<Term>(terms.size());
			for (Term t : terms) {
				cache(t);
				rv.add(t);
			}
			Collections.sort(rv, new Comparator<Term>() {
				@Override
				public int compare(Term o1, Term o2) {
					return o1.getStartDate().compareTo(o2.getStartDate());
				}
			});
			return rv;
		}

		private void frontLoad(Date start, Date end) {
			ContextInfo ctx = KsapFrameworkServiceLocator.getContext().getContextInfo();
			AtpService atpService = KsapFrameworkServiceLocator.getAtpService();
			AcademicCalendarService academicCalendarService = KsapFrameworkServiceLocator.getAcademicCalendarService();
			try {
				List<AtpInfo> atps = atpService.getAtpsByDates(start, end, ctx);
				List<String> termIds = new java.util.ArrayList<String>(atps.size());
				List<String> acalIds = new java.util.ArrayList<String>(atps.size());
				for (AtpInfo atp : atps) {
					String atpType = atp.getTypeKey();
					if (AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY.equals(atpType))
						acalIds.add(atp.getId());
					else if (getTermTypes().contains(atpType))
						termIds.add(atp.getId());
				}
				List<AcademicCalendarInfo> acals = academicCalendarService.getAcademicCalendarsByIds(acalIds, ctx);
				for (Term t : cache(academicCalendarService.getTermsByIds(termIds, ctx))) {
					for (AcademicCalendarInfo acal : acals) {
						if (!t.getStartDate().before(acal.getStartDate()) && !t.getEndDate().after(acal.getEndDate())) {
							List<AcademicCalendarInfo> termAcals = acalMap.get(t.getId());
							if (termAcals == null) {
								acalMap.put(t.getId(), termAcals = new java.util.LinkedList<AcademicCalendarInfo>());
							}
							termAcals.add(acal);

							List<Term> acalTerms = acalTermMap.get(acal.getId());
							if (acalTerms == null) {
								acalTermMap.put(acal.getId(), acalTerms = new java.util.LinkedList<Term>());
							}
							acalTerms.add(t);
						}
					}
				}
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("ATP lookup failure", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("ATP lookup failure", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("ATP lookup failure", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("ATP lookup failure", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("ATP lookup failure", e);
			}
		}
	}

	private TermMarker getTermMarker() {
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			TermMarker rv = (TermMarker) TransactionSynchronizationManager.getResource(MARKER_KEY);
			if (rv == null) {
				TransactionSynchronizationManager.bindResource(MARKER_KEY, rv = new TermMarker());
				TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
					@Override
					public void afterCompletion(int status) {
						TransactionSynchronizationManager.unbindResourceIfPossible(MARKER_KEY);
					}
				});
			}
			return rv;
		} else {
			return new TermMarker();
		}
	}

	@Override
	public void frontLoadForPlanner(String firstAtpId) {
		Term firstTerm = firstAtpId == null ? getPlanningTerms().get(0) : getTerm(firstAtpId);
		Date start = firstTerm.getStartDate();
		Date end = null;
		for (Term pt : getPlanningTerms()) {
			if (end == null || end.before(pt.getEndDate()))
				end = pt.getEndDate();
		}
		getTermMarker().frontLoad(start, end);
	}

	@Override
	public Term getTerm(String atpId) {
		TermMarker tm = getTermMarker();
		Term rv = tm.termMap.get(atpId);
		if (rv == null) {
			try {
				tm.cache(rv = KsapFrameworkServiceLocator.getAcademicCalendarService().getTerm(atpId,
                        KsapFrameworkServiceLocator.getContext().getContextInfo()));
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
		return rv;
	}

	@Override
	public YearTerm getYearTerm(String atpId) {
		TermMarker tm = getTermMarker();
		YearTerm rv = tm.yearTermMap.get(atpId);
		if (rv == null) {
			rv = getYearTerm(getTerm(atpId));
		}
		return rv;
	}

	@Override
	public List<Term> getCurrentTerms() {
		try {
            QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", PlanConstants.PUBLISHED),
                    or(getTermPredicates()), lessThanOrEqual("startDate", new Date()),greaterThanOrEqual("endDate",new Date()));
			List<TermInfo> rv = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(query,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
			if (rv == null)
				rv = Collections.emptyList();
			return getTermMarker().cache(rv);
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
	public Term getOldestHistoricalTerm() {
		Term lst = getLastScheduledTerm();
		Calendar c = Calendar.getInstance();
		c.setTime(lst.getStartDate());
		return getTerm(new DefaultYearTerm(lst.getId(), lst.getTypeKey(), c.get(Calendar.YEAR) - 10));
	}

	@Override
	public List<Term> getTermsInAcademicYear(YearTerm yearTerm) {
		try {
			Term t = getTerm(yearTerm);
			TermMarker tm = getTermMarker();
			List<AcademicCalendarInfo> acl = tm.acalMap.get(t.getId());
			if (acl == null)
				tm.acalMap.put(
						t.getId(),
						acl = KsapFrameworkServiceLocator.getAcademicCalendarService().getAcademicCalendarsForTerm(
								t.getId(), KsapFrameworkServiceLocator.getContext().getContextInfo()));
			if (acl == null || acl.isEmpty())
				throw new IllegalStateException(
						"AcademicCalendarService did not return an academic calendar for year/term " + yearTerm);
			AcademicCalendarInfo ac = acl.get(0);
			List<Term> rl = tm.acalTermMap.get(ac.getId());
			try {
				tm.acalTermMap.put(
						ac.getId(),
						rl = getTermMarker().cache(
								KsapFrameworkServiceLocator.getAcademicCalendarService().getTermsForAcademicCalendar(
										ac.getId(), KsapFrameworkServiceLocator.getContext().getContextInfo())));
			} catch (DoesNotExistException e) {
				throw new IllegalStateException(
						"AcademicCalendarService did not return any terms for academic calendar " + ac.getId(), e);
			}
			if (rl == null || rl.isEmpty())
				throw new IllegalStateException(
						"AcademicCalendarService did not return any terms for academic calendar " + ac.getId());

            List<String> termTypeKeys = getTermTypes();
            List<Term> terms = new ArrayList<Term>();
            for(Term term : rl)
                if (termTypeKeys.contains(term.getTypeKey()))
                    terms.add(term);

			for (Term at : terms)
				tm.acalMap.put(at.getId(), acl);
			Collections.sort(terms, new Comparator<Term>() {
				@Override
				public int compare(Term term1, Term term2) {
					return KsapFrameworkServiceLocator.getTermHelper().getYearTerm(term1)
							.compareTo(KsapFrameworkServiceLocator.getTermHelper().getYearTerm(term2));
				}
			});

			return terms;
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
	public Term getFirstTermOfAcademicYear(YearTerm yearTerm) {
		Term rv = null;
		for (Term t : getTermsInAcademicYear(yearTerm))
			if (rv == null || t.getStartDate().before(rv.getStartDate()))
				rv = t;
		assert rv != null;
		return rv;
	}

	@Override
	public int getNumberOfTermsInAcademicYear(YearTerm yearTerm) {
		return getTermsInAcademicYear(yearTerm).size();
	}

	@Override
	public int getNumberOfTermsInAcademicYear() {
		return getNumberOfTermsInAcademicYear(getYearTerm(getPlanningTerms().get(0)));
	}

	@Override
	public List<Term> getTermsInAcademicYear() {
		return getTermsInAcademicYear(getYearTerm(getPlanningTerms().get(0)));
	}

	@Override
	public String getTermNameInAcadmicYear(int index) {
		if (index >= getNumberOfTermsInAcademicYear())
			return "";
		Term temp = getTermsInAcademicYear().get(index);
		YearTerm yearTerm = getYearTerm(temp);
		return yearTerm.getTermName();
	}

	@Override
	public List<Term> getPlanningTerms() {
		try {
            QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", PlanConstants.PUBLISHED),
                    or(getTermPredicates()), greaterThanOrEqual("endDate",new Date()));
			List<TermInfo> rl = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(query,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
			if (rl == null || rl.isEmpty())
				throw new IllegalStateException("AcademicCalendarService did not return any planning terms");
			return getTermMarker().cache(rl);
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
	public List<Term> getTermsByDateRange(Date startDate, Date endDate) {
		ContextInfo ctx = KsapFrameworkServiceLocator.getContext().getContextInfo();
		AtpService atpService = KsapFrameworkServiceLocator.getAtpService();
		AcademicCalendarService academicCalendarService = KsapFrameworkServiceLocator.getAcademicCalendarService();
		List<AtpInfo> atps;
		try {
			atps = atpService.getAtpsByDates(startDate, endDate, ctx);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("ATP lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("ATP lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("ATP lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("ATP lookup failure", e);
		}
		List<Term> terms = new ArrayList<Term>(atps.size());

		List<String> termTypeKeys = getTermTypes();
		for (AtpInfo atp : atps)
			if (termTypeKeys.contains(atp.getTypeKey()))
				terms.add(getTerm(atp.getId()));
		return terms;
	}

	@Override
	public boolean isPublished(String termId) {
		for (Term t : getPublishedTerms())
			if (t.getId().equals(termId))
				return true;
		return false;
	}

	@Override
	public boolean isPlanning(String atpId) {
		for (Term t : getPlanningTerms())
			if (t.getId().equals(atpId))
				return true;
		return false;
	}

	@Override
	public boolean isCompleted(String atpId) {
		return getTerm(atpId).getEndDate().before(new Date());
	}

	@Override
	public boolean isCourseOffered(Term term, Course course) {
		try {
			List<CourseOfferingInfo> cos = KsapFrameworkServiceLocator.getCourseOfferingService()
					.getCourseOfferingsByCourseAndTerm(course.getId(), term.getId(),
							KsapFrameworkServiceLocator.getContext().getContextInfo());
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
			QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", PlanConstants.PUBLISHED),or(getTermPredicates()));
			List<TermInfo> rl = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(query,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
			if (rl == null || rl.isEmpty())
				throw new IllegalStateException("AcademicCalendarService did not return any in-progress terms");
			return getTermMarker().cache(rl);
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

	public Term getTerm(YearTerm yearTerm) {
		return getTerm(yearTerm.getTermId());
	}

	@Override
	public YearTerm getYearTerm(Term term) {
		Calendar c = Calendar.getInstance();
		c.setTime(term.getStartDate());
		return new DefaultYearTerm(term.getId(), term.getTypeKey(), c.get(Calendar.YEAR));
	}

    private Predicate[] getTermPredicates(){
        Predicate predicates[] = new Predicate[getTermTypes().size()];
        for(int i=0;i<getTermTypes().size();i++){
            predicates[i]=equal("typeKey", getTermTypes().get(i));
        }
        return predicates;
    }

    private List<String> getTermTypes(){
        if(termTypes==null){
            termTypes = new ArrayList<String>();
            for(String term : defaultTerms){
                termTypes.add(term);
            }
        }
        return termTypes;
    }

    @Override
    public List<Term> getCalendarTerms(Term startTerm){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, NUMBER_OF_FUTRUE_TERMS);
        List<Term> calendarTerms = getTermsByDateRange(startTerm.getStartDate(),c.getTime());
        Collections.sort(calendarTerms, new Comparator<Term>() {
            @Override
            public int compare(Term o1, Term o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });
        Term start = calendarTerms.get(0);
        Term end = calendarTerms.get(calendarTerms.size()-1);
        List<Term> startYear = getTermsInAcademicYear(new DefaultYearTerm(start.getId(),start.getTypeKey(),start.getStartDate().getYear()));
        List<Term> endYear=getTermsInAcademicYear(new DefaultYearTerm(end.getId(),end.getTypeKey(),end.getStartDate().getYear()));
        Collections.sort(startYear, new Comparator<Term>() {
            @Override
            public int compare(Term o1, Term o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });
        Collections.sort(endYear, new Comparator<Term>() {
            @Override
            public int compare(Term o1, Term o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });
        for(Term t : startYear){
            if(t.getStartDate().compareTo(start.getStartDate())<0){
                calendarTerms.add(0,t);
            }
        }
        for(Term t : endYear){
            if(t.getStartDate().compareTo(end.getStartDate())>0){
                calendarTerms.add(t);
            }
        }
        return calendarTerms;
    }


}
