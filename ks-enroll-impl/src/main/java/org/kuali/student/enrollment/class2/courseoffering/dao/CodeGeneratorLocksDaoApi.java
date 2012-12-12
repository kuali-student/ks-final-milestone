package org.kuali.student.enrollment.class2.courseoffering.dao;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 12/11/12
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CodeGeneratorLocksDaoApi {

    public void createLock (String code, String key, String namespace);

}
