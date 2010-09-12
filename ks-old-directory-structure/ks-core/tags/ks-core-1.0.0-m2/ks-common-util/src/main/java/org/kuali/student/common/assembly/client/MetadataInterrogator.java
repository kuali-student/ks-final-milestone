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
  Integer largestMinOccurs = getLargestMinOccurs ();
  // no min occurs specified so not required
  if (largestMinOccurs == null)
  {
   return false;
  }
  if (largestMinOccurs >= 1)
  {
   return true;
  }
  return false;
 }

 /**
  * get the largest min occurs value
  * @return null if none specified
  */
 public Integer getLargestMinOccurs ()
 {
  Integer largestMinOccurs = null;
  // TODO: worry aboutg special validators
  // TODO: worry about how this applies to non-strings?
  for (ConstraintMetadata cons : meta.getConstraints ())
  {
   if (cons.getMinOccurs () != null)
   {
    if (largestMinOccurs == null)
    {
     largestMinOccurs = cons.getMinOccurs ();
    }
    else if (cons.getMinOccurs () > largestMinOccurs)
    {
     largestMinOccurs = cons.getMinOccurs ();
    }
   }
  }
  return largestMinOccurs;
 }

 /**
  * checks if this field is a repeating field
  * @return true if the smallest maxOccurs is > 1
  */
 public boolean isRepeating ()
 {
  Integer smallestMaxOccurs = getSmallestMaxOccurs ();
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

 /**
  * checks if this field is a repeating field
  * @return true if the smallest maxOccurs is > 1
  */
 public Integer getSmallestMaxOccurs ()
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
  return smallestMaxOccurs;
 }

 /**
  * get the largest min occurs value
  * @return null if none specified
  */
 public Integer getLargestMinLength ()
 {
  Integer largestMinLength = null;
  // TODO: worry aboutg special validators
  // TODO: worry about how this applies to non-strings?
  for (ConstraintMetadata cons : meta.getConstraints ())
  {
   if (cons.getMinLength () != null)
   {
    if (largestMinLength == null)
    {
     largestMinLength = cons.getMinLength ();
    }
    else if (cons.getMaxOccurs () > largestMinLength)
    {
     largestMinLength = cons.getMinLength ();
    }
   }
  }
  return largestMinLength;
 }

 public Integer getSmallestMaxLength ()
 {
  Integer smallestMaxLength = null;
  // TODO: worry aboutg special validators
  // TODO: worry about how this applies to non-strings?
  for (ConstraintMetadata cons : meta.getConstraints ())
  {
   if (cons.getMaxLength () != null)
   {
    if (smallestMaxLength == null)
    {
     smallestMaxLength = cons.getMaxLength ();
    }
    else if (cons.getMaxLength () < smallestMaxLength)
    {
     smallestMaxLength = cons.getMaxLength ();
    }
   }
  }
  return smallestMaxLength;
 }

 public boolean isMultilined ()
 {
  // TODO: worry about hard coding ids
  for (ConstraintMetadata cons : meta.getConstraints ())
  {
   if (cons.getId () != null)
   {
    if (cons.getId ().equals ("rich.text"))
    {
     return true;
    }
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
  }
  // TODO: Worry about hard coding the cut-off point
  Integer maxLength = this.getSmallestMaxLength ();
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
