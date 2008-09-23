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

import org.drools.repository.RulesRepository;
import org.kuali.student.rules.repository.RuleEngineRepository;
import org.kuali.student.rules.repository.drools.DefaultDroolsRepository;
import org.kuali.student.rules.repository.drools.RuleEngineRepositoryDroolsImpl;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.exceptions.CategoryExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.rules.repository.exceptions.RuleExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetExistsException;
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
//@Transactional
//@ContextConfiguration(locations = {"classpath:rule-repository-context.xml"})
public class RuleRepositoryServiceImpl implements RuleRepositoryService {
    
	private final static RuleAdapter ruleAdapter = RuleAdapter.getInstance();
	
	/** Drools rule repository */
    //@Resource
    private RuleEngineRepository ruleEngineRepository;
    
    private RuleSetTranslator ruleSetTranslator;

    public RuleRepositoryServiceImpl() {
    	RulesRepository repository = new DefaultDroolsRepository("/drools-repository").getRepository();
        this.ruleEngineRepository = new RuleEngineRepositoryDroolsImpl(repository);
    }

    /**
     * Constructor
     * 
     * @param repository Drools rules repository
     */
    public RuleRepositoryServiceImpl(final RuleEngineRepository repository) {
        this.ruleEngineRepository = repository;
    }

    /**
     * 
     * @return
     */
	public RuleSetTranslator getRuleSetTranslator() {
		return ruleSetTranslator;
	}

	/**
	 * 
	 * @param ruleSetTranslator
	 */
	public void setRuleSetTranslator(RuleSetTranslator ruleSetTranslator) {
		this.ruleSetTranslator = ruleSetTranslator;
	}

	//TODO - Remove this method
    /*public void clearRepository() {
    		//this.defaultRepo.logout();
    		//this.defaultRepo.login(defaultRepo.getCredentials());
			this.defaultRepo.clearData();
    }*/

	/**
     * <p>
     * Creates a new category in the repository.
     * </p>
     * Example: Create a new category for rules
     * 
     * <pre>
     * boolean b = repository.createCategory(&quot;/&quot;, &quot;EnrollmentRules&quot;, &quot;A test category 1.0 description&quot;);
     * b = repository.createCategory(&quot;/EnrollmentRules&quot;, &quot;Math&quot;, &quot;A Math category description&quot;);
     *         
     * List&lt;String&gt; category = repository.loadChildCategories(&quot;/&quot;);
     * ... 
     * category = repository.loadChildCategories(&quot;/EnrollmentRules&quot;);
     * ...
     * </pre>
     * 
     * @param path
     *            Category path
     * @param name
     *            Category name
     * @param description
     *            Category description
     * @return True if category successfully created, otherwise false
     * @throws CategoryExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException
     */
    public Boolean createCategory(final String path, final String name, final String description) 
        throws CategoryExistsException {
        return this.ruleEngineRepository.createCategory(path, name, description);
    }

    public void removeCategory(final String path) {
    	this.ruleEngineRepository.removeCategory(path);
    }

    public List<String> fetchChildCategories(final String path) 
	    throws CategoryExistsException {
	    return this.ruleEngineRepository.loadChildCategories(path);
	}
	
