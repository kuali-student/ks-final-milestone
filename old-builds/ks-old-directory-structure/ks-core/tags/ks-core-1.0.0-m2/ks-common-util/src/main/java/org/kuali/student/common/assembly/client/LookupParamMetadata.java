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

import org.kuali.student.common.assembly.client.Data.Value;

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
public class LookupParamMetadata
{

 private static final long serialVersionUID = 1L;
 private String key;
 private Metadata metadata;
 private boolean optional;
 private Value defaultValue;
 private String defaultValuePath;

 public Metadata getMetadata ()
 {
  return metadata;
 }

 public void setMetadata (Metadata metadata)
 {
  this.metadata = metadata;
 }

 public String getKey ()
 {
  return key;
 }

 public void setKey (String key)
 {
  this.key = key;
 }

 public boolean isOptional ()
 {
  return optional;
 }

 public void setOptional (boolean optional)
 {
  this.optional = optional;
 }

 public Value getDefaultValue ()
 {
  return defaultValue;
 }

 public void setDefaultValue (Value defaultValue)
 {
  this.defaultValue = defaultValue;
 }

 public String getDefaultValuePath ()
 {
  return defaultValuePath;
 }

 public void setDefaultValuePath (String defaultValuePath)
 {
  this.defaultValuePath = defaultValuePath;
 }

}
