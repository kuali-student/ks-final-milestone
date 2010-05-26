package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.ModelDefinition;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.dictionary.poc.MetadataServiceImpl;
import org.kuali.student.lum.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:course-dev-test-context.xml"})
public class TestMetadaService {

	@Autowired
	CourseService courseService;
	
	@Test
	public void testGetMetadata(){
		MetadataServiceImpl metadataService = new MetadataServiceImpl(courseService);
        Metadata metadata = metadataService.getMetadata("org.kuali.student.lum.course.dto.CourseInfo");
            
        Map<String, Metadata> properties = metadata.getProperties();
        assertEquals(properties.size(), 25);
        
        ModelDefinition def = new DataModelDefinition(metadata);
        Data data = new Data();
        
        def.ensurePath(data, QueryPath.parse("state"), true);
        def.ensurePath(data, QueryPath.parse("campusLocations/1"), true);
        def.ensurePath(data, QueryPath.parse("formats/1/activities/1/id"), true);

        try{
        	def.ensurePath(data, QueryPath.parse("formats/1/activities/1/foo"), true);
        	assertTrue(false);
        } catch (Exception e) {
        	//An exception should occur here, since path is invalid
		}

        return;
	}
}
