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
 * Created by Daniel on 3/28/12
 */
package org.kuali.student.enrollment.class2.appointment.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.util.CommonUtils;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;
import org.kuali.student.enrollment.class2.appointment.service.AppointmentViewHelperService;
import org.kuali.student.enrollment.class2.appointment.util.AppointmentConstants;
import org.kuali.student.enrollment.class2.appointment.util.AppointmentSlotRuleTypeConversion;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.type.service.TypeService;

import javax.xml.namespace.QName;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class AppointmentViewHelperServiceImpl extends ViewHelperServiceImpl implements AppointmentViewHelperService {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AppointmentViewHelperServiceImpl.class);

    private transient AcademicCalendarService academicCalendarService;
    private transient TypeService typeService;
    private transient AppointmentService appointmentService;

    @Override
    public void searchForTerm(String typeKey, String year, RegistrationWindowsManagementForm form) throws Exception {

        //Parse the year to a date and the next year's date to compare against the startTerm
        DateFormat df = new SimpleDateFormat("yyyy");
        Date minBoundDate = df.parse(year);
        Date maxBoundDate = df.parse(Integer.toString(Integer.parseInt(year)+1));

        //Build up a term search criteria
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("atpType", typeKey),
                PredicateFactory.greaterThanOrEqual("startDate", minBoundDate),
                PredicateFactory.lessThan("startDate", maxBoundDate)));

        QueryByCriteria criteria = qbcBuilder.build();

        //Perform Term Search with Service Call
        AcademicCalendarService academicCalendarService = getAcalService();
        List<TermInfo> terms = academicCalendarService.searchForTerms(criteria, null);

        //Check for exceptions
        if(terms == null || terms.isEmpty()){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, AppointmentConstants.APPOINTMENT_MSG_ERROR_NO_TERMS_FOUND);
            return; //Nothing found
        }

        if(terms.size()>1){
            LOG.error("Too many terms!");
        }

        TermInfo term = terms.get(0);

        //Populate the result form
        form.setTermInfo(term);

        //Get the milestones and filter out anything that is not registration period
        List<KeyDateInfo> keyDates = academicCalendarService.getKeyDatesForTerm(term.getId(), null);
        if(keyDates != null){

            //Get the valid period types
            List<TypeTypeRelationInfo> milestoneTypeRelations = getTypeService().getTypeTypeRelationsByOwnerAndType("kuali.milestone.type.group.appt.regperiods","kuali.type.type.relation.type.group",new ContextInfo());
            List<String> validMilestoneTypes = new ArrayList<String>();
            for(TypeTypeRelationInfo milestoneTypeRelation:milestoneTypeRelations){
                validMilestoneTypes.add(milestoneTypeRelation.getRelatedTypeKey());
            }

            //Add in only valid milestones that are registration periods
            List<KeyDateInfo> periodMilestones = new ArrayList<KeyDateInfo>();
            for(KeyDateInfo keyDate:keyDates){
                if(validMilestoneTypes.contains(keyDate.getTypeKey())){
                    periodMilestones.add(keyDate);
                }
            }
            form.setPeriodMilestones(periodMilestones);

        }

        //Check if there are no periods (might want to handle this somewhere else and surface to the user)
        if(form.getPeriodMilestones()==null||form.getPeriodMilestones().isEmpty()){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, AppointmentConstants.APPOINTMENT_MSG_ERROR_NO_REG_PERIODS_FOR_TERM);
            //GlobalVariables.getMessageMap().putErrorForSectionId("KS-RegistrationWindowsManagement-SelectTermPage", AppointmentConstants.APPOINTMENT_MSG_ERROR_NO_REG_PERIODS_FOR_TERM);
            //GlobalVariables.getMessageMap().putError("termType", AppointmentConstants.APPOINTMENT_MSG_ERROR_NO_REG_PERIODS_FOR_TERM);
        }

    }

    public void loadTermAndPeriods(String termId, RegistrationWindowsManagementForm form) throws Exception {
        ContextInfo context = TestHelper.getContext1();
        TermInfo term = getAcalService().getTerm(termId, context);

        if (term.getId() != null && !term.getId().isEmpty()) {
            form.setTermInfo(term);
            loadPeriods(termId, form);
        }

    }

    public void loadPeriods(String termId, RegistrationWindowsManagementForm form) throws Exception {
        ContextInfo context = TestHelper.getContext1();
        List<KeyDateInfo> periodMilestones = new ArrayList<KeyDateInfo>();
        List<KeyDateInfo> keyDateInfoList = getAcalService().getKeyDatesForTerm(termId, context);
        List<TypeTypeRelationInfo> relations = getTypeService().getTypeTypeRelationsByOwnerAndType("kuali.milestone.type.group.appt.regperiods","kuali.type.type.relation.type.group",context);
        for (KeyDateInfo keyDateInfo : keyDateInfoList) {
            for (TypeTypeRelationInfo relationInfo : relations) {
                String relatedTypeKey = relationInfo.getRelatedTypeKey();
                if (keyDateInfo.getTypeKey().equals(relatedTypeKey))  {
                    periodMilestones.add(keyDateInfo);
                    break;
                }
            }
        }

        form.setPeriodMilestones(periodMilestones);
    }

    public boolean validateApptWidnow(AppointmentWindowWrapper apptWindow) {
        boolean isValid = true;
        //  1) a window end date is not required for a One-Slot or Max Number Slot Allocation Method/Window Type
        //  2) a window end date is required for uniform
        String windowTypeKey = apptWindow.getWindowTypeKey();
        if (AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY.equals(windowTypeKey)){
            if(apptWindow.getEndDate() == null)   {
                GlobalVariables.getMessageMap().putError("newCollectionLines['appointmentWindows'].endDate",
                        AppointmentConstants.APPOINTMENT_MSG_ERROR_END_DATE_REQUIRED_FOR_UNIFORM);
                isValid = false;
            }
            if(apptWindow.getEndTime() == null){
                GlobalVariables.getMessageMap().putError( "newCollectionLines['appointmentWindows'].endTime",
                        AppointmentConstants.APPOINTMENT_MSG_ERROR_END_TIME_REQUIRED_FOR_UNIFORM);
                isValid = false;
            }
            if  (apptWindow.getEndTime().isEmpty()){
                GlobalVariables.getMessageMap().putError( "newCollectionLines['appointmentWindows'].endTimeAmPm",
                        AppointmentConstants.APPOINTMENT_MSG_ERROR_END_TIME_REQUIRED_FOR_UNIFORM);
                isValid = false;
            }
        }
        // 3) when start/end date is not null, start/end date should be in the date range of the selected period
        String periodId = apptWindow.getPeriodKey();
        try {
            KeyDateInfo period = getAcalService().getKeyDate(periodId,getContextInfo());
            if (period.getStartDate().after(apptWindow.getStartDate()) || period.getEndDate().before(apptWindow.getStartDate())){
                GlobalVariables.getMessageMap().putError( "newCollectionLines['appointmentWindows'].startDate",
                        AppointmentConstants.APPOINTMENT_MSG_ERROR_START_DATE_OUT_OF_RANGE);
                isValid = false;
            }
            if (apptWindow.getEndDate() != null && !apptWindow.getEndDate().toString().isEmpty() ){
                if (period.getStartDate().after(apptWindow.getEndDate()) || period.getEndDate().before(apptWindow.getEndDate()) ){
                    GlobalVariables.getMessageMap().putError("newCollectionLines['appointmentWindows'].endDate",
                            AppointmentConstants.APPOINTMENT_MSG_ERROR_END_DATE_OUT_OF_RANGE);
                    isValid = false;
                }
            }
        }catch (Exception e){
            LOG.error("Fail to find periods for a selected term.",e);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, AppointmentConstants.APPOINTMENT_MSG_ERROR_NO_REG_PERIODS_FOR_TERM);
            isValid = false;
        }

        //4)  when the start date and the end date are not null,  the start date should be before the end date
        if (isValid){
            try{
                if (apptWindow.getEndDate() != null && !apptWindow.getEndTime().isEmpty() && !apptWindow.getEndTimeAmPm().isEmpty()){
                    Date startDate = CommonUtils.getDateWithTime(apptWindow.getStartDate(), apptWindow.getStartTime(), apptWindow.getStartTimeAmPm());
                    Date endDate = CommonUtils.getDateWithTime(apptWindow.getEndDate(), apptWindow.getEndTime(), apptWindow.getEndTimeAmPm());
                    if(startDate.after(endDate)){
                        GlobalVariables.getMessageMap().putError( "newCollectionLines['appointmentWindows'].endDate",
                                AppointmentConstants.APPOINTMENT_MSG_ERROR_END_DATE_IS_BEFORE_START_DATE);
                    }
                }
            } catch (Exception e){
                LOG.error("Fail to find periods for a selected term.",e);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, AppointmentConstants.APPOINTMENT_MSG_ERROR_END_DATE_IS_BEFORE_START_DATE);
                isValid = false;
            }
        }
        return isValid;
    }

    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof AppointmentWindowWrapper) {
            //in the AddLine (/inputLine) when the periodId is not all, need to set the selected periodId and periodName
            // in the addLine
            RegistrationWindowsManagementForm form = (RegistrationWindowsManagementForm) model;
            AppointmentWindowWrapper newCollectionLine= (AppointmentWindowWrapper)form.getNewCollectionLines().get("appointmentWindows");
            String periodId = form.getPeriodId();
            if (periodId != "all" && !periodId.isEmpty()){
                newCollectionLine.setPeriodName(form.getPeriodName());
                newCollectionLine.setPeriodKey(form.getPeriodId());
            }
        }
    }

    public boolean saveApptWindow(AppointmentWindowWrapper appointmentWindowWrapper) throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException, VersionMismatchException{
        boolean isSave = true;
        //Copy the form data from the wrapper to the bean.
        AppointmentWindowInfo appointmentWindowInfo = appointmentWindowWrapper.getAppointmentWindowInfo();
        appointmentWindowInfo.setTypeKey(appointmentWindowWrapper.getWindowTypeKey());
        appointmentWindowInfo.setPeriodMilestoneId(appointmentWindowWrapper.getPeriodKey());
        appointmentWindowInfo.setStartDate(CommonUtils.getDateWithTime(appointmentWindowWrapper.getStartDate(), appointmentWindowWrapper.getStartTime(), appointmentWindowWrapper.getStartTimeAmPm()));
        appointmentWindowInfo.setEndDate(CommonUtils.getDateWithTime(appointmentWindowWrapper.getEndDate(), appointmentWindowWrapper.getEndTime(), appointmentWindowWrapper.getEndTimeAmPm()));

        //TODO Default to some value if nothing is entered(Service team needs to make up some real types or make not nullable)
        if(appointmentWindowInfo.getAssignedOrderTypeKey() == null || appointmentWindowInfo.getAssignedOrderTypeKey().isEmpty()){
            appointmentWindowInfo.setAssignedOrderTypeKey("DUMMY_ID");
        }

        //Default to single slot type if nothing is entered
        if(appointmentWindowInfo.getTypeKey() == null || appointmentWindowInfo.getTypeKey().isEmpty()){
            appointmentWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY);
        }

        if(appointmentWindowInfo.getId()==null||appointmentWindowInfo.getId().isEmpty()){
            //Default the state to active
            appointmentWindowInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY);

            //Converting appointment rule type code to AppointmentSlotRuleInfo object when apptWindowInfo..getTypeKey != AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY
            if(!AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY.equals(appointmentWindowInfo.getTypeKey())) {
                appointmentWindowInfo.setSlotRule(AppointmentSlotRuleTypeConversion.convToAppointmentSlotRuleInfo(appointmentWindowWrapper.getSlotRuleEnumType()));
            }
            //appointmentWindowInfo.getSlotRule().setWeekdays(new ArrayList<Integer>());
            //appointmentWindowInfo.getSlotRule().getWeekdays().add(1);
            appointmentWindowInfo = getAppointmentService().createAppointmentWindow(appointmentWindowInfo.getTypeKey(),appointmentWindowInfo,new ContextInfo());
        }else{
            appointmentWindowInfo = getAppointmentService().updateAppointmentWindow(appointmentWindowInfo.getId(),appointmentWindowInfo,new ContextInfo());
        }

        //Reset the windowInfo from the service's returned value
        appointmentWindowWrapper.setAppointmentWindowInfo(appointmentWindowInfo);
        appointmentWindowWrapper.setId(appointmentWindowInfo.getId());
        appointmentWindowWrapper.setWindowName(appointmentWindowInfo.getName());

        return isSave;

    }

    public boolean saveWindows(RegistrationWindowsManagementForm form) throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException, VersionMismatchException {
        boolean isApptWindowSaved = true;
        boolean allWindowsSaved = true;
        if(form.getAppointmentWindows()!=null){

            for(AppointmentWindowWrapper appointmentWindowWrapper:form.getAppointmentWindows()){
                boolean isValid = validateApptWidnow(appointmentWindowWrapper);
                if (isValid) {
                    isApptWindowSaved=saveApptWindow(appointmentWindowWrapper);
                    if(!isApptWindowSaved)
                        allWindowsSaved = isApptWindowSaved;
                }
            }
            //Add a success message
            if (isApptWindowSaved)
                GlobalVariables.getMessageMap().putInfo( KRADConstants.GLOBAL_MESSAGES,
                        AppointmentConstants.APPOINTMENT_MSG_INFO_SAVED);
        }
        return allWindowsSaved;
    }

    protected void processBeforeAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof AppointmentWindowWrapper){
            RegistrationWindowsManagementForm form = (RegistrationWindowsManagementForm) model;
            List<KeyDateInfo> periodMilestones = form.getPeriodMilestones();
            String periodKey = ((AppointmentWindowWrapper) addLine).getPeriodKey();
            for (KeyDateInfo period : periodMilestones) {
                if (periodKey.equals(period.getId())){
                    if (period.getName() != null && !period.getName().isEmpty()){
                        ((AppointmentWindowWrapper) addLine).setPeriodName(period.getName());
                    }
                    else{
                        ((AppointmentWindowWrapper) addLine).setPeriodName(periodKey);
                    }
                    break;
                }
            }
            String windowName =  ((AppointmentWindowWrapper) addLine).getAppointmentWindowInfo().getName();
            ((AppointmentWindowWrapper) addLine).setWindowName(windowName);
        }
    }

    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model,
                                               Object addLine) {
        boolean isValid = true;
        if (addLine instanceof AppointmentWindowWrapper){
            AppointmentWindowWrapper apptWindow = (AppointmentWindowWrapper) addLine;
            isValid = validateApptWidnow(apptWindow);
            if(isValid) {
                try {
                    //need to persist the window that has passed the validation to DB
                    saveApptWindow((AppointmentWindowWrapper)addLine);
                    //Add a success message
                    GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_MESSAGES,
                            AppointmentConstants.APPOINTMENT_MSG_INFO_SAVED);
                } catch (Exception e) {
                    LOG.error("Fail to create a window.",e);
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, AppointmentConstants.APPOINTMENT_MSG_ERROR_WINDOW_SAVE_FAIL);
                    isValid = false;
                }
            }

        } else {
            super.performAddLineValidation(view, collectionGroup, model, addLine);
        }
        return isValid;
    }

    public AcademicCalendarService getAcalService() {
        if(academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }


    public AppointmentService getAppointmentService() {
        if(appointmentService == null) {
            appointmentService = (AppointmentService) GlobalResourceLoader.getService(new QName(AppointmentServiceConstants.NAMESPACE, AppointmentServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return appointmentService;
    }


    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }



    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        return contextInfo;
    }


}
