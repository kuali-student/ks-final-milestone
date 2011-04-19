/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.server.mvc.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.validator.DataModelValidator;
import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.Data.IntegerKey;
import org.kuali.student.core.validation.dto.ValidationResultInfo;


public class TestDataModel {

	DataModelDefinition rootModelDefinition;
	DataModelDefinition formatModelDefinition;
	
	@Before
	public void setup(){
		
		//Create course properties
		Metadata metaId = new Metadata();
		metaId.setDataType(DataType.STRING);
		ConstraintMetadata constraint = new ConstraintMetadata();
		constraint.setMinLength(10);
		constraint.setMaxLength(10);
		metaId.getConstraints().add(constraint);

		//Create format fields				
		Metadata metaTitle = new Metadata();
		metaTitle.setDataType(DataType.STRING);
		metaTitle.getConstraints().add(constraint);
		Metadata metaType = new Metadata();
		metaType.setDataType(DataType.STRING);
		
		//Create format field properties
		Map<String, Metadata> formatProperties;
		formatProperties = new HashMap<String, Metadata>();					
		formatProperties.put("title", metaTitle);		
		formatProperties.put("type", metaType);
		
		//Create format metadata
		Metadata metaFormat = new Metadata();
		metaFormat.setProperties(formatProperties);
		metaFormat.setDataType(DataType.DATA);
		
		//Create format list metadata
		Metadata metaFormats = new Metadata();
		Map<String, Metadata> formatListProperties = new HashMap<String, Metadata>();
		formatListProperties.put("*", metaFormat);
		metaFormats.setDataType(DataType.LIST);
		metaFormats.setProperties(formatListProperties);
		
		//Create root metadata structure
		Metadata metadataRoot = new Metadata();
		Map<String, Metadata> rootProperties = new HashMap<String, Metadata>();
		rootProperties.put("formats", metaFormats);						
		rootProperties.put("id", metaId);
		metadataRoot.setProperties(rootProperties);
		metadataRoot.setDataType(DataType.DATA);
	
		rootModelDefinition = new DataModelDefinition();
		rootModelDefinition.setMetadata(metadataRoot);
		
		formatModelDefinition = new DataModelDefinition();
		formatModelDefinition.setMetadata(metaFormat);
	}
	
	@Test
	public void testQueryWildpath(){
		DataModel dataModel = new DataModel();
		dataModel.setRoot(new Data());
		dataModel.setDefinition(rootModelDefinition);
		
		dataModel.set(QueryPath.parse("formats/0/title"), "Format 1");
		dataModel.set(QueryPath.parse("formats/1/title"), "Format 2");
		
		Map<QueryPath, Object> values = dataModel.query("formats");	
		Map<QueryPath, Object> formatValues = dataModel.query("formats/*");
		
		assertTrue(values.size()==1);
		assertTrue(formatValues.size()==2);
	}
	
	@Test
	public void testDataModelValidator(){
		DataModel dataModel = new DataModel();
		List<ValidationResultInfo> validationResults;
		DataModelValidator validator = new CustomDataModelValidator();
		
		//Validation test for full Data Model
		dataModel.setRoot(new Data());
		dataModel.setDefinition(rootModelDefinition);
	
		//Test invalid id
		dataModel.set(QueryPath.parse("id"), "1234");
		validationResults = validator.validate(dataModel);
		assertEquals(1, validationResults.size());
		assertEquals("id", validationResults.get(0).getElement());
		
		//Test valid id
		dataModel.set(QueryPath.parse("id"), "0123456890");
		validationResults = validator.validate(dataModel);		
		assertEquals(0, validationResults.size());
		
		//Test invalid format title
		dataModel.set(QueryPath.parse("formats/0/title"), "Format 1");
		validationResults = validator.validate(dataModel);		
		assertEquals(1, validationResults.size());
		//validation path should begin from full data model
		assertEquals("formats/0/title", validationResults.get(0).getElement());		
		
		//Validation test for partial data model (i.e. child data element)
		Data data = dataModel.getRoot();
		data = data.get("formats");
		data = data.get(new IntegerKey(0));		
		dataModel.setRoot(data);
		dataModel.setDefinition(formatModelDefinition);
		dataModel.setParentPath("formats/0");
		
		validationResults = validator.validate(dataModel);		
		assertEquals(1, validationResults.size());
		//validation path should be relative to the child element 
		assertEquals("title", validationResults.get(0).getElement());		
	}

	/**
	 * Extending DataModelValidator to override call to Application.getApplicationContext()
	 * for messages, since ApplicationContext does a GWT.create() which can't be called from
	 * JUnit tests.
	 *
	 */
	public class CustomDataModelValidator extends DataModelValidator {
		@Override
		protected String getValidationMessage(String msgKey) {
			return msgKey;
		}		
	}
}
