/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.repository.service;

import java.util.List;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.exceptions.CategoryExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.rules.repository.exceptions.RuleExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetExistsException;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;

/**
 * This is the rule engine runtime repository interface.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
@WebService(name = "RuleRepositoryService",
			targetNamespace = "http://student.kuali.org/wsdl/brms/RuleRepository")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, 
			 use = SOAPBinding.Use.LITERAL, 
			 parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface RuleRepositoryService {

	//TODO - Remove this method
    //@WebMethod
    //@Oneway
    //public void clearRepository();

    /**
     * Creates a new category in the repository.
     * 
     * @param path Category path
     * @param name Category name
     * @param description Category description
     * @return True if category successfully created, otherwise false
     * @throws CategoryExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException
     */
    @WebMethod
    public Boolean createCategory(@WebParam(name="path")String path, @WebParam(name="name")String name, @WebParam(name="description")String description) 
        throws CategoryExistsException;

    /**
     * Removes a category.
     * 
     * @param categoryPath Category path
     */
    @WebMethod
    @Oneway
    public void removeCategory(@WebParam(name="path")String path);
    
    @WebMethod
    public List<String> fetchChildCategories(@WebParam(name="path")String path) 
	    throws CategoryExistsException;

    /**
     * Creates, compiles and checks in a rule set into the repository.
     * 
     * @param ruleSet Rule set to create
     * @return Rule set uuid
     * @throws RuleExistsException Thrown if a rule within the rule set already exists
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException
     */
    @WebMethod
    public RuleSetDTO createRuleSet(@WebParam(name="ruleSet")RuleSetDTO ruleSet) throws RuleSetExistsException, RuleExistsException;

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws RuleEngineRepositoryException Thrown if removing rule set fails
     */
    @WebMethod
    @Oneway
    public void removeRuleSet(@WebParam(name="uuid")String uuid);

    /**
     * Deletes a rule set snapshot. 
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @throws RuleEngineRepositoryException Thrown if snapshot fails to be deleted or any other errors occur
     */
    @WebMethod
    @Oneway
    public void removeRuleSetSnapshot(@WebParam(name="ruleSetName")String ruleSetName, @WebParam(name="snapshotName")String snapshotName);

    /**
     * Updates a rule set in the repository and returns a new rule set.
     * 
     * @param ruleSet A rule set to update
     * @throws RuleEngineRepositoryException
     */
    //@WebMethod
    //public RuleSetDTO updateRuleSet(@WebParam(name="ruleSet")RuleSetDTO ruleSet) throws RuleEngineRepositoryException;

    /**
     * Checks in a rule set into the repository.
     * Checkin rule set will create a new version of the rule set.
     * Rule set version is incremented by 1.
     * 
     * @param uuid Rule set uuid
     * @param comment Checkin comments
     * @throws RuleEngineRepositoryException
     */
    @WebMethod
    public long checkinRuleSet(@WebParam(name="ruleSetUUID")String ruleSetUUID, @WebParam(name="comment")String comment) throws RuleEngineRepositoryException;

    /**
     * Loads a rule set (including all rules) by UUID from the repository.
     * 
     * @param uuid Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    @WebMethod
    public RuleSetDTO fetchRuleSet(@WebParam(name="ruleSetUUID")String ruleSetUUID) throws RuleEngineRepositoryException;

    /**
     * Loads a rule by uuid.
     * 
     * @param uuid Rule uuid
     * @return A rule
     * @throws RuleEngineRepositoryException Thrown if loading rule fails
     */
    //@WebMethod
    //public RuleDTO fetchRule(@WebParam(name="uuid")String uuid);
    
    /**
     * Loads a compiled rule set from the repository.
     * 
     * @param ruleSetUUID Rule set uuid
     * @return A compiled rule set as a byte array (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    @WebMethod
    public byte[] fetchCompiledRuleSet(@WebParam(name="ruleSetUUID")String ruleSetUUID) throws RuleEngineRepositoryException;

    /**
     * Creates a new rule set snapshot for deployment and stores it in the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @throws RuleEngineRepositoryException
     */
    @WebMethod
    @Oneway
    public void createRuleSetSnapshot(@WebParam(name="ruleSetName")String ruleSetName, @WebParam(name="snapshotName")String snapshotName, @WebParam(name="comment")String comment);

    /**
     * Rebuilds (recompiles) an existing rule set snapshot and stores it in the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @throws RuleEngineRepositoryException
     */
    @WebMethod
    @Oneway
    public void rebuildRuleSetSnapshot(@WebParam(name="ruleSetName")String ruleSetName, @WebParam(name="snapshotName")String snapshotName, @WebParam(name="comment")String comment);

    /**
     * Loads a compiled rule set snapshot from the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @return Compiled rule set as a byte array (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    @WebMethod
    public byte[] fetchCompiledRuleSetSnapshot(@WebParam(name="ruleSetName")String ruleSetName, @WebParam(name="snapshotName")String snapshotName) throws RuleEngineRepositoryException;

    /**
     * Loads a rule set snapshot.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Rule set's snapshot name
     * @return A rule set snapshot
     */
    @WebMethod
    public RuleSetDTO fetchRuleSetSnapshot(@WebParam(name="ruleSetName")String ruleSetName, @WebParam(name="snapshotName")String snapshotName) throws RuleEngineRepositoryException;

    /**
     * Creates a new status if it doesn't already exists.
     * 
     * @param name Status name
     * @return New status uuid
     */
    @WebMethod
    public String createState(@WebParam(name="name")String name);
    
    /**
     * Loads all states.
     * 
     * @return Array of all states (statuses)
     */
    @WebMethod
    public String[] fetchStates();

    /**
     * Removes a status from the repository.
     * 
     * @param uuid Status name
     */
    @WebMethod
    @Oneway
    public void removeState(@WebParam(name="name")String name);
    
    /**
     * Changes a rule set status by uuid.
     * 
     * @param uuid Rule set uuid
     * @param newState New rule set status
     */
    @WebMethod
    @Oneway
    public void changeRuleSetState(@WebParam(name="ruleSetUUID")String ruleSetUUID, @WebParam(name="newState")String newState);
    
    @WebMethod
    public RuleSetDTO generateRuleSet(@WebParam(name="businessRuleContainer")BusinessRuleContainerDTO businessRuleContainer) throws RuleSetTranslatorException;
}