    /**
     * <p>
     * Creates, compiles and checks in a rule set into the repository.
     * </p>
     * Example: Create a rule set and a rule and store it in the repository.
     * 
     * <pre>
     * // Create rule set
     * RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(&quot;MyNewRuleSet&quot;);
     * ruleSet.setDescription(&quot;My new rule set&quot;);
     * 
     * // Create rule
     * Rule rule = RuleFactory.getInstance().createRule(&quot;MyNewRule&quot;);
     * rule.setDescription(&quot;My new rule&quot;);
     * rule.setCategory(null);
     * rule.setFormat(&quot;drl&quot;);
     * rule.setContent(&quot;rule \&quot;new_rule\&quot; when then end&quot;);
     * ruleSet.addRule(rule);
     * 
     * // Create a category for the rules
     * repository.createCategory(&quot;/&quot;, &quot;MyCategory&quot;, &quot;My new rule category&quot;);
     * 
     * // Create and store the rule set in the repository
     * String ruleSetUUID = repository.createRuleSet(ruleSet);
     * RuleSet ruleSet2 = repository.loadRuleSet(ruleSetUUID);
     * ...
     * </pre>
     * 
     * @param ruleSet
     *            Rule set to create
     * @return Rule set uuid
     * @throws RuleExistsException Thrown if a rule within the rule set already exists
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException
     */
    public RuleSetDTO createRuleSet(final RuleSetDTO ruleSetDTO) 
        throws RuleSetExistsException, RuleExistsException {
    	RuleSet ruleSet = ruleAdapter.getRuleSet(ruleSetDTO);
        RuleSet newRuleSet = this.ruleEngineRepository.createRuleSet(ruleSet);
        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(newRuleSet);
        return dto;
    }

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws RuleEngineRepositoryException Thrown if removing rule set fails
     */
    public void removeRuleSet(final String uuid) {
    	this.ruleEngineRepository.removeRuleSet(uuid);
    }

    /**
     * Deletes a rule set snapshot. 
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @throws RuleEngineRepositoryException Thrown if snapshot fails to be deleted or any other errors occur
     */
    public void removeRuleSetSnapshot(final String ruleSetName, final String snapshotName) {
    	this.ruleEngineRepository.removeRuleSetSnapshot(ruleSetName, snapshotName);
    }
    
    /**
     * <p>
     * Updates a rule set in the repository and returns a new rule set.
     * </p>
     * Example: Load a rule set and update its header.
     * 
     * <pre>
     * // Load rule set
     * RuleSet ruleSet = repository.loadRuleSet(ruleSetUUID);
     * 
     * // Update rule set with new header
     * String header = &quot;import java.util.List;&quot;;
     * ruleSet.addHeader(header);
     * 
     * // Update rule set
     * repository.updateRuleSet(ruleSet);
     * ...
     * </pre>
     * 
     * @param ruleSet
     *            A rule set to update
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
     * <p>
     * Checks in a rule set into the repository. Rule set version is incremented by 1
     * </p>
     * Example: Create and check in a rule set.
     * 
     * <pre>
     * // Create rule set
     * RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(&quot;MyNewRuleSet&quot;);
     * ruleSet.setDescription(&quot;My new rule set&quot;);
     * 
     * // Rule Set Version 1
     * String ruleSetUUID = repository.createRuleSet(ruleSet);
     * 
     * // Check in rule set version 2
     * repository.checkinRuleSet(ruleSetUUID, &quot;Checkin Rule Set Version 2&quot;);
     * ...
     * </pre>
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    /*public void checkinRuleSet(final String uuid, final String comment) {
        this.ruleEngineRepository.checkinRuleSet(uuid, comment);
    }*/

    /**
     * Loads a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @return A rule
     * @throws RuleEngineRepositoryException Thrown if loading rule fails
     */
    /*public RuleDTO fetchRule(final String uuid) {
    	Rule rule = this.ruleEngineRepository.loadRule(uuid);
    	RuleDTO dto = ruleAdapter.getRuleDTO(rule);
    	return dto;
    }*/
    
