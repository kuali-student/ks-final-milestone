package org.kuali.student.enrollment.class2.courseoffering.service;

import java.util.List;

/**

 * User: Charles
 * Date: 9/26/12
 * Time: 1:44 PM
 * Used to test service calls on live DB.
 */
public interface TestServiceCallViewHelperService {
    List<String> getSocIdsByTerm(String termId) throws Exception;

    void verifyPopulations() throws Exception;
}

