/*
 * Copyright 2010 The Kuali Foundation
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
package org.kuali.student.service.lu.dev.api;


import java.util.Date;
import java.util.List;
import java.util.Map;


public interface CluPublicationInfo
{
	
	/**
	* Set CLU Identifier
	*
	* Type: cluId
	*
	* The identifier for the canonical learning unit which is described by this 
	* publication information.
	*/
	public void setCluId(String cluId);
	
	/**
	* Get CLU Identifier
	*
	* Type: cluId
	*
	* The identifier for the canonical learning unit which is described by this 
	* publication information.
	*/
	public String getCluId();
	
	
	
	/**
	* Set Publication Variants
	*
	* Type: fieldInfoList
	*
	* Fields in cluInfo whose values are overridden as part of this publication.
	*/
	public void setVariants(List<FieldInfo> variants);
	
	/**
	* Get Publication Variants
	*
	* Type: fieldInfoList
	*
	* Fields in cluInfo whose values are overridden as part of this publication.
	*/
	public List<FieldInfo> getVariants();
	
	
	
	/**
	* Set Publication Start Cycle
	*
	* Type: atpKey
	*
	* The start academic time period for when the CLU should be published in this type 
	* of usage. Should be less than or equal to endCycle.
	*/
	public void setStartCycle(String startCycle);
	
	/**
	* Get Publication Start Cycle
	*
	* Type: atpKey
	*
	* The start academic time period for when the CLU should be published in this type 
	* of usage. Should be less than or equal to endCycle.
	*/
	public String getStartCycle();
	
	
	
	/**
	* Set Publication End Cycle
	*
	* Type: atpKey
	*
	* The end academic time period for when the CLU should be published in this type 
	* of usage. If specified, should be greater than or equal to startCycle.
	*/
	public void setEndCycle(String endCycle);
	
	/**
	* Get Publication End Cycle
	*
	* Type: atpKey
	*
	* The end academic time period for when the CLU should be published in this type 
	* of usage. If specified, should be greater than or equal to startCycle.
	*/
	public String getEndCycle();
	
	
	
	/**
	* Set Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this LU publication type became effective. This is a similar 
	* concept to the effective date on enumerated values. When an expiration date has 
	* been specified, this field must be less than or equal to the expiration date.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this LU publication type became effective. This is a similar 
	* concept to the effective date on enumerated values. When an expiration date has 
	* been specified, this field must be less than or equal to the expiration date.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this LU publication type expires. This is a similar concept 
	* to the expiration date on enumerated values. If specified, this should be 
	* greater than or equal to the effective date. If this field is not specified, 
	* then no expiration date has been currently defined and should automatically be 
	* considered greater than the effective date.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this LU publication type expires. This is a similar concept 
	* to the expiration date on enumerated values. If specified, this should be 
	* greater than or equal to the effective date. If this field is not specified, 
	* then no expiration date has been currently defined and should automatically be 
	* considered greater than the effective date.
	*/
	public Date getExpirationDate();
	
	
	
	/**
	* Set Generic/dynamic attributes
	*
	* Type: attributeInfoList
	*
	* List of key/value pairs, typically used for dynamic attributes.
	*/
	public void setAttributes(Map<String,String> attributes);
	
	/**
	* Get Generic/dynamic attributes
	*
	* Type: attributeInfoList
	*
	* List of key/value pairs, typically used for dynamic attributes.
	*/
	public Map<String,String> getAttributes();
	
	
	
	/**
	* Set Create/Update meta info
	*
	* Type: metaInfo
	*
	* Create and last update info for the structure. This is optional and treated as 
	* read only since the data is set by the internals of the service during 
	* maintenance operations.
	*/
	public void setMetaInfo(MetaInfo metaInfo);
	
	/**
	* Get Create/Update meta info
	*
	* Type: metaInfo
	*
	* Create and last update info for the structure. This is optional and treated as 
	* read only since the data is set by the internals of the service during 
	* maintenance operations.
	*/
	public MetaInfo getMetaInfo();
	
	
	
	/**
	* Set Publication Type
	*
	* Type: luPublicationTypeKey
	*
	* Type of publication for which this information should be used.
	*/
	public void setType(String type);
	
	/**
	* Get Publication Type
	*
	* Type: luPublicationTypeKey
	*
	* Type of publication for which this information should be used.
	*/
	public String getType();
	
	
	
	/**
	* Set Publication State
	*
	* Type: string
	*
	* Current state of the information for this publication type. This value should be 
	* constrained to those within the cluPublishingState enumeration. In general, an 
	* "active" record for a type indicates that the clu should be published within 
	* that media, though that may be further constrained by the cycle information 
	* included.
	*/
	public void setState(String state);
	
	/**
	* Get Publication State
	*
	* Type: string
	*
	* Current state of the information for this publication type. This value should be 
	* constrained to those within the cluPublishingState enumeration. In general, an 
	* "active" record for a type indicates that the clu should be published within 
	* that media, though that may be further constrained by the cycle information 
	* included.
	*/
	public String getState();
	
	
	
	/**
	* Set Publishing Information Identifier
	*
	* Type: cluPublicationId
	*
	* Identifier for the publishing information. This is set by the service to be able 
	* to determine changes and alterations to the structure as well as provides a 
	* handle for searches. This structure is not currently accessible through unique 
	* operations, and it is strongly recommended that no external references to this 
	* particular identifier be maintained.
	*/
	public void setId(String id);
	
	/**
	* Get Publishing Information Identifier
	*
	* Type: cluPublicationId
	*
	* Identifier for the publishing information. This is set by the service to be able 
	* to determine changes and alterations to the structure as well as provides a 
	* handle for searches. This structure is not currently accessible through unique 
	* operations, and it is strongly recommended that no external references to this 
	* particular identifier be maintained.
	*/
	public String getId();
	
	
	
}

