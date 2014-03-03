package org.kuali.student.ap.framework.context.support;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.ap.framework.util.KsapConstants;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.AcademicCalendar;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThan;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.lessThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

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

    private final static Logger LOG = Logger.getLogger(DefaultTermHelper.class);

    private static final MarkerKey MARKER_KEY = new MarkerKey();

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
        private List<Term> planningTerms;
        private List<Term> currentTerms;
        private List<Term> currentTermsBasedOnKeyDate;
        private List<Term> currentTermsWithPublishedSOC;
        private List<Term> futureTermsWithPublishedSOC;
        private List<Term> officialTerms;

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

            rv = sortTermsByStartDate(rv,true);
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
					else if (KsapHelperUtil.getTermTypes().contains(atpType))
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
        if (!TransactionSynchronizationManager.isSynchronizationActive()){
            TransactionSynchronizationManager.initSynchronization();
        }
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

        // Unused need to check how TransactionSynchronization activation should be handled.
		/*if (TransactionSynchronizationManager.isSynchronizationActive()) {
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
		}*/
	}

	@Override
	public void frontLoadForPlanner(String firstAtpId) {
		Term firstTerm = firstAtpId == null ? getFirstPlanningTerm() : getTerm(firstAtpId);
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
        if(getTermMarker().currentTerms == null){
            try {
                QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", PlanConstants.PUBLISHED),
                        or(KsapHelperUtil.getTermPredicates()), lessThanOrEqual("startDate", new Date()),greaterThanOrEqual("endDate",new Date()));
                List<TermInfo> rv = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(query,
                        KsapFrameworkServiceLocator.getContext().getContextInfo());
                if (rv == null)
                    rv = Collections.emptyList();
                getTermMarker().currentTerms = getTermMarker().cache(rv);
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
        return getTermMarker().currentTerms;
    }

	@Override
	public Term getLastScheduledTerm() {
		Term rv = null;
		for (Term t : getOfficialTerms())
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
            // Works on the assumption that there is only 1 official calendar for any term.
            AcademicCalendarInfo ac =null;
            for(AcademicCalendarInfo calendar : acl){
                if(calendar.getStateKey().equals(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY)){
                    ac=calendar;
                    break;
                }
            }
            if(ac==null){
                throw new IllegalStateException(
                        "AcademicCalendarService did not return an academic calendar for year/term " + yearTerm);
            }

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

            List<String> termTypeKeys = KsapHelperUtil.getTermTypes();
            List<Term> terms = new ArrayList<Term>();
            for(Term term : rl)
                if (termTypeKeys.contains(term.getTypeKey()))
                    terms.add(term);

			for (Term at : terms)
				tm.acalMap.put(at.getId(), acl);
			terms = sortTermsByStartDate(terms,true);

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
		return getNumberOfTermsInAcademicYear(getYearTerm(getFirstPlanningTerm()));
	}

	@Override
	public List<Term> getTermsInAcademicYear() {
		return getTermsInAcademicYear(getYearTerm(getFirstPlanningTerm()));
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
        if(getTermMarker().planningTerms == null){
            try {
                QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", PlanConstants.PUBLISHED),
                        or(KsapHelperUtil.getTermPredicates()), greaterThanOrEqual("endDate",new Date()));
                List<TermInfo> rl = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(query,
                        KsapFrameworkServiceLocator.getContext().getContextInfo());
                if (rl == null || rl.isEmpty())
                    throw new IllegalStateException("AcademicCalendarService did not return any planning terms");
                List<Term> temp = new ArrayList<Term>(rl);
                sortTermsByStartDate(temp,true);
                getTermMarker().planningTerms = getTermMarker().cache(temp);
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
        return getTermMarker().planningTerms;
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

		List<String> termTypeKeys = KsapHelperUtil.getTermTypes();
		for (AtpInfo atp : atps)
			if (termTypeKeys.contains(atp.getTypeKey()))
				terms.add(getTerm(atp.getId()));
		return terms;
	}

	@Override
	public boolean isOfficial(String termId) {
		for (Term t : getOfficialTerms())
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
	public List<Term> getOfficialTerms() {
        if(getTermMarker().officialTerms == null){
            try {
                QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", PlanConstants.PUBLISHED),or(KsapHelperUtil.getTermPredicates()));
                List<TermInfo> rl = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(query,
                        KsapFrameworkServiceLocator.getContext().getContextInfo());
                if (rl == null || rl.isEmpty())
                    throw new IllegalStateException("AcademicCalendarService did not return any in-progress terms");
                getTermMarker().officialTerms = getTermMarker().cache(rl);
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
        return getTermMarker().officialTerms;
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

    /**
     * Gets the current term based on the current date.
     * Uses Logic - If the start date < current time < the end date of an Academic Term
     *
     * @return Current Term
     */
    @Override
    public Term getCurrentTerm() {
        List<Term> currentTerms = getCurrentTerms();
        if(currentTerms!=null && currentTerms.size()>0){
            try{
                return KSCollectionUtils.getRequiredZeroElement(currentTerms);
            }catch (OperationFailedException e){
                throw new IllegalArgumentException("Acal lookup failure, no current term found");
            }
        }
        LOG.warn("No Current Term Found using first term of planning");
        try{
            return getFirstPlanningTerm();
        }catch (IllegalStateException e){
            throw new IllegalArgumentException("Acal lookup failure, no current term found");
        }

    }

    /**
     * Gets the current academic calendar based on the current date
     * Uses Logic - If the start date < current time < the end date of an Academic Calendar
     *
     * @return Current academic calendar
     */
    @Override
    public AcademicCalendar getCurrentAcademicCalendar() {
        try{
            QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", PlanConstants.PUBLISHED),
                    or(equal("typeKey", "kuali.atp.type.AcademicCalendar")), lessThanOrEqual("startDate", new Date()),greaterThanOrEqual("endDate",new Date()));
            List<AcademicCalendarInfo> rv = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForAcademicCalendars(query,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
            AcademicCalendarInfo acal =null;
            for(AcademicCalendarInfo calendar : rv){
                if(calendar.getStateKey().equals(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY)){
                    acal=calendar;
                    break;
                }
            }
            if(acal == null){
                throw new IllegalArgumentException("No current calendar found");
            }
            return acal;
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

    /**
     * Sort a list by its start date
     *
     * @param terms     - List of Terms to be sorted
     * @param ascending - If True sort ascending, else sort descending
     * @return - A list of sorted terms
     */
    @Override
    public List<Term> sortTermsByStartDate(List<Term> terms, boolean ascending) {
        if(ascending){
            Collections.sort(terms, new Comparator<Term>() {
                @Override
                public int compare(Term o1, Term o2) {
                    return o1.getStartDate().compareTo(o2.getStartDate());
                }
            });
        }else{
            Collections.sort(terms, new Comparator<Term>() {
                @Override
                public int compare(Term o1, Term o2) {
                    return o2.getStartDate().compareTo(o1.getStartDate());
                }
            });
        }
        return terms;
    }

    /**
     * Sort a list by its end date
     *
     * @param terms     - List of Terms to be sorted
     * @param ascending - If True sort ascending, else sort descending
     * @return - A list of sorted terms
     */
    @Override
    public List<Term> sortTermsByEndDate(List<Term> terms, boolean ascending) {
        if(ascending){
            Collections.sort(terms, new Comparator<Term>() {
                @Override
                public int compare(Term o1, Term o2) {
                    return o1.getEndDate().compareTo(o2.getEndDate());
                }
            });
        }else{
            Collections.sort(terms, new Comparator<Term>() {
                @Override
                public int compare(Term o1, Term o2) {
                    return o2.getEndDate().compareTo(o1.getEndDate());
                }
            });
        }
        return terms;
    }

    /**
     *  See https://wiki.kuali.org/display/STUDENT/Term+Analysis
     *  Current term:
     *       Academic Calendar: Term Start Date through Registration Key Date: the start date of Last Day to Add Classes
     *       If Last Day to Add Classes does not exist for the term, or the start date of Last Day to Add Classes is blank,
     *       use the Academic Calendar: Term End Date
     */
    @Override
    public List<Term> getCurrentTermsBasedOnKeyDate() {
        ContextInfo contextInfo = KsapFrameworkServiceLocator.getContext().getContextInfo();
        if(getTermMarker().currentTermsBasedOnKeyDate == null){
            try {
                List<Term> rv = getCurrentTerms();
                List<Term> currentTermsForCourseSearch = new ArrayList<Term>();
                for (Term term:rv){
                    KeyDateInfo lastDayToAddClasses = getLastDayToAddClassesForTerm(term.getId(), contextInfo);
                    if(lastDayToAddClasses == null || lastDayToAddClasses.getStartDate() == null){
                        currentTermsForCourseSearch.add(term);
                    }else{
                        if ( lastDayToAddClasses.getStartDate().before(new Date())){
                            continue;
                        }else{
                            currentTermsForCourseSearch.add(term);
                        }
                    }
                }
                getTermMarker().currentTermsBasedOnKeyDate = getTermMarker().cache(currentTermsForCourseSearch);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("Acal lookup failure", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("Acal lookup failure", e);
            } catch (OperationFailedException e) {
                throw new IllegalStateException("Acal lookup failure", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalStateException("Acal lookup failure", e);
            } catch (DoesNotExistException e){
                throw new IllegalStateException("Acal lookup failure", e);
            }

        }
        return getTermMarker().currentTermsBasedOnKeyDate;
    }

    /**
     * Get a list of the current Academic Terms and make sure the SOC state associated
     * with the term is published, and return the list of the terms.
     * @return - A list of current terms
     */
    @Override
    public List<Term> getCurrentTermsWithPublishedSOC (){
        if (getTermMarker().currentTermsWithPublishedSOC == null) {
            List<Term> currentTermsWithPublishedSOC = new ArrayList<Term>();
            List<Term> rv = getCurrentTermsBasedOnKeyDate();
            if (rv.size() > 0){
                currentTermsWithPublishedSOC = getTermsWithPublishedSOC(rv);
            }
            getTermMarker().currentTermsWithPublishedSOC = getTermMarker().cache(currentTermsWithPublishedSOC);
        }

        return  getTermMarker().currentTermsWithPublishedSOC;
    }

    /**
     * Get a list of the future Academic Terms and make sure the SOC state associated
     * with the term is published, and return the list of the terms.
     * @return - A list of future terms
     */
    @Override
    public List<Term> getFutureTermsWithPublishedSOC (){
        if (getTermMarker().futureTermsWithPublishedSOC == null) {
            List<Term> futureTerms = new ArrayList<Term>();
            try {
                QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", AtpServiceConstants.ATP_OFFICIAL_STATE_KEY),
                        or(KsapHelperUtil.getTermPredicates()), greaterThan("startDate", new Date()));
                List<TermInfo> rl = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(query,
                        KsapFrameworkServiceLocator.getContext().getContextInfo());
                if (rl == null || rl.isEmpty()) {
                    LOG.info("There is no official future terms.");
                    //throw new IllegalStateException("AcademicCalendarService did not return any in-progress terms");
                }
                else{
                    List<Term> terms = new ArrayList<Term>();
                    for (TermInfo term:rl){
                        terms.add(term);
                    }
                    futureTerms = getTermsWithPublishedSOC(terms);
                }
                getTermMarker().futureTermsWithPublishedSOC = getTermMarker().cache(futureTerms);
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
        return getTermMarker().futureTermsWithPublishedSOC;

    }

    private  List<Term> getTermsWithPublishedSOC(List<Term> inputTerms) {
        List<Term> returnTerms = new ArrayList<Term>();
        for (Term term : inputTerms){
            List<String> socIds;
            try {
                socIds = KsapFrameworkServiceLocator.getCourseOfferingSetService().getSocIdsByTerm(term.getId(), ContextUtils.createDefaultContextInfo());
            } catch (Exception e){
                if (LOG.isDebugEnabled()){
                    LOG.debug("Getting SOCs for the term " + term.getCode() + " results in service error");
                }
                continue;
            }

            if (socIds == null || socIds.isEmpty()){
                continue;
            }

            if (socIds.size() > 1){   //Handle multiple soc when it is implemented (Not for M5)
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, KsapConstants.MessageKeys.ERROR_MULTIPLE_SOCS);
                continue;
            }

            SocInfo socInfo;
            int firstId = 0;
            try {
                socInfo = KsapFrameworkServiceLocator.getCourseOfferingSetService().getSoc(socIds.get(firstId), ContextUtils.createDefaultContextInfo());
            } catch (Exception e){
                if (LOG.isDebugEnabled()){
                    LOG.debug("Error getting the soc [id=" + socIds.get(firstId) + "]");
                }
                continue;
            }

            if(socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY)  ){
                returnTerms.add(term);
            }
        }
        return returnTerms;

    }

    /*
    *  retrieve LastDayToAddClasses KeydateInfo for a sepcified term
    */
    private KeyDateInfo getLastDayToAddClassesForTerm(String termId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,PermissionDeniedException {
        KeyDateInfo  lastDayToAddClasses = null;
        // Find the registration/instructional milestone types.  These appear to, collectively,
        // constitute the keydate types.
        SearchRequestInfo searchRequest = new SearchRequestInfo("milestone.search.milestoneIdsByAtpId");
        searchRequest.addParam("milestone.queryParam.atpId", termId);
        //Specify the type key for "Last Day to Add Classes"
        List<String> keydateTypes = new ArrayList<String>();
        keydateTypes.add("kuali.atp.milestone.AddDropPeriod1");
        // Make query
        searchRequest.addParam("milestone.queryParam.milestoneTypes", keydateTypes);
        SearchResultInfo searchResult = KsapFrameworkServiceLocator.getAtpService().search(searchRequest, context);
        if(searchResult.getRows().size()== 0){
            //No lastDayToAddClasses has been defined for the specified term
            return lastDayToAddClasses;
        }
        List<String> keyDateIds = new ArrayList<String>();
        //Code Changed for JIRA-9075 - SONAR Critical issues - Use get(0) with caution - 5
        int firstSearchResultCellInfo = 0;
        // Extract out IDs.  Each row should have one cell with one key, so just grab the value (which is an ID)
        for (SearchResultRowInfo row: searchResult.getRows()) {
            List<SearchResultCellInfo> cells = row.getCells();
            String id = cells.get(firstSearchResultCellInfo).getValue(); // keydate ID
            keyDateIds.add(id);
        }
        int first = 0;
        if (keyDateIds.size()>=1){
            //normally the KS application prevents to create more than one instance for lastDayToAddClasses
            //if return more than one, we only take the first one
            lastDayToAddClasses= KsapFrameworkServiceLocator.getAcademicCalendarService().getKeyDate(keyDateIds.get(first), context);
        }
        return lastDayToAddClasses;
    }

    private Term getFirstPlanningTerm(){
        return getPlanningTerms().get(0);
    }

    @Override
    public String getFirstTermIdOfCurrentAcademicYear() {
        List<Term> terms = KsapFrameworkServiceLocator.getTermHelper().getTermsInAcademicYear();
        if(terms.size()>0){
            return terms.get(0).getId();
        }
        return "";
    }


}
