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
import org.kuali.student.enrollment.common.infc.AttributeInfc;
import org.kuali.student.enrollment.common.infc.MetaInfc;


public interface LuiInfc
{
	
	/**
	* Set ????
	*
	* Type: String
	*
	* Code identifier/name for the LUI. This is typically used 
	* human readable form (e.g. ENGL 100 section 123).
	*/
	public void setLuiCode(String luiCode);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* Code identifier/name for the LUI. This is typically used 
	* human readable form (e.g. ENGL 100 section 123).
	*/
	public String getLuiCode();
	
	
	
	/**
	* Set ????
	*
	* Type: String
	*
	* Unique identifier for a Canonical Learning Unit (CLU).
	*/
	public void setCluId(String cluId);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* Unique identifier for a Canonical Learning Unit (CLU).
	*/
	public String getCluId();
	
	
	
	/**
	* Set ????
	*
	* Type: String
	*
	* Unique identifier for an Academic Time Period (ATP).
	*/
	public void setAtpId(String atpId);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* Unique identifier for an Academic Time Period (ATP).
	*/
	public String getAtpId();
	
	
	
	/**
	* Set ????
	*
	* Type: Integer
	*
	* Maximum number of "seats" that the LUI will hold for registration.
	*/
	public void setMaxSeats(Integer maxSeats);
	
	/**
	* Get ????
	*
	* Type: Integer
	*
	* Maximum number of "seats" that the LUI will hold for registration.
	*/
	public Integer getMaxSeats();
	
	
	
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
	public void setEffectiveDate(Date effectiveDate);
	
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
	public Date getEffectiveDate();
	
	
	
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
	public void setExpirationDate(Date expirationDate);
	
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
	* The current status of the LUI. The values for this field 
	* constrained to those in the luState enumeration. A 
	* setup operation does not exist for retrieval of the meta 
	* around this value.
	*/
	public void setState(String state);
	
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
	public String getState();
	
	
	
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
	public void setId(String id);
	
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
	public String getId();
	
	
	
}

