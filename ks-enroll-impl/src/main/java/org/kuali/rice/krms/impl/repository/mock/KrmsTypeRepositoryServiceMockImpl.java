/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.impl.repository.mock;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.krms.api.repository.type.KrmsAttributeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.RelationshipType;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;

public class KrmsTypeRepositoryServiceMockImpl implements MockService, KrmsTypeRepositoryService {
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order

    private Map<String, KrmsTypeDefinition> krmsTypeMap = new LinkedHashMap<String, KrmsTypeDefinition>();
    private Map<String, KrmsAttributeDefinition> krmsAttributeMap = new LinkedHashMap<String, KrmsAttributeDefinition>();
    private Map<String, TypeTypeRelation> typeTypeRelationMap = new LinkedHashMap<String, TypeTypeRelation>();

    @Override
    public void clear() {
        this.krmsTypeMap.clear();
        this.krmsAttributeMap.clear();
        this.krmsAttributeMap.clear();
    }

    @Override
    public KrmsTypeDefinition createKrmsType(KrmsTypeDefinition krmsType)
            throws RiceIllegalArgumentException, RiceIllegalStateException {
        // CREATE
        KrmsTypeDefinition orig = this.getTypeByName(krmsType.getNamespace(), krmsType.getName());
        if (orig != null) {
            throw new RiceIllegalArgumentException(krmsType.getNamespace() + "." + krmsType.getName());
        }
        KrmsTypeDefinition.Builder copy = KrmsTypeDefinition.Builder.create(krmsType);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        krmsType = copy.build();
        krmsTypeMap.put(krmsType.getId(), krmsType);
        return krmsType;
    }

    @Override
    public KrmsTypeDefinition updateKrmsType(KrmsTypeDefinition krmsType)
            throws RiceIllegalArgumentException, RiceIllegalStateException {
        // UPDATE
        KrmsTypeDefinition.Builder copy = KrmsTypeDefinition.Builder.create(krmsType);
        KrmsTypeDefinition old = this.getTypeById(krmsType.getId());
        if (!old.getVersionNumber().equals(copy.getVersionNumber())) {
            throw new RiceIllegalStateException("" + old.getVersionNumber());
        }
        copy.setVersionNumber(copy.getVersionNumber() + 1);
        krmsType = copy.build();
        this.krmsTypeMap.put(krmsType.getId(), krmsType);
        return krmsType;
    }

    @Override
    public KrmsTypeDefinition getTypeById(String id)
            throws RiceIllegalArgumentException {
        // GET_BY_ID
        if (!this.krmsTypeMap.containsKey(id)) {
            throw new RiceIllegalArgumentException(id);
        }
        return this.krmsTypeMap.get(id);
    }

    @Override
    public KrmsTypeDefinition getTypeByName(String namespaceCode, String name)
            throws RiceIllegalArgumentException, RiceIllegalStateException {
        for (KrmsTypeDefinition type : this.krmsTypeMap.values()) {
            if (type.getName().equals(name)) {
                if (type.getNamespace().equals(namespaceCode)) {
                    return type;
                }
            }
        }
        return null;
    }

    @Override
    public List<KrmsTypeDefinition> findAllTypesByNamespace(String namespaceCode)
            throws RiceIllegalArgumentException {
        List<KrmsTypeDefinition> list = new ArrayList<KrmsTypeDefinition>();
        for (KrmsTypeDefinition type : this.krmsTypeMap.values()) {
            if (type.getNamespace().equals(namespaceCode)) {
                list.add(type);
            }
        }
        return list;
    }

    @Override
    public List<KrmsTypeDefinition> findAllTypes() {
       return new ArrayList<KrmsTypeDefinition> (this.krmsTypeMap.values());
    }

    @Override
    public KrmsTypeDefinition getAgendaTypeByAgendaTypeIdAndContextId(String agendaTypeId, String contextId)
            throws RiceIllegalArgumentException {
        // GET_BY_ID
        if (!this.krmsTypeMap.containsKey(agendaTypeId)) {
            throw new RiceIllegalArgumentException(agendaTypeId);
        }
        return this.krmsTypeMap.get(agendaTypeId);
    }

    @Override
    public List<KrmsTypeDefinition> findAllAgendaTypesByContextId(String contextId)
            throws RiceIllegalArgumentException {
        // UNKNOWN
        throw new RiceIllegalArgumentException("findAllAgendaTypesByContextId has not been implemented");
    }

