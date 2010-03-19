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
package org.kuali.student.brms.repository.runtime;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.brms.repository.RuleEngineRepository;
import org.kuali.student.brms.repository.dto.RuleSetContainerInfo;
import org.kuali.student.brms.repository.dto.RuleSetInfo;
import org.kuali.student.brms.repository.dto.RuleSetVerificationResultInfo;
import org.kuali.student.brms.repository.exceptions.CategoryExistsException;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.exceptions.RuleExistsException;
import org.kuali.student.brms.repository.exceptions.RuleSetExistsException;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;
import org.kuali.student.brms.repository.service.RuleAdapter;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleContainerInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.translators.RuleSetTranslator;
import org.kuali.student.brms.translators.RuleSetValidator;
import org.kuali.student.brms.translators.RuleSetVerificationResult;
import org.kuali.student.brms.translators.drools.RuleSetTranslatorDroolsImpl;
import org.kuali.student.brms.translators.exceptions.RuleSetTranslatorException;
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
@Transactional
public class RuleRepositoryImpl implements RuleRepository {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetTranslatorDroolsImpl.class);
    
	private final static RuleAdapter ruleAdapter = RuleAdapter.getInstance();
	
	/** Drools rule repository */
    private RuleEngineRepository ruleEngineRepository;
    
    private RuleSetTranslator ruleSetTranslator;

    private RuleSetValidator ruleSetValidator;
    
    public RuleRepositoryImpl() { }

	/**
     * Sets the rule engine repository.
	 * 
	 * @param ruleEngineRepository Rule engine repository
	 */
	public void setRuleEngineRepository(final RuleEngineRepository ruleEngineRepository) {
		this.ruleEngineRepository = ruleEngineRepository;
	}

	/**
	 * Sets the rule set translator.
	 * 
	 * @param ruleSetTranslator
	 */
	public void setRuleSetTranslator(final RuleSetTranslator ruleSetTranslator) {
		this.ruleSetTranslator = ruleSetTranslator;
	}

	/**
	 * Sets the rule set validator.
	 * 
	 * @param ruleSetValidator Rule set validator
	 */
	public void setRuleSetValidator(final RuleSetValidator ruleSetValidator) {
		this.ruleSetValidator = ruleSetValidator;
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
    	if (path == null || path.isEmpty()) {
    		throw new MissingParameterException("Path cannot be null or empty");
    	} else if (name == null || name.isEmpty()) {
    		throw new MissingParameterException("Name cannot be null or empty");
    	}
    	try {
			return this.ruleEngineRepository.createCategory(path, name, description);
		} catch(CategoryExistsException e) {
			logger.error(e.getMessage(), e);
			throw new OperationFailedException(e.getMessage());
		} catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
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
    	if (path == null || path.isEmpty()) {
    		throw new MissingParameterException("Path cannot be null or empty");
    	}

    	try {
			this.ruleEngineRepository.removeCategory(path);
		} catch (RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
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
    	if (path == null || path.isEmpty()) {
    		throw new MissingParameterException("Path cannot be null or empty");
    	}

    	try {
		    return this.ruleEngineRepository.loadChildCategories(path);
		} catch (RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
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
    	try {
	    	RuleSet ruleSet = ruleAdapter.getRuleSet(ruleSetDTO);
	        RuleSet newRuleSet = this.ruleEngineRepository.createRuleSet(ruleSet);
	        RuleSetInfo dto = ruleAdapter.getRuleSetDTO(newRuleSet);
	        return dto;
		} catch (RuleSetExistsException e) {
			logger.error(e.getMessage(), e);
			throw new AlreadyExistsException(e.getMessage());
		} catch (RuleExistsException e) {
			logger.error(e.getMessage(), e);
			throw new AlreadyExistsException(e.getMessage());
		} catch (RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
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
    	try {
	    	this.ruleEngineRepository.removeRuleSet(uuid);
		} catch (RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Deletes a rule set snapshot. 
     * 
     * @param ruleSetUUID Rule set UUID
     * @param snapshotName Snapshot name
     * @throws OperationFailedException Thrown if removing snapshot fails or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void removeRuleSetSnapshot(
    		final String ruleSetUUID, 
    		final String snapshotName) 
    	throws OperationFailedException, InvalidParameterException {
    	try {
        	RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
        	String ruleSetName = ruleSet.getName();
	    	this.ruleEngineRepository.removeRuleSetSnapshot(ruleSetName, snapshotName);
		} catch (RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
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
    	try {
	        return this.ruleEngineRepository.checkinRuleSet(uuid, comment);
		} catch (RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
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
        try {
	    	RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
	        RuleSetInfo dto = ruleAdapter.getRuleSetDTO(ruleSet);
	        return dto;
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
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
        try {
	    	List<RuleSet> list = this.ruleEngineRepository.loadRuleSetsByCategory(category);
	        List<RuleSetInfo> dtoList = new ArrayList<RuleSetInfo>(list.size());
	    	for(RuleSet ruleSet : list) {
		    	RuleSetInfo dto = ruleAdapter.getRuleSetDTO(ruleSet);
		    	dtoList.add(dto);
	        }
	        return dtoList;
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		}
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
    public RuleSetInfo createRuleSetSnapshot(
    		final String ruleSetUUID, 
    		final String snapshotName, 
    		final String comment) 
    	throws OperationFailedException, InvalidParameterException {
        try {
        	RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
        	String ruleSetName = ruleSet.getName();
        	RuleSet ruleSetSnapshot = this.ruleEngineRepository.createRuleSetSnapshot(ruleSetName, snapshotName, comment);
        	return ruleAdapter.getRuleSetDTO(ruleSetSnapshot);
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Rebuilds (recompiles) an existing rule set snapshot in the repository.
     * 
     * @param ruleSetUUID Rule set UUID
     * @param snapshotName Snapshot name
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void rebuildRuleSetSnapshot(
    		final String ruleSetUUID, 
    		final String snapshotName) 
    	throws OperationFailedException, InvalidParameterException {
        try {
        	RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
        	String ruleSetName = ruleSet.getName();
	        this.ruleEngineRepository.rebuildRuleSetSnapshot(ruleSetName, snapshotName);
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
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
    public RuleSetInfo replaceRuleSetSnapshot(
    		final String ruleSetUUID, 
    		final String snapshotName, 
    		final String comment) 
    	throws OperationFailedException, InvalidParameterException {
        try {
        	RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
        	String ruleSetName = ruleSet.getName();
	        RuleSet ruleSetSnapshot = this.ruleEngineRepository.replaceRuleSetSnapshot(ruleSetName, snapshotName, comment);
        	return ruleAdapter.getRuleSetDTO(ruleSetSnapshot);
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
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
        try {
        	RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
        	String ruleSetName = ruleSet.getName();
	        RuleSet ruleSetSnapshot = this.ruleEngineRepository.loadRuleSetSnapshot(ruleSetName, snapshotName);
	        RuleSetInfo dto = ruleAdapter.getRuleSetDTO(ruleSetSnapshot);
	        return dto;
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Loads a list of rule set snapshots by tag name.
     * 
     * @param category Category name
     * @return A list of rule sets
     */
    public List<RuleSetInfo> getRuleSetSnapshotsByCategory(final String category)
		throws OperationFailedException {
        try {
	        List<RuleSet> list = this.ruleEngineRepository.loadRuleSetSnapshotsByCategory(category);
	        List<RuleSetInfo> dtoList = new ArrayList<RuleSetInfo>(list.size());
	    	for(RuleSet ruleSet : list) {
		    	RuleSetInfo dto = ruleAdapter.getRuleSetDTO(ruleSet);
		    	dtoList.add(dto);
	        }
	        return dtoList;
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		}
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
        try {
	    	return this.ruleEngineRepository.createStatus(name);
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
    }
    
    /**
     * Loads all states.
     * 
     * @return List of all states
     * @throws OperationFailedException Thrown if loading states fails
     */
    public List<String> getStates()
    	throws OperationFailedException {
        try {
	    	return this.ruleEngineRepository.loadStates();
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
        }
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
        try {
	    	this.ruleEngineRepository.removeStatus(name);
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
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
        try {
	    	this.ruleEngineRepository.changeRuleSetStatus(ruleSetUUID, newState);
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Log rule set information
     * 
     * @param ruleSet Ruleset to log
     * @param msg A logging message
     */
    private void log(RuleSet ruleSet, String msg) {
		if (logger.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("\n"+msg);
			sb.append("\nRuleSet.uuid:                "+ruleSet.getUUID());
			sb.append("\nRuleSet.name:                "+ruleSet.getName());
			sb.append("\nRuleSet.status:              "+ruleSet.getStatus());
			sb.append("\nRuleSet.snapshotName:        "+ruleSet.getSnapshotName());
			sb.append("\nRuleSet.version:             "+ruleSet.getVersionNumber());
			sb.append("\nRuleSet.versionSnapshotUUID: "+ruleSet.getVersionSnapshotUUID());
			logger.info(sb.toString());
		}
		if (logger.isDebugEnabled()) {
			logger.info(ruleSet.getContent());
		}
    }

    /**
     * Updates ruleset with new rules from <code>ruleSet1</code> and 
     * returns the updated ruleset.
     *  
     * @param ruleSet1 Ruleset of new rules
     * @return Updated ruleset
     * @throws RuleEngineRepositoryException
     */
    private RuleSet updateRuleSet(RuleSet ruleSet1) 
    	throws RuleEngineRepositoryException {
		RuleSet ruleSet2 = this.ruleEngineRepository.loadRuleSet(ruleSet1.getUUID());
		// Add headers
		ruleSet2.clearHeaders();
		for(String header : ruleSet1.getHeaderList()) {
			ruleSet2.addHeader(header);
		}
		// Add rules
		ruleSet2.clearRules();
		for(Rule rule : ruleSet1.getRules()) {
			ruleSet2.addRule(rule);
		}

		// Update rule set
		return this.ruleEngineRepository.updateRuleSet(ruleSet2);
    }

    /**
     * Changes the rule set state and rule state if they have been changed.
     * 
     * @param businessRule Business rule
     * @param ruleSet Rule set to change status on
     * @throws RuleEngineRepositoryException
     */
    private void changeRuleSetStatus(BusinessRuleInfo businessRule, RuleSet ruleSet)
    	throws RuleEngineRepositoryException {
    	if (businessRule.getState() == null) {
    		return;
    	}

    	// Create status in repository if it does not exist
    	if (!this.ruleEngineRepository.containsStatus(businessRule.getState())) {
    		this.ruleEngineRepository.createStatus(businessRule.getState());
    	}

    	if (!businessRule.getState().equals(ruleSet.getStatus())) {
    		// Change rule set status
    		this.ruleEngineRepository.changeRuleSetStatus(ruleSet.getUUID(), businessRule.getState());
    		// Change rule status
    		for(Rule rule : ruleSet.getRules()) {
    	    	if (!businessRule.getState().equals(rule.getStatus())) {
	    			this.ruleEngineRepository.changeRuleStatus(rule.getUUID(), businessRule.getState());
    	    	}
    		}
    	}
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
		if (businessRule == null) {
			throw new MissingParameterException("Business rule is null");
		} else if (businessRule.getName() == null || businessRule.getName().isEmpty()) {
			throw new MissingParameterException("Business rule name is null or empty");
		}
		
		if (businessRule.getDesc() == null) {
			businessRule.setDesc("");
		}

		RuleSet ruleSet = null;
    	try {
    		ruleSet = this.ruleSetTranslator.translate(businessRule);
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
        } catch(RuleSetTranslatorException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
        }
        
		boolean ruleSetExists = false;
		if (ruleSet.getUUID() != null) {
			ruleSetExists = this.ruleEngineRepository.containsRuleSet(ruleSet.getUUID());
		}

		try {
    		if (ruleSetExists) {
				log(ruleSet, "***** Update RuleSet *****");
    			// Update rule set
    			ruleSet = updateRuleSet(ruleSet);
    			// Checkin rule set to create new version
    			//this.ruleEngineRepository.checkinRuleSet(ruleSet.getUUID(), "Rule set update");
    			// Load rule set to get the new version
				ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSet.getUUID());
				log(ruleSet, "***** RuleSet Updated *****");
    		} else {
				log(ruleSet, "***** Create RuleSet *****");
    			// Create new rule set
        		ruleSet = this.ruleEngineRepository.createRuleSet(ruleSet);
				log(ruleSet, "***** RuleSet Created *****");
    		}
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		} catch(RuleSetTranslatorException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException("Ruleset translation error: " + e.getMessage());
        } catch(RuleSetExistsException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
        } catch(RuleExistsException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		}

		// Change rule set status to match business rule status
		try {
			changeRuleSetStatus(businessRule, ruleSet);
			ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSet.getUUID());
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		}
        
		RuleSetInfo dto = ruleAdapter.getRuleSetDTO(ruleSet);
		return dto;
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
		if (businessRuleContainer == null) {
			throw new MissingParameterException("businessRuleContainer is null");
		} else if (businessRuleContainer.getBusinessRules() == null || businessRuleContainer.getBusinessRules().isEmpty()) {
			throw new InvalidParameterException("Container contains no business rules");
		}
		
		RuleSetContainerInfo ruleSetContainer = new RuleSetContainerInfo();
    	for(BusinessRuleInfo businessRule : businessRuleContainer.getBusinessRules()) {
    		RuleSetInfo dto = generateRuleSetForBusinessRule(businessRule);
    		ruleSetContainer.addRuleSet(dto);
    	}
    	return ruleSetContainer;
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
		if (businessRule == null) {
			throw new MissingParameterException("businessRule is null");
		}
		
    	try {
    		RuleSet ruleSet = this.ruleSetTranslator.translate(businessRule);
	    	RuleSetVerificationResult result = this.ruleSetValidator.verify(ruleSet);
	        return ruleAdapter.getRuleSetVerificationResultDTO(result);
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
        } catch(RuleSetTranslatorException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
        } catch(RuleEngineRepositoryException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
        }
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
		if (businessRule == null) {
			throw new MissingParameterException("businessRule is null");
		}
		
		try {
			RuleSet ruleSet = this.ruleSetTranslator.translate(businessRule);
			return ruleAdapter.getRuleSetDTO(ruleSet);
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new InvalidParameterException(e.getMessage());
		} catch (RuleSetTranslatorException e) {
			logger.error(e.getMessage(), e);
        	throw new OperationFailedException(e.getMessage());
		}
    }
}
