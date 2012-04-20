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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;
import org.kuali.student.enrollment.class2.appointment.service.AppointmentViewHelperService;
import org.kuali.student.enrollment.class2.appointment.util.AppointmentSlotRuleTypeConversion;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.type.service.TypeService;

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
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES,AppointmentServiceConstants.APPOINTMENT_MSG_ERROR_NO_TERMS_FOUND);
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
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, AppointmentServiceConstants.APPOINTMENT_MSG_ERROR_NO_REG_PERIODS_FOR_TERM);
        }

    }

//    public Properties buildWindowWrapperURLParameters(AppointmentWindowWrapper windowWrapper,String methodToCall,boolean readOnlyView, ContextInfo context){
//        Properties props = new Properties();
//        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
//        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, "org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper");
//        props.put("id", windowWrapper.getId());
//        return props;
//    }

    public void loadTermAndPeriods(String termId, RegistrationWindowsManagementForm form) throws Exception {
        ContextInfo context = TestHelper.getContext1();
//        try {
        TermInfo term = getAcalService().getTerm(termId, context);

        if (term.getId() != null && !term.getId().isEmpty()) {
            form.setTermInfo(term);
            loadPeriods(termId, form);
        }
//        }catch (DoesNotExistException dnee){
//            System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get DoesNotExistException:  "+dnee.toString());
//        }catch (InvalidParameterException ipe){
//            System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get InvalidParameterException:  "+ipe.toString());
//        }catch (MissingParameterException mpe){
//            System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get MissingParameterException:  "+mpe.toString());
//        }catch (OperationFailedException ofe){
//            System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get OperationFailedException:  "+ofe.toString());
//        }catch (PermissionDeniedException pde){
//            System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get PermissionDeniedException:  "+pde.toString());
//        }

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
            RegistrationWindowsManagementForm form = (RegistrationWindowsManagementForm) model;
            AppointmentWindowWrapper apptWindow = (AppointmentWindowWrapper) addLine;
            //  1) a window end date is not required for a One-Slot or Max Number Slot Allocation Method/Window Type
            //  2) a window end date is required for uniform             
            String windowTypeKey = apptWindow.getWindowTypeKey();
            if (AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY.equals(windowTypeKey)){
                if(apptWindow.getEndDate() == null)   {
                    GlobalVariables.getMessageMap().putError( KRADConstants.GLOBAL_MESSAGES,
                            "Last Date is a required field and can't be null when Slot Allocation Method is Uniform");
                    isValid = false;
                }
                if(apptWindow.getEndTime() == null){
                    GlobalVariables.getMessageMap().putError( KRADConstants.GLOBAL_MESSAGES,
                            "Closing Time is a required field and can't be null when Slot Allocation Method is Uniform");
                    isValid = false;
                }
            }
            // 3) when start/end date is not null, start/end date should be in the date range of the selected period
            String periodId = apptWindow.getPeriodKey();
            try {
                KeyDateInfo period = getAcalService().getKeyDate(periodId,getContextInfo());
                if (period.getStartDate().after(apptWindow.getStartDate()) || period.getEndDate().before(apptWindow.getStartDate())){
                    GlobalVariables.getMessageMap().putError( KRADConstants.GLOBAL_MESSAGES,
                            "Window's start date is out of the date range of the select period.");
                    isValid = false;
                }
                if (apptWindow.getEndDate() != null){
                    if (period.getEndDate().before(apptWindow.getEndDate())){
                        GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES,"Window's end date is out of the date range of the select period.");
                        isValid = false;
                    }
                }
            }catch (Exception e){
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, "Fail to find periods for a selected term.");
                isValid = false;
            }

            if(isValid) {
                try {
                    //need to persist the window that has passed the validation to DB
                    _saveApptWindow((AppointmentWindowWrapper)addLine);
                    //Add a success message
                    GlobalVariables.getMessageMap().putInfo( KRADConstants.GLOBAL_MESSAGES,
                            AppointmentServiceConstants.APPOINTMENT_MSG_INFO_SAVED);
                } catch (Exception e) {
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES,"Fail to create a window.");
                    isValid = false;
                }
            }

        } else {
            super.performAddLineValidation(view, collectionGroup, model, addLine);
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

    private void _saveApptWindow(AppointmentWindowWrapper appointmentWindowWrapper) throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException, VersionMismatchException{
        //Copy the form data from the wrapper to the bean.
        AppointmentWindowInfo appointmentWindowInfo = appointmentWindowWrapper.getAppointmentWindowInfo();
        appointmentWindowInfo.setTypeKey(appointmentWindowWrapper.getWindowTypeKey());
        appointmentWindowInfo.setPeriodMilestoneId(appointmentWindowWrapper.getPeriodKey());
        appointmentWindowInfo.setStartDate(_updateTime(appointmentWindowWrapper.getStartDate(), appointmentWindowWrapper.getStartTime(), appointmentWindowWrapper.getStartTimeAmPm()));
        appointmentWindowInfo.setEndDate(_updateTime(appointmentWindowWrapper.getEndDate(), appointmentWindowWrapper.getEndTime(), appointmentWindowWrapper.getEndTimeAmPm()));

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

            //Converting appointment rule type code to AppointmentSlotRuleInfo object
            appointmentWindowInfo.setSlotRule(AppointmentSlotRuleTypeConversion.convToAppointmentSlotRuleInfo(appointmentWindowWrapper.getSlotRuleEnumType()));

            appointmentWindowInfo = getAppointmentService().createAppointmentWindow(appointmentWindowInfo.getTypeKey(),appointmentWindowInfo,new ContextInfo());
        }else{
            appointmentWindowInfo = getAppointmentService().updateAppointmentWindow(appointmentWindowInfo.getId(),appointmentWindowInfo,new ContextInfo());
        }

        //Reset the windowInfo from the service's returned value
        appointmentWindowWrapper.setAppointmentWindowInfo(appointmentWindowInfo);

    }

    //Copied from AcademicCalendarViewHelperServiceImpl //TODO(should be moved into common util class)
    private Date _updateTime(Date date,String time,String amPm){

        if(date == null || time == null || amPm == null){
            return null;
        }

        //FIXME: Use Joda DateTime

        // Get Calendar object set to the date and time of the given Date object
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // Set time fields to zero
        cal.set(Calendar.HOUR, Integer.parseInt(StringUtils.substringBefore(time, ":")));
        cal.set(Calendar.MINUTE, Integer.parseInt(StringUtils.substringAfter(time,":")));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (StringUtils.isNotBlank(amPm)){
            if (StringUtils.equalsIgnoreCase(amPm,"am")){
                cal.set(Calendar.AM_PM,Calendar.AM);
            }else if(StringUtils.equalsIgnoreCase(amPm,"pm")){
                cal.set(Calendar.AM_PM,Calendar.PM);
            }else{
                throw new RuntimeException("Unknown AM/PM format.");
            }
        }

        return cal.getTime();
    }

    public void saveWindows(RegistrationWindowsManagementForm form) throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException, VersionMismatchException {
        if(form.getAppointmentWindows()!=null){

            for(AppointmentWindowWrapper appointmentWindowWrapper:form.getAppointmentWindows()){
                _saveApptWindow(appointmentWindowWrapper);

            }
            //Add a success message
            GlobalVariables.getMessageMap().putInfo( KRADConstants.GLOBAL_MESSAGES,
                    AppointmentServiceConstants.APPOINTMENT_MSG_INFO_SAVED);
        }
    }

    public AcademicCalendarService getAcalService() {
        return (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
    }

    public TypeService getTypeService() {
        return (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeService.class.getSimpleName()));
    }

    public AppointmentService getAppointmentService() {
        return (AppointmentService) GlobalResourceLoader.getService(new QName(AppointmentServiceConstants.NAMESPACE, AppointmentServiceConstants.SERVICE_NAME_LOCAL_PART));
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
