/**
 * 
 */
package org.kuali.student.rules.brms.translators.drools;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class RuleTemplate {
    VelocityContext context;

    /**
     * Constructs and initializes a Velocity rule template
     *
     * @throws RuntimeException If Velocity rule template initialization fails
     */
    public RuleTemplate() {
        Properties p = new Properties();
        // Line below to disables logging, remove to enable
        p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        try {
            Velocity.init(p);
        } catch( Exception e ) {
            throw new RuntimeException( "Initializing Velocity rule template failed", e );
        }
    }

    public String process(String templateFile, String anchor, String ruleName, Map<String, Object> propMap) {
        Template template = null;
        StringWriter writer = new StringWriter();

        // should check that the name, lhs and rhs are set before continuing
        context = new VelocityContext(propMap);
        context.put("anchor", anchor);
        context.put("ruleName", ruleName);
        
        try {
            template = Velocity.getTemplate(templateFile);

            if (template != null) {
                template.merge(context, writer);
            }
        } catch ( Exception e ) {
            throw new RuntimeException( "Processing Velocity template failed", e );
        }

        return writer.toString();
    }
}

