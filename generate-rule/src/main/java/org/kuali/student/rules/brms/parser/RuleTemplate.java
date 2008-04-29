/**
 * 
 */
package org.kuali.student.rules.brms.parser;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class RuleTemplate {
    VelocityContext context;
    String ruleName;
    ArrayList<String> ruleAttributes;
    ArrayList<String> lhs;
    ArrayList<String> rhs;

    public RuleTemplate() throws Exception {
        Properties p = new Properties();
        // Line below to disables logging, remove to enable
        p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
    }

    public String process(String templateFile) throws Exception {
        Template template = null;
        StringWriter writer = new StringWriter();

        // should check that the name, lhs and rhs are set before continuing
        context = new VelocityContext();
        context.put("RuleName", getRuleName());
        context.put("RuleAttributes", getRuleAttributes());
        
        if (lhs != null && rhs != null) {
            context.put("LHS", lhs);
            context.put("RHS", rhs);
        } else {
            throw new RuntimeException("Rule Generation Failed : The left or Right hand side is missing");
        }

        template = Velocity.getTemplate(templateFile);

        if (template != null) {
            template.merge(context, writer);
        }

        //System.out.println(writer);
        return writer.toString();
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleAttributes(ArrayList<String> ruleAttributes) {
        this.ruleAttributes = ruleAttributes;
    }

    public ArrayList<String> getRuleAttributes() {
        return ruleAttributes;
    }

    public void setLHS(ArrayList<String> lhs) {
        this.lhs = lhs;
    }

    public ArrayList<String> getLHS() {
        return lhs;
    }

    public void setRHS(ArrayList<String> rhs) {
        this.rhs = rhs;
    }

    public ArrayList<String> getRHS() {
        return rhs;
    }
}

