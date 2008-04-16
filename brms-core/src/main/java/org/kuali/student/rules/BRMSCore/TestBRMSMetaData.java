package org.kuali.student.rules.BRMSCore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.io.StringWriter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.MethodInvocationException;

public class TestBRMSMetaData {	
	

	public static void main(String [ ] args) throws Exception {
		
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		
		// add a shutdown hook for the above context... 
        ctx.registerShutdownHook();	
		       
        BRMSMetaData metadata = (BRMSMetaData) ctx.getBean("BRMSMetaData");
        
		//BRMSMetaData metadata = new BRMSMetaData();
		
		//insert our demo MetaData
        try {
           // metadata.insertBusinessRuleMetadata();
        } catch (Exception e) {
            System.out.println("Cannot insert duplicate record:" + e.getMessage());
        }
        
        FunctionalBusinessRule rule = metadata.getFunctionalBusinessRuleTest("PR 40244");               
        System.out.println("metadata.getBusinessRuleSetDAO()="+metadata.getBusinessRuleSetDAO());
        //FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRule(id); 
        
        String functionString = metadata.getRuleFunctionString(rule);
        System.out.println("Function String: " + functionString);
        
        HashMap <String, RuleProposition> propositions = metadata.getRulePropositions(rule);
        
        //System.out.println("DROOLS:" + metadata.mapMetaRuleToDroolRule("test"));
        
	}	
}
