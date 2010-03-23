/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.brms.rulemanagement.dao;

import java.util.List;

import org.kuali.student.brms.internal.common.entity.AgendaType;
import org.kuali.student.brms.internal.common.entity.AnchorTypeKey;
import org.kuali.student.brms.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.brms.rulemanagement.entity.AgendaDetermination;
import org.kuali.student.brms.rulemanagement.entity.Agenda;
import org.kuali.student.brms.rulemanagement.entity.BusinessRule;
import org.kuali.student.brms.rulemanagement.entity.BusinessRuleType;
import org.kuali.student.brms.rulemanagement.entity.RuleElement;
import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;

/**
 * Defines DAO operations for Functional Business Rule
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
public interface RuleManagementDAO extends CrudDao, SearchableDao {

    /**
     * This method returns the agenda for given Id
     * 
     * @param agendaInfoId
     * @return
     */
    public Agenda lookupAgendaById(String agendaInfoId);

    /**
     * This method retrieves Agenda that matches the given type and the key,value pairs in the determination list
     * 
     * @param type
     * @param determinationStructureList
     * @return
     */
    public Agenda lookupAgendaByTypeAndStructure(AgendaType type, List<AgendaDetermination> determinationStructureList);

    /**
     * This method returns a list of all the known agenda types
     * 
     * @return
     */
    public List<AgendaType> lookupUniqueAgendaTypes();

    /**
     * This method returns the list of agendainfo objects for a given type
     * 
     * @param type
     * @return
     */
    public List<Agenda> lookupAgendasByType(AgendaType type);

    /**
     * This method returns all the unique business rule types known in the system
     * 
     * @return
     */
    public List<BusinessRuleTypeKey> lookupUniqueBusinessRuleTypeKeys();

    
    /**
     * 
     * This method lookups business rule types that belong to given agenda type
     * 
     * @param agendaType
     * @return
     */
    public List<BusinessRuleTypeKey> lookupBusinessRuleTypeByAgenda(AgendaType agendaType);
    
    
    /**
     * This method returns all the unique anchor type keys known in the system
     * 
     * @return
     */
    public List<AnchorTypeKey> lookupUniqueAnchorTypeKeys();

    /**
     * This method returns the BusinessRuleType for a given business rule type key and anchor type key combination
     * 
     * @param businessRuleTypekey
     * @param anchorTypeKey
     * @return
     */
    public BusinessRuleType lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey businessRuleTypekey, AnchorTypeKey anchorTypeKey);

    /**
     * Creates BusinessRule in database.
     * 
     * @param rule
     *            A business rule to persist in database.
     * @return persisted rule.
     */
    public BusinessRule createBusinessRule(BusinessRule rule);

    /**
     * Updates BusinessRule in database.
     * 
     * @param rule
     *            A business rule to update in database.
     * @return rule used for update.
     */
    public BusinessRule updateBusinessRule(BusinessRule rule);

    /**
     * Delete BusinessRule from database.
     * 
     * @param rule
     *            A business rule to delete from database.
     * @return true on success
     */
    public boolean deleteBusinessRule(BusinessRule rule);

    /**
     * 
     * This method deletes ruleelement
     * 
     * @param ruleElement
     * @return
     */
    public boolean deleteRuleElement(RuleElement ruleElement);
    
    /**
     * Delete BusinessRule from database.
     * 
     * @param id
     *            ID of a business rule to delete from database.
     * @return true on success
     */
    public boolean deleteBusinessRule(String id);

    /**
     * Finds BusinessRule in database.
     * 
     * @param id
     *            ID of a business rule to locate in database
     * @return found functional business rule or null if element not found.
     */
    public BusinessRule lookupBusinessRuleUsingId(String id);

    /**
     * Finds one or more BusinessRule in database based on given parameters.
     * 
     * @param businessRuleTypeKey
     * @param anchor
     * @return found functional business rules or null if element not found.
     */
    public List<BusinessRule> lookupBusinessRuleUsingAnchor(BusinessRuleTypeKey businessRuleTypeKey, String anchor);

    /**
     * Finds the BusinessRule in database that is active and is currently active
     * 
     * @param businessRuleTypeKey
     * @param anchor
     * @return found functional business rules or null if element not found.
     */
    public List<BusinessRule> lookupCurrentActiveBusinessRuleUsingAnchor(BusinessRuleTypeKey businessRuleTypeKey, String anchor);
    
    
    /**
     * 
     * This method returns all version of the same rule
     * 
     * @param firstVersionId
     * @return
     */
    public List<BusinessRule> lookupAllVersions(String originalRuleId);
    
    /**
     * 
     * This method returns all Business Rule instances for a given type key of Business Rule
     * 
     * @param businessRuleTypeKey
     * @return
     */
    public List<String> lookupBusinessRuleIdUsingRuleTypeKey(BusinessRuleTypeKey businessRuleTypeKey);
    
    /**
     * This method finds all the anchors uses for a given anchor type
     * 
     * @param anchorTypeKey
     * @return
     */
    public List<String> lookupAnchorByAnchorType(AnchorTypeKey anchorTypeKey);
}
