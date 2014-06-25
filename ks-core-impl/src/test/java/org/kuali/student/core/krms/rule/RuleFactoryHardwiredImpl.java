package org.kuali.student.core.krms.rule;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.rice.krms.impl.provider.repository.RepositoryToEngineTranslator;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.core.krms.proposition.IsAliveProposition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SW Genis on 2014/06/14.
 */
public class RuleFactoryHardwiredImpl implements RuleFactory {

    @Override
    public Rule getRule(String ruleId, ContextInfo contextInfo) throws DoesNotExistException, OperationFailedException {
        Proposition proposition = this.getProposition(ruleId, contextInfo);
        return new BasicRule(proposition, null);
    }

    // checks
    public static final String RULE_ID_IS_ALIVE = "kuali.rule.is.alive";

    protected Proposition getProposition(String ruleId, ContextInfo contextInfo) throws DoesNotExistException,
            OperationFailedException {

        if (ruleId.equals(RULE_ID_IS_ALIVE)) {
            Proposition prop = new IsAliveProposition();
            return prop;
        }
        throw new DoesNotExistException("unknown/unsupported proposition " + ruleId);
    }

}
