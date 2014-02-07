package org.kuali.student.core.process.evaluator;

import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.core.ges.service.proposition.IsGesValueApplicableOnDayProposition;
import org.kuali.student.core.ges.service.proposition.IsGesValueApplicableProposition;
import org.kuali.student.core.population.service.proposition.ClassStandingProposition;
import org.kuali.student.enrollment.class2.academicrecord.service.impl.ClassStanding;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * Factory for getting the required load calculation rule given a key
 */
public class PropositionFactoryHardwiredImpl implements PropositionFactory {

    public PropositionFactoryHardwiredImpl() {
    }
    // checks
    public static final String RULE_ID_IS_ALIVE = "kuali.rule.is.alive";
    public static final String RULE_ID_CREDIT_LOAD = "kuali.rule.credit.load";
    public static final String RULE_ID_CREDIT_LIMIT = "kuali.rule.credit.limit";
    public static final String RULE_ID_CREDIT_MINIMUM = "kuali.rule.credit.minimum";
    // GES service
    public static final String RULE_ID_IS_GES_VALUE_APPLICABLE = "kuali.ges.rule.is.applicable";
    public static final String RULE_ID_IS_GES_VALUE_APPLICABLE_ON_DAY = "kuali.ges.rule.is.applicable.onday";
    // class standings
    public static final String RULE_ID_IS_FRESHMAN = "kuali.rule.freshman";
    public static final String RULE_ID_SOPHOMORE = "kuali.rule.sophomore";
    public static final String RULE_ID_JUNIOR = "kuali.rule.junior";
    public static final String RULE_ID_SENIOR = "kuali.rule.senior";
    public Proposition getProposition(String ruleId, ContextInfo contextInfo) throws DoesNotExistException,
            OperationFailedException {
        if (ruleId.equals(RULE_ID_IS_ALIVE)) {
            Proposition prop = new IsAliveProposition();
            return prop;
        }
        if (ruleId.equals(RULE_ID_CREDIT_LOAD)) {
            Proposition prop = new CreditLoadProposition();
            return prop;
        }
        if (ruleId.equals(RULE_ID_CREDIT_LIMIT)) {
            Proposition prop = new CreditLimitProposition();
            return prop;
        }
        if (ruleId.equals(RULE_ID_CREDIT_MINIMUM)) {
            Proposition prop = new CreditMinimumProposition();
            return prop;
        }
        // GES Service
        if (ruleId.equals(RULE_ID_IS_GES_VALUE_APPLICABLE)) {
            Proposition prop = new IsGesValueApplicableProposition();
            return prop;
        }
        if (ruleId.equals(RULE_ID_IS_GES_VALUE_APPLICABLE_ON_DAY)) {
            Proposition prop = new IsGesValueApplicableOnDayProposition();
            return prop;
        }
        //Population Service
        if (ruleId.equals(RULE_ID_IS_FRESHMAN)) {
            Proposition prop = new ClassStandingProposition(ClassStanding.FRESHMAN.getDescription());
            return prop;
        }
        if (ruleId.equals(RULE_ID_SOPHOMORE)) {
            Proposition prop = new ClassStandingProposition(ClassStanding.SOPHOMORE.getDescription());
            return prop;
        }
        if (ruleId.equals(RULE_ID_JUNIOR)) {
            Proposition prop = new ClassStandingProposition(ClassStanding.JUNIOR.getDescription());
            return prop;
        }
        if (ruleId.equals(RULE_ID_SENIOR)) {
            Proposition prop = new ClassStandingProposition(ClassStanding.SENIOR.getDescription());
            return prop;
        }
        throw new DoesNotExistException("unknown/unsupported proposition " + ruleId);
    }
}
