/**
 *
 */
package org.kuali.student.mock;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

//import org.kuali.drools.util.AbstractDroolsExecutor;


/**
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 *
 */
public class MockCourseEnrollmentRequest  {
	private static final String NL = System.getProperty("line.separator");

    private String studentName;
    private Set<String> learningResults;
    private Boolean instructorConsent;
    private ArrayList<Integer> coreStudyUnits;
    private String academicLevel;
    private Boolean enrollable = true;
    private StringBuilder  advice = new StringBuilder();

    public MockCourseEnrollmentRequest (
        String name, String results, String coreStudyUnits, Boolean consent,
        String level) {
        this.studentName = name;
        this.instructorConsent = consent;
        this.academicLevel = level;
        
        this.coreStudyUnits = parseCousreUnitsList(coreStudyUnits);
        this.learningResults = parseLearningResults(results);
       

    }

    private Set<String> parseLearningResults(String results) {
        HashSet<String> rval = new HashSet<String>();
        String[] toks = results.split("\\s*,\\s*");
        // there's a one-liner for this...
        for (String t : toks) {
            rval.add(t);
        }
        return rval;
    }
    
    private ArrayList<Integer> parseCousreUnitsList(String list) {
        ArrayList<Integer> rval = new ArrayList<Integer>();
        String[] toks = list.split("\\s*,\\s*");

        // there's a one-liner for this...
        for (String t : toks) {
            rval.add(Integer.valueOf(t));
        }
        return rval;
    }
    
    public String getAdvice() {
    	return advice.toString();
    }
    public void addAdvice(String suggestion) {
    	advice.append(NL).append(suggestion);
    }
    public int countPrerequistesMet(Set<String> required) {
    	Set<String> met = and(learningResults, required);
    	return met.size();	
    }
    private Set<String> and(Set<String> theSet, Set<String> thatSet) {
    	Set<String> rval = new HashSet<String>(theSet);
    	 rval.retainAll(new HashSet<String>(thatSet));
    	 return rval;
    }
    //~--------- accessors -------- /
    
	public String getStudentName() {
		return studentName;
	}

	public Set<String> getLearningResults() {
		return learningResults;
	}

	public Boolean getInstructorConsent() {
		return instructorConsent;
	}

	public String getAcademicLevel() {
		return academicLevel;
	}

	public ArrayList<Integer> getCoreStudyUnits() {
		return coreStudyUnits;
	}

	public Boolean isEnrollable() {
		return enrollable;
	}

	public void setEnrollable(Boolean enrollable) {
		this.enrollable = enrollable;
	}

}
