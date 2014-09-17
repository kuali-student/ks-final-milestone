package org.kuali.student.enrollment.class1.hold.rule;

import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.rule.KsMaintenanceDocumentRuleBase;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldsResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.Date;
import java.util.List;

/**
 * Maintenance document rule class to perform bussiness rule validation on applied holds.
 *
 * @author Kuali Student Blue Team (SA)
 */
public class BasicHoldsRule extends KsMaintenanceDocumentRuleBase {

    protected String resolveTermId(String termCode, String propertyName) {
        try {
            TermInfo firstTermInfo = searchForTermIdByCode(termCode);
            if (firstTermInfo != null) {
                return firstTermInfo.getId();
            } else {
                GlobalVariables.getMessageMap().putError(propertyName, HoldsConstants.HOLDS_ISSUE_MSG_ERROR_INVALID_TERM, termCode);
            }
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(propertyName, HoldsConstants.HOLDS_ISSUE_MSG_ERROR_FIRST_APPLICATION_TERMID);
        }
        return null;
    }

    protected static TermInfo searchForTermIdByCode(String termCode)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        if ((termCode == null) || (termCode.isEmpty())) {
            return null;
        }

        List<TermInfo> results = HoldsResourceLoader.getAcademicCalendarService().getTermsByCode(termCode, ContextUtils.createDefaultContextInfo());
        return KSCollectionUtils.getOptionalZeroElement(results);
    }


    protected String resolveTermCode(String termId) {
        try {
            TermInfo firstTermInfo = searchForTermIdById(termId);
            if (firstTermInfo != null) {
                return firstTermInfo.getCode();
            }
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }
        return null;
    }

    protected static TermInfo searchForTermIdById(String termId)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,PermissionDeniedException {

        if ((termId == null) || (termId.isEmpty())) {
            return null;
        }

       TermInfo termInfo = HoldsResourceLoader.getAcademicCalendarService().getTerm(termId, ContextUtils.createDefaultContextInfo());
        return termInfo;
    }


    public static boolean isDateBiggerThanOrEqual(Date firstDate, Date secondDate) {
        if (firstDate != null && secondDate != null) {
            if (firstDate.after(secondDate) || firstDate.equals(secondDate)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean isDateSmallerThanOrEqual(Date firstDate, Date secondDate) {
        if (firstDate != null && secondDate != null) {
            if (firstDate.before(secondDate) || firstDate.equals(secondDate)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
    protected ContextInfo createContextInfo() {
        return ContextUtils.createDefaultContextInfo();
    }
}
