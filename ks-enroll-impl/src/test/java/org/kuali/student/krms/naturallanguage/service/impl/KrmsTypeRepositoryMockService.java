/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by NWUuser on 2/26/13
 */
package org.kuali.student.krms.naturallanguage.service.impl;

import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.krms.api.repository.type.KrmsAttributeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinitionContract;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;

import javax.jws.WebParam;
import java.util.List;
import org.kuali.rice.krms.api.repository.typerelation.RelationshipType;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KrmsTypeRepositoryMockService implements KrmsTypeRepositoryService {
    //public KrmsTypeDefinitionContract krmsTypeDefinitionContract = KRMSDataGenerator.createKrmsTypeDefinition("krmsType1","kuali.test.namespace",null,null,"kuali.type.id",true,0L);

    @Override
    public KrmsTypeDefinition createKrmsType(@WebParam(name = "krmsType") KrmsTypeDefinition krmsType) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KrmsTypeDefinition updateKrmsType(@WebParam(name = "krmsType") KrmsTypeDefinition krmsType) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KrmsTypeDefinition getTypeById(@WebParam(name = "id") String id) throws RiceIllegalArgumentException {
        String typeName;
        if(id.equals("xxx.xxx.xxx")){
              typeName = "invalid";
        }else if(id.equals("KUALI.xxx.CATALOG")){
              typeName = "invalid";
        }else if(id.equals("kuali.krms.proposition.type.course.courseset.completed.none")){
              typeName = "kuali.krms.proposition.type.course.courseset.completed.none";
        }else if(id.equals("kuali.krms.proposition.type.course.courseset.completed.nof")){
            typeName = "kuali.krms.proposition.type.course.courseset.completed.nof";
        }else if(id.equals("kuali.krms.proposition.type.course.courseset.gpa.min")){
            typeName = "kuali.krms.proposition.type.course.courseset.gpa.min";
        }else{
              typeName = "kuali.krms.proposition.type.success.course.courseset.completed.all";
        }

        KrmsTypeDefinitionContract krmsTypeDefinitionContract = KRMSDataGenerator.createKrmsTypeDefinition(typeName,"kuali.test.namespace",null,null,id,true,0L);
        return KrmsTypeDefinition.Builder.create(krmsTypeDefinitionContract).build();
    }

    @Override
    public KrmsTypeDefinition getTypeByName(@WebParam(name = "namespaceCode") String namespaceCode, @WebParam(name = "name") String name) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<KrmsTypeDefinition> findAllTypesByNamespace(@WebParam(name = "namespaceCode") String namespaceCode) throws RiceIllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<KrmsTypeDefinition> findAllTypes() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<KrmsTypeDefinition> findAllAgendaTypesByContextId(@WebParam(name = "contextId") String contextId) throws RiceIllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KrmsTypeDefinition getAgendaTypeByAgendaTypeIdAndContextId(@WebParam(name = "agendaTypeId") String agendaTypeId, @WebParam(name = "contextId") String contextId) throws RiceIllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<KrmsTypeDefinition> findAllRuleTypesByContextId(@WebParam(name = "contextId") String contextId) throws RiceIllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KrmsTypeDefinition getRuleTypeByRuleTypeIdAndContextId(@WebParam(name = "ruleTypeId") String ruleTypeId, @WebParam(name = "contextId") String contextId) throws RiceIllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<KrmsTypeDefinition> findAllActionTypesByContextId(@WebParam(name = "contextId") String contextId) throws RiceIllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KrmsTypeDefinition getActionTypeByActionTypeIdAndContextId(@WebParam(name = "actionTypeId") String actionTypeId, @WebParam(name = "contextId") String contextId) throws RiceIllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KrmsAttributeDefinition getAttributeDefinitionById(@WebParam(name = "attributeDefinitionId") String attributeDefinitionId) throws RiceIllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KrmsAttributeDefinition getAttributeDefinitionByName(@WebParam(name = "namespaceCode") String namespaceCode, @WebParam(name = "name") String name) throws RiceIllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeTypeRelation createTypeTypeRelation(TypeTypeRelation typeTypeRelation) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TypeTypeRelation getTypeTypeRelation(String typeTypeRelationId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateTypeTypeRelation(TypeTypeRelation typeTypeRelation) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteTypeTypeRelation(String typeTypeRelationId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeTypeRelation> findTypeTypeRelationsByFromType(String fromTypeId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeTypeRelation> findTypeTypeRelationsByToType(String toTypeId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KrmsTypeDefinition> findAllAgendaTypes() throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KrmsTypeDefinition> findAgendaTypesForContextType(String contextTypeId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KrmsTypeDefinition> findAgendaTypesForAgendaType(String agendaTypeId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KrmsTypeDefinition> findAllRuleTypes() throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KrmsTypeDefinition> findRuleTypesForAgendaType(String agendaTypeId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KrmsTypeDefinition> findAllPropositionTypes() throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KrmsTypeDefinition> findPropositionTypesForRuleType(String ruleTypeId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KrmsTypeDefinition> findAllPropositionParameterTypes() throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KrmsTypeDefinition> findPropositionParameterTypesForPropositionType(String propositionTypeId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KrmsTypeDefinition> findTermParameterTypesForTermPropositionParameterType(String termPropositionParameterTypeId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
