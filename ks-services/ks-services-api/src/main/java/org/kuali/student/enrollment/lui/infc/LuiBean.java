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

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.MetaInfc;

public class LuiBean
        implements LuiInfc, Serializable {

 private static final long serialVersionUID = 1L;
 private String luiCode;

 /**
  * Set ????
  *
  * Type: String
  *
  * Code identifier/name for the LUI. This is typically used
  * human readable form (e.g. ENGL 100 section 123).
  */
 @Override
 public void setLuiCode(String luiCode) {
  this.luiCode = luiCode;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * Code identifier/name for the LUI. This is typically used
  * human readable form (e.g. ENGL 100 section 123).
  */
 @Override
 public String getLuiCode() {
  return this.luiCode;
 }
 private String cluId;

 /**
  * Set ????
  *
  * Type: String
  *
  * Unique identifier for a Canonical Learning Unit (CLU).
  */
 @Override
 public void setCluId(String cluId) {
  this.cluId = cluId;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * Unique identifier for a Canonical Learning Unit (CLU).
  */
 @Override
 public String getCluId() {
  return this.cluId;
 }
 private String atpId;

 /**
  * Set ????
  *
  * Type: String
  *
  * Unique identifier for an Academic Time Period (ATP).
  */
 @Override
 public void setAtpId(String atpId) {
  this.atpId = atpId;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * Unique identifier for an Academic Time Period (ATP).
  */
 @Override
 public String getAtpId() {
  return this.atpId;
 }
 private Integer maxSeats;

 /**
  * Set ????
  *
  * Type: Integer
  *
  * Maximum number of "seats" that the LUI will hold for registration.
  */
 @Override
 public void setMaxSeats(Integer maxSeats) {
  this.maxSeats = maxSeats;
 }

 /**
  * Get ????
  *
  * Type: Integer
  *
  * Maximum number of "seats" that the LUI will hold for registration.
  */
 @Override
 public Integer getMaxSeats() {
  return this.maxSeats;
 }
 private Date effectiveDate;

 /**
  * Set ????
  *
  * Type: Date
  *
  * Date and time that this LUI became effective. This is a
  * concept to the effective date on enumerated values. When
  * expiration date has been specified, this field must be
  * than or equal to the expiration date.
  */
 @Override
 public void setEffectiveDate(Date effectiveDate) {
  this.effectiveDate = effectiveDate;
 }

 /**
  * Get ????
  *
  * Type: Date
  *
  * Date and time that this LUI became effective. This is a
  * concept to the effective date on enumerated values. When
  * expiration date has been specified, this field must be
  * than or equal to the expiration date.
  */
 @Override
 public Date getEffectiveDate() {
  return this.effectiveDate;
 }
 private Date expirationDate;

 /**
  * Set ????
  *
  * Type: Date
  *
  * Date and time that this LUI expires. This is a similar
  * to the expiration date on enumerated values. If specified,
  * should be greater than or equal to the effective date. If
  * field is not specified, then no expiration date has
  * currently defined and should automatically be
  * greater than the effective date.
  */
 @Override
 public void setExpirationDate(Date expirationDate) {
  this.expirationDate = expirationDate;
 }

 /**
  * Get ????
  *
  * Type: Date
  *
  * Date and time that this LUI expires. This is a similar
  * to the expiration date on enumerated values. If specified,
  * should be greater than or equal to the effective date. If
  * field is not specified, then no expiration date has
  * currently defined and should automatically be
  * greater than the effective date.
  */
 @Override
 public Date getExpirationDate() {
  return this.expirationDate;
 }
 private List<AttributeInfc> attributes;

 /**
  * Set ????
  *
  * Type: List<AttributeInfc>
  *
  * List of key/value pairs, typically used for dynamic attributes.
  */
 @Override
 public void setAttributes(List<AttributeInfc> attributes) {
  this.attributes = attributes;
 }

 /**
  * Get ????
  *
  * Type: List<AttributeInfc>
  *
  * List of key/value pairs, typically used for dynamic attributes.
  */
 @Override
 public List<AttributeInfc> getAttributes() {
  return this.attributes;
 }
 private MetaInfc metaInfo;

 /**
  * Set ????
  *
  * Type: MetaInfo
  *
  * Create and last update info for the structure. This is
  * and treated as read only since the data is set by the
  * of the service during maintenance operations.
  */
 @Override
 public void setMetaInfo(MetaInfc metaInfo) {
  this.metaInfo = metaInfo;
 }

 /**
  * Get ????
  *
  * Type: MetaInfo
  *
  * Create and last update info for the structure. This is
  * and treated as read only since the data is set by the
  * of the service during maintenance operations.
  */
 @Override
 public MetaInfc getMetaInfo() {
  return this.metaInfo;
 }
 private String state;

 /**
  * Set ????
  *
  * Type: String
  *
  * The current status of the LUI. The values for this field
  * constrained to those in the luState enumeration. A
  * setup operation does not exist for retrieval of the meta
  * around this value.
  */
 @Override
 public void setState(String state) {
  this.state = state;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * The current status of the LUI. The values for this field
  * constrained to those in the luState enumeration. A
  * setup operation does not exist for retrieval of the meta
  * around this value.
  */
 @Override
 public String getState() {
  return this.state;
 }
 private String id;

 /**
  * Set ????
  *
  * Type: String
  *
  * Unique identifier for a Learning Unit Instance (LUI). This
  * optional, due to the identifier being set at the time
  * creation. Once the LUI has been created, this should be seen
  * required.
  */
 @Override
 public void setId(String id) {
  this.id = id;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * Unique identifier for a Learning Unit Instance (LUI). This
  * optional, due to the identifier being set at the time
  * creation. Once the LUI has been created, this should be seen
  * required.
  */
 @Override
 public String getId() {
  return this.id;
 }
}

