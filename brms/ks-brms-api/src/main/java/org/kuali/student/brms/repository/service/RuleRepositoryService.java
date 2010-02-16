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
package org.kuali.student.brms.repository.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.brms.repository.dto.RuleSetContainerInfo;
import org.kuali.student.brms.repository.dto.RuleSetInfo;
import org.kuali.student.brms.repository.dto.RuleSetVerificationResultInfo;
import org.kuali.student.brms.repository.runtime.RuleRepository;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleContainerInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;

/**
 * <b>IMPORTANT:</b> This service contract is currently under development. If you are planning to implement the Kuali Student System or parts thereof, <b>please do not consider this service to be final!</b> Consult this page for status before making any plans that rely on specific implementations of these services.</p>
 * 
 * <h3><a name="KSDOC-ServiceDescriptions-Description"></a>Description</h3>
 * 
 * <p>The Repository Service is the storage service for compiled business rules from the Business Rules Management Service (BRMS). The service is essentially a wrapper of a "Jackrabbit" repository, which is a storage and packaging tool used by Drools (JBoss Rules).</p>
 * 
 * <h3><a name="KSDOC-ServiceDescriptions-Assumptions"></a>Assumptions</h3>
 * 
 * <p>The design of this service considers the following assumptions:</p>
 * <ul>
 * 	<li>This system is the system of record for all executable business rules maintained through the BRMS</li>
 * 	<li>All maintenance of business rules should occur through the BRMS. Directly modifying rules via the repository would produce synchronization errors with BRMS</li>
 * 
 * </ul>
 * 
 * @author Kuali Student Team
 *
 */
@WebService(name = "RuleRepositoryService",
			targetNamespace = "http://student.kuali.org/wsdl/brms/RuleRepository")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, 
			 use = SOAPBinding.Use.LITERAL, 
			 parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface RuleRepositoryService extends RuleRepository {

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
    		@WebParam(name="path")String path, 
    		@WebParam(name="name")String name, 
    		@WebParam(name="description")String description) 
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
    public void removeCategory(@WebParam(name="path")String path) 
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
    public List<String> getCategories(@WebParam(name="path")String path) 
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
    public RuleSetInfo createRuleSet(@WebParam(name="ruleSet")RuleSetInfo ruleSet) 
		throws AlreadyExistsException, OperationFailedException, InvalidParameterException;

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws OperationFailedException Thrown if removing rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
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
    public RuleSetInfo getRuleSet(@WebParam(name="ruleSetUUID")String ruleSetUUID) 
    	throws OperationFailedException, InvalidParameterException; 

    /**
     * Loads a list of rule sets by tag name.
     * 
     * @param category Category name
     * @return A list of rule sets
     * @throws OperationFailedException Thrown if loading rule set list fails
     */
    @WebMethod
    public List<RuleSetInfo> getRuleSetsByCategory(@WebParam(name="category")String category)
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
    public RuleSetInfo createRuleSetSnapshot(
    		@WebParam(name="ruleSetUUID")String ruleSetUUID, 
    		@WebParam(name="snapshotName")String snapshotName, 
    		@WebParam(name="comment")String comment)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Replaces an existing rule set snapshot with a new rule set snapshot 
     * in the repository.
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
    public RuleSetInfo replaceRuleSetSnapshot(
    		@WebParam(name="ruleSetUUID")String ruleSetUUID, 
    		@WebParam(name="snapshotName")String snapshotName, 
    		@WebParam(name="comment")String comment)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Rebuilds (recompiles) an existing rule set snapshot in the repository.
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
    public RuleSetInfo getRuleSetSnapshot(
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
    public List<RuleSetInfo> getRuleSetSnapshotsByCategory(@WebParam(name="category")String category)
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
     * @return List of all states
     * @throws OperationFailedException Thrown if loading states fails
     */
    @WebMethod
    public List<String> getStates() 
    	throws OperationFailedException;

    /**
     * Removes a status from the repository.
     * 
     * @param uuid Status name
     * @throws OperationFailedException Thrown if removing status fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
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
    public void changeRuleSetState(
    		@WebParam(name="ruleSetUUID")String ruleSetUUID, 
    		@WebParam(name="newState")String newState)
    	throws OperationFailedException, InvalidParameterException;
    
    /**
     * Generates and creates or updates a rule set (rule engine specific source code) 
     * from a <code>BusinessRuleContainerInfo</code>.
     *  
     * @param businessRule A business rule
     * @return A rule set
     * @throws OperationFailedException Thrown if translating/generating rule set fails
	 * @throws MissingParameterException Thrown if parameter is missing
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod(operationName="generateRuleSetForBusinessRule")
    public RuleSetInfo generateRuleSetForBusinessRule(@WebParam(name="businessRule")BusinessRuleInfo businessRule)
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
    public RuleSetContainerInfo generateRuleSetForBusinessRuleContainer(@WebParam(name="businessRuleContainer")BusinessRuleContainerInfo businessRuleContainer) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException;

    /**
     * Validates that a business rule can be translated and compiled into an
     * vendor specific executable rule (E.g. Drools compiled rule).
     * 
     * @param businessRule A Business rule
     * @return A rule set verification result 
     * @throws OperationFailedException Thrown if translating/generating rule set fails
	 * @throws MissingParameterException Thrown if parameter is missing
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    public RuleSetVerificationResultInfo validateBusinessRule(@WebParam(name="businessRule")BusinessRuleInfo businessRule) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException;

    /**
     * Translates a business rule into a rule set.
     * The rule set content contains the vendor specific rule source code
     * (e.g. Drools source code). 
     * Rule set is not saved to the rule repository.
     * 
     * @param businessRule A Business rule
     * @return A rule set
     * @throws OperationFailedException Thrown if translating rule set fails
	 * @throws MissingParameterException Thrown if parameter is missing
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    @WebMethod
    public RuleSetInfo translateBusinessRule(@WebParam(name="businessRule")BusinessRuleInfo businessRule) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException;
}
