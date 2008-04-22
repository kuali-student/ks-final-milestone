package org.kuali.student.rules.BRMSCore;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * An interface to BRMS Meta Data that allows to store and retrieve a business rule
 * 
 * @author Zdenek Zraly (zdenek.zraly@ub.ca)
 */
@Repository
@Transactional
public class BRMSMetaData {

    VelocityContext context;

    @Autowired
    private FunctionalBusinessRuleDAO businessRuleDAO;

    /**
     * Generates a function string from a functional business rule based on RuleElementType classification.
     * 
     * @param rule
     *            Functional business rule used to create its function string representation
     * @return Returns function string e.g. "(A OR B) AND C"
     */
    public String getRuleFunctionString(FunctionalBusinessRule rule) {

        Collection<RuleElement> ruleElements = rule.getRuleElements();

        String functionString = new String("");
        char proposition = 'A'; // each proposition is represented as a letter

        // step through rule elements and create a function string
        for (RuleElement ruleElement : ruleElements) {
            functionString += " ";
            switch (ruleElement.getOperation()) {
                case AND_TYPE:
                    functionString += RuleElementType.AND_TYPE.getName();
                    break;
                case LPAREN_TYPE:
                    functionString += RuleElementType.LPAREN_TYPE.getName();
                    break;
                case NOT_TYPE:
                    functionString += RuleElementType.NOT_TYPE.getName();
                    break;
                case OR_TYPE:
                    functionString += RuleElementType.OR_TYPE.getName();
                    break;
                case PROPOSITION_TYPE:
                    functionString += proposition;
                    proposition++;
                    break;
                case RPAREN_TYPE:
                    functionString += RuleElementType.RPAREN_TYPE.getName();
                    break;
                case XOR_TYPE:
                    functionString += RuleElementType.XOR_TYPE.getName();
                    break;
                default:
                    functionString += "(unknown)";
            }
        }
        return functionString;
    }

    /**
     * Generates a HashMap of <unique alphabet character, proposition> pair from a functional business rule.
     * 
     * @param rule
     *            Functional business rule used to generate HashMap
     * @return Returns HashMap
     */
    public HashMap<String, RuleProposition> getRulePropositions(FunctionalBusinessRule rule) {

        HashMap<String, RuleProposition> propositions = new HashMap<String, RuleProposition>();
        Collection<RuleElement> ruleElements = rule.getRuleElements();

        char key = 'A';
        for (RuleElement ruleElement : ruleElements) {
            if (ruleElement.getOperation() == RuleElementType.PROPOSITION_TYPE) {
                propositions.put(String.valueOf(key), ruleElement.getRuleProposition());
                key++;
            }
        }
        return propositions;
    }

    /**
     * Transforms a functional business rule into Drool WHEN part of the Drool rule.
     * 
     * @param rule
     *            Functional business rule used to transform.
     * @return Returns WHEN part
     */
    public String mapMetaRuleToDroolRule(FunctionalBusinessRule rule) {

        try {
            Properties p = new Properties();
            p.setProperty("resource.loader", "class");
            p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            Velocity.init(p);
        } catch (Exception e) {
            System.out.println("Problem initializing Velocity : " + e);
            return null;
        }

        // for now we only retrieve the first proposition
        HashMap<String, RuleProposition> propositions = getRulePropositions(rule);
        RuleProposition ruleProposition = propositions.get("A");
        LeftHandSide leftHandSide = ruleProposition.getLeftHandSide();

        // String allFacts = "MATH101, MATH102, MATH103";
        ArrayList<String> facts = leftHandSide.getMethodParameters();
        String allFacts = "";
        for (String fact : facts) {
            allFacts += fact;
        }

        context = new VelocityContext();
        context.put("criteria", new String("1"));
        context.put("fact", allFacts);
        Template template = null;
        StringWriter sw = new StringWriter();

        // create WHEN part based on supplied fact, criteria and template
        try {
            template = Velocity.getTemplate("RuleWhenTemplate.vm");
            template.merge(context, sw);
        } catch (ResourceNotFoundException rnfe) {
            // couldn't find the template
            System.out.println("Velocity: Could not find the template. " + rnfe);
            return null;
        } catch (ParseErrorException pee) {
            // syntax error: problem parsing the template
            System.out.println("Velocity: parsing template error. " + pee);
            return null;
        } catch (MethodInvocationException mie) {
            // something invoked in the template threw an exception
            System.out.println("Velocity: template method exception. " + mie);
            return null;
        } catch (Exception e) {
            System.out.println("Velocity: error occured. " + e);
            return null;
        }

        return sw.toString();
    }

    /**
     * Retrieves a functional business rule from database based on Rule ID.
     * 
     * @param ruleID
     *            Functional business rule ID.
     * @return Returns functional business rule with Rule ID
     */
    public FunctionalBusinessRule getFunctionalBusinessRule(String ruleID) {

        FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRuleID(ruleID);

        // force Hibernate to load the RuleElements collection as well
        rule.getRuleElements().size();

        return rule;
    }

    /**
     * @return the businessRuleDAO
     */
    public final FunctionalBusinessRuleDAO getBusinessRuleDAO() {
        return businessRuleDAO;
    }

    /**
     * @param businessRuleDAO
     *            the businessRuleDAO to set
     */
    public final void setBusinessRuleDAO(FunctionalBusinessRuleDAO businessRuleDAO) {
        this.businessRuleDAO = businessRuleDAO;
    }
}
