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
package org.kuali.student.brms.repository.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.brms.repository.dto.RuleSetContainerInfo;
import org.kuali.student.brms.repository.dto.RuleSetInfo;
import org.kuali.student.brms.repository.dto.RuleSetVerificationResultInfo;
import org.kuali.student.brms.repository.exceptions.RuleExistsException;
import org.kuali.student.brms.repository.exceptions.RuleSetExistsException;
import org.kuali.student.brms.repository.runtime.RuleRepository;
import org.kuali.student.brms.repository.service.RuleRepositoryService;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleContainerInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.translators.drools.RuleSetTranslatorDroolsImpl;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
/**
 * This is a convenience interface for the rules repository interface.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 */
@WebService(endpointInterface = "org.kuali.student.brms.repository.service.RuleRepositoryService", 
			serviceName = "RuleRepositoryService", 
			portName = "RuleRepositoryService", 
			targetNamespace = "http://student.kuali.org/wsdl/brms/RuleRepository")
@Transactional
public class RuleRepositoryServiceImpl implements RuleRepositoryService {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetTranslatorDroolsImpl.class);
    
    private RuleRepository ruleRepository;
    
    public RuleRepositoryServiceImpl() { }

    public void setRuleRepository(RuleRepository ruleRepository) {
		this.ruleRepository = ruleRepository;
	}

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
    public Boolean createCategory(final String path, final String name, final String description) 
        throws OperationFailedException, MissingParameterException, InvalidParameterException {
    	return this.ruleRepository.createCategory(path, name, description);
    }

    /**
     * Removes a category.
     * 
     * @param categoryPath Category path
     * @throws OperationFailedException Thrown if removing category fails
     * @throws MissingParameterException Thrown if parameter is null or empty
     * @throws IllegalArgumentException If path is invalid
     */
    public void removeCategory(final String path) 
    	throws OperationFailedException, InvalidParameterException, MissingParameterException {
    	this.ruleRepository.removeCategory(path);
    }

    /**
     * Loads child categories from <code>path</code>.
     * 
     * @param categoryPath Category path
     * @return List of child category names
     * @throws OperationFailedException Thrown if fetching category fails
     * @throws MissingParameterException Thrown if parameter is null or empty
     * @throws IllegalArgumentException If path is invalid
     */
    public List<String> getCategories(final String path) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException {
    	return this.ruleRepository.getCategories(path);
	}
	
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
    public RuleSetInfo createRuleSet(final RuleSetInfo ruleSetDTO) 
		throws AlreadyExistsException, OperationFailedException, InvalidParameterException {
    	return this.ruleRepository.createRuleSet(ruleSetDTO);
    }

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws OperationFailedException Thrown if removing rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void removeRuleSet(final String uuid) 
    	throws OperationFailedException, InvalidParameterException {
    	this.ruleRepository.removeRuleSet(uuid);
    }

    /**
     * Deletes a rule set snapshot. 
     * 
     * @param ruleSetUUID Rule set UUID
     * @param snapshotName Snapshot name
     * @throws OperationFailedException Thrown if removing snapshot fails or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void removeRuleSetSnapshot(final String ruleSetUUID, final String snapshotName) 
    	throws OperationFailedException, InvalidParameterException {
    	this.ruleRepository.removeRuleSetSnapshot(ruleSetUUID, snapshotName);
    }
    
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
    public long checkinRuleSet(final String uuid, final String comment)
    	throws OperationFailedException, InvalidParameterException {
    	return this.ruleRepository.checkinRuleSet(uuid, comment);
    }

    /**
     * Loads a rule set (including all rules) by UUID from the repository.
     * 
     * @param uuid Rule set uuid
     * @return A rule set
     * @throws OperationFailedException Thrown if loading rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public RuleSetInfo getRuleSet(final String ruleSetUUID) 
    	throws OperationFailedException, InvalidParameterException {
    	return this.ruleRepository.getRuleSet(ruleSetUUID);
    }

    /**
     * Loads a list of rule sets by tag name.
     * 
     * @param category Category name
     * @return A list of rule sets
     * @throws OperationFailedException Thrown if loading rule set list fails
     */
    public List<RuleSetInfo> getRuleSetsByCategory(final String category)
    	throws OperationFailedException {
    	return this.ruleRepository.getRuleSetsByCategory(category);
    }
     
    /**
     * Creates a new rule set snapshot for deployment and stores it in the repository.
     * 
     * @param ruleSetUUID Rule set UUID
     * @param comment Comments for creating the snapshot
     * @return A new rule set which contains a new UUID
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public RuleSetInfo createRuleSetSnapshot(final String ruleSetUUID, final String snapshotName, final String comment) 
    	throws OperationFailedException, InvalidParameterException {
    	return this.ruleRepository.createRuleSetSnapshot(ruleSetUUID, snapshotName, comment);
    }

    /**
     * Rebuilds (recompiles) an existing rule set snapshot in the repository.
     * 
     * @param ruleSetUUID Rule set UUID
     * @param snapshotName Snapshot name
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void rebuildRuleSetSnapshot(final String ruleSetUUID, final String snapshotName) 
    	throws OperationFailedException, InvalidParameterException {
    	this.ruleRepository.rebuildRuleSetSnapshot(ruleSetUUID, snapshotName);
    }

    /**
     * Replaces an existing rule set snapshot with a new rule set snapshot 
     * in the repository.
     * 
     * @param ruleSetUUID Rule set UUID
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @return A new rule set which contains a new UUID
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public RuleSetInfo replaceRuleSetSnapshot(final String ruleSetUUID, final String snapshotName, final String comment) 
    	throws OperationFailedException, InvalidParameterException {
    	return this.ruleRepository.replaceRuleSetSnapshot(ruleSetUUID, snapshotName, comment);
    }

    /**
     * Loads a rule set snapshot.
     * 
     * @param ruleSetUUID Rule set UUID
     * @param snapshotName Rule set's snapshot name
     * @return A rule set snapshot
     * @throws OperationFailedException Thrown if loading a snapshots fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public RuleSetInfo getRuleSetSnapshot(final String ruleSetUUID, final String snapshotName) 
    	throws OperationFailedException, InvalidParameterException {
    	return this.ruleRepository.getRuleSetSnapshot(ruleSetUUID, snapshotName);
    }

    /**
     * Loads a list of rule set snapshots by tag name.
     * 
     * @param category Category name
     * @return A list of rule sets
     */
    public List<RuleSetInfo> getRuleSetSnapshotsByCategory(final String category)
		throws OperationFailedException {
    	return this.ruleRepository.getRuleSetSnapshotsByCategory(category);
    }

    /**
     * Creates a new status if it doesn't already exists.
     * 
     * @param name Status name
     * @return New status uuid
     * @throws OperationFailedException Thrown if creating status fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public String createState(final String name)
    	throws OperationFailedException, InvalidParameterException {
    	return this.ruleRepository.createState(name);
    }
    
    /**
     * Loads all states.
     * 
     * @return List of all states
     * @throws OperationFailedException Thrown if loading states fails
     */
    public List<String> getStates()
    	throws OperationFailedException {
    	return this.ruleRepository.getStates();
    }

    /**
     * Removes a status from the repository.
     * 
     * @param uuid Status name
     * @throws OperationFailedException Thrown if removing status fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void removeState(final String name)
    	throws OperationFailedException, InvalidParameterException {
    	this.ruleRepository.removeState(name);
    }

    /**
     * Changes a rule set status by uuid.
     * 
     * @param uuid Rule set uuid
     * @param newState New rule set status
     * @throws OperationFailedException Thrown if changing rule set status fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void changeRuleSetState(final String ruleSetUUID, final String newState)
    	throws OperationFailedException, InvalidParameterException {
    	this.ruleRepository.changeRuleSetState(ruleSetUUID, newState);
    }

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
    public RuleSetInfo generateRuleSetForBusinessRule(final BusinessRuleInfo businessRule) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException {
    	return this.ruleRepository.generateRuleSetForBusinessRule(businessRule);
    }

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
    public RuleSetContainerInfo generateRuleSetForBusinessRuleContainer(final BusinessRuleContainerInfo businessRuleContainer) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException {
    	return this.ruleRepository.generateRuleSetForBusinessRuleContainer(businessRuleContainer);
    }

    /**
     * Validates that a business rule can be translated and compiled into an
     * vendor specific executable rule (E.g. Drools compiled rule).
     * 
     * @param businessRule A Business Rule
     * @return A validation response 
     * @throws OperationFailedException Thrown if translating/generating rule set fails
	 * @throws MissingParameterException Thrown if parameter is missing
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public RuleSetVerificationResultInfo validateBusinessRule(final BusinessRuleInfo businessRule) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException {
    	return this.ruleRepository.validateBusinessRule(businessRule);
    }

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
    public RuleSetInfo translateBusinessRule(final BusinessRuleInfo businessRule) 
		throws OperationFailedException, MissingParameterException, InvalidParameterException {
    	return this.ruleRepository.translateBusinessRule(businessRule);
    }
}
