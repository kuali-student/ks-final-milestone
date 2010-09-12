package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.poc.MetadataServiceImpl;
import org.kuali.student.lum.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:course-dev-test-context.xml"})
public class TestMetadataService {

	@Autowired
	CourseService courseService;
	
	@Test
	public void testGetMetadata(){
		MetadataServiceImpl metadataService = new MetadataServiceImpl(courseService);
        Metadata metadata = metadataService.getMetadata("org.kuali.student.lum.course.dto.CourseInfo");
            
        Map<String, Metadata> properties = metadata.getProperties();
        assertEquals(properties.size(), 25);
        
        assertTrue(properties.containsKey("state"));
        assertTrue(properties.containsKey("campusLocations"));
           
        assertTrue(properties.containsKey("formats"));
        metadata = properties.get("formats");
        
        properties = metadata.getProperties();
        assertTrue(properties.containsKey("*"));
        metadata = properties.get("*");
               
        properties = metadata.getProperties();
        assertTrue(properties.containsKey("activities"));
        metadata = properties.get("activities");

        properties = metadata.getProperties();
        assertTrue(properties.containsKey("*"));
        metadata = properties.get("*");

        properties = metadata.getProperties();
        assertFalse(properties.containsKey("foo"));
        
        return;
	}
}
