package org.kuali.student.core.dictionary.service;

import static org.junit.Assert.assertNotNull;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.kuali.student.core.dictionary.dto.DataType;
import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.service.impl.DictionaryServiceImpl;
import org.kuali.student.core.dictionary.service.jaxws.GetObjectStructureResponse;

public class DictionaryServiceImplTest {
	@Test
	public void testMarshalling() throws JAXBException{
		ObjectStructureDefinition new_return = new ObjectStructureDefinition();
		new_return.setName("lalala");
		
		ObjectStructureDefinition nest1 = new ObjectStructureDefinition();
		nest1.setName("nest1");
		FieldDefinition nest1fd1 = new FieldDefinition();
		nest1fd1.setDataType(DataType.COMPLEX);
		nest1fd1.setDataObjectStructure(nest1);
		nest1.getAttributes().add(nest1fd1);
		
		FieldDefinition rootfd1 = new FieldDefinition();
		rootfd1.setDataType(DataType.COMPLEX);
		rootfd1.setDataObjectStructure(nest1);
		new_return.getAttributes().add(rootfd1);
		
		
		GetObjectStructureResponse response = new GetObjectStructureResponse();
		response.setReturn(new_return);
		JAXBContext context = JAXBContext.newInstance(GetObjectStructureResponse.class);
		Marshaller marshaller = context.createMarshaller();
	    StringWriter writer = new StringWriter();
		marshaller.marshal(response, writer);
		System.out.println(writer.toString());
		
		Unmarshaller unmarshaller = context.createUnmarshaller();
		GetObjectStructureResponse out = (GetObjectStructureResponse) unmarshaller.unmarshal(new StringReader(writer.toString()));
		
		DictionaryServiceImpl dm = new DictionaryServiceImpl("classpath:ks-courseInfo-dictionary-test-context.xml");
		ObjectStructureDefinition courseInfo = dm.getObjectStructure("org.kuali.student.lum.course.dto.CourseInfo");
		
		courseInfo.getAttributes();
		response = new GetObjectStructureResponse();
		response.setReturn(courseInfo);
		writer = new StringWriter();
		marshaller.marshal(response, writer);
		out = (GetObjectStructureResponse) unmarshaller.unmarshal(new StringReader(writer.toString()));
		assertNotNull(out.getReturn().getAttributes().get(11).getDataObjectStructure());
		System.out.print(writer.toString());
		

	}
}
