package org.kuali.student.krms.termresolver;

import org.kuali.student.krms.util.KSKRMSExecutionConstants;
import org.kuali.student.r2.lum.clu.service.CluService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/01/25
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCourseTermResolver {

    protected final static Set<String> prerequisites = new HashSet<String>(1);

    static {
        prerequisites.add(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY);
        prerequisites.add(KSKRMSExecutionConstants.CONTEXT_INFO_TERM_NAME);
    }

    private CluService cluService;

    public CluService getCluService() {
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public Set<String> getPrerequisites() {
        return prerequisites;
    }

    protected String[] toCodesArray(String commaSeparateList){

        commaSeparateList.trim();
        return commaSeparateList.split(",");
    }

    protected String resolveCourseCode(Map<String, String> parameters){

        // First check if the code is not already resolved.
        String courseCode = parameters.get(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY);

        if (courseCode != null){
            return courseCode;
        }
//        else {
//            //TODO: Retrieve course id and resolve.
//        }

        return null;
    }

    protected String[] resolveCourseCodes(Map<String, String> parameters){

        // First check if codes are not already resolved.
        String courseCodes = parameters.get(KSKRMSExecutionConstants.COURSE_CODES_TERM_PROPERTY);

        if (courseCodes != null){
            return toCodesArray(courseCodes);
        }
//        else {
//            //TODO: Retrieve course set id and resolve.
//        }

        return null;
    }
}
