/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model.util;

import java.util.List;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.XmlType;

/**
 *
 * @author nwright
 */
public class DictionaryParentSetter
{

 private DictionaryModel model;
 private ModelFinder finder;

 public DictionaryParentSetter (DictionaryModel model)
 {
  this.model = model;
  this.finder = new ModelFinder (model);
 }

 public void set ()
 {
  for (int i = 1; i < model.getDictionary ().size (); i ++)
  {
   Dictionary child = model.getDictionary ().get (i);
   Dictionary parent = calcParent (i, child);
   child.setParent (parent);
  }
 }

 private Dictionary calcParent (int index, Dictionary child)
 {
  // if first field the parent is by default null
  // course.official
  if (index == 0)
  {
   return null;
  }
  // if first field of the defnition of a new main type
  // then the parent is null
  // i.e. program.official is the start of a new cluInfo definition
  XmlType xmlType = finder.findXmlType (child.getXmlObject ());
  if (xmlType == null)
  {
   throw new DictionaryExecutionException ("child.getXmlObject ()=" + child.getXmlObject ());
  }
  if (xmlType.hasOwnCreateUpdate ())
  {
   List<Field> fields = finder.findFields (child.getXmlObject ());
   if (fields.get (0).getShortName ().equalsIgnoreCase (child.getShortName ()))
   {
    return null;
   }
  }
  Dictionary prev = model.getDictionary ().get (index - 1);
  // if this is just another field on same object as prev
  // then they have the same parent
  // course.official.no	             cluIdentifierInfo
  // course.official.transcriptTitle	cluIdentifierInfo    << this has the same parent as previous
  if (prev.getXmlObject ().equalsIgnoreCase (child.getXmlObject ()))
  {
   return prev.getParent ();
  }
  // objects are different so check if we are going down or up
  // if going down the hierarchy
  Field prevField = finder.findField (prev);
  if (prevField == null)
  {
   throw new DictionaryExecutionException
    ("Could not find field associated with dictionary entry with id =" + prev.getId ());
  }
  // going down heirarchy if this sub-object is the same type of the previous field
  // because that means this field is  the 1st field of the sub-type definition
  if (calcType (prevField.getXmlType ()).equalsIgnoreCase (child.getXmlObject ()))
  {
   // loop back to find the first (default) definition for that field -- that is the real parent
   // not the state override
   // course.desc                <<<< Make this the parent
   // course.desc.draft.private
   // course.desc.template
   // course.desc.draft.public   <<<< not this
   // course.desc.plain          <<<< of this
   for (int i = index - 2; i > -1; i --)
   {
    Dictionary prev2 = model.getDictionary ().get (i);
    if (prev2.getXmlObject ().equalsIgnoreCase (prev.getXmlObject ()))
    {
     if (prev2.getShortName ().equalsIgnoreCase (prev.getShortName ()))
     {
      prev = prev2;
      continue;
     }
    }
    break;
   }
   return prev;
  }
  // we are popping up from down in the heirarchy
  // have to go back to find the previous item at the same level
  // course.desc	          		            cluInfo
  // course.desc.draft.private	          cluInfo
  // course.desc.template	          	    cluInfo
  // course.desc.draft.public	           cluInfo       << use this parent
  // course.desc.plain	          	       richTextInfo
  // course.desc.plain.draft.private	    richTextInfo
  // course.desc.plain.draft.public	     richTextInfo
  // course.desc.formatted	              richTextInfo
  // course.desc.formatted.draft.private	richTextInfo
  // course.desc.formatted.draft.public	 richTextInfo
  // course.rationale	          	        cluInfo       << as this field's parent
  for (int i = index - 1; i > -1; i --)
  {
   Dictionary dict = model.getDictionary ().get (i);
   if (dict.getXmlObject ().equalsIgnoreCase (child.getXmlObject ()))
   {
    return dict.getParent ();
   }
  }
  throw new DictionaryExecutionException ("dictionary entry " + child.getId ()
   + " could not calculate the parent");
 }

 private String calcType (String type)
 {
  if (type.endsWith ("List"))
  {
   type = type.substring (0, type.length () - "List".length ());
  }

  return type;
 }

}