    @Override
    public List<KrmsTypeDefinition> findAllRuleTypesByContextId(String contextId)
            throws RiceIllegalArgumentException {
        // UNKNOWN
        throw new RiceIllegalArgumentException("findAllRuleTypesByContextId has not been implemented");
    }

    @Override
    public KrmsTypeDefinition getRuleTypeByRuleTypeIdAndContextId(String ruleTypeId, String contextId)
            throws RiceIllegalArgumentException {
        // GET_BY_ID
        if (!this.krmsTypeMap.containsKey(ruleTypeId)) {
            throw new RiceIllegalArgumentException(ruleTypeId);
        }
        return this.krmsTypeMap.get(ruleTypeId);
    }

    @Override
    public List<KrmsTypeDefinition> findAllActionTypesByContextId(String contextId)
            throws RiceIllegalArgumentException {
        // UNKNOWN
        throw new RiceIllegalArgumentException("findAllActionTypesByContextId has not been implemented");
    }

    @Override
    public KrmsTypeDefinition getActionTypeByActionTypeIdAndContextId(String actionTypeId, String contextId)
            throws RiceIllegalArgumentException {
        // GET_BY_ID
        if (!this.krmsTypeMap.containsKey(actionTypeId)) {
            throw new RiceIllegalArgumentException(actionTypeId);
        }
        return this.krmsTypeMap.get(actionTypeId);
    }

    @Override
    public KrmsAttributeDefinition getAttributeDefinitionById(String attributeDefinitionId)
            throws RiceIllegalArgumentException {
        // GET_BY_ID
        if (!this.krmsAttributeMap.containsKey(attributeDefinitionId)) {
            throw new RiceIllegalArgumentException(attributeDefinitionId);
        }
        return this.krmsAttributeMap.get(attributeDefinitionId);
    }

    @Override
    public KrmsAttributeDefinition getAttributeDefinitionByName(String namespaceCode, String name)
            throws RiceIllegalArgumentException {
        // UNKNOWN
        throw new RiceIllegalArgumentException("getAttributeDefinitionByName has not been implemented");
    }

    @Override
    public TypeTypeRelation createTypeTypeRelation(TypeTypeRelation typeTypeRelation)
            throws RiceIllegalArgumentException {
        // CREATE
        TypeTypeRelation orig = this.getTypeTypeRelation(typeTypeRelation.getId());
        if (orig != null) {
            throw new RiceIllegalArgumentException(typeTypeRelation.getId ());
        }
        TypeTypeRelation.Builder copy = TypeTypeRelation.Builder.create(typeTypeRelation);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        typeTypeRelation = copy.build();
        typeTypeRelationMap.put(typeTypeRelation.getId(), typeTypeRelation);
        return typeTypeRelation;
    }

    @Override
    public TypeTypeRelation getTypeTypeRelation(String typeTypeRelationId)
            throws RiceIllegalArgumentException {
        // GET_BY_ID
        if (!this.typeTypeRelationMap.containsKey(typeTypeRelationId)) {
            throw new RiceIllegalArgumentException(typeTypeRelationId);
        }
        return this.typeTypeRelationMap.get(typeTypeRelationId);
    }

    @Override
    public void updateTypeTypeRelation(TypeTypeRelation typeTypeRelation)
            throws RiceIllegalArgumentException {
        // UPDATE
        TypeTypeRelation.Builder copy = TypeTypeRelation.Builder.create(typeTypeRelation);
        TypeTypeRelation old = this.getTypeTypeRelation(typeTypeRelation.getId());
        if (!old.getVersionNumber().equals(copy.getVersionNumber())) {
            throw new RiceIllegalStateException("" + old.getVersionNumber());
        }
        copy.setVersionNumber(copy.getVersionNumber() + 1);
        typeTypeRelation = copy.build();
        this.typeTypeRelationMap.put(typeTypeRelation.getId(), typeTypeRelation);
        return;
    }

    @Override
    public void deleteTypeTypeRelation(String typeTypeRelationId)
            throws RiceIllegalArgumentException {
        // DELETE
        if (this.typeTypeRelationMap.remove(typeTypeRelationId) == null) {
            throw new RiceIllegalArgumentException(typeTypeRelationId);
        }
        return;
    }

