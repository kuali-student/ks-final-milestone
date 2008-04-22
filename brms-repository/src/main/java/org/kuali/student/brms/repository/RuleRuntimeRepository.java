package org.kuali.student.brms.repository;

import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;

public interface RuleRuntimeRepository {

    /**
     * Creates a new category.
     * 
     * @param path
     *            Category path
     * @param name
     *            Category name
     * @param description
     *            Category description
     * @return True if category successfully created, otherwise false
     * @throws RuleEngineRepositoryException
     */
    public Boolean createCategory(String path, String name, String description) throws RuleEngineRepositoryException;
    
    /**
     * Creates and checks in a rule set with all its rules.
     * 
     * @param ruleSet
     *            Rule set to create
     * @return Rule set uuid
     * @throws RuleEngineRepositoryException
     */
    public String createRuleSet(RuleSet ruleSet) throws RuleEngineRepositoryException;
    
    /**
     * Checkin a rule set into the repository.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRuleSet(String uuid, String comment) throws RuleEngineRepositoryException;

    /**
     * Loads all rule set (and rules) for a specific uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    public RuleSet loadRuleSet(String uuid) throws RuleEngineRepositoryException;

    /**
     * Loads a compiled rule set.
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @return A compiled rule set (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public Object loadCompiledRuleSet(String ruleSetUUID) throws RuleEngineRepositoryException;

    /**
     * Creates a new rule set snapshot for deployment. Creates a copy of the rule set for deployment.
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @param replaceExisting
     *            Replace existing snapshot
     * @param comment
     *            Comments for creating the snapshot
     * @throws RuleEngineRepositoryException
     */
    public void createRuleSetSnapshot(String ruleSetName, String snapshotName, boolean replaceExisting, String comment) throws RuleEngineRepositoryException;

    /**
     * Loads a compiled rule set snapshot.
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @return Compiled rule set (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public Object loadCompiledRuleSetSnapshot(String ruleSetName, String snapshotName) throws RuleEngineRepositoryException;
}
