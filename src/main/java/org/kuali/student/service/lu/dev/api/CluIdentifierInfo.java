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



public interface CluIdentifierInfo
{
	
	/**
	* Set Code
	*
	* Type: string
	*
	* The composite string that is used to officially reference or publish the CLU. 
	* Note it may have an internal structure that each Institution may want to 
	* enforce. This structure may be composed from the other parts of the structure 
	* such as Level & Division, but may include items such as cluType.
	*/
	public void setCode(String code);
	
	/**
	* Get Code
	*
	* Type: string
	*
	* The composite string that is used to officially reference or publish the CLU. 
	* Note it may have an internal structure that each Institution may want to 
	* enforce. This structure may be composed from the other parts of the structure 
	* such as Level & Division, but may include items such as cluType.
	*/
	public String getCode();
	
	
	
	/**
	* Set Short Name
	*
	* Type: string
	*
	* Abbreviated name of the CLU, commonly used on transcripts
	*/
	public void setShortName(String shortName);
	
	/**
	* Get Short Name
	*
	* Type: string
	*
	* Abbreviated name of the CLU, commonly used on transcripts
	*/
	public String getShortName();
	
	
	
	/**
	* Set Long Name
	*
	* Type: string
	*
	* Full name of the CLU, commonly used on catalogues
	*/
	public void setLongName(String longName);
	
	/**
	* Get Long Name
	*
	* Type: string
	*
	* Full name of the CLU, commonly used on catalogues
	*/
	public String getLongName();
	
	
	
	/**
	* Set Level
	*
	* Type: string
	*
	* A code that indicates whether this is introductory, advanced, etc.
	*/
	public void setLevel(String level);
	
	/**
	* Get Level
	*
	* Type: string
	*
	* A code that indicates whether this is introductory, advanced, etc.
	*/
	public String getLevel();
	
	
	
	/**
	* Set Division
	*
	* Type: string
	*
	* A code that indicates what school, program, major, subject area, etc. Examples: 
	* "Chem", "18"
	*/
	public void setDivision(String division);
	
	/**
	* Get Division
	*
	* Type: string
	*
	* A code that indicates what school, program, major, subject area, etc. Examples: 
	* "Chem", "18"
	*/
	public String getDivision();
	
	
	
	/**
	* Set Suffix Code
	*
	* Type: string
	*
	* The "extra" portion of the code, which usually corresponds with the most 
	* detailed part of the number. Ex. at MIT we might map Division to subject 
	* area(Ex:6) but overall we need to say the code is 6.120. This field would 
	* represent the 120 part.
	*/
	public void setSuffixCode(String suffixCode);
	
	/**
	* Get Suffix Code
	*
	* Type: string
	*
	* The "extra" portion of the code, which usually corresponds with the most 
	* detailed part of the number. Ex. at MIT we might map Division to subject 
	* area(Ex:6) but overall we need to say the code is 6.120. This field would 
	* represent the 120 part.
	*/
	public String getSuffixCode();
	
	
	
	/**
	* Set Variation
	*
	* Type: string
	*
	* A number that indicates the sequence or order of versions in cases where several 
	* different Clus have the same offical Identifier
	*/
	public void setVariation(String variation);
	
	/**
	* Get Variation
	*
	* Type: string
	*
	* A number that indicates the sequence or order of versions in cases where several 
	* different Clus have the same offical Identifier
	*/
	public String getVariation();
	
	
	
	/**
	* Set Organization Identifier
	*
	* Type: orgId
	*
	* The identifier of the organization associated with this cluIdentifier.
	*/
	public void setOrgId(String orgId);
	
	/**
	* Get Organization Identifier
	*
	* Type: orgId
	*
	* The identifier of the organization associated with this cluIdentifier.
	*/
	public String getOrgId();
	
	
	
	/**
	* Set CLU Identifier Type
	*
	* Type: string
	*
	* Identifies the type of usage for the identifier. While most usages will have the 
	* same data constraints, this may provide some context around what the specific 
	* intent is for this identifier. (Ex. Why does this alternate identifier exist?)
	*/
	public void setType(String type);
	
	/**
	* Get CLU Identifier Type
	*
	* Type: string
	*
	* Identifies the type of usage for the identifier. While most usages will have the 
	* same data constraints, this may provide some context around what the specific 
	* intent is for this identifier. (Ex. Why does this alternate identifier exist?)
	*/
	public String getType();
	
	
	
	/**
	* Set CLU Identifier State
	*
	* Type: string
	*
	* Identifies the state of the identifier. Values for this field are constrained to 
	* values present within the cluIdentifierState enumeration.
	*/
	public void setState(String state);
	
	/**
	* Get CLU Identifier State
	*
	* Type: string
	*
	* Identifies the state of the identifier. Values for this field are constrained to 
	* values present within the cluIdentifierState enumeration.
	*/
	public String getState();
	
	
	
	/**
	* Set CLU Identifier Identifier
	*
	* Type: string
	*
	* Identifies the particular identifier structure. This is set by the service to be 
	* able to determine changes and alterations to the structure as well as provides a 
	* handle for searches. This structure is not accessible through unique operations, 
	* and it is strongly recommended that no external references to this particular 
	* identifier be maintained.
	*/
	public void setId(String id);
	
	/**
	* Get CLU Identifier Identifier
	*
	* Type: string
	*
	* Identifies the particular identifier structure. This is set by the service to be 
	* able to determine changes and alterations to the structure as well as provides a 
	* handle for searches. This structure is not accessible through unique operations, 
	* and it is strongly recommended that no external references to this particular 
	* identifier be maintained.
	*/
	public String getId();
	
	
	
}

