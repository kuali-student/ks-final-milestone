/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.student.enrollment.lui.infc;

import java.util.Date;
import java.util.List;
import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.MetaInfc;

public interface LuiLuiRelationInfc {

 /**
  * Set ????
  *
  * Type: String
  *
  * Unique identifier for a Learning Unit Instance (LUI).
  */
 public void setLuiId(String luiId);

 /**
  * Get ????
  *
  * Type: String
  *
  * Unique identifier for a Learning Unit Instance (LUI).
  */
 public String getLuiId();

 /**
  * Set ????
  *
  * Type: String
  *
  * Unique identifier for a Learning Unit Instance (LUI).
  */
 public void setRelatedLuiId(String relatedLuiId);

 /**
  * Get ????
  *
  * Type: String
  *
  * Unique identifier for a Learning Unit Instance (LUI).
  */
 public String getRelatedLuiId();

 /**
  * Set ????
  *
  * Type: Date
  *
  * Date and time that this LUI to LUI relationship type
  * effective. This is a similar concept to the effective date
  * enumerated values. When an expiration date has been
  * this field must be less than or equal to the expiration date.
  */
 public void setEffectiveDate(Date effectiveDate);

 /**
  * Get ????
  *
  * Type: Date
  *
  * Date and time that this LUI to LUI relationship type
  * effective. This is a similar concept to the effective date
  * enumerated values. When an expiration date has been
  * this field must be less than or equal to the expiration date.
  */
 public Date getEffectiveDate();

 /**
  * Set ????
  *
  * Type: Date
  *
  * Date and time that this LUI to LUI relationship
  * expires. This is a similar concept to the expiration date
  * enumerated values. If specified, this should be greater than
  * equal to the effective date. If this field is not
  * then no expiration date has been currently defined and
  * automatically be considered greater than the effective date.
  */
 public void setExpirationDate(Date expirationDate);

 /**
  * Get ????
  *
  * Type: Date
  *
  * Date and time that this LUI to LUI relationship
  * expires. This is a similar concept to the expiration date
  * enumerated values. If specified, this should be greater than
  * equal to the effective date. If this field is not
  * then no expiration date has been currently defined and
  * automatically be considered greater than the effective date.
  */
 public Date getExpirationDate();

 /**
  * Set ????
  *
  * Type: List<AttributeInfc>
  *
  * List of key/value pairs, typically used for dynamic attributes.
  */
 public void setAttributes(List<AttributeInfc> attributes);

 /**
  * Get ????
  *
  * Type: List<AttributeInfc>
  *
  * List of key/value pairs, typically used for dynamic attributes.
  */
 public List<AttributeInfc> getAttributes();

 /**
  * Set ????
  *
  * Type: MetaInfo
  *
  * Create and last update info for the structure. This is
  * and treated as read only since the data is set by the
  * of the service during maintenance operations.
  */
 public void setMetaInfo(MetaInfc metaInfo);

 /**
  * Get ????
  *
  * Type: MetaInfo
  *
  * Create and last update info for the structure. This is
  * and treated as read only since the data is set by the
  * of the service during maintenance operations.
  */
 public MetaInfc getMetaInfo();

 /**
  * Set ????
  *
  * Type: String
  *
  * Unique identifier for the LU to LU relation type.
  */
 public void setType(String type);

 /**
  * Get ????
  *
  * Type: String
  *
  * Unique identifier for the LU to LU relation type.
  */
 public String getType();

 /**
  * Set ????
  *
  * Type: String
  *
  * The current status of the LUI to LUI relationship. The
  * for this field are constrained to those in
  * luLuRelationState enumeration. A separate setup operation
  * not exist for retrieval of the meta data around this value.
  */
 public void setState(String state);

 /**
  * Get ????
  *
  * Type: String
  *
  * The current status of the LUI to LUI relationship. The
  * for this field are constrained to those in
  * luLuRelationState enumeration. A separate setup operation
  * not exist for retrieval of the meta data around this value.
  */
 public String getState();

 /**
  * Set ????
  *
  * Type: String
  *
  * Unique identifier for a LUI to LUI relation. This is
  * due to the identifier being set at the time of creation.
  * the relation has been created, this should be seen as required.
  */
 public void setId(String id);

 /**
  * Get ????
  *
  * Type: String
  *
  * Unique identifier for a LUI to LUI relation. This is
  * due to the identifier being set at the time of creation.
  * the relation has been created, this should be seen as required.
  */
 public String getId();
}

