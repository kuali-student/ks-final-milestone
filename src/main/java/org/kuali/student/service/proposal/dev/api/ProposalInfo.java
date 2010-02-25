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
package org.kuali.student.service.proposal.dev.api;


import java.util.Date;
import java.util.List;
import java.util.Map;


public interface ProposalInfo
{
	
	/**
	* Set Proposal Name
	*
	* Type: string
	*
	* The name or title of the proposal. Any finite sequence of characters with 
	* letters, numerals, symbols and punctuation marks. The length can be any natural 
	* number between zero or any positive integer.
	*/
	public void setName(String name);
	
	/**
	* Get Proposal Name
	*
	* Type: string
	*
	* The name or title of the proposal. Any finite sequence of characters with 
	* letters, numerals, symbols and punctuation marks. The length can be any natural 
	* number between zero or any positive integer.
	*/
	public String getName();
	
	
	
	/**
	* Set Proposer Person
	*
	* Type: personIdList
	*
	* List of person identifiers. Structure should contain a proposerPerson OR a 
	* proposerOrg.
	*/
	public void setProposerPerson(List<String> proposerPerson);
	
	/**
	* Get Proposer Person
	*
	* Type: personIdList
	*
	* List of person identifiers. Structure should contain a proposerPerson OR a 
	* proposerOrg.
	*/
	public List<String> getProposerPerson();
	
	
	
	/**
	* Set Proposer Organization
	*
	* Type: orgIdList
	*
	* List of organization identifiers. Structure should contain a proposerPerson OR a 
	* proposerOrg
	*/
	public void setProposerOrg(List<String> proposerOrg);
	
	/**
	* Get Proposer Organization
	*
	* Type: orgIdList
	*
	* List of organization identifiers. Structure should contain a proposerPerson OR a 
	* proposerOrg
	*/
	public List<String> getProposerOrg();
	
	
	
	/**
	* Set Proposal Reference Type
	*
	* Type: referenceTypeKey
	*
	* Unique identifier for a reference type.
	*/
	public void setProposalReferenceType(String proposalReferenceType);
	
	/**
	* Get Proposal Reference Type
	*
	* Type: referenceTypeKey
	*
	* Unique identifier for a reference type.
	*/
	public String getProposalReferenceType();
	
	
	
	/**
	* Set Proposal Reference
	*
	* Type: referenceIdList
	*
	* List of reference identifiers.
	*/
	public void setProposalReference(List<String> proposalReference);
	
	/**
	* Get Proposal Reference
	*
	* Type: referenceIdList
	*
	* List of reference identifiers.
	*/
	public List<String> getProposalReference();
	
	
	
	/**
	* Set Rationale
	*
	* Type: string
	*
	* Brief explanation of the reason for the proposal
	*/
	public void setRationale(String rationale);
	
	/**
	* Get Rationale
	*
	* Type: string
	*
	* Brief explanation of the reason for the proposal
	*/
	public String getRationale();
	
	
	
	/**
	* Set Detailed Description
	*
	* Type: string
	*
	* Detailed description of the proposed changes.
	*/
	public void setDetailDesc(String detailDesc);
	
	/**
	* Get Detailed Description
	*
	* Type: string
	*
	* Detailed description of the proposed changes.
	*/
	public String getDetailDesc();
	
	
	
	/**
	* Set Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this proposal became effective. This is a similar concept to 
	* the effective date on enumerated values. When an expiration date has been 
	* specified, this field must be less than or equal to the expiration date.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this proposal became effective. This is a similar concept to 
	* the effective date on enumerated values. When an expiration date has been 
	* specified, this field must be less than or equal to the expiration date.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this proposal expires. This is a similar concept to the 
	* expiration date on enumerated values. If specified, this should be greater than 
	* or equal to the effective date. If this field is not specified, then no 
	* expiration date has been currently defined and should automatically be 
	* considered greater than the effective date.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this proposal expires. This is a similar concept to the 
	* expiration date on enumerated values. If specified, this should be greater than 
	* or equal to the effective date. If this field is not specified, then no 
	* expiration date has been currently defined and should automatically be 
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
	* Set Proposal Type
	*
	* Type: proposalTypeKey
	*
	* Unique identifier for a proposal type.
	*/
	public void setType(String type);
	
	/**
	* Get Proposal Type
	*
	* Type: proposalTypeKey
	*
	* Unique identifier for a proposal type.
	*/
	public String getType();
	
	
	
	/**
	* Set Proposal State
	*
	* Type: string
	*
	* The current status of the proposal. The values for this field are constrained to 
	* those in the proposalState enumeration. A separate setup operation does not 
	* exist for retrieval of the meta data around this value.
	*/
	public void setState(String state);
	
	/**
	* Get Proposal State
	*
	* Type: string
	*
	* The current status of the proposal. The values for this field are constrained to 
	* those in the proposalState enumeration. A separate setup operation does not 
	* exist for retrieval of the meta data around this value.
	*/
	public String getState();
	
	
	
	/**
	* Set Proposal Identifier
	*
	* Type: proposalId
	*
	* Unique identifier for a Proposal. This is optional, due to the identifier being 
	* set at the time of creation. Once the proposal has been created, this should be 
	* seen as required.
	*/
	public void setId(String id);
	
	/**
	* Get Proposal Identifier
	*
	* Type: proposalId
	*
	* Unique identifier for a Proposal. This is optional, due to the identifier being 
	* set at the time of creation. Once the proposal has been created, this should be 
	* seen as required.
	*/
	public String getId();
	
	
	
}

