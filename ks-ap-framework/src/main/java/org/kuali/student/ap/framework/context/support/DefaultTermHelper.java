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
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.common.util.KSCollectionUtils;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.AcademicCalendar;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
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
                getTermMarker().planningTerms = getTermMarker().cache(rl);
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
        throw new IllegalArgumentException("Acal lookup failure, no current term found");
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

            return KSCollectionUtils.getRequiredZeroElement(rv);
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

    private Term getFirstPlanningTerm(){
        return getPlanningTerms().get(0);
    }


}
