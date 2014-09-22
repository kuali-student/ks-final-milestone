package org.kuali.student.enrollment.krms.proposition;

import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.core.krms.proposition.PropositionFactory;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

/**
 * Factory for getting the required load calculation rule given a key
 */
public class PropositionFactoryHardwiredImpl extends
        org.kuali.student.core.krms.proposition.PropositionFactoryHardwiredImpl
        implements PropositionFactory {

    public PropositionFactoryHardwiredImpl() {
    }
    // checks
    public static final String RULE_ID_CREDIT_LOAD = "kuali.rule.credit.load";
    public static final String RULE_ID_CREDIT_LIMIT = "kuali.rule.credit.limit";
    public static final String RULE_ID_CREDIT_MINIMUM = "kuali.rule.credit.minimum";
    public static final String RULE_ID_REQUISITES = "kuali.rule.requisites";

    @Override
    public Proposition getProposition(String ruleId, ContextInfo contextInfo) throws DoesNotExistException,
            OperationFailedException {
        Proposition prop = null;
        switch (ruleId) {
            case RULE_ID_CREDIT_LOAD:
                prop = new CreditLoadProposition();
                break;
            case RULE_ID_CREDIT_LIMIT:
                prop = new CreditLimitProposition();
                break;
            case RULE_ID_CREDIT_MINIMUM:
                prop = new CreditMinimumProposition();
                break;
            case RULE_ID_REQUISITES:
                prop = new RequisitesProposition(KSKRMSServiceConstants.AGENDA_TYPE_COURSE_ENROLLMENTELIGIBILITY,
                        KSKRMSServiceConstants.RULE_TYPE_COURSE_ACADEMICREADINESS_ANTIREQ);
                break;
            default:
                super.getProposition(ruleId, contextInfo);
                break;
        }
        return prop;
    }
}
