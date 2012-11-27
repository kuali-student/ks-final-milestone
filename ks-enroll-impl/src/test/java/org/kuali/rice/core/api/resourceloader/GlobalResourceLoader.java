package org.kuali.rice.core.api.resourceloader;

import org.kuali.student.common.test.mock.MockGlobalResourceLoader;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 11/21/12
 * Time: 11:04 AM
 * This is a stripped down local copy of the GlobalResourceLoader. Because of our dependency on rice, we have to put
 * this in our local classpath, otherwise the real rice one is used, which breaks because rice isn't running.
 *
 * If you can think of a way to do this VERY simple mock wiring in Spring, please do so. Thanks!
 */
public class GlobalResourceLoader extends MockGlobalResourceLoader{
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GlobalResourceLoader.class);


}
