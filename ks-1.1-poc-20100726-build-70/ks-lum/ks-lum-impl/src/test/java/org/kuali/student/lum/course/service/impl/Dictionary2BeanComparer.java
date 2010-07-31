package org.kuali.student.lum.course.service.impl;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;

public class Dictionary2BeanComparer
{


 private Class<?> clazz;
 private ObjectStructureDefinition osDict;

 public Dictionary2BeanComparer (Class<?> clazz, ObjectStructureDefinition osDict)
 {
  this.clazz = clazz;
  this.osDict = osDict;
 }

 
 public List<String> validate ()
 {
  ObjectStructureDefinition osBean = new Bean2DictionaryConverter (clazz).convert ();
  return compare (osDict, osBean);

 }

 private List<String> compare (ObjectStructureDefinition osDict,
                               ObjectStructureDefinition osBean)
 {
  List<String> errors = new ArrayList ();
  compareAddError (errors, "Java Object Name", osBean.getName (), osDict.getName ());
  compareAddError (errors, "hasMetaData", osBean.isHasMetaData (), osDict.isHasMetaData ());
  compareAddError (errors, "BusinessObjectClass", osBean.getBusinessObjectClass (), osDict.getBusinessObjectClass ());
  for (FieldDefinition fdDict : osDict.getAttributes ())
  {
   FieldDefinition fdBean = findField (fdDict.getName (), osBean);
   if (fdBean == null)
   {
    if ( ! fdDict.isDynamic ())
    {
     errors.add ("Field " + fdDict.getName () + " missing from bean");
    }
    continue;
   }
   compareAddError (errors, fdDict.getName () + " dataType", fdDict.getDataType (), fdBean.getDataType ());
   compareAddError (errors, fdDict.getName () + " maxOccurs", fdDict.getMaxOccurs (), fdBean.getMaxOccurs ());
  }
   for (FieldDefinition fdBean : osBean.getAttributes ())
  {
   FieldDefinition fdDict = findField (fdBean.getName (), osDict);
   if (fdDict == null)
   {
    errors.add ("Field " + fdBean.getName () + " missing from dict");
    continue;
   }
  }
  return errors;
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

 private void compareAddError (List<String> errors, String field, Object value1,
                               Object value2)
 {
  String error = compare (field, value1, value2);
  if (error != null)
  {
   errors.add (error);
  }
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
  return field + " inconsistent: dict=[" + value1 + "], bean=[" + value2 + "]";
 }
}
