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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Metadata
{
 // TODO this class, and referenced classes, need to be moved into a GWT module

 private static final long serialVersionUID = 1L;

 public enum WriteAccess
 {

  ON_CREATE, /* must also be required */
  ALWAYS,
  NEVER
 }
 private WriteAccess writeAccess;
 private Data.DataType dataType;
 private Data.Value defaultValue;
 private List<ConstraintMetadata> constraints;
 private LookupMetadata lookupMetadata;
 private Map<String, Metadata> childProperites;

 public List<ConstraintMetadata> getConstraints ()
 {
  if (constraints == null)
  {
   constraints = new ArrayList<ConstraintMetadata> ();
  }
  return constraints;
 }

 public void setConstraints (List<ConstraintMetadata> constraints)
 {
  this.constraints = constraints;
 }

 public Data.DataType getDataType ()
 {
  return dataType;
 }

 /**
  * @deprecated
  * @see #setDataType
  */
 public void setDataType (String strType)
 {
  for (Data.DataType dt : Data.DataType.values ())
  {
   if (dt.toString ().equalsIgnoreCase (strType))
   {
    setDataType (dt);
    return;
   }
  }
  throw new IllegalArgumentException (strType);
 }

 public void setDataType (Data.DataType dataType)
 {
  this.dataType = dataType;
 }

 public Data.Value getDefaultValue ()
 {
  return defaultValue;
 }

 public void setDefaultValue (Data.Value defaultValue)
 {
  this.defaultValue = defaultValue;
 }

 public LookupMetadata getLookupMetadata ()
 {
  return lookupMetadata;
 }

 public void setLookupMetadata (LookupMetadata lookupMetadata)
 {
  this.lookupMetadata = lookupMetadata;
 }

 public Map<String, Metadata> getProperties ()
 {
  if (childProperites == null)
  {
   childProperites = new HashMap<String, Metadata> ();
  }
  return childProperites;
 }

 public void setProperties (Map<String, Metadata> properties)
 {
  this.childProperites = properties;
 }

 public WriteAccess getWriteAccess ()
 {
  return writeAccess;
 }

 public void setWriteAccess (WriteAccess writeAccess)
 {
  this.writeAccess = writeAccess;
 }

 private boolean onChangeRefereshMetadata;

 public boolean isOnChangeRefereshMetadata ()
 {
  return onChangeRefereshMetadata;
 }

 public void setOnChangeRefereshMetadata (boolean onChangeRefereshMetadata)
 {
  this.onChangeRefereshMetadata = onChangeRefereshMetadata;
 }

}
