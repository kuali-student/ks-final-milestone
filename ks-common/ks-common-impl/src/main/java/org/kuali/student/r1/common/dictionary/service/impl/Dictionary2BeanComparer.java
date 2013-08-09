package org.kuali.student.r1.common.dictionary.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.RichTextInfo;

@Deprecated
public class Dictionary2BeanComparer
{


 private String className;
 private ObjectStructureDefinition osDict;

 public Dictionary2BeanComparer (String className, ObjectStructureDefinition osDict)
 {
  this.className = className;
  this.osDict = osDict;
 }


 public List<String> compare ()
 {
  if (className == null)
  {
   return Arrays.asList (osDict.getName () + " does not have a corresponding java class");
  }
  Class<?> clazz = null;
  try
  {
   clazz = Class.forName (className);
  }
  catch (ClassNotFoundException ex)
  {
   return Arrays.asList (className + " does not have a corresponding java class");
  }
  ObjectStructureDefinition osBean = new Bean2DictionaryConverter (clazz).convert ();
  return compare (osDict, osBean);

 }

 private List<String> compare (ObjectStructureDefinition osDict,
                               ObjectStructureDefinition osBean)
 {
  List<String> discrepancies = new ArrayList ();
  if (osDict == null && osBean != null) {
	discrepancies.add("osDict is null " + osBean.getName());
}
  if (osBean == null && osDict != null) {
	discrepancies.add("osBean is null " + osDict.getName());
}
  if (discrepancies.size() > 0) {
	return discrepancies;
}
  compareAddDiscrepancy (discrepancies, "Java class name", osDict.getName (), osBean.getName ());
//Debuggin System.out.println(osDict.getName());
  if (!osDict.getClass().equals(RichTextInfo.class)) {	
  compareAddDiscrepancy (discrepancies, "Has meta data?" + osDict.getName() + " vs " + osBean.getName(), osDict.isHasMetaData (), osBean.isHasMetaData ());
  }
  compareAddDiscrepancy (discrepancies, "Business object class", osDict.getBusinessObjectClass (), osBean.getBusinessObjectClass ());
  for (FieldDefinition fdDict : osDict.getAttributes ())
  {
   FieldDefinition fdBean = findField (fdDict.getName (), osBean);
// Debuggin System.out.println(fdDict.getName());
   if (fdBean == null)
   {
    if ( ! fdDict.isDynamic ())
    {
     discrepancies.add ("Field " + fdDict.getName () + " does not exist in the corresponding java class");
    }
    continue;
   }
   compareAddDiscrepancy (discrepancies, fdDict.getName () + " dataType", fdDict.getDataType (), fdBean.getDataType ());
   compareAddDiscrepancy (discrepancies, fdDict.getName () + " maxOccurs", fdDict.getMaxOccurs (), fdBean.getMaxOccurs ());
  }
   for (FieldDefinition fdBean : osBean.getAttributes ())
  {
   FieldDefinition fdDict = findField (fdBean.getName (), osDict);
// Debuggin System.out.println(fdBean.getName());
   if (fdDict == null)	{ 
   if (!fdBean.getName().equals("meta") 
		   && !fdBean.getName().equals("state")
		   && !fdBean.getName().equals("type")) {
	// Debuggin System.out.println("test");
    discrepancies.add (" Field " + fdBean.getName () + " missing from the dictictionary");
    continue;
   }
   }
  }
  return discrepancies;
 }

 private FieldDefinition findField (String name, ObjectStructureDefinition os)
 {
  for (FieldDefinition fd : os.getAttributes ())
  {
   if (name.equals (fd.getName ()))
   {
    return fd;
   }
  }
  return null;
 }

 private void compareAddDiscrepancy (List<String> discrepancies, String field, boolean value1,
                               boolean value2)
 {
  String discrep = compare (field, value1, value2);
  if (discrep != null)
  {
   discrepancies.add (discrep);
  }
 }

 private void compareAddDiscrepancy (List<String> discrepancies, String field, Object value1,
                               Object value2)
 {
  String discrep = compare (field, value1, value2);
  if (discrep != null)
  {
   discrepancies.add (discrep);
  }
 }

  private String compare (String field, boolean value1, boolean value2)
 {
  if (value1)
  {
   if (value2)
   {
    return null;
   }
  }
  if ( ! value1)
  {
   if ( ! value2)
   {
    return null;
   }
  }
  return field + " inconsistent: dictionary='" + value1 + "', java class='" + value2 + "'";
 }

 private String compare (String field, Object value1, Object value2)
 {
  if (value1 == null)
  {
   if (value2 == null)
   {
    return null;
   }
  }
  else
  {
   if (value1.equals (value2))
   {
    return null;
   }
  }
  return field + " inconsistent: dictionary='" + value1 + "'], java class='" + value2 + "'";
 }
}
