package org.kuali.student.core.krms.rule;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.rice.krms.impl.provider.repository.RepositoryToEngineTranslator;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.core.krms.proposition.PropositionFactory;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import javax.xml.namespace.QName;

/**
 * Created by SW Genis on 2014/06/14.
 */
public class RuleFactoryImpl implements RuleFactory {

    private RuleManagementService ruleManagementService;

    @Override
    public Rule getRule(String ruleId, ContextInfo contextInfo) throws DoesNotExistException, OperationFailedException {
        RuleDefinition definition = this.getRuleManagementService().getRule(ruleId);
        if(definition!=null){
            return this.getRepositoryToEngineTranslator().translateRuleDefinition(definition);
        }
        throw new DoesNotExistException("unknown/unsupported rule " + ruleId);
    }

    public RuleManagementService getRuleManagementService(){
        if (ruleManagementService == null) {
            ruleManagementService = GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

    public RepositoryToEngineTranslator getRepositoryToEngineTranslator(){
        return KrmsRepositoryServiceLocator.getKrmsRepositoryToEngineTranslator();
    }
}
