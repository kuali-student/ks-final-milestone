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

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.repository.dto.RuleSetContainerDTO;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.dto.RuleSetVerificationResultDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;

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
     * @throws OperationFailedException Thrown if creating category fails or category already exists
     * @throws MissingParameterException Thrown if parameters are null or empty
     * @throws IllegalArgumentException If path and/or name is invalid
     */
    @WebMethod
    public Boolean createCategory(
    		final String path, 
    		final String name, 
    		final String description) 
        throws OperationFailedException, MissingParameterException, InvalidParameterException;

    /**
     * Removes a category.
     * 
     * @param categoryPath Category path
     * @throws OperationFailedException Thrown if removing category fails
     * @throws MissingParameterException Thrown if parameter is null or empty
     * @throws IllegalArgumentException If path is invalid
     */
    @WebMethod
    @Oneway
    public void removeCategory(final String path) 
    	throws OperationFailedException, InvalidParameterException, MissingParameterException;
    
    /**
     * Loads child categories from <code>path</code>.
     * 
     * @param categoryPath Category path
     * @return List of child category names
     * @throws OperationFailedException Thrown if fetching category fails
     * @throws MissingParameterException Thrown if parameter is null or empty
     * @throws IllegalArgumentException If path is invalid
     */
    @WebMethod
    public List<String> fetchCategories(final String path) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException;

    /**
     * Creates, compiles and checks in a rule set into the repository.
     * 
     * @param ruleSet Rule set to create
     * @return New rule set
     * @throws RuleExistsException Thrown if a rule within the rule set already exists
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws OperationFailedException Thrown if compiling a rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    public RuleSetDTO createRuleSet(@WebParam(name="ruleSet")RuleSetDTO ruleSet) 
		throws AlreadyExistsException, OperationFailedException, InvalidParameterException;

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws OperationFailedException Thrown if removing rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    @Oneway
    public void removeRuleSet(@WebParam(name="uuid")String uuid)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Deletes a rule set snapshot. 
     * 
     * @param ruleSetUUID Rule set UUID
     * @param snapshotName Snapshot name
     * @throws OperationFailedException Thrown if removing snapshot fails or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    @Oneway
    public void removeRuleSetSnapshot(
    		@WebParam(name="ruleSetUUID")String ruleSetUUID, 
    		@WebParam(name="snapshotName")String snapshotName)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Checks in a rule set into the repository.
     * Checkin rule set will create a new version of the rule set.
     * Rule set version is incremented by 1.
     * 
     * @param uuid Rule set uuid
     * @param comment Checkin comments
     * @return New version number
     * @throws OperationFailedException Thrown if checkin rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    public long checkinRuleSet(
    		@WebParam(name="ruleSetUUID")String ruleSetUUID, 
    		@WebParam(name="comment")String comment)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Loads a rule set (including all rules) by UUID from the repository.
     * Loading a rule set by UUID will always return the latest version of a
     * rule set snapshot version.
     * 
     * @param uuid Rule set uuid
     * @return A rule set
     * @throws OperationFailedException Thrown if loading rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    public RuleSetDTO fetchRuleSet(@WebParam(name="ruleSetUUID")String ruleSetUUID) 
    	throws OperationFailedException, InvalidParameterException; 

    /**
     * Loads a list of rule sets by tag name.
     * 
     * @param category Category name
     * @return A list of rule sets
     * @throws OperationFailedException Thrown if loading rule set list fails
     */
    @WebMethod
    public List<RuleSetDTO> fetchRuleSetsByCategory(@WebParam(name="category")String category)
    	throws OperationFailedException;

    /**
     * Creates a new rule set snapshot for deployment and stores it in the repository.
     * 
     * @param ruleSetUUID Rule set UUID
     * @param comment Comments for creating the snapshot
     * @return A new rule set which contains a new UUID
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    public RuleSetDTO createRuleSetSnapshot(
    		@WebParam(name="ruleSetUUID")String ruleSetUUID, 
    		@WebParam(name="snapshotName")String snapshotName, 
    		@WebParam(name="comment")String comment)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Replaces an existing rule set snapshot in the repository.
     * 
     * @param ruleSetUUID Rule set uuid
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @return A new rule set which contains a new UUID
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    public RuleSetDTO replaceRuleSetSnapshot(
    		@WebParam(name="ruleSetUUID")String ruleSetUUID, 
    		@WebParam(name="snapshotName")String snapshotName, 
    		@WebParam(name="comment")String comment)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Rebuilds (recompiles) an existing rule set snapshot and in the repository.
     * 
     * @param ruleSetUUID Rule set UUID
     * @param snapshotName Snapshot name
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void rebuildRuleSetSnapshot(
    		@WebParam(name="ruleSetUUID")String ruleSetUUID, 
    		@WebParam(name="snapshotName")String snapshotName) 
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Loads a rule set snapshot.
     * 
     * @param ruleSetUUID Rule set UUID
     * @param snapshotName Rule set's snapshot name
     * @return A rule set snapshot
     * @throws OperationFailedException Thrown if loading a snapshots fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    public RuleSetDTO fetchRuleSetSnapshot(
    		@WebParam(name="ruleSetUUID")String ruleSetUUID, 
    		@WebParam(name="snapshotName")String snapshotName)
    	throws OperationFailedException, InvalidParameterException; 

    /**
     * Loads a list of rule set snapshots by tag name.
     * 
     * @param category Category name
     * @return A list of rule sets
     */
    @WebMethod
    public List<RuleSetDTO> fetchRuleSetSnapshotsByCategory(@WebParam(name="category")String category)
		throws OperationFailedException; 

    /**
     * Creates a new status if it doesn't already exists.
     * 
     * @param name Status name
     * @return New status uuid
     * @throws OperationFailedException Thrown if creating status fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
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
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    @Oneway
    public void removeState(@WebParam(name="name")String name)
    	throws OperationFailedException, InvalidParameterException;
    
    /**
     * Changes a rule set status by UUID. If changing a rule set snapshot
     * then only the last snapshot version's state will be changed.
     * 
     * @param uuid Rule set uuid
     * @param newState New rule set status
     * @throws OperationFailedException Thrown if changing rule set status fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    @Oneway
    public void changeRuleSetState(
    		@WebParam(name="ruleSetUUID")String ruleSetUUID, 
    		@WebParam(name="newState")String newState)
    	throws OperationFailedException, InvalidParameterException;
    
    /**
     * Generates and creates or updates a rule set (rule engine specific source code) 
     * from a <code>BusinessRuleContainerDTO</code>.
     *  
     * @param businessRule A business rule
     * @return A rule set
     * @throws OperationFailedException Thrown if translating/generating rule set fails
	 * @throws MissingParameterException Thrown if parameter is missing
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod(operationName="generateRuleSetForBusinessRule")
    public RuleSetDTO generateRuleSetForBusinessRule(@WebParam(name="businessRule")BusinessRuleInfoDTO businessRule)
		throws OperationFailedException, MissingParameterException, InvalidParameterException;

    /**
     * Generates and creates or updates a rule set (rule engine specific source code) 
     * from a <code>businessRuleContainer</code>.
     *  
     * @param businessRuleContainer A container of business rules
     * @return A rule set container with a list of rule sets
     * @throws OperationFailedException Thrown if translating/generating rule set fails
	 * @throws MissingParameterException Thrown if parameter is missing
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod(operationName="generateRuleSetForBusinessRuleContainer")
    public RuleSetContainerDTO generateRuleSetForBusinessRuleContainer(@WebParam(name="businessRuleContainer")BusinessRuleContainerDTO businessRuleContainer) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException;

    /**
     * Validates that a business rule can be translated and compiled into an
     * vendor specific executable rule (E.g. Drools compiled rule).
     * 
     * @param businessRule A Business Rule
     * @return A rule set verification result 
     * @throws OperationFailedException Thrown if translating/generating rule set fails
	 * @throws MissingParameterException Thrown if parameter is missing
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    public RuleSetVerificationResultDTO validateBusinessRule(@WebParam(name="businessRule")BusinessRuleInfoDTO businessRule) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException;
}
