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
package org.kuali.student.rules.repository.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.repository.RuleEngineRepository;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.exceptions.CategoryExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.rules.repository.exceptions.RuleExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetExistsException;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.repository.service.RuleAdapter;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.translators.RuleSetTranslator;
import org.springframework.transaction.annotation.Transactional;
/**
 * This is a convenience interface for the rules repository interface.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 */
@WebService(endpointInterface = "org.kuali.student.rules.repository.service.RuleRepositoryService", 
			serviceName = "RuleRepositoryService", 
			portName = "RuleRepositoryService", 
			targetNamespace = "http://student.kuali.org/wsdl/brms/RuleRepository")
@Transactional
public class RuleRepositoryServiceImpl implements RuleRepositoryService {
    
	private final static RuleAdapter ruleAdapter = RuleAdapter.getInstance();
	
	/** Drools rule repository */
    private RuleEngineRepository ruleEngineRepository;
    
    private RuleSetTranslator ruleSetTranslator;

    public RuleRepositoryServiceImpl() { }

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
     * Creates a new category in the repository.
     * 
     * @param path Category path
     * @param name Category name
     * @param description Category description
     * @return True if category successfully created, otherwise false
     * @throws CategoryExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException Thrown if creating category fails
     */
    /*public Boolean createCategory(final String path, final String name, final String description) 
        throws CategoryExistsException, OperationFailedException, InvalidParameterException {
        try {
			return this.ruleEngineRepository.createCategory(path, name, description);
		} catch(RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }*/

    /**
     * Removes a category.
     * 
     * @param categoryPath Category path
     * @throws RuleEngineRepositoryException Thrown if removing a category fails
     */
    /*public void removeCategory(final String path) 
    	throws OperationFailedException, InvalidParameterException {
    	try {
			this.ruleEngineRepository.removeCategory(path);
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }*/

    /**
     * Loads child categories from <code>path</code>.
     * 
     * @param categoryPath Category path
     * @return List of child category names
     * @throws OperationFailedException Thrown if loading child categories fails
     */
    /*public List<String> fetchCategories(final String path) 
    	throws OperationFailedException, InvalidParameterException {
    	try {
		    return this.ruleEngineRepository.loadChildCategories(path);
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
	}*/
	
