package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.framework.context.UserSessionHelper;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserSessionHelperMockTest implements UserSessionHelper {
    /**
     * Returns true if a user has authenticated as a adviser. All sorts of
     * conditional behavior depends on this method.
     *
     * @return True if the user is an adviser. Otherwise, false.
     */
    @Override
    public boolean isAdviser() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Determines the student id that should be used for queries. If the user
     * has the adviser flag set in the session then there should also be a
     * student id. Otherwise, just return the principal it.
     *
     * @return The Id
     */
    @Override
    public String getStudentId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Is the User logged into a session
     *
     * @return True if a session is active
     */
    @Override
    public boolean isUserSession() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Determines the student id that should be used for queries. If the user
     * has the adviser flag set in the session then there should also be a
     * student id. Otherwise, just return the principal it.
     *
     * @return Student Name
     */
    @Override
    public String getStudentName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Queries the person service to get the name (first last) of a person given
     * a principle ID.
     *
     * @param principleId
     * @return The name in first last format.
     */
    @Override
    public String getName(String principleId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Queries the person service to get the name (first last) of a person given
     * a principle ID.
     *
     * @param principleId
     * @return The name in first last format.
     */
    @Override
    public String getNameCapitalized(String principleId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String capitalize(String value) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getMailAddress(String principleId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAuditSystemKey() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isStudent() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getStudentNumber() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
