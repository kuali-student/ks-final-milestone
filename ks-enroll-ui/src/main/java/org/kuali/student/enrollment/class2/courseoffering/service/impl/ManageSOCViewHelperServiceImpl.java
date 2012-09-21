package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ManageSOCStatusHistory;
import org.kuali.student.enrollment.class2.courseoffering.form.ManageSOCForm;
import org.kuali.student.enrollment.class2.courseoffering.service.ManageSOCViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManageSOCViewHelperServiceImpl extends ViewHelperServiceImpl implements ManageSOCViewHelperService {

    private transient AcademicCalendarService acalService;
    private transient CourseOfferingSetService courseOfferingSetService;
    private transient StateService stateService;

    public List<TermInfo> getTermByCode(String termCode) throws Exception {

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));

        QueryByCriteria criteria = qbcBuilder.build();

        AcademicCalendarService acalService = getAcalService();
        List<TermInfo> terms = acalService.searchForTerms(criteria, new ContextInfo());
        return terms;
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

        try {
            List<String> socIds = getCourseOfferingSetService().getSocIdsByTerm(socForm.getTermInfo().getId(), ContextUtils.createDefaultContextInfo());

            if (socIds.isEmpty()){
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM,"SOC doesnt exists for this term");
                socForm.setTermInfo(null);
                return;
            }

            if (socIds.size() > 1){   //Handle multiple soc when it is implemented (Not for M5)
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Should not have multiple SOCs for a term (Not yet implemented departmental soc)");
            }

            SocInfo socInfo = getCourseOfferingSetService().getSoc(socIds.get(0), ContextUtils.createDefaultContextInfo());
            socForm.setSocInfo(socInfo);
            socForm.setSocSchedulingStatus(getSocSchedulingStatus(socInfo));
            String stateName = getStateName(socInfo.getStateKey());
            socForm.setSocStatus(stateName);

            List<String> validSOCStates = Arrays.asList(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_STATE_KEYS);
            DateFormat dateFormat = new SimpleDateFormat(CourseOfferingSetServiceConstants.STATE_CHANGE_DATE_FORMAT);

            for (AttributeInfo info : socInfo.getAttributes()){
                if (validSOCStates.contains(socInfo.getStateKey())){
                    stateName = getStateName(info.getKey());

                    Date date = null;
                    String dateUI = info.getValue();
                    if (StringUtils.isNotBlank(info.getValue())){
                        try{
                            date = dateFormat.parse(info.getValue());
                            dateUI = formatScheduleDate(date);
                        }catch(ParseException e){
                            //shallow for now
                        }
                    }

                    ManageSOCStatusHistory history = new ManageSOCStatusHistory(stateName,dateUI,date);
                    socForm.getStatusHistory().add(history);
                }
            }

            //Sort histories based on the date
            Collections.sort(socForm.getStatusHistory());

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

            socForm.setScheduleInitiatedDate(formatScheduleDate(socInfo.getLastSchedulingRunStarted()));
            socForm.setScheduleCompleteDate(formatScheduleDate(socInfo.getLastSchedulingRunCompleted()));

            socForm.setPublishInitiatedDate(formatScheduleDate(socInfo.getPublishingStarted()));
            socForm.setPublishCompleteDate(formatScheduleDate(socInfo.getPublishingCompleted()));

            if (socInfo.getLastSchedulingRunCompleted() != null && socInfo.getLastSchedulingRunStarted() != null){
                socForm.setScheduleDuration(getTimeDiff(socInfo.getLastSchedulingRunCompleted(),socInfo.getLastSchedulingRunStarted()));
            }

            if (socInfo.getPublishingCompleted() != null && socInfo.getPublishingStarted() != null){
                socForm.setPublishDuration(getTimeDiff(socInfo.getPublishingCompleted(),socInfo.getPublishingStarted()));
            }

        } catch (DoesNotExistException e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,e.getMessage());
        } catch (PermissionDeniedException e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * This calculates the time difference for display. Generates a time difference string in the format hh:mm
     *
     *  @param dateOne
     * @param dateTwo
     * @return formatted date String (hh:mm)
     */
    public String getTimeDiff(Date dateOne, Date dateTwo) {
        return DurationFormatUtils.formatDuration(dateOne.getTime() - dateTwo.getTime(),"HH:mm",true);
    }

    /**
     * This will lock the SOC state to LOCKED so that the departments cant make any changes to the CO/FO/AOs. Once we change the state, disable the
     * 'LOCK Set' button
     *
     * @param socForm SOC form
     */
    public void lockSOC(ManageSOCForm socForm){
        changeSOCState(socForm.getSocInfo(),CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY,"Set of Courses has been Locked");
    }

    /**
     * This method changes the SOC state to FINALEDIT so that the departments can make final edits to their COs
     *
     * @param socForm SOC form
     */
    public void allowSOCFinalEdit(ManageSOCForm socForm){
        changeSOCState(socForm.getSocInfo(),CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY,"Set of Courses has been opened for Final Edits.");
    }

    /**
     * This method changes the SOC state to PUBLISHING. This will be processed by mass publishing process and it changes the state to PUBLISHED
     *
     * @param socForm SOC form
     */
    public void publishSOC(ManageSOCForm socForm){
        changeSOCState(socForm.getSocInfo(),CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY,"Set of Courses has been Published.");
    }

    /**
     * This method allows the PUBLISHED SOCs to be closed.
     *
     * @param socForm SOC form
     */
    public void closeSOC(ManageSOCForm socForm){
        changeSOCState(socForm.getSocInfo(),CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY,"Set of Courses has been closed.");
    }

    /**
     * This method changes the state of SOC. This is called to change the SOC state to locked,finaledits,publishing and closed.
     *
     * @param socInfo SOCInfo
     * @param stateKey
     */
    public void changeSOCState(SocInfo socInfo,String stateKey,String message){

        try{
            StatusInfo status = getCourseOfferingSetService().updateSocState(socInfo.getId(), stateKey, ContextUtils.createDefaultContextInfo());

            if (status.getIsSuccess()){
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, message);
                //Once state changed, disable the Lock button.
                socInfo.setStateKey(stateKey);
            }else{
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, "SOC status change fails - " + status.getMessage());
            }
        } catch (DoesNotExistException e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected String formatScheduleDate(Date date){
        if (date != null){
           DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
           return dateFormat.format(date);
        }
        return StringUtils.EMPTY;
    }

    protected String getStateName(String stateKey) {

        try{
           return getStateService().getState(stateKey,ContextUtils.createDefaultContextInfo()).getName();
        } catch (Exception e){
           throw new RuntimeException(e);
        }

    }

    protected String getSocSchedulingStatus(SocInfo info) {
        if(info.getSchedulingStateKey() != null) {
                 try{
                    return  getStateService().getState(info.getSchedulingStateKey(), ContextUtils.createDefaultContextInfo()).getName();
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
        }

        return "Not Started";
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

    public StateService getStateService() {
        if (stateService == null){
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return stateService;
    }

    public void startMassScheduling(ManageSOCForm socForm) {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        try {
            List<String> optionKeys = new ArrayList<String>();

            StatusInfo status = getCourseOfferingSetService().startScheduleSoc(socForm.getSocInfo().getId(), optionKeys, contextInfo);

            if (status.getIsSuccess()){
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM,  "Approved activities were successfully sent to Scheduler.");
                SocInfo socInfo = getCourseOfferingSetService().getSoc(socForm.getSocInfo().getId(), contextInfo);
                socForm.setSocInfo(socInfo);
                socForm.setSocSchedulingStatus(getSocSchedulingStatus(socInfo));
                String stateName = getStateName(socInfo.getStateKey());
                socForm.setSocStatus(stateName);
            } else {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, "Error locking SOC");
            }
        } catch (DoesNotExistException e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
