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

import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
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

    /**
     * Creates a new category in the repository.
     * 
     * @param path Category path
     * @param name Category name
     * @param description Category description
     * @return True if category successfully created, otherwise false
     * @throws CategoryExistsException Thrown if rule set already exists
     * @throws OperationFailedException Thrown if creating category fails
     */
    //@WebMethod
    //public Boolean createCategory(@WebParam(name="path")String path, @WebParam(name="name")String name, @WebParam(name="description")String description) 
    //    throws CategoryExistsException, OperationFailedException, InvalidParameterException;

    /**
     * Removes a category.
     * 
     * @param categoryPath Category path
     * @throws RuleEngineRepositoryException Thrown if removing a category fails
     */
    //@WebMethod
    //@Oneway
    //public void removeCategory(@WebParam(name="path")String path)
    //	throws OperationFailedException, InvalidParameterException;
    
    /**
     * Loads child categories from <code>path</code>.
     * 
     * @param categoryPath Category path
     * @return List of child category names
     * @throws OperationFailedException Thrown if loading child categories fails
     */
    //@WebMethod
    //public List<String> fetchCategories(@WebParam(name="path")String path)
    //	throws OperationFailedException, InvalidParameterException;

    /**
     * Creates, compiles and checks in a rule set into the repository.
     * 
     * @param ruleSet Rule set to create
     * @return Rule set uuid
     * @throws RuleExistsException Thrown if a rule within the rule set already exists
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws OperationFailedException Thrown if compiling a rule set fails
     */
    @WebMethod
    public RuleSetDTO createRuleSet(@WebParam(name="ruleSet")RuleSetDTO ruleSet) 
    	throws RuleSetExistsException, RuleExistsException, OperationFailedException, InvalidParameterException;

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws OperationFailedException Thrown if removing rule set fails
     */
    @WebMethod
    @Oneway
    public void removeRuleSet(@WebParam(name="uuid")String uuid)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Deletes a rule set snapshot. 
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @throws OperationFailedException Thrown if snapshot fails to be deleted or any other errors occur
     */
    @WebMethod
    @Oneway
    public void removeRuleSetSnapshot(@WebParam(name="ruleSetName")String ruleSetName, @WebParam(name="snapshotName")String snapshotName)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Updates a rule set in the repository and returns a new rule set.
     * 
     * @param ruleSet A rule set to update
     * @throws RuleEngineRepositoryException
     */
    //@WebMethod
    //public RuleSetDTO updateRuleSet(@WebParam(name="ruleSet")RuleSetDTO ruleSet);

    /**
     * Checks in a rule set into the repository.
     * Checkin rule set will create a new version of the rule set.
     * Rule set version is incremented by 1.
     * 
     * @param uuid Rule set uuid
     * @param comment Checkin comments
     * @throws OperationFailedException Thrown if checkin rule set fails
     */
    @WebMethod
    public long checkinRuleSet(@WebParam(name="ruleSetUUID")String ruleSetUUID, @WebParam(name="comment")String comment)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Loads a rule set (including all rules) by UUID from the repository.
     * 
     * @param uuid Rule set uuid
     * @return A rule set
     * @throws OperationFailedException Thrown if loading rule set fails
     */
    @WebMethod
    public RuleSetDTO fetchRuleSet(@WebParam(name="ruleSetUUID")String ruleSetUUID) 
    	throws OperationFailedException, InvalidParameterException; 

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
     * @throws OperationFailedException Thrown if compiling a rule set fails
     */
    @WebMethod
    public byte[] fetchCompiledRuleSet(@WebParam(name="ruleSetUUID")String ruleSetUUID)
    	throws OperationFailedException, InvalidParameterException; 

    /**
     * Creates a new rule set snapshot for deployment and stores it in the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     */
    @WebMethod
    @Oneway
    public void createRuleSetSnapshot(@WebParam(name="ruleSetName")String ruleSetName, @WebParam(name="snapshotName")String snapshotName, @WebParam(name="comment")String comment)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Rebuilds (recompiles) an existing rule set snapshot and stores it in the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     */
    @WebMethod
    @Oneway
    public void rebuildRuleSetSnapshot(@WebParam(name="ruleSetName")String ruleSetName, @WebParam(name="snapshotName")String snapshotName, @WebParam(name="comment")String comment)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Loads a compiled rule set snapshot from the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @return Compiled rule set as a byte array (e.g. <code>org.drools.rule.Package</code>)
     * @throws OperationFailedException Thrown if loading a snapshots fails
     */
    @WebMethod
    public byte[] fetchCompiledRuleSetSnapshot(@WebParam(name="ruleSetName")String ruleSetName, @WebParam(name="snapshotName")String snapshotName)
    	throws OperationFailedException, InvalidParameterException; 

    /**
     * Loads a rule set snapshot.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Rule set's snapshot name
     * @return A rule set snapshot
     * @throws OperationFailedException Thrown if loading a snapshots fails
     */
    @WebMethod
    public RuleSetDTO fetchRuleSetSnapshot(@WebParam(name="ruleSetName")String ruleSetName, @WebParam(name="snapshotName")String snapshotName)
    	throws OperationFailedException, InvalidParameterException; 

    /**
     * Creates a new status if it doesn't already exists.
     * 
     * @param name Status name
     * @return New status uuid
     * @throws OperationFailedException Thrown if creating status fails
     */
    @WebMethod
    public String createState(@WebParam(name="name")String name)
    	throws OperationFailedException, InvalidParameterException;
    
    /**
     * Loads all states.
     * 
     * @return Array of all states (statuses)
     * @throws OperationFailedException Thrown if loading states fails
     */
    @WebMethod
    public String[] fetchStates() 
    	throws OperationFailedException;

    /**
     * Removes a status from the repository.
     * 
     * @param uuid Status name
     * @throws OperationFailedException Thrown if removing status fails
     */
    @WebMethod
    @Oneway
    public void removeState(@WebParam(name="name")String name)
    	throws OperationFailedException, InvalidParameterException;
    
    /**
     * Changes a rule set status by uuid.
     * 
     * @param uuid Rule set uuid
     * @param newState New rule set status
     * @throws OperationFailedException Thrown if changing rule set status fails
     */
    @WebMethod
    @Oneway
    public void changeRuleSetState(@WebParam(name="ruleSetUUID")String ruleSetUUID, @WebParam(name="newState")String newState)
    	throws OperationFailedException, InvalidParameterException;
    
    /**
     * Generates rule engine specific source code from a <code>BusinessRuleContainerDTO</code>.
     *  
     * @param businessRuleContainer A container of business rules
     * @return A rule set
	 * @throws RuleSetTranslatorException Thrown if translating a rule set fails
     */
    @WebMethod
    public RuleSetDTO generateRuleSet(@WebParam(name="businessRuleContainer")BusinessRuleContainerDTO businessRuleContainer) 
    	throws RuleSetTranslatorException, OperationFailedException, InvalidParameterException;
}
