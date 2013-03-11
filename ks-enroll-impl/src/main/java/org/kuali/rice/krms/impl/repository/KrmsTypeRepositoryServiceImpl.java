/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.rice.krms.impl.repository;

import java.util.ArrayList;
import java.util.List;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krms.api.repository.type.KrmsAttributeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeBoService;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.RelationshipType;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;

/**
 *
 * @author nwright
 */
public class KrmsTypeRepositoryServiceImpl implements KrmsTypeRepositoryService {

    private KrmsTypeBoService krmsTypeBoService = new KrmsTypeBoServiceImpl();
    private TypeTypeRelationBoService typeTypeRelationBoService = new TypeTypeRelationBoServiceImpl();

    @Override
    public KrmsTypeDefinition createKrmsType(KrmsTypeDefinition krmsType) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return krmsTypeBoService.createKrmsType(krmsType);
    }

    @Override
    public KrmsTypeDefinition updateKrmsType(KrmsTypeDefinition krmsType) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return krmsTypeBoService.updateKrmsType(krmsType);
    }

    @Override
    public KrmsTypeDefinition getTypeById(String id) throws RiceIllegalArgumentException {
        return krmsTypeBoService.getTypeById(id);
    }

    @Override
    public KrmsTypeDefinition getTypeByName(String namespaceCode, String name) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return krmsTypeBoService.getTypeByName(namespaceCode, name);
    }

    @Override
    public List<KrmsTypeDefinition> findAllTypesByNamespace(String namespaceCode) throws RiceIllegalArgumentException {
        return krmsTypeBoService.findAllTypesByNamespace(namespaceCode);
    }

    @Override
    public List<KrmsTypeDefinition> findAllTypes() {
        return krmsTypeBoService.findAllTypes();
    }

    @Override
    public List<KrmsTypeDefinition> findAllAgendaTypesByContextId(String contextId) throws RiceIllegalArgumentException {
        return krmsTypeBoService.findAllAgendaTypesByContextId(contextId);
    }

    @Override
    public KrmsTypeDefinition getAgendaTypeByAgendaTypeIdAndContextId(String agendaTypeId, String contextId) throws RiceIllegalArgumentException {
        return krmsTypeBoService.getAgendaTypeByAgendaTypeIdAndContextId(agendaTypeId, contextId);
    }

    @Override
    public List<KrmsTypeDefinition> findAllRuleTypesByContextId(String contextId) throws RiceIllegalArgumentException {
        return krmsTypeBoService.findAllRuleTypesByContextId(contextId);
    }

    @Override
    public KrmsTypeDefinition getRuleTypeByRuleTypeIdAndContextId(String ruleTypeId, String contextId) throws RiceIllegalArgumentException {
        return krmsTypeBoService.getRuleTypeByRuleTypeIdAndContextId(ruleTypeId, contextId);
    }

    @Override
    public List<KrmsTypeDefinition> findAllActionTypesByContextId(String contextId) throws RiceIllegalArgumentException {
        return krmsTypeBoService.findAllActionTypesByContextId(contextId);
    }

    @Override
    public KrmsTypeDefinition getActionTypeByActionTypeIdAndContextId(String actionTypeId, String contextId) throws RiceIllegalArgumentException {
        return krmsTypeBoService.getActionTypeByActionTypeIdAndContextId(actionTypeId, contextId);
    }

    @Override
    public KrmsAttributeDefinition getAttributeDefinitionById(String attributeDefinitionId) throws RiceIllegalArgumentException {
        return krmsTypeBoService.getAttributeDefinitionById(attributeDefinitionId);
    }

    @Override
    public KrmsAttributeDefinition getAttributeDefinitionByName(String namespaceCode, String name) throws RiceIllegalArgumentException {
        return krmsTypeBoService.getAttributeDefinitionByName(namespaceCode, name);
    }

    ////
    //// type type relation methods
    ////
    @Override
    public TypeTypeRelation createTypeTypeRelation(TypeTypeRelation typeTypeRelation) {
        return typeTypeRelationBoService.createTypeTypeRelation(typeTypeRelation);
    }

    @Override
    public TypeTypeRelation getTypeTypeRelation(String typeTypeRelationId) {
        return typeTypeRelationBoService.getTypeTypeRelation(typeTypeRelationId);
    }

    @Override
    public void updateTypeTypeRelation(TypeTypeRelation typeTypeRelation) {
        typeTypeRelationBoService.updateTypeTypeRelation(typeTypeRelation);
    }

    @Override
    public void deleteTypeTypeRelation(String typeTypeRelationId) {
        typeTypeRelationBoService.deleteTypeTypeRelation(typeTypeRelationId);
    }

    @Override
    public List<TypeTypeRelation> findTypeTypeRelationsByFromType(String fromTypeId) {
        return typeTypeRelationBoService.findTypeTypeRelationsByFromType(fromTypeId);
    }

    @Override
    public List<TypeTypeRelation> findTypeTypeRelationsByToType(String toTypeId) {
        return typeTypeRelationBoService.findTypeTypeRelationsByToType(toTypeId);
    }

    @Override
    public List<TypeTypeRelation> findTypeTypeRelationsByRelationshipType(RelationshipType relationshipType) {
        return typeTypeRelationBoService.findTypeTypeRelationsByRelationshipType(relationshipType);
    }

    /**
     * Sets the businessObjectService property.
     *
     * @param businessObjectService The businessObjectService to set.
     */
    public void setBusinessObjectService(final BusinessObjectService businessObjectService) {
        if (krmsTypeBoService instanceof KrmsTypeBoServiceImpl) {
            ((KrmsTypeBoServiceImpl) krmsTypeBoService).setBusinessObjectService(businessObjectService);
        }
        if (typeTypeRelationBoService instanceof TypeTypeRelationBoServiceImpl) {
            ((TypeTypeRelationBoServiceImpl) typeTypeRelationBoService).setBusinessObjectService(businessObjectService);
        }
    }

    @Override
    public List<KrmsTypeDefinition> findAllTypesByServiceName(String serviceName) throws RiceIllegalArgumentException {
        return krmsTypeBoService.findAllTypesByServiceName(serviceName);
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
