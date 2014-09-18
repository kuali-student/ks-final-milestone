/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.ap.framework.context.support;

import org.kuali.rice.core.api.config.property.ConfigContext;
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
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
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
import org.kuali.student.r2.core.constants.AtpSearchServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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

    private static final Logger LOG = LoggerFactory.getLogger(DefaultTermHelper.class);

	@Override
	public void frontLoadForPlanner(String firstAtpId) {

	}

    @Override
	public Term getTerm(String atpId) {
        Term term;
        try {
            term = KsapFrameworkServiceLocator.getAcademicCalendarService().getTerm(atpId,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
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
		return term;
	}

	@Override
	public YearTerm getYearTerm(String atpId) {
		return getYearTerm(getTerm(atpId));
	}

    @Override
    public List<Term> getCurrentTerms() {
        List<Term> currentTerms = new ArrayList<Term>();
        try {
            QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", PlanConstants.PUBLISHED),
                    or(KsapHelperUtil.getTermPredicates()), lessThanOrEqual("startDate", KsapHelperUtil.getCurrentDate()),greaterThanOrEqual("endDate",KsapHelperUtil.getCurrentDate()));
            List<TermInfo> rv = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(query,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
            if (rv == null)
                rv = Collections.emptyList();
            for(Term term : rv){
                currentTerms.add(term);
            }
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Acal lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("Acal lookup failure", e);
        }
        return currentTerms;
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
			List<AcademicCalendarInfo> acl = KsapFrameworkServiceLocator.getAcademicCalendarService().getAcademicCalendarsForTerm(
                    t.getId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
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

			List<Term> rl = new ArrayList<Term>();
			try {
                List<TermInfo> temp = KsapFrameworkServiceLocator.getAcademicCalendarService().getTermsForAcademicCalendar(
                                ac.getId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
                for(Term term : temp){
                    rl.add(term);
                }
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
        List<Term> planningTerms = new ArrayList<Term>();
        List<Term> officialTerms = KsapFrameworkServiceLocator.getTermHelper().getOfficialTerms();
        for(Term term : officialTerms){
            if(KsapFrameworkServiceLocator.getTermHelper().isPlanning(term.getId())){
                planningTerms.add(term);
            }
        }
        sortTermsByStartDate(planningTerms, true);
        return planningTerms;
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

    /**
     * @see org.kuali.student.ap.framework.context.TermHelper#isPlanning(String)
     */
	@Override
	public boolean isPlanning(String termId) {
        Date now = KsapHelperUtil.getCurrentDate();
        Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);
        if(now.before(term.getStartDate())){
            return true;
        }
        if(now.after(term.getEndDate())){
            return false;
        }
        if(isRegistrationOpen(termId)){
            return true;
        }
		return false;
	}

    /**
     * @see org.kuali.student.ap.framework.context.TermHelper#isCompleted(String)
     */
	@Override
	public boolean isCompleted(String termId) {
        Date now = KsapHelperUtil.getCurrentDate();
        Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);
        boolean complete = term.getEndDate().before(now);
		return complete;
	}

    /**
     * @see org.kuali.student.ap.framework.context.TermHelper#isInProgress(String)
     */
    @Override
    public boolean isInProgress(String termId) {
        Date now = KsapHelperUtil.getCurrentDate();
        Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);
        boolean inProgress = !now.before(term.getStartDate()) && !now.after(term.getEndDate());
        return inProgress;
    }

    /**
     * @see org.kuali.student.ap.framework.context.TermHelper#isFutureTerm(String)
     */
    @Override
    public boolean isFutureTerm(String termId) {
        Date now = KsapHelperUtil.getCurrentDate();
        Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);
        boolean futureTerm = now.before(term.getStartDate());
        return futureTerm;
    }

    /**
     * @see org.kuali.student.ap.framework.context.TermHelper#isCurrentTerm(String)
     */
    @Override
    public boolean isCurrentTerm(String termId) {
        Term current = KsapFrameworkServiceLocator.getTermHelper().getCurrentTerm();
        if(current.getId().equals(termId)){
            return true;
        }
        return false;
    }

    /**
     * Registration is considered open so long as the schedule adjustment period has not ended.
     *
     * @see org.kuali.student.ap.framework.context.TermHelper#isRegistrationOpen(String)
     */
    @Override
    public boolean isRegistrationOpen(String termId) {
        Date now = KsapHelperUtil.getCurrentDate();
        KeyDateInfo endOfScheduleAdjustment = null;
        try {
            endOfScheduleAdjustment = getLastDayToAddClassesForTerm(termId, KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        }
        boolean isRegistrationOpen = false;
        if(endOfScheduleAdjustment!=null){
            isRegistrationOpen = !endOfScheduleAdjustment.getEndDate().before(now);
        }
        return isRegistrationOpen;
    }


    @Override
	public List<Term> getOfficialTerms() {
        List<Term> officialTerms = new ArrayList<Term>();
        try {
            QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", PlanConstants.PUBLISHED),or(KsapHelperUtil.getTermPredicates()));
            List<TermInfo> temp = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(query,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
            if (temp == null || temp.isEmpty())
                throw new IllegalStateException("AcademicCalendarService did not return any in-progress terms");
            for(TermInfo t : temp){
                officialTerms.add(t);
            }
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Acal lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("Acal lookup failure", e);
        }
        return officialTerms;
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
                    or(equal("typeKey", AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY)), lessThanOrEqual("startDate", KsapHelperUtil.getCurrentDate()),greaterThanOrEqual("endDate",KsapHelperUtil.getCurrentDate()));
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
     * Sort a list by the date the Schedule of Classes was released
     *
     * @param terms     - List of Terms to be sorted
     * @param ascending - If True sort ascending, else sort descending
     * @return - A list of sorted terms
     */
    @Override
    public List<Term> sortTermsBySocReleaseDate(List<Term> terms, boolean ascending) {
        Map<String, SocInfo> socsByTerm = new HashMap<String, SocInfo>(terms.size());

        for (Term term : terms) {
            SocInfo socInfo = getPublishedSocForTerm(term.getId());
            socsByTerm.put(term.getId(), socInfo);
        }

        Collections.sort(terms, new TermSocComparator(socsByTerm, ascending));
        return terms;
    }

    /**
     * Comparator that uses a map containing SocInfo objects mapped by termId
     */
    private class TermSocComparator implements Comparator<Term> {
        private Map<String, SocInfo> socsByTerm;
        private boolean ascending;

        public TermSocComparator(final Map<String, SocInfo> socsByTerm, boolean ascending) {
            this.socsByTerm = socsByTerm;
            this.ascending = ascending;
        }
        @Override
        public int compare(Term o1, Term o2) {
            SocInfo soc1 = socsByTerm.get(o1.getId());
            SocInfo soc2 = socsByTerm.get(o2.getId());
            if (ascending)
                return soc1.getPublishingCompleted().compareTo(soc2.getPublishingCompleted());
            else
                return soc2.getPublishingCompleted().compareTo(soc1.getPublishingCompleted());
        }
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
        List<Term> currentTermsForCourseSearch = new ArrayList<Term>();
        List<Term> rv = getCurrentTerms();
        for (Term term:rv){
            if(KsapFrameworkServiceLocator.getTermHelper().isRegistrationOpen(term.getId())){
                currentTermsForCourseSearch.add(term);
            }
        }

        return currentTermsForCourseSearch;
    }

    /**
     * Get a list of the current Academic Terms and make sure the SOC state associated
     * with the term is published, and return the list of the terms.
     * @return - A list of current terms
     */
    @Override
    public List<Term> getCurrentTermsWithPublishedSOC (){
        List<Term> currentTermsWithPublishedSOC = new ArrayList<Term>();
        List<Term> rv = getCurrentTermsBasedOnKeyDate();
        if (rv.size() > 0){
            currentTermsWithPublishedSOC = getTermsWithPublishedSOC(rv);
        }
        return  currentTermsWithPublishedSOC;
    }

    /**
     * Get a list of the future Academic Terms and make sure the SOC state associated
     * with the term is published, and return the list of the terms.
     * @return - A list of future terms
     */
    @Override
    public List<Term> getFutureTermsWithPublishedSOC (){
        List<Term> futureTerms = new ArrayList<Term>();
        try {
            QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(equal("atpStatus", AtpServiceConstants.ATP_OFFICIAL_STATE_KEY),
                    or(KsapHelperUtil.getTermPredicates()), greaterThan("startDate", KsapHelperUtil.getCurrentDate()));
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
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Acal lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Acal lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("Acal lookup failure", e);
        }
        return futureTerms;
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
                    LOG.debug("Error getting the soc [id={}]", socIds.get(firstId));
                }
                continue;
            }

            if(socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY)  ){
                returnTerms.add(term);
            }
        }
        return returnTerms;

    }

    private SocInfo getPublishedSocForTerm(String termId) {
        List<String> socIds = null;
        try {
            socIds = KsapFrameworkServiceLocator.getCourseOfferingSetService().getSocIdsByTerm(termId, ContextUtils.createDefaultContextInfo());
        } catch (Exception e){
            if (LOG.isDebugEnabled()){
                LOG.debug("Getting SOCs for the term " + termId + " results in service error");
            }
        }

        SocInfo socInfo = null;
        if (socIds != null && socIds.size() == 1) {
            String socId = null;
            try {
                socId = KSCollectionUtils.getRequiredZeroElement(socIds);
            } catch (OperationFailedException e) {
                if (LOG.isDebugEnabled()){
                    LOG.debug("Getting SOCs for the term " + termId + " results in service error");
                }
            }
            if (socId != null) {
                try {
                    socInfo = KsapFrameworkServiceLocator.getCourseOfferingSetService().getSoc(socId, ContextUtils.createDefaultContextInfo());
                } catch (Exception e){
                    if (LOG.isDebugEnabled()){
                        LOG.debug("Error getting the soc [id={}]", socId);
                    }
                }
            }
        }
        return socInfo;
    }

    /*
    *  retrieve LastDayToAddClasses KeydateInfo for a sepcified term
    */
    private KeyDateInfo getLastDayToAddClassesForTerm(String termId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,PermissionDeniedException {
        KeyDateInfo  lastDayToAddClasses = null;
        // Find the registration/instructional milestone types.  These appear to, collectively,
        // constitute the keydate types.
        SearchRequestInfo searchRequest = new SearchRequestInfo(
                AtpSearchServiceConstants.ATP_SEARCH_MILESTONE_IDS_BY_ATP_ID);
        searchRequest.addParam(AtpSearchServiceConstants.ATP_QUERYPARAM_MILESTONE_ATP_ID, termId);
        //Specify the type key for "Last Day to Add Classes"
        List<String> keydateTypes = new ArrayList<String>();
        keydateTypes.add(AtpServiceConstants.MILESTONE_SCHEDULE_ADJUSTMENT_PERIOD_TYPE_KEY);
        // Make query
        searchRequest.addParam(AtpSearchServiceConstants.ATP_QUERYPARAM_MILESTONE_TYPES, keydateTypes);
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
