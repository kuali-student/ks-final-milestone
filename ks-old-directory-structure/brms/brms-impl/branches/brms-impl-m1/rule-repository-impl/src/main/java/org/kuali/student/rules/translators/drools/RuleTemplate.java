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
package org.kuali.student.rules.translators.drools;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.kuali.student.rules.translators.exceptions.RuleSetTranslatorException;

public class RuleTemplate {
    VelocityContext context;

    /**
     * Constructs and initializes a Velocity rule template.
     */
    public RuleTemplate() {
    	this(false);
    }

    /**
     * Constructs and initializes a Velocity rule template
     *
     * @param enableLogging True enables logging; false disables logging
     * @throws RuleSetTranslatorException If Velocity rule template initialization fails
     */
    public RuleTemplate(boolean enableLogging) {
        Properties p = new Properties();
        if (!enableLogging) {
	        // Line below to disables logging, remove to enable
	        p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        }
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        try {
            Velocity.init(p);
        } catch( Exception e ) {
            throw new RuleSetTranslatorException( "Velocity rule template initialization failed", e );
        }
    }

    /**
     * Generates source code from a Velocity template.
     * 
     * @param templateFile Velocity template file to use
     * @param anchor Rule anchor
     * @param ruleName Rule name
     * @param propMap Map of propositions
     * @return Source code
     * @throws IllegalArgumentException if templateFile, anchor, ruleName or propMap is null or empty
     */
    public String process(String templateFile, Map<String, Object> propMap) {
    	if ( templateFile == null || templateFile.trim().isEmpty()) {
    		throw new IllegalArgumentException("templateFile cannot be null");
    	}
    	else if ( propMap == null || propMap.isEmpty()) {
    		throw new IllegalArgumentException("propMap cannot be null");
    	}

        StringWriter writer = new StringWriter();

        context = new VelocityContext(propMap);
        
        try {
            Template template = Velocity.getTemplate(templateFile);
            template.merge(context, writer);
        } catch ( Exception e ) {
            throw new RuleSetTranslatorException( "Processing Velocity template failed", e );
        }

        return writer.toString();
    }
}

