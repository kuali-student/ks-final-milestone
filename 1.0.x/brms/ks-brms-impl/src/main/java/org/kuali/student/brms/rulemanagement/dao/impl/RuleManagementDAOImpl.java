/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.brms.rulemanagement.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.brms.internal.common.entity.AgendaType;
import org.kuali.student.brms.internal.common.entity.AnchorTypeKey;
import org.kuali.student.brms.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.brms.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.brms.rulemanagement.dao.RuleManagementDAO;
import org.kuali.student.brms.rulemanagement.entity.AgendaDetermination;
import org.kuali.student.brms.rulemanagement.entity.Agenda;
import org.kuali.student.brms.rulemanagement.entity.BusinessRule;
import org.kuali.student.brms.rulemanagement.entity.BusinessRuleType;
import org.kuali.student.brms.rulemanagement.entity.RuleElement;
import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on Functional Business Rule entity using Spring JPA
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Repository
public class RuleManagementDAOImpl extends AbstractSearchableCrudDaoImpl implements RuleManagementDAO {

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
    public Agenda lookupAgendaById(String agendaInfoId) {
        return entityManager.find(Agenda.class, agendaInfoId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public java.util.List<Agenda> lookupAgendasByType(AgendaType type) {
        Query query = entityManager.createNamedQuery("Agenda.findByAgendaType");
        query.setParameter("agendaType", type.toString());
        List<Agenda> agendaInfoList = query.getResultList();
        return agendaInfoList;
    }

    //TODO Provide implementation
    @Override
    public Agenda lookupAgendaByTypeAndStructure(AgendaType type, List<AgendaDetermination> determinationStructureList) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AgendaType> lookupUniqueAgendaTypes() {
        Query query = entityManager.createNamedQuery("Agenda.findUniqueAgendaTypes");
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
        List<BusinessRuleTypeKey> ruleTypeStrList = query.getResultList();

        List<BusinessRuleTypeKey> typeList = new ArrayList<BusinessRuleTypeKey>();
        for (BusinessRuleTypeKey ruleType : ruleTypeStrList) {
            typeList.add(ruleType);
        }

        return typeList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusinessRuleTypeKey> lookupBusinessRuleTypeByAgenda(AgendaType agendaType) {
        Query query = entityManager.createNamedQuery("Agenda.findBusinessRuleTypes");
        query.setParameter("agendaType", agendaType.toString());
        List<BusinessRuleType> ruleTypeStrList = query.getResultList();

        List<BusinessRuleTypeKey> typeList = new ArrayList<BusinessRuleTypeKey>();
        for (BusinessRuleType ruleType : ruleTypeStrList) {
            typeList.add( ruleType.getBusinessRuleTypeKey() );
        }

        return typeList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AnchorTypeKey> lookupUniqueAnchorTypeKeys() {
        Query query = entityManager.createNamedQuery("BusinessRuleType.findUniqueAnchorTypes");
        List<AnchorTypeKey> anchorTypeStrList = query.getResultList();

        List<AnchorTypeKey> anchorTypeList = new ArrayList<AnchorTypeKey>();
        for (AnchorTypeKey anchorType : anchorTypeStrList) {
            anchorTypeList.add(anchorType);
        }

        return anchorTypeList;
    }

    @Override
    public BusinessRuleType lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey businessRuleTypekey, AnchorTypeKey anchorTypeKey) {
        Query query = entityManager.createNamedQuery("BusinessRuleType.findByKeyAndAnchorType");
        query.setParameter("businessRuleTypeKey", businessRuleTypekey);
        query.setParameter("anchorTypeKey", anchorTypeKey);
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
        entityManager.remove(lookupBusinessRuleUsingId(ruleId));
        return true;
    }

    @Override
    public BusinessRule lookupBusinessRuleUsingId(String id) {
        BusinessRule rule = entityManager.find(BusinessRule.class, id);
        if (null == rule) {
            throw new NoResultException("No record found for Id:" + id);
        }
        
        return rule;
    }

    @Override
    @SuppressWarnings(value = {"unchecked"})
    public List<BusinessRule> lookupBusinessRuleUsingAnchor(BusinessRuleTypeKey businessRuleTypeKey, String anchor) {
        Query query = entityManager.createNamedQuery("BusinessRule.findByBusinessRuleTypeAndAnchor");
        query.setParameter("businessRuleTypeKey", businessRuleTypeKey);
        query.setParameter("anchor", anchor);
        List<BusinessRule> functionalBusinessRules = query.getResultList();
        return functionalBusinessRules;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusinessRule> lookupCurrentActiveBusinessRuleUsingAnchor(BusinessRuleTypeKey businessRuleTypeKey, String anchor) {
        Query query = entityManager.createNamedQuery("BusinessRule.findByState");
        query.setParameter("businessRuleTypeKey", businessRuleTypeKey);
        query.setParameter("anchor", anchor);
        query.setParameter("state", BusinessRuleStatus.ACTIVE);
        query.setParameter("today", new Date());
        List<BusinessRule> functionalBusinessRules = query.getResultList();
        return functionalBusinessRules;
     }
    
    @SuppressWarnings("unchecked")
    @Override
    public java.util.List<String> lookupBusinessRuleIdUsingRuleTypeKey(BusinessRuleTypeKey businessRuleTypeKey) {
        Query query = entityManager.createNamedQuery("BusinessRule.findIdsByBusinessRuleType");
        query.setParameter("businessRuleTypeKey", businessRuleTypeKey);
        List<String> businessRuleIdList = query.getResultList();
        return businessRuleIdList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> lookupAnchorByAnchorType(AnchorTypeKey anchorTypeKey) {
        Query query = entityManager.createNamedQuery("BusinessRule.findAnchorsByAnchorType");
        query.setParameter("anchorTypeKey", anchorTypeKey);
        List<String> anchorList = query.getResultList();
        return anchorList;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<BusinessRule> lookupAllVersions(String originalRuleId) {
        Query query = entityManager.createNamedQuery("BusinessRule.findAllVersions");
        query.setParameter("originalRuleId", originalRuleId);
        List<BusinessRule> functionalBusinessRules = query.getResultList();
        return functionalBusinessRules;
    }
}
