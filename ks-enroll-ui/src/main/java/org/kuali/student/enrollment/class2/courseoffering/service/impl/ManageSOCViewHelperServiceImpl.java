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
import org.kuali.student.enrollment.class2.courseofferingset.service.impl.CourseOfferingSetPublishingHelper;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;

public class ManageSOCViewHelperServiceImpl extends ViewHelperServiceImpl implements ManageSOCViewHelperService {
    final static Logger LOG = Logger.getLogger(ManageSOCViewHelperServiceImpl.class);

    private transient AcademicCalendarService acalService;

    private transient CourseOfferingSetService courseOfferingSetService;
    private transient StateService stateService;

    //  Storage for SOC state descriptions.
    private static Map<String, String> socStateDescriptions = new HashMap<String, String>();

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
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, "SOC does not exist for this term");
                socForm.setTermInfo(null);
                return;
            }

            if (socIds.size() > 1){   //Handle multiple soc when it is implemented (Not for M5)
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Should not have multiple SOCs for a term (Not yet implemented departmental soc)");
            }

            SocInfo socInfo = getCourseOfferingSetService().getSoc(socIds.get(0), ContextUtils.createDefaultContextInfo());
            socForm.setSocInfo(socInfo);
            socForm.setSocSchedulingStatus(getSocSchedulingStatus(socInfo));
            socForm.setSocPublishingStatus(getSocPublishingStatus(socInfo));
            String stateName = socForm.getSocStateKeys2Names().get(socInfo.getStateKey());
            socForm.setSocStatus(stateName);

            List<String> validSOCStates = Arrays.asList(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_STATE_KEYS);
            DateFormat dateFormat = new SimpleDateFormat(CourseOfferingSetServiceConstants.STATE_CHANGE_DATE_FORMAT);

            for (AttributeInfo info : socInfo.getAttributes()){
                if (validSOCStates.contains(info.getKey())){
                    stateName = socForm.getSocStateKeys2Names().get(info.getKey());

                    Date date = null;
                    String dateUI = info.getValue();
                    if (StringUtils.isNotBlank(info.getValue())){
                        try{
                            date = dateFormat.parse(info.getValue());
                            dateUI = formatScheduleDate(date);
                        }catch(ParseException e){
                            throw new RuntimeException(e);
                        }
                    }

                    ManageSOCStatusHistory history = new ManageSOCStatusHistory(stateName,info.getKey(),dateUI,date);
                    socForm.getStatusHistory().add(history);
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
                            stateName = socForm.getSocStateKeys2Names().get(validSOCStates.get(i));
                        }
                        ManageSOCStatusHistory nextState = new ManageSOCStatusHistory(stateName,null,null,null);
                        socForm.getStatusHistory().add(nextState);
                    }
                }
            }

            highlightAndGreyTextHistories(socForm);

            socForm.setScheduleInitiatedDate(formatScheduleDate(socInfo.getLastSchedulingRunStarted()));
            socForm.setScheduleCompleteDate(formatScheduleDate(socInfo.getLastSchedulingRunCompleted()));

            socForm.setPublishInitiatedDate(formatScheduleDate(socInfo.getPublishingStarted()));
            socForm.setPublishCompleteDate(formatScheduleDate(socInfo.getPublishingCompleted()));

            if (socInfo.getLastSchedulingRunCompleted() != null && socInfo.getLastSchedulingRunStarted() != null){
                socForm.setScheduleDuration(getTimeDiffUI(socInfo.getLastSchedulingRunCompleted(), socInfo.getLastSchedulingRunStarted()));
            }

            if (socInfo.getPublishingCompleted() != null && socInfo.getPublishingStarted() != null){
                socForm.setPublishDuration(getTimeDiffUI(socInfo.getPublishingCompleted(), socInfo.getPublishingStarted()));
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
     * Highlight and grey color History entries.
     *
     * @param socForm
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
     *  @param dateOne
     * @param dateTwo
     * @return formatted date String (hh:mm)
     */
    protected String getTimeDiffUI(Date dateOne, Date dateTwo) {
        return DurationFormatUtils.formatDuration(dateOne.getTime() - dateTwo.getTime(),"HH:mm",true);
    }

    /**
     * This will lock the SOC state to LOCKED so that the departments cant make any changes to the CO/FO/AOs. Once we change the state, disable the
     * 'LOCK Set' button
     *
     * @param socForm SOC form
     */
    public void lockSOC(ManageSOCForm socForm){
        changeSOCState(socForm.getSocInfo(),CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, "Set of Courses has been Locked");
    }

    /**
     * This method changes the SOC state to FINALEDIT so that the departments can make final edits to their COs
     *
     * @param socForm SOC form
     */
    public void allowSOCFinalEdit(ManageSOCForm socForm){
        changeSOCState(socForm.getSocInfo(),CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, "Set of Courses has been opened for Final Edits.");
    }

    /**
     * This method changes the SOC state to PUBLISHING.
     * This will be processed by mass publishing process and it changes the state to PUBLISHED
     *
     * @param socForm SOC form
     */
    public void publishSOC(ManageSOCForm socForm) {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        CourseOfferingSetPublishingHelper mpeHelper = new CourseOfferingSetPublishingHelper();
        try {
            //  First state change the SOC to state "publishing"
            getCourseOfferingSetService().updateSocState(socForm.getSocInfo().getId(), CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY, contextInfo);
            //  Then kick off the runner.
            mpeHelper.startMassPublishingEvent(socForm.getSocInfo().getId(), new ArrayList<String>(), contextInfo);
            reload(socForm, contextInfo);
            socForm.setSocPublishingStatus(getSocStateDescription(CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY));
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }
    }

    /**
     * This method allows the PUBLISHED SOCs to be closed.
     *
     * @param socForm SOC form
     */
    public void closeSOC(ManageSOCForm socForm){
        changeSOCState(socForm.getSocInfo(),CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY, "Set of Courses has been closed.");
    }

    /**
     * This method changes the state of SOC. This is called to change the SOC state to locked,finaledits,publishing and closed.
     *
     * @param socInfo SOCInfo
     * @param stateKey
     */
    public void changeSOCState(SocInfo socInfo,String stateKey,String message){
        try {
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
            //  First state change the SOC to state "inprogress".
            getCourseOfferingSetService().updateSocState(socForm.getSocInfo().getId(),
                    CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS, contextInfo);

            // Then kick off the mass scheduling event.
            List<String> optionKeys = new ArrayList<String>();
            StatusInfo status = getCourseOfferingSetService().startScheduleSoc(socForm.getSocInfo().getId(), optionKeys, contextInfo);

            if (status.getIsSuccess()){
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, "Approved activities were successfully sent to Scheduler.");
                reload(socForm, contextInfo);
                socForm.setSocSchedulingStatus(getSocStateDescription(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS));
            } else {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, "Error locking SOC");
            }
        } catch (DoesNotExistException e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected String getSocPublishingStatus(SocInfo info) {
        if (info.getStateKey() != null) {
            if (StringUtils.equals(info.getStateKey(), CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY)) {
                return getSocStateDescription(CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY);
            } else if (StringUtils.equals(info.getStateKey(), CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY) ||
                    StringUtils.equals(info.getStateKey(), CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY)) {
                return getSocStateDescription(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED);
            }
        }
        return getSocStateDescription(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_NOT_STARTED);
    }

    private void reload(ManageSOCForm socForm, ContextInfo contextInfo)  {
        SocInfo socInfo;
        try {
            socInfo = getCourseOfferingSetService().getSoc(socForm.getSocInfo().getId(), contextInfo);
            socForm.setSocInfo(socInfo);
            socForm.setSocSchedulingStatus(getSocSchedulingStatus(socInfo));
            socForm.setSocPublishingStatus(getSocPublishingStatus(socInfo));
            String stateName = socForm.getSocStateKeys2Names().get(socInfo.getStateKey());
            socForm.setSocStatus(stateName);
        } catch (DoesNotExistException e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, "No SOC exists!");
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, "Error reloading SOC");
        }
    }

    /**
     * Gets a SOC state given a SOC state or SOC scheduling state key.
     * @return The description of a SOC state.
     */
     public String getSocStateDescription(String stateKey) {
        if (socStateDescriptions == null) {
            List<StateInfo> allSOCStates = null;
            try {
                allSOCStates = CourseOfferingResourceLoader.loadStateService()
                        .getStatesByLifecycle(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_KEY, ContextUtils.createDefaultContextInfo());
            } catch (Exception e) {
                throw new RuntimeException("Call to state service failed.", e);
            }
            socStateDescriptions = new HashMap<String, String>();
            for (StateInfo stateInfo : allSOCStates) {
                socStateDescriptions.put(stateInfo.getKey(), stateInfo.getName());
            }
        }
        return socStateDescriptions.get(stateKey);
    }
}
