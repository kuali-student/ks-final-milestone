package org.kuali.student.common.util;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

public class DataDictionaryUtil {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SecurityException, NoSuchFieldException, IOException {
		// TODO Auto-generated method stub
		
		String dictPackageString="org.kuali.student.core.organization.dto";
		String outputFileName="org-dict.xml";
		
		if(args!=null&&args.length>0&&args[0]!=null){
			outputFileName = args[0];
		}
		if(args!=null&&args.length>1&&args[1]!=null){
			dictPackageString = args[1];
		}

		
		StringBuilder sb = new StringBuilder();
		
		//XML Header
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<beans xmlns=\"http://www.springframework.org/schema/beans\" "+
	"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:dict=\"http://student.kuali.org/xsd/dictionary-extension\" "+
	"xsi:schemaLocation=\" "+
	"http://student.kuali.org/xsd/dictionary-extension http://student.kuali.org/xsd/dictionary-extension/dictionary-extension.xsd "+
	"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd"+
	"\">");
		
		//Repeating/singl constraints
		sb.append("\n<dict:constraint key=\"repeating\" id=\"repeating\"");
		sb.append("\nserverSide=\"false\">");
		sb.append("\n\t<!-- Repeating -->");
		sb.append("\n\t<!-- Nine 9's get translated as \"(unbounded)\" -->");
		sb.append("\n\t<dict:maxOccurs>(unbounded)</dict:maxOccurs>");
		sb.append("\n</dict:constraint>");

		sb.append("\n<dict:constraint key=\"single\" id=\"single\" serverSide=\"false\">");
		sb.append("\n\t<!-- Single valued (non-repeating) -->");
		sb.append("\n\t<!-- Used to override a repeating constraint -->");
		sb.append("\n\t<dict:maxOccurs>1</dict:maxOccurs>");
		sb.append("\n</dict:constraint>");
		
		//output object structures
		Set<Class<?>> processedClasses = new HashSet<Class<?>>();
		for(Class<?> c:getClasses(dictPackageString, true)){
			processObjectStructure(c, sb, processedClasses);
		}
		//Close
		sb.append("\n</beans>");
		