    @Override
    public List<TypeTypeRelation> findTypeTypeRelationsByFromType(String fromTypeId)
            throws RiceIllegalArgumentException {
        // UNKNOWN
        throw new RiceIllegalArgumentException("findTypeTypeRelationsByFromType has not been implemented");
    }

    @Override
    public List<TypeTypeRelation> findTypeTypeRelationsByToType(String toTypeId)
            throws RiceIllegalArgumentException {
        // UNKNOWN
        throw new RiceIllegalArgumentException("findTypeTypeRelationsByToType has not been implemented");
    }

    @Override
    public List<TypeTypeRelation> findTypeTypeRelationsByRelationshipType(RelationshipType relationshipType) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KrmsTypeDefinition> findAllTypesByServiceName(String serviceName) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public List<KrmsTypeDefinition> findAllContextTypes() throws RiceIllegalArgumentException {
        return this.findAllTypesByServiceName(CONTEXT_SERVICE_NAME);
    }

    @Override
    public List<KrmsTypeDefinition> findAllAgendaTypes() throws RiceIllegalArgumentException {
        return this.findAllTypesByServiceName(AGENDA_SERVICE_NAME);
    }

    @Override
    public List<KrmsTypeDefinition> findAllRuleTypes() throws RiceIllegalArgumentException {
        return this.findAllTypesByServiceName(RULE_SERVICE_NAME);
    }

    @Override
    public List<KrmsTypeDefinition> findAllPropositionTypes() throws RiceIllegalArgumentException {
        return this.findAllTypesByServiceName(PROPOSITION_SERVICE_NAME);
    }

    @Override
    public List<KrmsTypeDefinition> findAllPropositionParameterTypes() throws RiceIllegalArgumentException {
        return this.findAllTypesByServiceName(PROPOSITION_PARAMETER_SERVICE_NAME);
    }

    @Override
    public List<KrmsTypeDefinition> findAgendaTypesForContextType(String contextTypeId) throws RiceIllegalArgumentException {
        List<TypeTypeRelation> rels = this.findTypeTypeRelationsByFromType(contextTypeId);
        List<KrmsTypeDefinition> list = new ArrayList<KrmsTypeDefinition>(rels.size());
        for (TypeTypeRelation rel : rels) {
            list.add(this.getTypeById(rel.getToTypeId()));
        }
        return list;
    }

    @Override
    public List<KrmsTypeDefinition> findAgendaTypesForAgendaType(String agendaTypeId) throws RiceIllegalArgumentException {
        List<TypeTypeRelation> rels = this.findTypeTypeRelationsByFromType(agendaTypeId);
        List<KrmsTypeDefinition> list = new ArrayList<KrmsTypeDefinition>(rels.size());
        for (TypeTypeRelation rel : rels) {
            list.add(this.getTypeById(rel.getToTypeId()));
        }
        return list;
    }

    @Override
    public List<KrmsTypeDefinition> findRuleTypesForAgendaType(String agendaTypeId) throws RiceIllegalArgumentException {
        List<TypeTypeRelation> rels = this.findTypeTypeRelationsByFromType(agendaTypeId);
        List<KrmsTypeDefinition> list = new ArrayList<KrmsTypeDefinition>(rels.size());
        for (TypeTypeRelation rel : rels) {
            list.add(this.getTypeById(rel.getToTypeId()));
        }
        return list;
    }

    @Override
    public List<KrmsTypeDefinition> findPropositionTypesForRuleType(String ruleTypeId) throws RiceIllegalArgumentException {
        List<TypeTypeRelation> rels = this.findTypeTypeRelationsByFromType(ruleTypeId);
        List<KrmsTypeDefinition> list = new ArrayList<KrmsTypeDefinition>(rels.size());
        for (TypeTypeRelation rel : rels) {
            list.add(this.getTypeById(rel.getToTypeId()));
        }
        return list;
    }

    @Override
    public List<KrmsTypeDefinition> findPropositionParameterTypesForPropositionType(String propositionTypeId)
            throws RiceIllegalArgumentException {
        List<TypeTypeRelation> rels = this.findTypeTypeRelationsByFromType(propositionTypeId);
        List<KrmsTypeDefinition> list = new ArrayList<KrmsTypeDefinition>(rels.size());
        for (TypeTypeRelation rel : rels) {
            list.add(this.getTypeById(rel.getToTypeId()));
        }
        return list;
    }
}
