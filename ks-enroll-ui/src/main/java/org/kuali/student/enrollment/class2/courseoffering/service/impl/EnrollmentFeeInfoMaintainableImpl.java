package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.FeeServiceConstants;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeInfo;

import javax.xml.namespace.QName;
import java.util.Map;

public class EnrollmentFeeInfoMaintainableImpl extends MaintainableImpl {
    private static final long serialVersionUID = 1L;

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
                EnrollmentFeeInfo  feeInfo  = CourseOfferingManagementUtil.getFeeService().createFee(efi.getTypeKey(), efi, ContextUtils.createDefaultContextInfo() );

                setDataObject(new EnrollmentFeeInfo(feeInfo));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {   //should be edit action
            EnrollmentFeeInfo efi = (EnrollmentFeeInfo) getDataObject();
            try {
                CourseOfferingManagementUtil.getFeeService().updateFee(efi.getId(), efi, ContextUtils.createDefaultContextInfo());
            } catch (Exception e) {
                throw new RuntimeException("Error updating Fee", e);
            }
        }
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            EnrollmentFeeInfo efi = CourseOfferingManagementUtil.getFeeService().getFee(dataObjectKeys.get("id"), ContextUtils.createDefaultContextInfo());
            document.getNewMaintainableObject().setDataObject(efi);
            document.getOldMaintainableObject().setDataObject(efi);
            return efi;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        document.getDocumentHeader().setDocumentDescription("Activity Offering");
    }
}