    /**
     * Creates, compiles and checks in a rule set into the repository.
     * 
     * @param ruleSet Rule set to create
     * @return Rule set uuid
     * @throws RuleExistsException Thrown if a rule within the rule set already exists
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws OperationFailedException Thrown if compiling a rule set fails
     */
    public RuleSetDTO createRuleSet(final RuleSetDTO ruleSetDTO) 
        throws RuleSetExistsException, RuleExistsException, OperationFailedException, InvalidParameterException {
    	try {
	    	RuleSet ruleSet = ruleAdapter.getRuleSet(ruleSetDTO);
	        RuleSet newRuleSet = this.ruleEngineRepository.createRuleSet(ruleSet);
	        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(newRuleSet);
	        return dto;
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws OperationFailedException Thrown if removing rule set fails
     */
    public void removeRuleSet(final String uuid) 
    	throws OperationFailedException, InvalidParameterException {
    	try {
	    	this.ruleEngineRepository.removeRuleSet(uuid);
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Deletes a rule set snapshot. 
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @throws OperationFailedException Thrown if snapshot fails to be deleted or any other errors occur
     */
    public void removeRuleSetSnapshot(final String ruleSetName, final String snapshotName) 
    	throws OperationFailedException, InvalidParameterException {
    	try {
	    	this.ruleEngineRepository.removeRuleSetSnapshot(ruleSetName, snapshotName);
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }
    
    /**
     * Updates a rule set in the repository and returns a new rule set.
     * 
     * @param ruleSet A rule set to update
     * @return An updated rule set
     * @throws RuleEngineRepositoryException
     */
    /*public RuleSetDTO updateRuleSet(RuleSetDTO ruleSetDTO) {
    	RuleSet ruleSet = ruleAdapter.getRuleSet(ruleSetDTO);
        RuleSet updatedRuleSet = this.ruleEngineRepository.updateRuleSet(ruleSet);
        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(updatedRuleSet);
        return dto;
    }*/

    /**
     * Checks in a rule set into the repository.
     * Checkin rule set will create a new version of the rule set.
     * Rule set version is incremented by 1.
     * 
     * @param uuid Rule set uuid
     * @param comment Checkin comments
     * @throws RuleEngineRepositoryException Thrown if checkin rule set fails
     */
    public long checkinRuleSet(final String uuid, final String comment)
    	throws OperationFailedException, InvalidParameterException {
    	try {
	        return this.ruleEngineRepository.checkinRuleSet(uuid, comment);
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Loads a rule by uuid.
     * 
     * @param uuid Rule uuid
     * @return A rule
     * @throws RuleEngineRepositoryException Thrown if loading rule fails
     */
    /*public RuleDTO fetchRule(final String uuid) {
    	Rule rule = this.ruleEngineRepository.loadRule(uuid);
    	RuleDTO dto = ruleAdapter.getRuleDTO(rule);
    	return dto;
    }*/
    
    /**
     * Loads a rule set (including all rules) by UUID from the repository.
     * 
     * @param uuid Rule set uuid
     * @return A rule set
     * @throws OperationFailedException Thrown if loading rule set fails
     */
    public RuleSetDTO fetchRuleSet(final String ruleSetUUID) 
    	throws OperationFailedException, InvalidParameterException {
        try {
	    	RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
	        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
	        return dto;
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Loads a compiled rule set from the repository.
     * 
     * @param ruleSetUUID Rule set UUID
     * @return A compiled rule set as a byte array (e.g. <code>org.drools.rule.Package</code>)
     * @throws OperationFailedException Thrown if compiling a rule set fails
     */
    public byte[] fetchCompiledRuleSet(final String ruleSetUUID) 
    	throws OperationFailedException, InvalidParameterException {
        try {
	        return this.ruleEngineRepository.loadCompiledRuleSetAsBytes(ruleSetUUID);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Creates a new rule set snapshot for deployment and stores it in the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     */
    public void createRuleSetSnapshot(final String ruleSetName, final String snapshotName, final String comment) 
    	throws OperationFailedException, InvalidParameterException {
        try {
	        this.ruleEngineRepository.createRuleSetSnapshot(ruleSetName, snapshotName, comment);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Rebuilds (recompiles) an existing rule set snapshot and stores it in the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     */
    public void rebuildRuleSetSnapshot(final String ruleSetName, final String snapshotName, final String comment) 
    	throws OperationFailedException, InvalidParameterException {
        try {
	        this.ruleEngineRepository.rebuildRuleSetSnapshot(ruleSetName, snapshotName, comment);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Loads a compiled rule set snapshot from the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @return Compiled rule set as a byte array (e.g. <code>org.drools.rule.Package</code>)
     * @throws OperationFailedException Thrown if loading a snapshots fails
     */
    public byte[] fetchCompiledRuleSetSnapshot(final String ruleSetName, final String snapshotName) 
    	throws OperationFailedException, InvalidParameterException {
        try {
	        return this.ruleEngineRepository.loadCompiledRuleSetSnapshotAsBytes(ruleSetName, snapshotName);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Loads a rule set snapshot.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Rule set's snapshot name
     * @return A rule set snapshot
     * @throws OperationFailedException Thrown if loading a snapshots fails
     */
    public RuleSetDTO fetchRuleSetSnapshot(final String ruleSetName, final String snapshotName) 
    	throws OperationFailedException, InvalidParameterException {
        try {
	        RuleSet ruleSet = this.ruleEngineRepository.loadRuleSetSnapshot(ruleSetName, snapshotName);
	        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
	        return dto;
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Creates a new status if it doesn't already exists.
     * 
     * @param name Status name
     * @return New status uuid
     * @throws OperationFailedException Thrown if creating status fails
     */
    public String createState(final String name)
    	throws OperationFailedException, InvalidParameterException {
        try {
	    	return this.ruleEngineRepository.createStatus(name);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }
    
    /**
     * Loads all states.
     * 
     * @return Array of all states (statuses)
     * @throws RuleEngineRepositoryException Thrown if loading states fails
     */
    public String[] fetchStates()
    	throws OperationFailedException {
        try {
	    	return this.ruleEngineRepository.loadStates();
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
        }
    }

    /**
     * Removes a status from the repository.
     * 
     * @param uuid Status name
     * @throws RuleEngineRepositoryException Thrown if removing status fails
     */
    public void removeState(final String name)
    	throws OperationFailedException, InvalidParameterException {
        try {
	    	this.ruleEngineRepository.removeStatus(name);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Changes a rule set status by uuid.
     * 
     * @param uuid Rule set uuid
     * @param newState New rule set status
     * @throws RuleEngineRepositoryException Thrown if changing rule set status fails
     */
    public void changeRuleSetState(final String ruleSetUUID, final String newState)
    	throws OperationFailedException, InvalidParameterException {
        try {
	    	this.ruleEngineRepository.changeRuleSetStatus(ruleSetUUID, newState);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }
    
    private RuleSet updateRuleSet(RuleSet ruleSet1) 
    	throws RuleEngineRepositoryException {
		RuleSet ruleSet2 = this.ruleEngineRepository.loadRuleSetByName(ruleSet1.getName());
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
     * Generates and creates or updates a rule set (rule engine specific source code) 
     * from a <code>BusinessRuleContainerDTO</code>.
     *  
     * @param businessRuleContainer A container of business rules
     * @return A rule set
	 * @throws RuleSetTranslatorException Thrown if translating a rule set fails
     */
    public RuleSetDTO generateRuleSet(BusinessRuleContainerDTO businessRuleContainer) 
    	throws RuleSetTranslatorException, OperationFailedException, InvalidParameterException {
		RuleSet ruleSet1 = null;
    	try {
    		ruleSet1 = this.ruleSetTranslator.translate(businessRuleContainer);
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
        } catch(RuleSetTranslatorException e) {
        	throw new OperationFailedException(e.getMessage());
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
        }
        
		boolean ruleSetExists = this.ruleEngineRepository.containsRuleSet(ruleSet1.getName());

		try {
    		if (ruleSetExists) {
    			// Update rule set
    			ruleSet1 = updateRuleSet(ruleSet1);
    		} else {
    			// Create new rule set
        		ruleSet1 = this.ruleEngineRepository.createRuleSet(ruleSet1);
    		}
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
        } catch(RuleSetExistsException e) {
        	throw new OperationFailedException(e.getMessage());
        } catch(RuleExistsException e) {
        	throw new OperationFailedException(e.getMessage());
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		}

		RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet1);
    	return dto;
    }

}
