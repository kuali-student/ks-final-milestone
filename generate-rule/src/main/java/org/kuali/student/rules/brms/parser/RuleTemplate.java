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
	
	
	public static void main(String[] args){
		RuleTemplate rt = new RuleTemplate();
		
		rt.setRuleName("The Rule Name");
	    
		ArrayList<String> ruleAttributes = new ArrayList<String>();
		ruleAttributes.add("#no-loop true" );
		ruleAttributes.add("#salience 1" );
		rt.setRuleAttributes(ruleAttributes);
		
        ArrayList<String> lhs = new ArrayList<String>();
        lhs.add("n : BooleanNode()" );
        rt.setLHS(lhs);
        
        ArrayList<String> rhs = new ArrayList<String>();
        rhs.add("String logMessage = n.getLeftNode().getRuleFailureMessage() + \" OR \" + n.getRightNode().getRuleFailureMessage();"  );
        rhs.add("n.setRuleFailureMessage(logMessage);");
        rhs.add("System.out.println( n.getRuleFailureMessage() + \"   rule OR\");"  );
        rhs.add("BooleanNode parent = n.getParent();"  );
        rhs.add("if (parent != null){"  );
        rhs.add("    update(parent);"   );
        rhs.add("}"  );
        rt.setRHS(rhs);
        
		rt.process("RuleTemplate.vm");
	}
	   
	public RuleTemplate(){
        try{
        	Properties p = new Properties();
        	p.setProperty("resource.loader", "class");
        	p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader ");
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
