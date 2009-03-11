package org.kuali.student.rules.internal.common.utils;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.tools.generic.ComparisonDateTool;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.apache.velocity.tools.generic.SortTool;

/**
 * Velocity template engine.
 * <p>Velocity tools supported (See <a href="http://velocity.apache.org/tools/devel/generic.html">http://velocity.apache.org/tools/devel/generic.html<a/>}:</p>
 * <ul>
 * <li><a href="http://velocity.apache.org/tools/devel/javadoc/org/apache/velocity/tools/generic/DateTool.html">DateTool</a></li>
 * <li><a href="http://velocity.apache.org/tools/devel/javadoc/org/apache/velocity/tools/generic/ComparisonDateTool.html">ComparisonDateTool</a></li>
 * <li><a href="http://velocity.apache.org/tools/devel/javadoc/org/apache/velocity/tools/generic/MathTool.html">MathTool</a></li>
 * <li><a href="http://velocity.apache.org/tools/devel/javadoc/org/apache/velocity/tools/generic/NumberTool.html">NumberTool</a></li>
 * <li><a href="http://velocity.apache.org/tools/devel/javadoc/org/apache/velocity/tools/generic/SortTool.html">SortTool</a></li>
 * </ul>
 * 
 * Examples:
 * <pre>
 * $dateTool:            <code>$dateTool.get('yyyy-M-d H:m:s')                                 -> 2003-10-19 21:54:50</code>
 * 
 * $dateComparisonTool:  <code>$dateComparisonTool.difference('2005-07-04','2007-02-15').abbr  -> 1 yr</code>
 * 
 * $mathTool:            <code>$mathTool.toNumber($value)                                      -> Converts java.lang.String $value to a java.lang.Number</code>
 * 
 * $numberTool:          <code>$numberTool.currency($myNumber)                                 -> $13.55</code>
 * 
 * $sortTool:            <code>$sorter.sort($collection, "name:asc")                           -> Sorts $collection with property 'name' in ascending order</code>
 * </pre>
 */
public class VelocityTemplateEngine {

	private final VelocityEngine velocityEngine = new VelocityEngine();
	private VelocityContext defaultContext;

	/**
	 * Constructs a velocity template engine.
	 */
	public VelocityTemplateEngine() {
		this(false);
	}

	/**
	 * Constructs a velocity template engine.
	 * 
	 * @param enableLogging True enables logging; false disables logging
	 */
	public VelocityTemplateEngine(boolean enableLogging) {
        if (!enableLogging) {
	        // Line below to disables logging, remove to enable
        	velocityEngine.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogSystem");
        }
        
        velocityEngine.setProperty(VelocityEngine.RESOURCE_LOADER, "class");
        velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        defaultContext = new VelocityContext();
        defaultContext.put("dateTool", new DateTool());
        defaultContext.put("dateComparisonTool", new ComparisonDateTool());
        defaultContext.put("mathTool", new MathTool());
        defaultContext.put("numberTool", new NumberTool());
        defaultContext.put("sortTool", new SortTool());
        // Following tools need VelocityTools version 2.0+
        //defaultContext.put("displayTool", new DisplayTool());
        //defaultContext.put("xmlTool", new XmlTool());
        
        try {
			velocityEngine.init();
		} catch (Exception e) {
			throw new VelocityException(e);
		}
	}
	
	/**
	 * Evaluates a template with a map of objects. <code>contextMap</code> can
	 * be null if no keys/tokens are referenced in the <code>template</code>
	 * 
	 * @param contextMap Map of objects to be used in the template
	 * @param template Velocity Template
	 * @return Evaluated template
	 */
	public String evaluate(Map<String, Object> contextMap, String template) throws VelocityException {
		Reader readerOut = ((Reader) (new BufferedReader(new StringReader(template))));
		return evaluate(contextMap, readerOut);
	}

	/**
	 * Evaluates a template with a map of objects.
	 * 
	 * @param mapContext Map of Objects to be used in the template
	 * @param template Velocity Template
	 * @return Evaluated template
	 */
	public String evaluate(Map<String, Object> mapContext, Reader template) throws VelocityException {
		VelocityContext context = new VelocityContext(mapContext, defaultContext);
		
		StringWriter writerOut = new StringWriter();
		try {
			velocityEngine.evaluate(context, writerOut, "VelocityEngine", template);
			return writerOut.toString();
		} catch(Exception e) {
			throw new VelocityException(e);
		}
	}

}
