/**
 * 
 */
package org.kuali.student.rules.brms.drools.translator;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class RuleTemplate {
    VelocityContext context;

    public RuleTemplate() throws Exception {
        Properties p = new Properties();
        // Line below to disables logging, remove to enable
        p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
    }

    public String process(String templateFile, String ruleName, Map<String, Object> propMap)  throws Exception {
        Template template = null;
        StringWriter writer = new StringWriter();

        // should check that the name, lhs and rhs are set before continuing
        context = new VelocityContext(propMap);
        context.put("ruleName", ruleName);
        
        template = Velocity.getTemplate(templateFile);

        if (template != null) {
            template.merge(context, writer);
        }

        //System.out.println(writer);
        return writer.toString();
    }
}

