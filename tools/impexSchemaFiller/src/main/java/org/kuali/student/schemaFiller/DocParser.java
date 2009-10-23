package org.kuali.student.schemaFiller;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class DocParser extends DefaultHandler{
	boolean entity = false;
	private HashMap<String,String> annotMap = new HashMap<String,String>();
	
	public HashMap<String,String> getannotMap(){
		return annotMap;
	}
	
	public void  setannotMap(HashMap<String,String> annotMap){
		this.annotMap=annotMap;
	}
	 public void startDocument ()
	    {
		System.out.println("Start document");
	    }


	    public void endDocument ()
	    {
		System.out.println("End document");
	    }


	    public void startElement (String uri, String name,
				      String qName, Attributes atts)
	    {
	    	System.out.println("Start: " + qName);
	    	if(qName.equals("xs:jelclass")){
	    		String packageName = atts.getValue("package");
	    		if(packageName.matches("(?i).*.entity.*")){
	    			System.out.println("Class: " + packageName);
	    			entity=true;
	    			
	    		}
	    		
	    	}
	    	
	    	if(qName.equals("xs:annotation")){
	    		if(atts.getValue("name").equals("Column")){
	    			annotMap.put(atts.getValue("value"), "");
	    		}
	    	}
	    }


	    public void endElement (String uri, String name, String qName)
	    {
		if ("".equals (uri))
		    System.out.println("End element: " + qName);
		else
		    System.out.println("End element:   {" + uri + "}" + name);
		
		
	    }


	    public void characters (char ch[], int start, int length)
	    {
	    	if(entity){
	    		
	    	}
	    }

}