		//Save to file
		saveToFile(outputFileName, sb);
		

	}
    private static void processObjectStructure(Class<?> beanClass, StringBuilder sb, Set<Class<?>> alreadyProcessed) throws ClassNotFoundException {
		if(!alreadyProcessed.contains(beanClass)){
			alreadyProcessed.add(beanClass);
			
	    	Set<Class<?>> childStructures = new HashSet<Class<?>>();

	    	//output parent object structure and default type/state
	    	sb.append("\n<dict:objectStructure key=\""+beanClass.getSimpleName()+"-parent\" id=\""+beanClass.getSimpleName()+"-parent\" abstract=\"true\">");
			sb.append("\n\t<dict:type key=\"default\" id=\""+beanClass.getSimpleName()+".type.default\">");
			sb.append("\n\t\t<dict:state key=\"default\" id=\""+beanClass.getSimpleName()+".state.default\">");
	
			//Loop through properties and output fields
			for(PropertyDescriptor pd:PropertyUtils.getPropertyDescriptors(beanClass)){
				Class<?> type = pd.getPropertyType();
				String name = pd.getName();
				
				//Dont process if not a real field (type,state,id,metaInfo,attributes)
				if(isFieldDescriptor(type,name)){
					sb.append("\n\t\t\t<dict:field key=\""+name+"\">");
					sb.append("\n\t\t\t\t<dict:fieldDescriptor>");
					sb.append("\n\t\t\t\t\t<dict:name>"+name+"</dict:name>");
					sb.append("\n\t\t\t\t\t<dict:desc>"+name+"</dict:desc>");
					//Check if it's a list
					boolean isList = false;
					if(type.isAssignableFrom(java.util.List.class)){
						//Get generic Type
						Type[] actualTypeArguments =  ((ParameterizedType)pd.getReadMethod().getGenericReturnType()).getActualTypeArguments();
						type = (Class<?>) actualTypeArguments[0];
						isList = true;
					}
					
					//see if it's complex
					if(isComplex(type)){
						//Add a complex type and reference
						String complexRef = type.getSimpleName();
						sb.append("\n\t\t\t\t\t<dict:dataType>complex</dict:dataType>");
						sb.append("\n\t\t\t\t\t<dict:objectStructureRef bean=\""+complexRef+"\"/>");

						//Add this type to the list of child structures we need to add
						childStructures.add(type);
					}else{
						//Get regular type
						sb.append("\n\t\t\t\t\t<dict:dataType>"+getTypeString(type)+"</dict:dataType>");
					}
					
					//Close up field descriptor
					sb.append("\n\t\t\t\t</dict:fieldDescriptor>");
					
					
					//Add single/repeating constraints
					sb.append("\n\t\t\t\t<dict:constraintDescriptor>");
					if(isList){
						sb.append("\n\t\t\t\t\t<dict:constraintRef bean=\"repeating\"/>");
					}else{
						sb.append("\n\t\t\t\t\t<dict:constraintRef bean=\"single\"/>");
					}
					sb.append("\n\t\t\t\t</dict:constraintDescriptor>");
					
					sb.append("\n\t\t\t</dict:field>");
				}
			}
			sb.append("\n\t\t</dict:state>");
			sb.append("\n\t</dict:type>");
	    	sb.append("\n</dict:objectStructure>");
	    	
	    	//Output a child objectstructure
	    	sb.append("\n<dict:objectStructure key=\""+beanClass.getSimpleName()+"\" id=\""+beanClass.getSimpleName()+"\" parent=\""+beanClass.getSimpleName()+"-parent\"/>");
	    	
	    	//Process any child structures
	    	for(Class<?> childStructureClass:childStructures){
	    		processObjectStructure(childStructureClass, sb, alreadyProcessed);
	    	}
		}
	}
	private static void saveToFile(String filename, StringBuilder sb) throws IOException {
    	PrintWriter out = new PrintWriter(new FileWriter(filename));
    	out.print(sb.toString());
    	out.close();
//    	System.out.print(sb.toString());
    }

	private static String getTypeString(Class<?> type) {
		return type.getSimpleName();
	}

	private static boolean isComplex(Class<?> type){
		if(type.isPrimitive()){
			return false;
		}
		if(type.isAssignableFrom(String.class)||
		   type.isAssignableFrom(Date.class)||
		   type.isAssignableFrom(Integer.class)||
		   type.isAssignableFrom(Boolean.class)){
			return false;
		}
		
		return true;
	}

	private static boolean isFieldDescriptor(Class<?> type, String name) {
		//System.out.println("Checking if Field Type:"+type.toString()+" Name:"+name);
		if("id".equals(name)||
		   "type".equals(name)||
		   "state".equals(name)||
		   "metaInfo".equals(name)||
		   "attributes".equals(name)||
		   "class".equals(name)){
			return false;
		}
		if(type.isAnnotation()){
			return false;
		}
		return true;
	}
	
	//Taken from http://forums.sun.com/thread.jspa?threadID=341935&start=15
	private static File getClassesHelper(String pckgname)
			throws ClassNotFoundException {
		// Get a File object for the package
		File directory = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			String path = pckgname.replace('.', '/');
			URL resource = cld.getResource(path);
			if (resource == null) {
				throw new ClassNotFoundException("No resource for " + path);
			}
			directory = new File(resource.getFile());
			return directory;
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(pckgname + " (" + directory
					+ ") does not appear to be a valid package");
		}
	}

	public static Class<?>[] getClasses(String pckgname,
			boolean skipDollarClasses) {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		try {
			File directory = getClassesHelper(pckgname);

			if (directory.exists()) {
				// Get the list of the files contained in the package
				String[] files = directory.list();
				for (int i = 0; i < files.length; i++) {
					// we are only interested in .class files
					if (files[i].endsWith(".class")) {
						// get rid of the ".class" at the end
						String withoutclass = pckgname + '.'
								+ files[i].substring(0, files[i].length() - 6);

						// in case we don't want $1 $2 etc. endings (i.e. common
						// in GUI classes)
						if (skipDollarClasses) {
							int dollar_occurence = withoutclass.indexOf("$");
							if (dollar_occurence != -1) {
								withoutclass = withoutclass.substring(0,
										dollar_occurence);
							}
						}

						// add this class to our list but avoid duplicates
						boolean already_contained = false;
						for (Class<?> c : classes) {
							if (c.getCanonicalName().equals(withoutclass)) {
								already_contained = true;
							}
						}
						if (!already_contained) {
							classes.add(Class.forName(withoutclass));
						}
						// REMARK this kind of checking is quite slow using
						// reflection, it would be better
						// to do the class.forName(...) stuff outside of this
						// method and change the method
						// to only return an ArrayList with fqcn Strings. Also
						// in reality we have the $1 $2
						// etc. classes in our packages, so we are skipping some
						// "real" classes here
					}
				}
			} else {
				throw new ClassNotFoundException(pckgname
						+ " does not appear to be a valid package");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Class<?>[] classesA = new Class[classes.size()];
		classes.toArray(classesA);
		return classesA;
	}
}
