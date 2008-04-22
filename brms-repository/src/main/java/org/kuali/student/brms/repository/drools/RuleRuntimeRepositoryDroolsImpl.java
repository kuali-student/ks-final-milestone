package org.kuali.student.brms.repository.drools;

import org.drools.repository.RulesRepository;
import org.kuali.student.brms.repository.RuleRuntimeRepository;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;

/**
 * This is a convenience interface.
 *
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleRuntimeRepositoryDroolsImpl 
    extends RuleEngineRepositoryDroolsImpl 
    implements RuleRuntimeRepository {

    public RuleRuntimeRepositoryDroolsImpl(RulesRepository repository) {
        super(repository);
    }

    /**
     * <p>Creates a new category.</p>
     *
     * Example:
     * <pre>
     * boolean b = repo.createCategory("/", "EnrollmentRules", "A test category 1.0 description");
     * b = repo.createCategory("/EnrollmentRules", "Math", "A Math category description");
        
     * List<String> category = brmsRepository.loadChildCategories("/");
     * ... 
     * category = brmsRepository.loadChildCategories("/EnrollmentRules");
     * ...
     * </pre>
     * @param path
     *            Category path
     * @param name
     *            Category name
     * @param description
     *            Category description
     * @return True if category successfully created, otherwise false
     * @throws RuleEngineRepositoryException
     */
    public Boolean createCategory(String path, String name, String description) throws RuleEngineRepositoryException {
        return super.createCategory(path, name, description);
    }

    /**
     * Updates a rule and saves it to the repository.
     * 
     * @param rule A rule to update
     * @throws RuleEngineRepositoryException
     */
    public void updateRule(Rule rule) throws RuleEngineRepositoryException {
        super.updateRule(rule);
    }
    
    /**
     * Checkin a rule by uuid into the repository.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRule(String uuid, String comment) throws RuleEngineRepositoryException {
        super.checkinRule(uuid, comment);
    }

    /**
     * <p>Creates and checks in a rule set with all its rules.</p>
     * 
     * Example:
     * <pre>
     * RuleSetImpl ruleSet = new RuleSetImpl(name);
     * ruleSet.setDescription(description);
     * ruleSet.addRule(rule1);
     *
     * RuleImpl rule = new RuleImpl(name);
     * rule.setDescription(description);
     * rule.setCategory(category);
     * rule.setFormat("drl");
     * rule.setContent(content);
     * ruleSet.addRule(rule2);
     * 
     * brmsRepository.createCategory("/", "MyCategory", "My new rule category");
     * String ruleSetUUID = repo.createRuleSet(ruleSet);
     * RuleSet ruleSet2 = repo.loadRuleSet(ruleSetUUID);
     * ...
     * </pre>
     * @param ruleSet
     *            Rule set to create
     * @return Rule set uuid
     * @throws RuleEngineRepositoryException
     */
    public String createRuleSet(RuleSet ruleSet) throws RuleEngineRepositoryException {
        return super.createRuleSet(ruleSet);
    }
    
    /**
     * Updates a rule and save it to the repository.
     *  
     * @param ruleSet A rule set to update
     * @throws RuleEngineRepositoryException
     */
    public void updateRuleSet(RuleSet ruleSet) throws RuleEngineRepositoryException {
        super.updateRuleSet(ruleSet);
    }

    /**
     * Checkin a rule set into the repository.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRuleSet(String uuid, String comment) throws RuleEngineRepositoryException {
        super.checkinRuleSet(uuid, comment);
    }

    /**
     * Loads all rule set (and rules) for a specific uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    public RuleSet loadRuleSet(String uuid) throws RuleEngineRepositoryException {
        return super.loadRuleSet(uuid);
    }

    /**
     * Loads a compiled rule set.
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @return A compiled rule set (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public Object loadCompiledRuleSet(String ruleSetUUID) throws RuleEngineRepositoryException {
        return super.loadCompiledRuleSet(ruleSetUUID);
    }

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
    public void createRuleSetSnapshot(String ruleSetName, String snapshotName, boolean replaceExisting, String comment) throws RuleEngineRepositoryException {
        super.createRuleSetSnapshot(ruleSetName, snapshotName, replaceExisting, comment);
    }

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
    public Object loadCompiledRuleSetSnapshot(String ruleSetName, String snapshotName) throws RuleEngineRepositoryException {
        return super.loadCompiledRuleSetSnapshot(ruleSetName, snapshotName);
    }
}
