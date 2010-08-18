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

 
 public List<String> compare ()
 {
  ObjectStructureDefinition osBean = new Bean2DictionaryConverter (clazz).convert ();
  return compare (osDict, osBean);

 }

 private List<String> compare (ObjectStructureDefinition osDict,
                               ObjectStructureDefinition osBean)
 {
  List<String> discrepancies = new ArrayList ();
  compareAddDiscrepancy (discrepancies, "Java Object Name", osDict.getName (), osBean.getName ());
  compareAddDiscrepancy (discrepancies, "hasMetaData", osDict.isHasMetaData (), osBean.isHasMetaData ());
  compareAddDiscrepancy (discrepancies, "BusinessObjectClass", osDict.getBusinessObjectClass (), osBean.getBusinessObjectClass ());
  for (FieldDefinition fdDict : osDict.getAttributes ())
  {
   FieldDefinition fdBean = findField (fdDict.getName (), osBean);
   if (fdBean == null)
   {
    if ( ! fdDict.isDynamic ())
    {
     discrepancies.add ("Field " + fdDict.getName () + " does not exist in the bean");
    }
    continue;
   }
   compareAddDiscrepancy (discrepancies, fdDict.getName () + " dataType", fdDict.getDataType (), fdBean.getDataType ());
   compareAddDiscrepancy (discrepancies, fdDict.getName () + " maxOccurs", fdDict.getMaxOccurs (), fdBean.getMaxOccurs ());
  }
   for (FieldDefinition fdBean : osBean.getAttributes ())
  {
   FieldDefinition fdDict = findField (fdBean.getName (), osDict);
   if (fdDict == null)
   {
    discrepancies.add ("Field " + fdBean.getName () + " missing from dict");
    continue;
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
  return field + " inconsistent: dict=[" + value1 + "], bean=[" + value2 + "]";
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
