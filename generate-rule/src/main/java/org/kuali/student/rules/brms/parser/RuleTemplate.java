/**
 * 
 */
package org.kuali.student.rules.brms.parser;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
/**
 * @author Rich Diaz
 *
 */
public class RuleTemplate {
	VelocityContext context;
	String ruleName;
	ArrayList<String> ruleAttributes;
	ArrayList<String> lhs;
	ArrayList<String> rhs;
	
	public RuleTemplate(){
        try{
        	Properties p = new Properties();
        	// Line below to disables logging, remove to enable
        	p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        	p.setProperty("resource.loader", "class");
        	p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        	Velocity.init(p);
        }
        catch( Exception e ){
        	System.out.println("Problem initializing Velocity : " + e );
        }
    }
	
	public String process(String templateFile){
		Template template =  null;
		StringWriter writer = new StringWriter();
		
		// should check that the name, lhs and rhs are set before continuing
        context = new VelocityContext();
        context.put("RuleName", getRuleName());
        context.put("RuleAttributes", getRuleAttributes());
        context.put("LHS", getLHS());
        context.put("RHS", getRHS());
        
		try{
         
            try{
                template = Velocity.getTemplate(templateFile);
            }
            catch( ResourceNotFoundException rnfe ){
                System.out.println("Error : cannot find template " + templateFile );
            }
            catch( ParseErrorException pee ){
                System.out.println("Syntax error in template " + templateFile + ":" + pee );
            }
            
            if ( template != null)
                template.merge(context, writer); 
        }
        catch( Exception e )
        {
            System.out.println("An exception occured " + e);
        }
        System.out.println(writer);
        return writer.toString();
    }
	
	public void setRuleName(String ruleName){
		this.ruleName = ruleName;
	}
	
	public String getRuleName(){
		return ruleName;
	}
	
	public void setRuleAttributes(ArrayList<String> ruleAttributes){
		this.ruleAttributes = ruleAttributes;
	}
	
    public ArrayList<String> getRuleAttributes(){
        return ruleAttributes;
    }
    
    public void setLHS(ArrayList<String> lhs){
        this.lhs = lhs;
    }
    
    public ArrayList<String> getLHS(){
        return lhs;
    }
    
    public void setRHS(ArrayList<String> rhs){
        this.rhs = rhs;
    }
    
    public ArrayList<String> getRHS(){
        return rhs;
    }
}
