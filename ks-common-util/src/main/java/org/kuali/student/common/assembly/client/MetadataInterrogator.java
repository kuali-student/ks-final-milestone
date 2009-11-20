/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.assembly.client;

/**
 *
 * @author nwright
 */
public class MetadataInterrogator
{

 private Metadata meta;

 public MetadataInterrogator (Metadata meta)
 {
  this.meta = meta;
 }

 /**
  * checks if is required
  * @return true if any minOccurs > 1
  */
 public boolean isRequired ()
 {
  // TODO: worry about special validators
  for (ConstraintMetadata cons : meta.getConstraints ())
  {
   if (cons.getMinOccurs () != null)
   {
    if (cons.getMinOccurs () > 0)
    {
     return true;
    }
   }
  }
  return false;
 }

 /**
  * checks if this field is a repeating field
  * @return true if the smallest maxOccurs is > 1
  */
 public boolean isRepeating ()
 {
  Integer smallestMaxOccurs = null;
  // TODO: worry aboutg special validators
  // TODO: worry about how this applies to non-strings?
  for (ConstraintMetadata cons : meta.getConstraints ())
  {
   if (cons.getMaxOccurs () != null)
   {
    if (smallestMaxOccurs == null)
    {
     smallestMaxOccurs = cons.getMaxOccurs ();
    }
    else if (cons.getMaxOccurs () < smallestMaxOccurs)
    {
     smallestMaxOccurs = cons.getMaxOccurs ();
    }
   }
  }
  // not specified so unbounded
  if (smallestMaxOccurs == null)
  {
   return true;
  }
  if (smallestMaxOccurs > 1)
  {
   return true;
  }
  return false;
 }

 public Integer getMaxLength ()
 {
  Integer maxLength = null;
  // TODO: worry aboutg special validators
  // TODO: worry about how this applies to non-strings?
  for (ConstraintMetadata cons : meta.getConstraints ())
  {
   if (cons.getMaxLength () != null)
   {
    if (maxLength == null)
    {
     maxLength = cons.getMaxLength ();
    }
    else if (cons.getMaxLength () > maxLength)
    {
     maxLength = cons.getMaxLength ();
    }
   }
  }
  return maxLength;
 }

 public boolean isMultilined ()
 {
  // TODO: worry about hard coding ids
  for (ConstraintMetadata cons : meta.getConstraints ())
  {
   if (cons.getId ().equals ("multi.line.text"))
   {
    return true;
   }
   if (cons.getId ().equals ("single.line.text"))
   {
    return false;
   }
   if (cons.getId ().equals ("code"))
   {
    return false;
   }
   if (cons.getId ().equals ("no.linefeeds"))
   {
    return false;
   }
  }
  // TODO: Worry about hard coding the cut-off point
  Integer maxLength = this.getMaxLength ();
  if (maxLength != null)
  {
   if (maxLength > 60)
   {
    return true;
   }
  }
  return false;
 }

}
