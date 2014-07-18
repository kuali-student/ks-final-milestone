/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.List;

/**
 * This is not a regular service contract but an interface to allow us to inject
 * optionKeys into the processing of rollover SOC, rollover CO and create CO methods.
 * 
 * These are intended to be called by the application to get a list of default options 
 * that they could then present to the user and allow them to tweak (or not allow them) 
 * before executing the operation.
 * 
 * Eventually this should be backed by the Global Environment Settings service.
 *
 * @author nwright
 */
public interface DefaultOptionKeysService {
    
    /**
     * get the default option keys that should be supplied to the rollover SOC 
     * method.
     */
    public List<String> getDefaultOptionKeysForRolloverSoc ();
    
    /**
     * get the default option keys that should be supplied to the rollover CO 
     * method when it is invoked to run it individually to copy a single CO.
     */
    public List<String> getDefaultOptionKeysForCopySingleCourseOffering ();
    
    /**
     * get the default option keys that should be supplied to the create CO 
     * method.
     */
    public List<String> getDefaultOptionKeysForCreateCourseOfferingFromCanonical ();
}
