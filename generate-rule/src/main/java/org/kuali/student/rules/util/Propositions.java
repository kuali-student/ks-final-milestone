/**
 * 
 */
package org.kuali.student.rules.util;

import java.util.HashMap;
import java.util.List;


/**
 * @author Rich Diaz
 *
 */

public class Propositions {
	private static HashMap<String, Boolean> nodeValueMap;
	private static HashMap<String, String> nodeFailureMessageMap;
	
	public static final void main(String[] args) {
		Propositions.init("A0*B4+(C*D)");
		System.out.println("Prop A0 is " + Propositions.getProposition("A0"));
		System.out.println("Prop A0 failure message is " + Propositions.getFailureMessage("A0"));
	}
	
	public static void init(String functionString) {
		
		Function f = new Function(functionString);
		List<String> funcVars = f.getVariables();
		
		nodeValueMap = new HashMap<String, Boolean>();
		nodeFailureMessageMap = new HashMap<String, String>();
		
		for (String var : funcVars) {
			//System.out.println("The var is a " + var );
			setProposition(var, false);
			setFailureMessage(var, "null");
        }
	}
	
	public static void setProposition(String propVar, Boolean propValue){
		nodeValueMap.put(propVar, propValue);
	}
	
	public static void setFailureMessage(String propVar, String propFailureMessage){
		nodeFailureMessageMap.put(propVar, propFailureMessage);
	}
	
	public static Boolean getProposition(String propVar){
		return nodeValueMap.get(propVar);
	}
	
	public static String getFailureMessage(String propVar){
		return nodeFailureMessageMap.get(propVar);
	}
	
	/*
	public HashMap<String, Boolean> getPropositions(){
		return nodeValueMap;
	}
	
	public HashMap<String, String> getFailureMessages(){
		return nodeFailureMessageMap;
	}
	*/
}
