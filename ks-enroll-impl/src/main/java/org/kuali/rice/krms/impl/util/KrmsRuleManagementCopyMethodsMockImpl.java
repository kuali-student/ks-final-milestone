/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.rice.krms.impl.util;

import java.util.ArrayList;
import java.util.List;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;

/**
 * Mock impl just so I can plug this logic into the course rollover logic.
 * @author nwright
 */
public class KrmsRuleManagementCopyMethodsMockImpl implements KrmsRuleManagementCopyMethods {

    private RuleManagementService ruleManagementService;
    
    @Override
    public List<ReferenceObjectBinding> deepCopyReferenceObjectBindingsFromTo(String fromReferenceDiscriminatorType,
            String fromReferenceObjectId,
            String toReferenceDiscriminatorType,
            String toReferenceObjectId,
            List<String> optionKeys)
            throws RiceIllegalArgumentException,
            RiceIllegalStateException {
        _checkEmptyParam(fromReferenceDiscriminatorType, "fromReferenceDiscriminatorType");
        _checkEmptyParam(fromReferenceObjectId, "fromReferenceObjectId");
        _checkEmptyParam(toReferenceDiscriminatorType, "toReferenceDiscriminatorType");
        _checkEmptyParam(toReferenceObjectId, "toReferenceObjectId");
        List<ReferenceObjectBinding> list = new ArrayList<ReferenceObjectBinding>();
        System.out.println("KrmsRuleManagementCopyMethodsMockImpl implementation does not really do any copying");
        return list;
    }

    private void _checkEmptyParam(String param, String message)
            throws RiceIllegalArgumentException {
        if (param == null) {
            throw new RiceIllegalArgumentException(message);
        }
        if (param.trim().isEmpty()) {
            throw new RiceIllegalArgumentException(message);
        }
    }

    public RuleManagementService getRuleManagementService() {
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }   
    
}
