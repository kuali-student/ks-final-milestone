/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.keyvalues;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.NaturalLanguageTemplateBoService;
import org.kuali.rice.krms.impl.repository.NaturalLanguageUsageBoService;
import org.kuali.rice.krms.impl.repository.TypeTypeRelationBoService;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Kuali Student Team
 */
public class RuleInstructionKeyValues implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient RuleManagementService ruleManagementService;


    public Map<String, String> getKeyValues(Set<String> ruleTypeIds) {
        Map<String, String> keyValuesInstruction = new HashMap<String, String>();
        NaturalLanguageUsage usageInstruction = getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(PermissionServiceConstants.KS_SYS_NAMESPACE, KsKrmsConstants.KRMS_NL_TYPE_INSTRUCTION);
         //Use Type Type Relation to get Rule Types
        try {
            for(String ruleTypeId : ruleTypeIds) {
                NaturalLanguageTemplate templateInstruction = null;
                try{
                    templateInstruction = getRuleManagementService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en", ruleTypeId, usageInstruction.getId());
                }catch (IndexOutOfBoundsException e){
                    //Ignore, rice error in NaturalLanguageTemplateBoServiceImpl line l
                }

                if (templateInstruction != null) {
                    keyValuesInstruction.put(ruleTypeId, templateInstruction.getTemplate());
                } else {
                    keyValuesInstruction.put(ruleTypeId, KsKrmsConstants.KRMS_NL_TYPE_INSTRUCTION);
                }
            };
        } catch (Exception ex) {}
        return keyValuesInstruction;
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }
}
