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
	
	
	import java.io.Serializable;
	import java.util.Date;
	import java.util.List;
	
	
	public class CluResultBean
	 implements CluResultInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private RichTextInfo desc;
		
		/**
		* Set CLU Result Description
		*
		* Type: richTextInfo
		*
		* Narrative description of the CLU result.
		*/
		@Override
		public void setDesc(RichTextInfo desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get CLU Result Description
		*
		* Type: richTextInfo
		*
		* Narrative description of the CLU result.
		*/
		@Override
		public RichTextInfo getDesc()
		{
			return this.desc;
		}
						
		private String cluId;
		
		/**
		* Set CLU Identifier
		*
		* Type: cluId
		*
		* The cluId to which the CLU Result is linked.Unique identifier for a Canonical 
		* Learning Unit (CLU).
		*/
		@Override
		public void setCluId(String cluId)
		{
			this.cluId = cluId;
		}
		
		/**
		* Get CLU Identifier
		*
		* Type: cluId
		*
		* The cluId to which the CLU Result is linked.Unique identifier for a Canonical 
		* Learning Unit (CLU).
		*/
		@Override
		public String getCluId()
		{
			return this.cluId;
		}
						
		private List<ResultOptionInfo> resultOptions;
		
		/**
		* Set Result Options
		*
		* Type: resultOptionInfoList
		*
		* List of learning result option information.
		*/
		@Override
		public void setResultOptions(List<ResultOptionInfo> resultOptions)
		{
			this.resultOptions = resultOptions;
		}
		
		/**
		* Get Result Options
		*
		* Type: resultOptionInfoList
		*
		* List of learning result option information.
		*/
		@Override
		public List<ResultOptionInfo> getResultOptions()
		{
			return this.resultOptions;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Type: dateTime
		*
		* Date and time that this CLU result became effective. This is a similar concept 
		* to the effective date on enumerated values. When an expiration date has been 
		* specified, this field must be less than or equal to the expiration date.
		*/
		@Override
		public void setEffectiveDate(Date effectiveDate)
		{
			this.effectiveDate = effectiveDate;
		}
		
		/**
		* Get Effective Date
		*
		* Type: dateTime
		*
		* Date and time that this CLU result became effective. This is a similar concept 
		* to the effective date on enumerated values. When an expiration date has been 
		* specified, this field must be less than or equal to the expiration date.
		*/
		@Override
		public Date getEffectiveDate()
		{
			return this.effectiveDate;
		}
						
		private Date expirationDate;
		
		/**
		* Set Expiration Date
		*
		* Type: dateTime
		*
		* Date and time that this CLU result expires. This is a similar concept to the 
		* expiration date on enumerated values. If specified, this must be greater than or 
		* equal to the effective date. If this field is not specified, then no expiration 
		* date has been currently defined and should automatically be considered greater 
		* than the effective date.
		*/
		@Override
		public void setExpirationDate(Date expirationDate)
		{
			this.expirationDate = expirationDate;
		}
		
		/**
		* Get Expiration Date
		*
		* Type: dateTime
		*
		* Date and time that this CLU result expires. This is a similar concept to the 
		* expiration date on enumerated values. If specified, this must be greater than or 
		* equal to the effective date. If this field is not specified, then no expiration 
		* date has been currently defined and should automatically be considered greater 
		* than the effective date.
		*/
		@Override
		public Date getExpirationDate()
		{
			return this.expirationDate;
		}
						
		private MetaInfo metaInfo;
		
		/**
		* Set Create/Update meta info
		*
		* Type: metaInfo
		*
		* Create and last update info for the structure. This is optional and treated as 
		* read only since the data is set by the internals of the service during 
		* maintenance operations.
		*/
		@Override
		public void setMetaInfo(MetaInfo metaInfo)
		{
			this.metaInfo = metaInfo;
		}
		
		/**
		* Get Create/Update meta info
		*
		* Type: metaInfo
		*
		* Create and last update info for the structure. This is optional and treated as 
		* read only since the data is set by the internals of the service during 
		* maintenance operations.
		*/
		@Override
		public MetaInfo getMetaInfo()
		{
			return this.metaInfo;
		}
						
		private String type;
		
		/**
		* Set Clu Result Type
		*
		* Type: cluResultTypeKey
		*
		* Unique identifier for a clu learning result object type.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get Clu Result Type
		*
		* Type: cluResultTypeKey
		*
		* Unique identifier for a clu learning result object type.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set CLU Result State
		*
		* Type: string
		*
		* The current status of the CLU Result. The values for this field are constrained 
		* to those in the cluResultState enumeration. A separate setup operation does not 
		* exist for retrieval of the meta data around this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get CLU Result State
		*
		* Type: string
		*
		* The current status of the CLU Result. The values for this field are constrained 
		* to those in the cluResultState enumeration. A separate setup operation does not 
		* exist for retrieval of the meta data around this value.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String id;
		
		/**
		* Set CLU Result Identifier
		*
		* Type: cluResultId
		*
		* Unique identifier for a CLU result. This is optional, due to the identifier 
		* being set at the time of creation. Once the result set has been created, this 
		* should be seen as required.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get CLU Result Identifier
		*
		* Type: cluResultId
		*
		* Unique identifier for a CLU result. This is optional, due to the identifier 
		* being set at the time of creation. Once the result set has been created, this 
		* should be seen as required.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

