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
	
	
	public class CluIdentifierBean
	 implements CluIdentifierInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String code;
		
		/**
		* Set Code
		*
		* The composite string that is used to officially reference or publish the CLU. 
		* Note it may have an internal structure that each Institution may want to 
		* enforce. This structure may be composed from the other parts of the structure 
		* such as Level & Division, but may include items such as cluType.
		*/
		@Override
		public void setCode(String code)
		{
			this.code = code;
		}
		
		/**
		* Get Code
		*
		* The composite string that is used to officially reference or publish the CLU. 
		* Note it may have an internal structure that each Institution may want to 
		* enforce. This structure may be composed from the other parts of the structure 
		* such as Level & Division, but may include items such as cluType.
		*/
		@Override
		public String getCode()
		{
			return this.code;
		}
						
		private String shortName;
		
		/**
		* Set Short Name
		*
		* Abbreviated name of the CLU, commonly used on transcripts
		*/
		@Override
		public void setShortName(String shortName)
		{
			this.shortName = shortName;
		}
		
		/**
		* Get Short Name
		*
		* Abbreviated name of the CLU, commonly used on transcripts
		*/
		@Override
		public String getShortName()
		{
			return this.shortName;
		}
						
		private String longName;
		
		/**
		* Set Long Name
		*
		* Full name of the CLU, commonly used on catalogues
		*/
		@Override
		public void setLongName(String longName)
		{
			this.longName = longName;
		}
		
		/**
		* Get Long Name
		*
		* Full name of the CLU, commonly used on catalogues
		*/
		@Override
		public String getLongName()
		{
			return this.longName;
		}
						
		private String level;
		
		/**
		* Set Level
		*
		* A code that indicates whether this is introductory, advanced, etc.
		*/
		@Override
		public void setLevel(String level)
		{
			this.level = level;
		}
		
		/**
		* Get Level
		*
		* A code that indicates whether this is introductory, advanced, etc.
		*/
		@Override
		public String getLevel()
		{
			return this.level;
		}
						
		private String division;
		
		/**
		* Set Division
		*
		* A code that indicates what school, program, major, subject area, etc. Examples: 
		* "Chem", "18"
		*/
		@Override
		public void setDivision(String division)
		{
			this.division = division;
		}
		
		/**
		* Get Division
		*
		* A code that indicates what school, program, major, subject area, etc. Examples: 
		* "Chem", "18"
		*/
		@Override
		public String getDivision()
		{
			return this.division;
		}
						
		private String suffixCode;
		
		/**
		* Set Suffix Code
		*
		* The "extra" portion of the code, which usually corresponds with the most 
		* detailed part of the number. Ex. at MIT we might map Division to subject 
		* area(Ex:6) but overall we need to say the code is 6.120. This field would 
		* represent the 120 part.
		*/
		@Override
		public void setSuffixCode(String suffixCode)
		{
			this.suffixCode = suffixCode;
		}
		
		/**
		* Get Suffix Code
		*
		* The "extra" portion of the code, which usually corresponds with the most 
		* detailed part of the number. Ex. at MIT we might map Division to subject 
		* area(Ex:6) but overall we need to say the code is 6.120. This field would 
		* represent the 120 part.
		*/
		@Override
		public String getSuffixCode()
		{
			return this.suffixCode;
		}
						
		private String variation;
		
		/**
		* Set Variation
		*
		* A number that indicates the sequence or order of versions in cases where several 
		* different Clus have the same offical Identifier
		*/
		@Override
		public void setVariation(String variation)
		{
			this.variation = variation;
		}
		
		/**
		* Get Variation
		*
		* A number that indicates the sequence or order of versions in cases where several 
		* different Clus have the same offical Identifier
		*/
		@Override
		public String getVariation()
		{
			return this.variation;
		}
						
		private String orgId;
		
		/**
		* Set Organization Identifier
		*
		* The identifier of the organization associated with this cluIdentifier.
		*/
		@Override
		public void setOrgId(String orgId)
		{
			this.orgId = orgId;
		}
		
		/**
		* Get Organization Identifier
		*
		* The identifier of the organization associated with this cluIdentifier.
		*/
		@Override
		public String getOrgId()
		{
			return this.orgId;
		}
						
		private String type;
		
		/**
		* Set CLU Identifier Type
		*
		* Identifies the type of usage for the identifier. While most usages will have the 
		* same data constraints, this may provide some context around what the specific 
		* intent is for this identifier. (Ex. Why does this alternate identifier exist?)
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get CLU Identifier Type
		*
		* Identifies the type of usage for the identifier. While most usages will have the 
		* same data constraints, this may provide some context around what the specific 
		* intent is for this identifier. (Ex. Why does this alternate identifier exist?)
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set CLU Identifier State
		*
		* Identifies the state of the identifier. Values for this field are constrained to 
		* values present within the cluIdentifierState enumeration.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get CLU Identifier State
		*
		* Identifies the state of the identifier. Values for this field are constrained to 
		* values present within the cluIdentifierState enumeration.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String id;
		
		/**
		* Set CLU Identifier Identifier
		*
		* Identifies the particular identifier structure. This is set by the service to be 
		* able to determine changes and alterations to the structure as well as provides a 
		* handle for searches. This structure is not accessible through unique operations, 
		* and it is strongly recommended that no external references to this particular 
		* identifier be maintained.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get CLU Identifier Identifier
		*
		* Identifies the particular identifier structure. This is set by the service to be 
		* able to determine changes and alterations to the structure as well as provides a 
		* handle for searches. This structure is not accessible through unique operations, 
		* and it is strongly recommended that no external references to this particular 
		* identifier be maintained.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

