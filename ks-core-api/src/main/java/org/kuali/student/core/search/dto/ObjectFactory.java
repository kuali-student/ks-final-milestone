/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.core.search.dto;

import javax.xml.bind.annotation.XmlRegistry;

import org.kuali.student.core.dictionary.dto.FieldDescriptor;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the org.kuali.student.dictionary.dto package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {
	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: org.kuali.student.dictionary.dto
	 * 
	 */
	public ObjectFactory() {
	}

	public QueryParamInfo createQueryParamInfo() {
		return new QueryParamInfo();
	}

	public QueryParamValue createQueryParamValue() {
		return new QueryParamValue();
	}

	public Result createResult() {
		return new Result();
	}

	public ResultCell createResultCell() {
		return new ResultCell();
	}

	public ResultColumnInfo createResultColumnInfo() {
		return new ResultColumnInfo();
	}

	public SearchCriteriaTypeInfo createSearchCriteriaTypeInfo() {
		return new SearchCriteriaTypeInfo();
	}

	public SearchRelationship createSearchRelationship() {
		return new SearchRelationship();
	}

	public SearchResultTypeInfo createSearchResultTypeInfo() {
		return new SearchResultTypeInfo();
	}

	public SearchTypeInfo createSearchTypeInfo() {
		return new SearchTypeInfo();
	}

	public TypeAttribute createTypeAttribute() {
		return new TypeAttribute();
	}
	public FieldDescriptor createFieldDescriptor(){
		return new FieldDescriptor();
	}
}