    /**
     * <p>
     * Loads a rule set (including all rules) by UUID from the repository.
     * </p>
     * Example: Load a rule set by UUID.
     * 
     * <pre>
     * // Create rule set
     * RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(&quot;MyNewRuleSet&quot;);
     * ...
     * 
     * // Create rule
     * Rule rule = RuleFactory.getInstance().createRule(&quot;MyNewRule&quot;);
     * ...
     * 
     * // Create rule set
     * String ruleSetUUID = repository.createRuleSet(ruleSet);
     * 
     * // Load rule set
     * RuleSet ruleSet = repository.loadRuleSet(ruleSetUUID);
     * ...
     * </pre>
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    public RuleSetDTO fetchRuleSet(final String ruleSetUUID) {
        RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
        return dto;
    }

    /**
     * <p>
     * Loads a compiled rule set from the repository.
     * </p>
     * Example: Load a compiled rule set (e.g. Drools package).
     * 
     * <pre>
     * org.drools.rule.Package binPkg = (org.drools.rule.Package)
     *     repository.loadCompiledRuleSet(ruleSetUUID);
     * ...
     * </pre>
     * 
     * @param ruleSetUUID
     *            Rule set UUID
     * @return A compiled rule set as a byte array (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public byte[] fetchCompiledRuleSet(final String ruleSetUUID) {
        return this.ruleEngineRepository.loadCompiledRuleSetAsBytes(ruleSetUUID);
    }

    /**
     * <p>
     * Creates a new rule set snapshot for deployment and stores it in the repository.
     * </p>
     * Example: Create and load a rule set snapshot.
     * 
     * <pre>
     * // Load rule set
     * RuleSet ruleSet = repository.loadRuleSet(ruleSetUUID);
     * 
     * repository.createRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;,
     *     &quot;Snapshot Version 1&quot;);
     * 
     * org.drools.rule.Package pkg = (org.drools.rule.Package)
     *     repository.loadCompiledRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;);
     * ...
     * </pre>
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @param comment
     *            Comments for creating the snapshot
     * @throws RuleEngineRepositoryException
     */
    public void createRuleSetSnapshot(final String ruleSetName, final String snapshotName, final String comment) {
        this.ruleEngineRepository.createRuleSetSnapshot(ruleSetName, snapshotName, comment);
    }

    /**
     * Replaces an existing rule set snapshot and stores it in the repository.
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @param comment
     *            Comments for creating the snapshot
     * @throws RuleEngineRepositoryException
     */
    public void replaceRuleSetSnapshot(final String ruleSetName, final String snapshotName, final String comment) {
        this.ruleEngineRepository.rebuildRuleSetSnapshot(ruleSetName, snapshotName, comment);
    }

    /**
     * <p>
     * Loads a compiled rule set snapshot from the repository.
     * </p>
     * Example: Load and execute a compiled rule set snapshot.
     * 
     * <pre>
     * // Load rule set
     * RuleSet ruleSet = repository.loadRuleSet(ruleSetUUID);
     * 
     * repository.createRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;,
     *     false, &quot;Snapshot Version 1&quot;);
     * 
     * org.drools.rule.Package pkg = (org.drools.rule.Package)
     *     repository.loadCompiledRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;);
     * 
     * RuleBase rb = RuleBaseFactory.newRuleBase();
     * rb.addPackage( pkg );
     * StatelessSession sess = rb.newStatelessSession();
     * sess.execute( ... );
     * ...
     * </pre>
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @return Compiled rule set as a byte array (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public byte[] fetchCompiledRuleSetSnapshot(final String ruleSetName, final String snapshotName) {
        return this.ruleEngineRepository.loadCompiledRuleSetSnapshotAsBytes(ruleSetName, snapshotName);
    }

    public RuleSetDTO fetchRuleSetSnapshot(final String ruleSetName, final String snapshotName) {
        RuleSet ruleSet = this.ruleEngineRepository.loadRuleSetSnapshot(ruleSetName, snapshotName);
        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
        return dto;
    }

    public String createState(final String name) {
    	return this.ruleEngineRepository.createStatus(name);
    }
    
    public String[] fetchStates() {
    	return this.ruleEngineRepository.loadStates();
    }

    public void removeState(String name) {
    	this.ruleEngineRepository.removeStatus(name);
    }

    public void changeRuleSetState(final String ruleSetUUID, final String newState) {
    	this.ruleEngineRepository.changeRuleSetStatus(ruleSetUUID, newState);
    }
    
    /**
     * TODO: Needs fixing and testing...
     * 
     * @param businessRuleContainerDTO
     * @return
     * @throws RuleSetTranslatorException
     */
    public RuleSetDTO generateRuleSet(BusinessRuleContainerDTO businessRuleContainer) throws RuleSetTranslatorException {
    	RuleSet ruleSet = ruleSetTranslator.translate(businessRuleContainer);
    	ruleSet = this.ruleEngineRepository.createRuleSet(ruleSet);
    	RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
    	return dto;
    }

}
