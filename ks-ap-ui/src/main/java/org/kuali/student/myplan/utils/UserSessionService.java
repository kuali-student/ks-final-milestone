package org.kuali.student.myplan.utils;


import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.student.r2.common.dto.ContextInfo;

public interface UserSessionService {

    public IdentityService getIdentityService();

    public PersonService getPersonService();

    public void setIdentityService(IdentityService identityService);

    public void setPersonService(PersonService personService);

    public ContextInfo makeContextInfoInstance();

    /**
     * Returns true if a user has authenticated as a adviser. All sorts of conditional behavior depends on this method.
     *
     * @return True if the user is an adviser. Otherwise, false.
     */
    public boolean isAdviser();

    /**
     * Determines the student id that should be used for queries. If the user has the
     * adviser flag set in the session then there should also be a student id. Otherwise, just return the principal it.
     *
     * @return The Id
     */
    public String getStudentId();

    /**
     * Determines the student id that should be used for queries. If the user has the
     * adviser flag set in the session then there should also be a student id. Otherwise,
     * just return the principal it.
     *
     * @return Student Name
     */
    public String getStudentName();

    /**
     * Queries the person service to get the name (first last) of a person given a principle ID.
     *
     * @param principleId
     * @return The name in first last format.
     */
    public String getName(String principleId);

    /**
     * Queries the person service to get the name (first last) of a person given a principle ID.
     *
     * @param principleId
     * @return The name in first last format.
     */
    public String getNameCapitalized( String principleId );

    public String capitalize( String value );

    public String getMailAddress(String principleId);

    public String getAuditSystemKey();

    public boolean isStudent();

}
