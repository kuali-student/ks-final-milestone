package org.kuali.student.r1.core.dictionary.service;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;

public class Dictionary2DictionaryComparer
{

 private ObjectStructureDefinition os1;
 private ObjectStructureDefinition os2;

 public Dictionary2DictionaryComparer (ObjectStructureDefinition os1,
                                       ObjectStructureDefinition os2)
 {
  this.os1 = os1;
  this.os2 = os2;
 }

 public List<String> compare ()
 {
  return compare (os1, os2);

 }

 private List<String> compare (ObjectStructureDefinition os1,
                               ObjectStructureDefinition os2)
 {
  List<String> discrepancies = new ArrayList ();
  compare (discrepancies, "Java Object Name", os2.getName (),
           os1.getName ());
  compare (discrepancies, "hasMetaData", os2.isHasMetaData (),
           os1.isHasMetaData ());
  compare (discrepancies, "BusinessObjectClass",
           os2.getBusinessObjectClass (),
           os1.getBusinessObjectClass ());
  for (FieldDefinition fdDict : os1.getAttributes ())
  {
   FieldDefinition fdBean = findField (fdDict.getName (), os2);
   if (fdBean == null)
   {
    discrepancies.add ("Field " + fdDict.getName ()
                       + " is missing from the 1st structure");
   }
   compare (discrepancies, fdDict.getName () + " dataType",
            fdDict.getDataType (), fdBean.getDataType ());
   compare (discrepancies, fdDict.getName () + " minOccurs",
            fdDict.getMinOccurs (), fdBean.getMinOccurs ());
   compare (discrepancies, fdDict.getName () + " maxOccurs",
            fdDict.getMaxOccurs (), fdBean.getMaxOccurs ());
  }
  for (FieldDefinition fdBean : os2.getAttributes ())
  {
   FieldDefinition fdDict = findField (fdBean.getName (), os1);
   if (fdDict == null)
   {
    discrepancies.add ("Field " + fdBean.getName ()
                       + " missing from the 2nd structure");
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

 private void compare (List<String> discrepancies,
                       String field,
                       Object value1,
                       Object value2)
 {
  String discrep = compare (field, value1, value2);
  if (discrep != null)
  {
   discrepancies.add (discrep);
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
  return field + " inconsistent: os1=[" + value1 + "], os2=[" + value2 + "]";
 }
}
