/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

/**
 *
 * @author nwright
 */
public class DefaultOptionKeysServiceImpl implements DefaultOptionKeysService {

    @Override
    public List<String> getDefaultOptionKeysForRolloverSoc() {
        List<String> options = new ArrayList<String>();
        // Rollover now runs asynchronously. KSENROLL-1545
        // options.add(CourseOfferingSetServiceConstants.RUN_SYNCHRONOUSLY_OPTION_KEY);
        options.add(CourseOfferingSetServiceConstants.LOG_SUCCESSES_OPTION_KEY);
        //Add this option to skip rollover if there is a new version
        options.add(CourseOfferingSetServiceConstants.IF_NO_NEW_VERSION_OPTION_KEY);
        return options;
    }

    @Override
    public List<String> getDefaultOptionKeysForCopySingleCourseOffering() {
        List<String> options = new ArrayList<String>();
        return options;
    }

    @Override
    public List<String> getDefaultOptionKeysForCreateCourseOfferingFromCanonical() {
        List<String> options = new ArrayList<String>();
        options.add(CourseOfferingSetServiceConstants.USE_CANONICAL_OPTION_KEY);
        return options;
    }
}
