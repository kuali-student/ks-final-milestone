package org.kuali.student.enrollment.class2.appointment.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.type.service.TypeService;
import org.kuali.student.mock.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PeriodForAppWindowKeyDatesFinder extends UifKeyValuesFinderBase implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient AcademicCalendarService acalService;
    private transient TypeService typeService;
    private ContextInfo contextInfo;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        RegistrationWindowsManagementForm form = (RegistrationWindowsManagementForm)model;

        ContextInfo context = TestHelper.getContext1();
        try {
            TermInfo term = form.getTermInfo();
            if (term.getId() != null && !term.getId().isEmpty()) {
                List<KeyDateInfo> keyDateInfoList = getAcalService().getKeyDatesForTerm(term.getId(), context);
                if (!keyDateInfoList.isEmpty())
                    keyValues.add(new ConcreteKeyValue("", "Select Period..."));
                try{
                    List<TypeTypeRelationInfo> relations = getTypeService().getTypeTypeRelationsByOwnerAndType("kuali.milestone.type.group.appt.regperiods","kuali.type.type.relation.type.group",context);
                    for (KeyDateInfo keyDateInfo : keyDateInfoList) {
                        for (TypeTypeRelationInfo relationInfo : relations) {
                            String relatedTypeKey = relationInfo.getRelatedTypeKey();
                            if (keyDateInfo.getTypeKey().equals(relatedTypeKey) &&
                                   (AtpServiceConstants.ATP_OFFICIAL_STATE_KEY.equals(keyDateInfo.getStateKey()) ||
                                    AtpServiceConstants.ATP_OFFICIAL_STATE_KEY.equals(keyDateInfo.getStateKey()))) {
                                keyValues.add(new ConcreteKeyValue(keyDateInfo.getId(), keyDateInfo.getName()));
                                break;
                            }
                        }
                    }

                }catch (Exception e){
                     //ToDo   
                }
//                for (KeyDateInfo keyDateInfo : keyDateInfoList) {
//                    if (keyDateInfo.getTypeKey().equals("kuali.atp.milestone.RegistrationPeriod")){
//                        ConcreteKeyValue keyValue = new ConcreteKeyValue();
//                        keyValue.setKey(keyDateInfo.getId());
//                        keyValue.setValue(keyDateInfo.getName());
//                        keyValues.add(keyValue);
//                    }
//                }
            }
        }catch (DoesNotExistException dnee){
            System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get DoesNotExistException:  "+dnee.toString());
        }catch (InvalidParameterException ipe){
            System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get InvalidParameterException:  "+ipe.toString());
        }catch (MissingParameterException mpe){
            System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get MissingParameterException:  "+mpe.toString());
        }catch (OperationFailedException ofe){
            System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get OperationFailedException:  "+ofe.toString());
        }catch (PermissionDeniedException pde){
            System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get PermissionDeniedException:  "+pde.toString());
        }
        return keyValues;
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }
}
