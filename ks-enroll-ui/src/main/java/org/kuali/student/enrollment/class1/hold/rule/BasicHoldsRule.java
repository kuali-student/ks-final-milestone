package org.kuali.student.enrollment.class1.hold.rule;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.rule.KsMaintenanceDocumentRuleBase;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.dto.AuthorizedOrgWrapper;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldsResourceLoader;
import org.kuali.student.enrollment.class2.acal.util.AcalCommonUtils;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.ArrayList;
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

    protected ContextInfo createContextInfo() {
        return ContextUtils.createDefaultContextInfo();
    }
}
