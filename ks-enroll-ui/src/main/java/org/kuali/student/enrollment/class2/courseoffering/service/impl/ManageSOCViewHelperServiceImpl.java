package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
     * @param socForm
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

            dateFormat = new SimpleDateFormat("hh:mm a");

            if (socInfo.getLastSchedulingRunCompleted() != null && socInfo.getLastSchedulingRunStarted() != null){
                long schedulingDuration = socInfo.getLastSchedulingRunCompleted().getTime() - socInfo.getLastSchedulingRunStarted().getTime();
                socForm.setScheduleDuration(dateFormat.format(schedulingDuration));
            }

            if (socInfo.getPublishingCompleted() != null && socInfo.getPublishingStarted() != null){
                long publishingDuration = socInfo.getPublishingCompleted().getTime() - socInfo.getPublishingStarted().getTime();
                socForm.setPublishDuration(dateFormat.format(publishingDuration));
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
     * This will lock the SOC state to LOCKED so that the departments cant make any changes to the CO/FO/AOs. Once we change the state, disable the
     * 'LOCK Set' button
     *
     * @param socForm SOC form
     */
    public void lockSOC(ManageSOCForm socForm){

        try {
            StatusInfo status = getCourseOfferingSetService().updateSocState(socForm.getSocInfo().getId(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, ContextUtils.createDefaultContextInfo());

            if (status.getIsSuccess()){
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, "SOC has been locked sucessfully");
                //Once state changed, disable the Lock button.
                socForm.getSocInfo().setStateKey(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY);
            }else{
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, "Error locking SOC");
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

}
