package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.r2.common.util.constants.*;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeInfo;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.state.service.StateService;
import org.kuali.student.r2.common.type.service.TypeService;


import org.kuali.student.r2.core.fee.service.FeeService;

import javax.xml.namespace.QName;
import java.util.Locale;
import java.util.Map;

public class EnrollmentFeeInfoMaintainableImpl extends MaintainableImpl {

    private transient CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;
    private transient TypeService typeService;
    private transient StateService stateService;


    private FeeService feeService;


    @Override
    public void prepareForSave() {
        System.out.println("This is prepareForSave");
    }


    @Override
    public void saveDataObject() {
        if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {
            try {

                EnrollmentFeeInfo efi = (EnrollmentFeeInfo) getDataObject();
                efi.setTypeKey(FeeServiceConstants.FEE_ENROLLMENT_TYPE_KEY);
                efi.setStateKey(FeeServiceConstants.FEE_ACTIVE_STATE_KEY);
                EnrollmentFeeInfo  feeInfo  = getFeeService().createFee(efi.getTypeKey(), efi,getContextInfo() );

                setDataObject(new EnrollmentFeeInfo(feeInfo));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {   //should be edit action
            EnrollmentFeeInfo efi = (EnrollmentFeeInfo) getDataObject();
            try {
                EnrollmentFeeInfo  feeInfo  = getFeeService().updateFee(efi.getId(), efi, getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            EnrollmentFeeInfo efi = getFeeService().getFee(dataObjectKeys.get("id"),getContextInfo());
            document.getNewMaintainableObject().setDataObject(efi);
            document.getOldMaintainableObject().setDataObject(efi);
//            StateInfo state = getStateService().getState(formObject.getDto().getStateKey(), getContextInfo());
//            formObject.setStateName(state.getName());
            return efi;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        EnrollmentFeeInfo efi = (EnrollmentFeeInfo)document.getNewMaintainableObject().getDataObject();
        document.getDocumentHeader().setDocumentDescription("Activity Offering");
        try {
//            StateInfo state = getStateService().getState(formObject.getDto().getStateKey(), getContextInfo());
//            formObject.setStateName(state.getName());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = new ContextInfo();
            contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            LocaleInfo localeInfo = new LocaleInfo();
            localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
            localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
            contextInfo.setLocale(localeInfo);
        }
        return contextInfo;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    protected FeeService getFeeService() {
        if (feeService == null) {
            feeService = (FeeService) GlobalResourceLoader.getService(new QName(FeeServiceConstants.NAMESPACE, FeeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return feeService;
    }
}
