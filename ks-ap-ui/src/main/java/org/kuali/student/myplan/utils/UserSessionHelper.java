package org.kuali.student.myplan.utils;

import org.apache.log4j.Logger;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.StringUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.type.EntityTypeContactInfo;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.myplan.plan.PlanConstants;
import org.kuali.student.r2.common.dto.ContextInfo;



import java.util.List;
import java.util.Map;

/**
 * Provides an initialized Context which can be used for service requests.
 */
public class UserSessionHelper {

    private static final Logger logger = Logger.getLogger(UserSessionHelper.class);

    private static UserSessionHelper userSessionHelper = new UserSessionHelper();

    private static UserSessionService userSessionService;

    public UserSessionHelper(){

    }

    public static UserSessionHelper createInstance(){
        return userSessionHelper;
    }

    public void setUserSessionService(UserSessionService userSessionService){
       this.userSessionService=userSessionService;
    }

    public UserSessionService getUserSessionService(){
       return userSessionService;
    }

    public synchronized static ContextInfo makeContextInfoInstance() {
       return userSessionService.makeContextInfoInstance();
    }

    /**
     * Returns true if a user has authenticated as a adviser. All sorts of conditional behavior depends on this method.
     *
     * @return True if the user is an adviser. Otherwise, false.
     */
    public synchronized static boolean isAdviser() {
       return userSessionService.isAdviser();
    }

    /**
     * Determines the student id that should be used for queries. If the user has the
     * adviser flag set in the session then there should also be a student id. Otherwise, just return the principal it.
     *
     * @return The Id
     */
    public synchronized static String getStudentId() {
        return userSessionService.getStudentId();
    }

    /**
     * Determines the student id that should be used for queries. If the user has the
     * adviser flag set in the session then there should also be a student id. Otherwise,
     * just return the principal it.
     *
     * @return Student Name
     */
    public synchronized static String getStudentName() {
        return userSessionService.getStudentName();
    }

    /**
     * Queries the person service to get the name (first last) of a person given a principle ID.
     *
     * @param principleId
     * @return The name in first last format.
     */
    public synchronized static String getName(String principleId) {
        return userSessionService.getName(principleId);
    }

    /**
     * Queries the person service to get the name (first last) of a person given a principle ID.
     *
     * @param principleId
     * @return The name in first last format.
     */
    public synchronized static String getNameCapitalized( String principleId ){
        return userSessionService.getNameCapitalized(principleId);
    }

    public static String capitalize( String value )
    {
        return userSessionService.capitalize(value);
    }

    public synchronized static String getMailAddress(String principleId) {
        return userSessionService.getMailAddress(principleId);
    }

    public synchronized static String getAuditSystemKey() {
        return userSessionService.getAuditSystemKey();
    }

    public synchronized static boolean isStudent(){
        return userSessionService.isStudent();
    }

}
