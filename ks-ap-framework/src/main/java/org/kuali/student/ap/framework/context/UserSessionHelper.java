package org.kuali.student.ap.framework.context;

public interface UserSessionHelper {

	/**
	 * Returns true if a user has authenticated as a adviser. All sorts of
	 * conditional behavior depends on this method.
	 * 
	 * @return True if the user is an adviser. Otherwise, false.
	 */
	boolean isAdviser();

	/**
	 * Determines the student id that should be used for queries. If the user
	 * has the adviser flag set in the session then there should also be a
	 * student id. Otherwise, just return the principal it.
	 * 
	 * @return The Id
	 */
	String getStudentId();

    /**
     * Is the User logged into a session
     *
     * @return True if a session is active
     */
    public boolean isUserSession();

	/**
	 * Determines the student id that should be used for queries. If the user
	 * has the adviser flag set in the session then there should also be a
	 * student id. Otherwise, just return the principal it.
	 * 
	 * @return Student Name
	 */
	String getStudentName();

	/**
	 * Queries the person service to get the name (first last) of a person given
	 * a principle ID.
	 * 
	 * @param principleId
	 * @return The name in first last format.
	 */
	String getName(String principleId);

	/**
	 * Queries the person service to get the name (first last) of a person given
	 * a principle ID.
	 * 
	 * @param principleId
	 * @return The name in first last format.
	 */
	String getNameCapitalized(String principleId);

	String capitalize(String value);

	String getMailAddress(String principleId);

	String getAuditSystemKey();

	boolean isStudent();

    String getStudentNumber();
}
