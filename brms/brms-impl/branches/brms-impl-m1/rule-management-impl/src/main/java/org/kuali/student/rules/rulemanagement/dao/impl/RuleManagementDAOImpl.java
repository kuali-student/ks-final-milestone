/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.rules.internal.common.entity.AgendaType;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.rulemanagement.dao.RuleManagementDAO;
import org.kuali.student.rules.rulemanagement.entity.AgendaInfo;
import org.kuali.student.rules.rulemanagement.entity.AgendaInfoDeterminationStructure;
import org.kuali.student.rules.rulemanagement.entity.BusinessRule;
import org.kuali.student.rules.rulemanagement.entity.BusinessRuleType;
import org.kuali.student.rules.rulemanagement.entity.RuleElement;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on Functional Business Rule entity using Spring JPA
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Repository
public class RuleManagementDAOImpl implements RuleManagementDAO {

    @PersistenceContext(unitName = "RuleManagement")
    private EntityManager entityManager;

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }
    /**     
     * @param entityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public AgendaInfo lookupAgendaInfoById(String agendaInfoId) {
        return entityManager.find(AgendaInfo.class, agendaInfoId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public java.util.List<AgendaInfo> lookupAgendaInfosByType(AgendaType type) {
        Query query = entityManager.createNamedQuery("AgendaInfo.findByAgendaType");
        query.setParameter("agendaType", type);
        List<AgendaInfo> agendaInfoList = query.getResultList();
        return agendaInfoList;
    }

    @Override
    public AgendaInfo lookupAgendaInfoByTypeAndStructure(AgendaType type, List<AgendaInfoDeterminationStructure> determinationStructureList) {

        // StringBuilder queryString = new StringBuilder("SELECT a FROM AgendaInfo a INNER JOIN FETCH
        // a.agendaInfoDeterminationStructureList b WHERE c.type = :agendaType AND");

        // Query query = entityManager.createQuery(queryString.toString());
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AgendaType> lookupUniqueAgendaTypes() {
        Query query = entityManager.createNamedQuery("AgendaInfo.findUniqueAgendaTypes");
        List<String> agendaTypeStrList = query.getResultList();

        List<AgendaType> agendaTypeList = new ArrayList<AgendaType>();
        for (String agendaType : agendaTypeStrList) {
            agendaTypeList.add(AgendaType.valueOf(agendaType));
        }

        return agendaTypeList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusinessRuleTypeKey> lookupUniqueBusinessRuleTypeKeys() {
        Query query = entityManager.createNamedQuery("BusinessRuleType.findBusinessRuleTypes");
        List<String> ruleTypeStrList = query.getResultList();

        List<BusinessRuleTypeKey> typeList = new ArrayList<BusinessRuleTypeKey>();
        for (String ruleType : ruleTypeStrList) {
            typeList.add(BusinessRuleTypeKey.valueOf(ruleType));
        }

        return typeList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusinessRuleTypeKey> lookupBusinessRuleTypeByAgenda(AgendaType agendaType) {
        Query query = entityManager.createNamedQuery("AgendaInfo.findBusinessRuleTypes");
        query.setParameter("agendaType", agendaType.toString());
        List<BusinessRuleType> ruleTypeStrList = query.getResultList();

        List<BusinessRuleTypeKey> typeList = new ArrayList<BusinessRuleTypeKey>();
        for (BusinessRuleType ruleType : ruleTypeStrList) {
            typeList.add(BusinessRuleTypeKey.valueOf( ruleType.getBusinessRuleTypeKey() ));
        }

        return typeList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AnchorTypeKey> lookupUniqueAnchorTypeKeys() {
        Query query = entityManager.createNamedQuery("BusinessRuleType.findUniqueAnchorTypes");
        List<String> anchorTypeStrList = query.getResultList();

        List<AnchorTypeKey> anchorTypeList = new ArrayList<AnchorTypeKey>();
        for (String anchorType : anchorTypeStrList) {
            anchorTypeList.add(AnchorTypeKey.valueOf(anchorType));
        }

        return anchorTypeList;
    }

    @Override
    public BusinessRuleType lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey businessRuleTypekey, AnchorTypeKey anchorTypeKey) {
        Query query = entityManager.createNamedQuery("BusinessRuleType.findByKeyAndAnchorType");
        query.setParameter("businessRuleTypeKey", businessRuleTypekey.toString());
        query.setParameter("anchorTypeKey", anchorTypeKey.toString());
        BusinessRuleType brType = (BusinessRuleType) query.getSingleResult();

        return brType;
    }

    @Override
    public BusinessRule createBusinessRule(BusinessRule rule) {
        entityManager.persist(rule);
        return rule;
    }

    @Override
    public BusinessRule updateBusinessRule(BusinessRule rule) {   
        entityManager.merge(rule);
        return rule;
    }

    @Override
    public boolean deleteRuleElement(RuleElement ruleElement) {
        entityManager.remove(ruleElement);
        return true;
    }
    
    @Override
    public boolean deleteBusinessRule(BusinessRule rule) {
        entityManager.remove(rule);
        return true;
    }

    @Override
    public boolean deleteBusinessRule(String ruleId) {
        entityManager.remove(lookupBusinessRuleUsingRuleId(ruleId));
        return true;
    }

    @Override
    public BusinessRule lookupBusinessRuleUsingId(String id) {
        return entityManager.find(BusinessRule.class, id);
    }

    @Override
    public BusinessRule lookupBusinessRuleUsingRuleId(String ruleIdentifier) {
        Query query = entityManager.createNamedQuery("BusinessRule.findByRuleID");
        query.setParameter("ruleID", ruleIdentifier);
        BusinessRule functionalBusinessRule = (BusinessRule) query.getSingleResult();
        return functionalBusinessRule;
    }

    @Override
    @SuppressWarnings(value = {"unchecked"})
    public List<BusinessRule> lookupBusinessRuleUsingAnchor(BusinessRuleTypeKey businessRuleTypeKey, String anchor) {
        Query query = entityManager.createNamedQuery("BusinessRule.findByBusinessRuleTypeAndAnchor");
        query.setParameter("businessRuleTypeKey", businessRuleTypeKey.toString());
        query.setParameter("anchor", anchor);
        List<BusinessRule> functionalBusinessRule = query.getResultList();
        return functionalBusinessRule;
    }

    @SuppressWarnings("unchecked")
    @Override
    public java.util.List<String> lookupBusinessRuleIdUsingRuleTypeKey(BusinessRuleTypeKey businessRuleTypeKey) {
        Query query = entityManager.createNamedQuery("BusinessRule.findIdsByBusinessRuleType");
        query.setParameter("businessRuleTypeKey", businessRuleTypeKey.toString());
        List<String> businessRuleIdList = query.getResultList();
        return businessRuleIdList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> lookupAnchorByAnchorType(AnchorTypeKey anchorTypeKey) {
        Query query = entityManager.createNamedQuery("BusinessRule.findAnchorsByAnchorType");
        query.setParameter("anchorTypeKey", anchorTypeKey.toString());
        List<String> anchorList = query.getResultList();
        return anchorList;
    }
}
