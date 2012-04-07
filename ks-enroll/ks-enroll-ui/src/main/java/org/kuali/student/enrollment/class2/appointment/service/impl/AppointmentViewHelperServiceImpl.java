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
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;
import org.kuali.student.enrollment.class2.appointment.service.AppointmentViewHelperService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.mock.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class AppointmentViewHelperServiceImpl extends ViewHelperServiceImpl implements AppointmentViewHelperService {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AppointmentViewHelperServiceImpl.class);

    @Override
    public RegistrationWindowsManagementForm searchForTerm(String typeKey, String year, RegistrationWindowsManagementForm form) throws Exception {

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
        if(terms == null){
            return null; //Nothing found and null
        }
        if(terms.isEmpty()){
            return null; //Nothing found
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
            List<KeyDateInfo> periodMilestones = new ArrayList<KeyDateInfo>();
            for(KeyDateInfo keyDate:keyDates){
                if(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY.equals(keyDate.getTypeKey())){//TODO  what types do we filter on?
                    periodMilestones.add(keyDate);
                }
            }
            form.setPeriodMilestones(periodMilestones);
        }

        //Check if there are no periods (might want to handle this somewhere else and surface to the user)
        if(form.getPeriodMilestones()==null||form.getPeriodMilestones().isEmpty()){
            throw new Exception("No periods exist for term");//TODO what happens in this case
        }
        
        return form;
    }

    public void loadTermAndPeriods(String termId, RegistrationWindowsManagementForm form) throws Exception {
        ContextInfo context = TestHelper.getContext1();
//        try {
            TermInfo term = getAcalService().getTerm(termId, context);
            if (term.getId() != null && !term.getId().isEmpty()) {
                form.setTermInfo(term);
                List<KeyDateInfo> periodMilestones = form.getPeriodMilestones();
                List<KeyDateInfo> keyDateInfoList = getAcalService().getKeyDatesForTerm(term.getId(), context);
                for (KeyDateInfo keyDateInfo : keyDateInfoList) {
                    if (AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY.equals(keyDateInfo.getTypeKey())){
                        System.out.println(">>>find "+keyDateInfo.getName());
                        periodMilestones.add (keyDateInfo);
                    }
                }
                form.setPeriodMilestones(periodMilestones);
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
        }
    }
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof AppointmentWindowWrapper) {
            RegistrationWindowsManagementForm form = (RegistrationWindowsManagementForm) model;
            AppointmentWindowWrapper newCollectionLine= (AppointmentWindowWrapper)form.getNewCollectionLines().get("appointmentWindows");
            String periodId = form.getPeriodId();
            if (periodId != "all" && !periodId.isEmpty()){
                newCollectionLine.setPeriodName(form.getPeriodName());
            }
        }
    }

    public AcademicCalendarService getAcalService() {
        return (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
    }
}
