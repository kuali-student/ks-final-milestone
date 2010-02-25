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
	
	
	public class CurrencyAmountBean
	 implements CurrencyAmountInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String currencyTypeKey;
		
		/**
		* Set currencyTypeKey
		*
		* The kind of units associated with the quantity, such as US Dollars
		*/
		@Override
		public void setCurrencyTypeKey(String currencyTypeKey)
		{
			this.currencyTypeKey = currencyTypeKey;
		}
		
		/**
		* Get currencyTypeKey
		*
		* The kind of units associated with the quantity, such as US Dollars
		*/
		@Override
		public String getCurrencyTypeKey()
		{
			return this.currencyTypeKey;
		}
						
		private Integer currencyQuantity;
		
		/**
		* Set currency Quantity
		*
		* The amount of currency
		*/
		@Override
		public void setCurrencyQuantity(Integer currencyQuantity)
		{
			this.currencyQuantity = currencyQuantity;
		}
		
		/**
		* Get currency Quantity
		*
		* The amount of currency
		*/
		@Override
		public Integer getCurrencyQuantity()
		{
			return this.currencyQuantity;
		}
						
	}

