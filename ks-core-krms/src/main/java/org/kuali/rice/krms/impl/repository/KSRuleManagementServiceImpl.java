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
package org.kuali.rice.krms.impl.repository;

import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.NaturalLanguage;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.in;

/**
 * The implementation of {@link org.kuali.rice.krms.api.repository.RuleManagementService} operations facilitate management of rules and
 * associated information.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class KSRuleManagementServiceImpl extends RuleManagementServiceImpl implements RuleManagementService {

    private boolean isSame(AgendaDefinition agenda, AgendaDefinition existing) {
        if (!this.isSame(agenda.isActive(), existing.isActive())) {
            return false;
        }

        if (!this.isSame(agenda.getAttributes(), existing.getAttributes())) {
            return false;
        }

        if (!this.isSame(agenda.getContextId(), existing.getContextId())) {
            return false;
        }

        if (!this.isSame(agenda.getFirstItemId(), existing.getFirstItemId())) {
            return false;
        }

        if (!this.isSame(agenda.getName(), existing.getName())) {
            return false;
        }

        if (!this.isSame(agenda.getTypeId(), existing.getTypeId())) {
            return false;
        }

        return true;
    }

    private boolean isSame(boolean o1, boolean o2) {
        if (o1 && !o2) {
            return false;
        }

        if (!o1 && o2) {
            return false;
        }

        return true;
    }

    private boolean isSame(Map<String, String> o1, Map<String, String> o2) {
        if (o1 == null && o2 != null) {
            return false;
        }

        if (o1 != null && o2 == null) {
            return false;
        }

        return o1.equals(o2);
    }

    private boolean isSame(String o1, String o2) {
        if (o1 == null && o2 != null) {
            return false;
        }

        if (o1 != null && o2 == null) {
            return false;
        }

        return o1.equals(o2);
    }

    @Override
    public List<ReferenceObjectBinding> findReferenceObjectBindingsByReferenceObjectIds(String referenceObjectReferenceDiscriminatorType,
                                                                                        List<String> referenceObjectIds)
            throws RiceIllegalArgumentException {
        if (referenceObjectReferenceDiscriminatorType == null) {
            throw new RiceIllegalArgumentException("reference binding object discriminator type must not be null");
        }

        if (referenceObjectIds == null) {
            throw new RiceIllegalArgumentException("reference object ids must not be null");
        }

        List<ReferenceObjectBinding> list = new ArrayList<ReferenceObjectBinding>();
        for(String referenceObjectId : referenceObjectIds){
            for (ReferenceObjectBinding binding : this.getReferenceObjectBindingBoService().findReferenceObjectBindingsByReferenceObject(referenceObjectId)) {
                if (binding.getReferenceDiscriminatorType().equals(referenceObjectReferenceDiscriminatorType)) {
                    list.add(binding);
                }
            }
        }

        return list;
    }

    @Override
    public List<NaturalLanguage> translateNaturalLanguageForObjects(String naturalLanguageUsageId, String typeId, List<String> krmsObjectIds,
                                                                    String languageCode)
            throws RiceIllegalArgumentException {

        List<NaturalLanguage> nlList = new ArrayList<NaturalLanguage>();
        for(String krmsObjectId : krmsObjectIds){
            String nl = this.getTranslateBusinessMethods().translateNaturalLanguageForObject(naturalLanguageUsageId, typeId, krmsObjectId, languageCode);

            NaturalLanguage.Builder nlBuilder = NaturalLanguage.Builder.create();
            nlBuilder.setKrmsObjectId(krmsObjectId);
            nlBuilder.setNaturalLanguage(nl);
            nlList.add(nlBuilder.build());
        }

        return nlList;
    }

}

