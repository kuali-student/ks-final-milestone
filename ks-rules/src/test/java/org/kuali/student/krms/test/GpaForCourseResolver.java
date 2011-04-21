package org.kuali.student.krms.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.engine.TermSpecification;

public class GpaForCourseResolver implements TermResolver<Float> {

    private Map<String, Map<String, Float>> gpas = new HashMap<String, Map<String,Float>>();
    
    public GpaForCourseResolver() {
        
        // student 1: 3.0 in course 1
        Map<String, Float> student1Gpas = new HashMap<String, Float>();
        student1Gpas.put("1", 3.0f);
        
        gpas.put("1", student1Gpas);
        
        // student 2: 3.0 in course 2, 0.4 in course 3
        Map<String, Float> student2Gpas = new HashMap<String, Float>();
        student1Gpas.put("2", 3.0f);
        student1Gpas.put("3", 0.4f);
        
        gpas.put("2", student2Gpas);
        
        // student 3: 3.5 in courses 1,2,3
        Map<String, Float> student3Gpas = new HashMap<String, Float>();
        student1Gpas.put("1", 3.5f);
        student1Gpas.put("2", 3.5f);
        student1Gpas.put("3", 3.5f);
        
        gpas.put("3", student3Gpas);
    }
    
    @Override
    public Set<TermSpecification> getPrerequisites() {
        return Collections.singleton(Constants.studentIdTermSpec);
    }

    @Override
    public TermSpecification getOutput() {
        return Constants.gpaForCourseTermSpec;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(Constants.COURSE_ID_TERM_PROPERTY_NAME);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public Float resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String studentId = resolvedPrereqs.get(Constants.studentIdTermSpec).toString();
        String courseId = parameters.get(Constants.COURSE_ID_TERM_PROPERTY_NAME);
        
        if(gpas.containsKey(studentId)) {
            Map<String, Float> gpasForStudent = gpas.get(studentId);
            if(gpasForStudent.containsKey(courseId)) {
                return gpasForStudent.get(courseId);
            }
        }

        throw new TermResolutionException("Could not find gpa for student id: " + studentId + " and course id: " + courseId);
    }

}
