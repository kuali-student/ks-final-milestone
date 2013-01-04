package org.kuali.student.enrollment.class2.courseoffering.dao;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 12/11/12
 * Time: 1:25 PM
 * This is the interface for the CodeGenerator.
 */
public interface CodeGeneratorLocksDaoApi {

    public void createLock (String code, String key, String namespace);
    public void removeLock (String code, String key, String namespace);
    public void cleanLocks (String key, String pendingKey, String namespace);
}
