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


import java.util.List;
import java.util.Map;


public interface CluFeeRecordInfo
{
	
	/**
	* Set Fee Type
	*
	* Type: string
	*
	* Any finite sequence of characters with letters, numerals, symbols and 
	* punctuation marks. The length can be any natural number between zero or any 
	* positive integer.
	*/
	public void setFeeType(String feeType);
	
	/**
	* Get Fee Type
	*
	* Type: string
	*
	* Any finite sequence of characters with letters, numerals, symbols and 
	* punctuation marks. The length can be any natural number between zero or any 
	* positive integer.
	*/
	public String getFeeType();
	
	
	
	/**
	* Set Fee Amount
	*
	* Type: currencyAmountInfo
	*
	* The page currencyAmount Structure does not exist.
	*/
	public void setFeeAmount(CurrencyAmountInfo feeAmount);
	
	/**
	* Get Fee Amount
	*
	* Type: currencyAmountInfo
	*
	* The page currencyAmount Structure does not exist.
	*/
	public CurrencyAmountInfo getFeeAmount();
	
	
	
	/**
	* Set List of Affiliated Orgs
	*
	* Type: affiliatedOrgInfoList
	*
	* List of affiliated organizations.
	*/
	public void setAffiliatedOrgInfoList(List<AffiliatedOrgInfo> affiliatedOrgInfoList);
	
	/**
	* Get List of Affiliated Orgs
	*
	* Type: affiliatedOrgInfoList
	*
	* List of affiliated organizations.
	*/
	public List<AffiliatedOrgInfo> getAffiliatedOrgInfoList();
	
	
	
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
	* Set CLU Fee Record Identifier
	*
	* Type: string
	*
	* Identifier for the clu fee record.
	*/
	public void setId(String id);
	
	/**
	* Get CLU Fee Record Identifier
	*
	* Type: string
	*
	* Identifier for the clu fee record.
	*/
	public String getId();
	
	
	
}

