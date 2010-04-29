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

package org.kuali.student.brms.repository.runtime;

import java.util.List;

import org.kuali.student.brms.repository.dto.RuleSetContainerInfo;
import org.kuali.student.brms.repository.dto.RuleSetInfo;
import org.kuali.student.brms.repository.dto.RuleSetVerificationResultInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleContainerInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;

/**
 * This is the rule engine runtime repository interface.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public interface RuleRepository {
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
    public Boolean createCategory(String path, String name, String description) 
        throws OperationFailedException, MissingParameterException, InvalidParameterException;

    /**
     * Removes a category.
     * 
     * @param categoryPath Category path
     * @throws OperationFailedException Thrown if removing category fails
     * @throws MissingParameterException Thrown if parameter is null or empty
     * @throws IllegalArgumentException If path is invalid
     */
    public void removeCategory(String path) 
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
    public List<String> getCategories(String path) 
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
    public RuleSetInfo createRuleSet(RuleSetInfo ruleSet) 
		throws AlreadyExistsException, OperationFailedException, InvalidParameterException;

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws OperationFailedException Thrown if removing rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void removeRuleSet(String uuid)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Deletes a rule set snapshot. 
     * 
     * @param ruleSetUUID Rule set UUID
     * @param snapshotName Snapshot name
     * @throws OperationFailedException Thrown if removing snapshot fails or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void removeRuleSetSnapshot(String ruleSetUUID, String snapshotName)
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
    public long checkinRuleSet(String ruleSetUUID, String comment)
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
    public RuleSetInfo getRuleSet(String ruleSetUUID) 
    	throws OperationFailedException, InvalidParameterException; 

    /**
     * Loads a list of rule sets by tag name.
     * 
     * @param category Category name
     * @return A list of rule sets
     * @throws OperationFailedException Thrown if loading rule set list fails
     */
    public List<RuleSetInfo> getRuleSetsByCategory(String category)
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
    public RuleSetInfo createRuleSetSnapshot(String ruleSetUUID, String snapshotName, String comment)
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
    public RuleSetInfo replaceRuleSetSnapshot(String ruleSetUUID, String snapshotName, String comment)
    	throws OperationFailedException, InvalidParameterException;

    /**
     * Rebuilds (recompiles) an existing rule set snapshot in the repository.
     * 
     * @param ruleSetUUID Rule set UUID
     * @param snapshotName Snapshot name
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void rebuildRuleSetSnapshot(String ruleSetUUID, String snapshotName) 
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
    public RuleSetInfo getRuleSetSnapshot(String ruleSetUUID, String snapshotName)
    	throws OperationFailedException, InvalidParameterException; 

    /**
     * Loads a list of rule set snapshots by tag name.
     * 
     * @param category Category name
     * @return A list of rule sets
     */
    public List<RuleSetInfo> getRuleSetSnapshotsByCategory(String category)
		throws OperationFailedException; 

    /**
     * Creates a new status if it doesn't already exists.
     * 
     * @param name Status name
     * @return New status uuid
     * @throws OperationFailedException Thrown if creating status fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public String createState(String name)
    	throws OperationFailedException, InvalidParameterException;
    
    /**
     * Loads all states.
     * 
     * @return List of all states
     * @throws OperationFailedException Thrown if loading states fails
     */
    public List<String> getStates() 
    	throws OperationFailedException;

    /**
     * Removes a status from the repository.
     * 
     * @param uuid Status name
     * @throws OperationFailedException Thrown if removing status fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void removeState(String name)
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
    public void changeRuleSetState(String ruleSetUUID, String newState)
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
    public RuleSetInfo generateRuleSetForBusinessRule(BusinessRuleInfo businessRule)
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
    public RuleSetContainerInfo generateRuleSetForBusinessRuleContainer(BusinessRuleContainerInfo businessRuleContainer) 
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
    public RuleSetVerificationResultInfo validateBusinessRule(BusinessRuleInfo businessRule) 
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
    public RuleSetInfo translateBusinessRule(BusinessRuleInfo businessRule) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException;
}
