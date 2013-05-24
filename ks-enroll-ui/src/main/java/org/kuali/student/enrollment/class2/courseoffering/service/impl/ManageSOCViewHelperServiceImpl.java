/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 */

package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ManageSOCStatusHistory;
import org.kuali.student.enrollment.class2.courseoffering.form.ManageSOCForm;
import org.kuali.student.enrollment.class2.courseoffering.service.ManageSOCViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.class2.courseofferingset.service.impl.CourseOfferingSetPublishingHelper;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This is the view helper class which takes care of most of the functionalities to load data into the model. All the
 * calls to this service are from ManageSOCController.
 *
 * @author Kuali Student Team
 */
public class ManageSOCViewHelperServiceImpl extends KSViewHelperServiceImpl implements ManageSOCViewHelperService {
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = Logger.getLogger(ManageSOCViewHelperServiceImpl.class);

    private transient AcademicCalendarService acalService;
    private transient CourseOfferingSetService courseOfferingSetService;

    public TermInfo getTermByCode(String termCode) {

        try{
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

            qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));

            QueryByCriteria criteria = qbcBuilder.build();

            AcademicCalendarService acalService = getAcalService();
            List<TermInfo> terms = acalService.searchForTerms(criteria, createContextInfo());
            if (terms.size() > 1) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_MULTIPLE_TERMS);
                return null;
            }
            if (terms.isEmpty()) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_INVALID_TERM);
                return null;
            }
            return terms.get(0);
        }catch (Exception e){
            if (LOG.isDebugEnabled()){
                LOG.debug("Error getting term for the code - " + termCode);

            }
            convertServiceExceptionsToUI(e);
        }
        return null;
    }

    /**
     * Builds the model for the UI.
     *
     * 1. It gets the SOC for the user entered term and builds the state change history details (ManageSOCStatusHistory).
     * 2. Sort the state history entries  (<i>ManageSOCStatusHistory</i> implements <i>Comparable</i>)
     * 3. Iterate all the history entries and mark each one for UI highlight and grey text (UX requirement)
     * 4. Calculates the process duration for both scheduling and publishing
     *
     * @param socForm SOC form
     */
    public void buildModel(ManageSOCForm socForm){

        if (LOG.isInfoEnabled()){
            LOG.info("Building Manage SOC model for the term " + socForm.getTermCode());
        }

        List<String> socIds;

        try {
            socIds = getCourseOfferingSetService().getSocIdsByTerm(socForm.getTermInfo().getId(), createContextInfo());
        } catch (Exception e){
            if (LOG.isDebugEnabled()){
                LOG.debug("Getting SOCs for the term " + socForm.getTermCode() + " results in service error");
            }
            throw convertServiceExceptionsToUI(e);
        }

        if (socIds.isEmpty()){
            GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, ManageSocConstants.MessageKeys.ERROR_SOC_NOT_EXISTS);
            socForm.clear();
            return;
        }

        if (socIds.size() > 1){   //Handle multiple soc when it is implemented (Not for M5)
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_MULTIPLE_SOCS);
            return;
        }

        SocInfo socInfo;
        try {
            socInfo = getCourseOfferingSetService().getSoc(socIds.get(0), createContextInfo());
        } catch (Exception e){
            if (LOG.isDebugEnabled()){
                LOG.debug("Error getting the soc [id=" + socIds.get(0) + "]");
            }
            throw convertServiceExceptionsToUI(e);
        }

        socForm.setSocInfo(socInfo);
        socForm.setSocSchedulingStatus(getSocSchedulingStatus(socInfo));
        socForm.setSocPublishingStatus(getSocPublishingStatus(socInfo));
        String stateName = getStateInfo(socInfo.getStateKey()).getName();
        socForm.setSocStatus(stateName);

        buildStatusHistory(socForm);

        socForm.setScheduleInitiatedDate(formatScheduleDate(socInfo.getLastSchedulingRunStarted()));
        socForm.setScheduleCompleteDate(formatScheduleDate(socInfo.getLastSchedulingRunCompleted()));

        if(StringUtils.equals(socForm.getSocSchedulingStatus(), ManageSocConstants.SOC_IN_PROGRESS_PUBLISHING_STATUS_UI)) {
            socForm.setScheduleCompleteDate(ManageSocConstants.DISPLAY_SCHEDULE_IN_PROGRESS);
        }

        socForm.setPublishInitiatedDate(formatScheduleDate(socInfo.getPublishingStarted()));
        socForm.setPublishCompleteDate(formatScheduleDate(socInfo.getPublishingCompleted()));

        if(StringUtils.equals(socForm.getSocSchedulingStatus(), ManageSocConstants.SOC_IN_PROGRESS_PUBLISHING_STATUS_UI)) {
            Date curDate = new Date();

            if(socInfo.getLastSchedulingRunCompleted() != null)  {
                curDate = socInfo.getLastSchedulingRunCompleted();
            }

            socForm.setScheduleDuration(getTimeDiffUI(curDate, socInfo.getLastSchedulingRunStarted(), true) + ManageSocConstants.DISPLAY_IN_PROGRESS);
        }

        if (socInfo.getLastSchedulingRunCompleted() != null && socInfo.getLastSchedulingRunStarted() != null){
            socForm.setScheduleDuration(getTimeDiffUI(socInfo.getLastSchedulingRunCompleted(), socInfo.getLastSchedulingRunStarted(), true));
        }

        if(socInfo.getPublishingStarted() != null) {
            Date curDate = new Date();
            if (socInfo.getPublishingCompleted() != null && !StringUtils.equals(ManageSocConstants.DISPLAY_PUBLISHING_IN_PROGRESS, socForm.getPublishCompleteDate())) {
                socForm.setPublishDuration(getTimeDiffUI(socInfo.getPublishingCompleted(), socInfo.getPublishingStarted(), true));
            } else {
                socForm.setPublishDuration(getTimeDiffUI(curDate, socInfo.getPublishingStarted(), true)+ ManageSocConstants.DISPLAY_IN_PROGRESS);
                socForm.setPublishCompleteDate(ManageSocConstants.DISPLAY_PUBLISHING_IN_PROGRESS);
            }
        }
    }

    protected void buildStatusHistory(ManageSOCForm socForm){

        if (LOG.isInfoEnabled()){
            LOG.info("Building Status history model");
        }

        SocInfo socInfo = socForm.getSocInfo();
        String stateName;

        List<String> validSOCStates = Arrays.asList(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_STATE_KEYS);

        for (AttributeInfo info : socInfo.getAttributes()){
            if (validSOCStates.contains(info.getKey())){
                stateName = getStateInfo(info.getKey()).getName();

                Date date = null;
                String dateUI = info.getValue();
                if (StringUtils.isNotBlank(info.getValue())){
                    try{
                        date = DateFormatters.STATE_CHANGE_DATE_FORMATTER.parse(dateUI);
                        dateUI = formatScheduleDate(date);
                    }catch(IllegalArgumentException e){
                        throw new RuntimeException(e);
                    }
                }

                ManageSOCStatusHistory history = new ManageSOCStatusHistory(stateName,info.getKey(),dateUI,date);
                socForm.getStatusHistory().add(history);
            }

            if(socInfo.getPublishingStarted() != null) {
                Date curDate = new Date();
                if (socInfo.getPublishingCompleted() != null) {
                    socForm.setPublishDuration(getTimeDiffUI(socInfo.getPublishingCompleted(), socInfo.getPublishingStarted(), true));
                } else {
                    socForm.setPublishDuration(getTimeDiffUI(curDate, socInfo.getPublishingStarted(), true)+ "  (in progress)");
                }
            }
        }

        //Sort histories based on the date
        Collections.sort(socForm.getStatusHistory());

        //Add all the future(Not yet processed) states to the history for display purpose
        if (!socForm.getStatusHistory().isEmpty()){
            ManageSOCStatusHistory lastHistory = socForm.getStatusHistory().get(socForm.getStatusHistory().size()-1);
            int index = validSOCStates.indexOf(lastHistory.getStateKey());
            //Start from the next state to the end and add it to the history.
            if (index < validSOCStates.size()-1){
                for(int i = index+1;i<validSOCStates.size();i++){
                    if (StringUtils.equals(CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY,validSOCStates.get(i))){
                        stateName = "Closed"; //As we don't have this state in DB, hard code for now.
                    } else {
                        stateName = getStateInfo((validSOCStates.get(i))).getName();
                    }
                    ManageSOCStatusHistory nextState = new ManageSOCStatusHistory(stateName,null,null,null);
                    socForm.getStatusHistory().add(nextState);
                }
            }
        }

        highlightAndGreyTextHistories(socForm);
    }

    /**
     * Highlight and grey color History entries.
     *
     * @param socForm - SOC form
     */
    protected void highlightAndGreyTextHistories(ManageSOCForm socForm){
        //Highlight or grey text histories.
        for (int i=0;i<socForm.getStatusHistory().size();i++){
            ManageSOCStatusHistory history = socForm.getStatusHistory().get(i);
            // If it's last element or only one element present, highlight that component
            if (socForm.getStatusHistory().size()-1 == i){
                history.setHighlightUI(true);
            }else{
                ManageSOCStatusHistory nextHistory = socForm.getStatusHistory().get(i+1);
                if (nextHistory.getDateObject() == null){
                    history.setHighlightUI(true);
                    break;
                } else {
                    history.setGreyText(true);
                }
            }
        }
    }

    /**
     * This calculates the time difference for display. Generates a time difference string in the format hh:mm
     *
     *  @param dateOne end time
     *  @param dateTwo start time
     *  @param roundUpMinute whether or not to round up the time difference to the nearest minute
     *
     *  @return formatted date String (hh:mm)
     */
    protected String getTimeDiffUI(Date dateOne, Date dateTwo, boolean roundUpMinute) {

        if (LOG.isDebugEnabled()){
            LOG.debug("Get time difference between " + dateOne + " and " + dateTwo + " with roundUpMinute=" + roundUpMinute);
        }

        Long millisDuration = dateOne.getTime() - dateTwo.getTime();
        if (roundUpMinute) {
            // check the difference between the two dates as milliseconds, and round up to the nearest minute

            // if dividing by one minute results in a remainder, then round up to the next minute
            if(millisDuration % ManageSocConstants.ONE_MINUTE_IN_MILLIS != 0l) {
                Long difference = ManageSocConstants.ONE_MINUTE_IN_MILLIS - (millisDuration % ManageSocConstants.ONE_MINUTE_IN_MILLIS);
                millisDuration += difference;
            }
        }

        return DurationFormatUtils.formatDuration(millisDuration, ManageSocConstants.SCHEDULE_DURATION_TIME_FORMAT, true);
    }

    /**
     * This will lock the SOC state to LOCKED so that the departments cant make any changes to the CO/FO/AOs. Once we change the state, disable the
     * 'LOCK Set' button
     *
     * @param socForm SOC form
     */
    public void lockSOC(ManageSOCForm socForm){
        changeSOCState(socForm.getSocInfo(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, ManageSocConstants.MessageKeys.INFO_SOC_LOCKED);
    }

    /**
     * This method changes the SOC state to FINALEDIT so that the departments can make final edits to their COs
     *
     * @param socForm SOC form
     */
    public void allowSOCFinalEdit(ManageSOCForm socForm){
        changeSOCState(socForm.getSocInfo(), CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, ManageSocConstants.MessageKeys.INFO_FINAL_EDITS);
    }

    /**
     * This method changes the SOC state to PUBLISHING.
     * This will be processed by mass publishing process and it changes the state to PUBLISHED
     *
     * @param socForm SOC form
     */
    public void publishSOC(ManageSOCForm socForm) {

        if (LOG.isInfoEnabled()){
            LOG.info("Publishing SOC for the term - " + socForm.getTermCode());
        }

        ContextInfo contextInfo = createContextInfo();
//        CourseOfferingSetPublishingHelper mpeHelper = (CourseOfferingSetPublishingHelper)getHelper("org.kuali.student.enrollment.class2.courseofferingset.service.impl.CourseOfferingSetPublishingHelper");
        CourseOfferingSetPublishingHelper mpeHelper =  new CourseOfferingSetPublishingHelper();
        try {
            //  First state change the SOC to state "publishing"
            getCourseOfferingSetService().changeSocState(socForm.getSocInfo().getId(), CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY, contextInfo);
            //  Then kick off the runner.
            mpeHelper.startMassPublishingEvent(socForm.getSocInfo().getId(), new ArrayList<String>(), contextInfo);
        } catch (Exception e) {
            if (LOG.isDebugEnabled()){
                LOG.debug("Error publishing SOC - " + e.getMessage());
            }
            throw convertServiceExceptionsToUI(e);
        }

        reload(socForm, contextInfo);
        socForm.setSocPublishingStatus(getStateInfo((CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY)).getName());
    }

    /**
     * This method allows the PUBLISHED SOCs to be closed.
     *
     * @param socForm SOC form
     */
    public void closeSOC(ManageSOCForm socForm){
        changeSOCState(socForm.getSocInfo(), CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY, ManageSocConstants.MessageKeys.INFO_CLOSED);
    }

    /**
     * This method changes the state of SOC. This is called to change the SOC state to locked,finaledits,publishing and closed.
     *
     * @param socInfo SOCInfo
     * @param stateKey state to set the SOC to
     * @param message Success message to show in UI
     */
    public void changeSOCState(SocInfo socInfo,String stateKey,String message){

        if (LOG.isInfoEnabled()){
            LOG.info("Changing SOC state to " + stateKey);
        }

        try {
            StatusInfo status = getCourseOfferingSetService().changeSocState(socInfo.getId(), stateKey, createContextInfo());

            if (status.getIsSuccess()){
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, message);
                //Once state changed, disable the Lock button.
                socInfo.setStateKey(stateKey);
            }else{
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_INFO, ManageSocConstants.MessageKeys.ERROR_STATUS_CHANGE_FAILED,status.getMessage());
            }
        } catch (Exception e) {
            if (LOG.isDebugEnabled()){
                LOG.debug("Error Changing SOC State - " + e.getMessage());
            }
            throw convertServiceExceptionsToUI(e);
        }
    }

    protected String formatScheduleDate(Date date){
        if (date != null){
           return DateFormatters.DEFAULT_MONTH_YEAR_TIME_DATE_FORMATTER.format(date);
        }
        return StringUtils.EMPTY;
    }

    protected String getSocSchedulingStatus(SocInfo info) {

        if(StringUtils.isNotBlank(info.getSchedulingStateKey())) {
            return getStateInfo(info.getSchedulingStateKey()).getName();
        }

        return ManageSocConstants.NOT_STARTED_STATUS_UI;
    }

    public void startMassScheduling(ManageSOCForm socForm) {

        ContextInfo contextInfo = createContextInfo();

        if (LOG.isInfoEnabled()){
            LOG.info("Mass scheduling method called.");
        }

        try {
            //  First state change the SOC to state "inprogress".
            getCourseOfferingSetService().changeSocState(socForm.getSocInfo().getId(), CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS, contextInfo);

            // Then kick off the mass scheduling event.
            List<String> optionKeys = new ArrayList<String>();
            StatusInfo status = getCourseOfferingSetService().startScheduleSoc(socForm.getSocInfo().getId(), optionKeys, contextInfo);

            if (status.getIsSuccess()){
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, ManageSocConstants.MessageKeys.INFO_SEND_TO_SCHEDULER);
            } else {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_INFO, ManageSocConstants.MessageKeys.ERROR_SEND_TO_SCHEDULER,status.getMessage());
            }
        } catch (Exception e) {
            if (LOG.isDebugEnabled()){
                LOG.debug("Error starting Mass Scheduler - " + e.getMessage());
            }
            throw convertServiceExceptionsToUI(e);
        }

        reload(socForm, contextInfo);
        socForm.setSocSchedulingStatus(getStateInfo(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS).getName());
    }

    protected String getSocPublishingStatus(SocInfo info) {

        if (StringUtils.isNotBlank(info.getStateKey())) {
            if (StringUtils.equals(info.getStateKey(), CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY)) {
                return getStateInfo(CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY).getName();
            } else if (StringUtils.equals(info.getStateKey(), CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY) ||
                    StringUtils.equals(info.getStateKey(), CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY)) {
                return getStateInfo(info.getStateKey()).getName();
            }
        }
        return ManageSocConstants.NOT_STARTED_STATUS_UI;
    }

    private void reload(ManageSOCForm socForm, ContextInfo contextInfo)  {

        if (LOG.isDebugEnabled()){
            LOG.debug("Reloading the form");
        }

        SocInfo socInfo;

        try {
            socInfo = getCourseOfferingSetService().getSoc(socForm.getSocInfo().getId(), contextInfo);
        } catch (Exception e) {
            if (LOG.isDebugEnabled()){
                LOG.debug("Error getting SOC - " + e.getMessage());
            }
            throw convertServiceExceptionsToUI(e);
        }

        socForm.setSocInfo(socInfo);
        socForm.setSocSchedulingStatus(getSocSchedulingStatus(socInfo));
        socForm.setSocPublishingStatus(getSocPublishingStatus(socInfo));
        String stateName = getStateInfo(socInfo.getStateKey()).getName();
        socForm.setSocStatus(stateName);

    }

    protected AcademicCalendarService getAcalService() {
        if (acalService == null){
            acalService = CourseOfferingResourceLoader.loadAcademicCalendarService();
        }
        return acalService;
    }

    protected CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
            courseOfferingSetService = CourseOfferingResourceLoader.loadCourseOfferingSetService();
        }
        return courseOfferingSetService;
    }


}
