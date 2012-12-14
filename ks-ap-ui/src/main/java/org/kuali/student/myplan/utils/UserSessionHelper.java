package org.kuali.student.myplan.utils;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.api.common.template.Template;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.StringUtils;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.identity.email.EntityEmail;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.type.EntityTypeContactInfo;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.myplan.plan.PlanConstants;
import org.kuali.student.r2.common.dto.ContextInfo;



import java.util.List;
import java.util.Map;

/**
 * Provides an initialized Context which can be used for service requests.
 */
public class    UserSessionHelper {

    private static final Logger logger = Logger.getLogger(UserSessionHelper.class);

    private static transient IdentityService identityService;
    private static transient PersonService personService;

    public synchronized static IdentityService getIdentityService() {
        if (identityService == null) {
            identityService = KimApiServiceLocator.getIdentityService();
        }
        return identityService;
    }

    public synchronized static PersonService getPersonService() {
        if (personService == null) {
            personService = KimApiServiceLocator.getPersonService();
        }
        return personService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public synchronized static ContextInfo makeContextInfoInstance() {
        ContextInfo contextInfo = new ContextInfo();
        Person user = GlobalVariables.getUserSession().getPerson();
        contextInfo.setPrincipalId(user.getPrincipalId());
        return contextInfo;
    }

    /**
     * Returns true if a user has authenticated as a adviser. All sorts of conditional behavior depends on this method.
     *
     * @return True if the user is an adviser. Otherwise, false.
     */
    public synchronized static boolean isAdviser() {
        UserSession session = GlobalVariables.getUserSession();
        return session.retrieveObject(PlanConstants.SESSION_KEY_IS_ADVISER) != null;
    }

    /**
     * Determines the student id that should be used for queries. If the user has the
     * adviser flag set in the session then there should also be a student id. Otherwise, just return the principal it.
     *
     * @return The Id
     */
    public synchronized static String getStudentId() {
        UserSession session = GlobalVariables.getUserSession();
        String studentId;
        if (isAdviser()) {
            studentId = (String) session.retrieveObject(PlanConstants.SESSION_KEY_STUDENT_ID);
            if (studentId == null) {
                throw new RuntimeException("User is in adviser mode, but no student id was set in the session. (This shouldn't happen and should be reported).");
            }
        } else {
            studentId = session.getPerson().getPrincipalId();
        }
        return studentId;
    }

    /**
     * Determines the student id that should be used for queries. If the user has the
     * adviser flag set in the session then there should also be a student id. Otherwise,
     * just return the principal it.
     *
     * @return Student Name
     */
    public synchronized static String getStudentName() {
        UserSession session = GlobalVariables.getUserSession();
        String studentName;
        if (isAdviser()) {
            studentName = (String) session.retrieveObject(PlanConstants.SESSION_KEY_STUDENT_NAME);
            if (studentName == null) {
                throw new RuntimeException("User is in adviser mode, but no student name was set in the session. (This shouldn't happen and should be reported).");
            }
        } else {
            studentName = session.getPerson().getFirstName().substring(0, 1).toUpperCase() + session.getPerson().getFirstName().substring(1, session.getPerson().getFirstName().length()) + " " + session.getPerson().getLastName().substring(0, 1).toUpperCase();
        }
        return studentName;
    }

    /**
     * Queries the person service to get the name (first last) of a person given a principle ID.
     *
     * @param principleId
     * @return The name in first last format.
     */
    public synchronized static String getName(String principleId) {
        Person person = null;
        try {
            person = getPersonService().getPerson(principleId);
        } catch (Exception e) {
            logger.error("Could not load the Person Information", e);
        }
        if (person != null) {
            return String.format("%s %s", person.getFirstName(), person.getLastName());
        } else {
            return null;
        }
    }

    /**
     * Queries the person service to get the name (first last) of a person given a principle ID.
     *
     * @param principleId
     * @return The name in first last format.
     */
    public synchronized static String getNameCapitalized( String principleId )
    {

        try
        {
            Person person = getPersonService().getPerson(principleId);
            return person.getName().toUpperCase();

//            String firstName = capitalize(person.getFirstName());
//            String middleName = capitalize(person.getMiddleName());
//            String lastName = capitalize(person.getLastName());

            //return firstName + " " + middleName + " " + lastName;
        }
        catch (Exception e)
        {
            logger.error("Could not load the Person Information", e);
        }

        return null;

    }

    public static String capitalize( String value )
    {
        if( value == null ) return null;
        if( value.length() == 0 ) return value;
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public synchronized static String getMailAddress(String principleId) {
        try{
        Person user = GlobalVariables.getUserSession().getPerson();
        String emailAddress = user.getEmailAddress();

        Entity entity = getIdentityService().getEntityByPrincipalId(principleId);
            if(entity==null){
                return null;
            }
        List <EntityTypeContactInfo> contactInfos = entity.getEntityTypeContactInfos();
        for (EntityTypeContactInfo ci : contactInfos) {
            emailAddress = ci.getDefaultEmailAddress().getEmailAddress();
            /*for (EntityEmail e : ci.getEmailAddresses()) {
                //  FIXME: Probably want to make this more deterministic.
                if (e.getEmailType().getName().equals("Student")) {
                    emailAddress = e.getEmailAddress();
                }
                if (e.getEmailType().getName().equals("Employee")) {
                    emailAddress = e.getEmailAddress();
                }
            } */
        }
        return emailAddress;
        }
        catch (Exception e){
            logger.error("Could not get the Email Address for the student"+e);
           return null;
        }
    }

    public synchronized static String getAuditSystemKey() {
        String systemKey = null;
        String studentId = getStudentId();
        Person person = null;
        try {
            person = getPersonService().getPerson(studentId);
        } catch (Exception e) {
            logger.error("Could not load the Person Information", e);
        }
        if (person != null) {
            Map<String,String> idmap = person.getExternalIdentifiers();
            for( String key : idmap.keySet() )
            {
                String value = idmap.get( key );
                logger.info( "identifier : " + key + " = " + value );

            }
            logger.info(person.getExternalIdentifiers().values());
            systemKey = person.getExternalIdentifiers().get("systemKey");
        }
        if ( ! StringUtils.hasText(systemKey)) {
            throw new DataRetrievalFailureException("Could not find the SystemKey for the Student");
        }
        return systemKey;
    }

    public synchronized static boolean isStudent(){
        Person person = null;
        boolean isStudent=false;
        try {
            person = getPersonService().getPerson(getStudentId());
        } catch (Exception e) {
            logger.error("Could not load the Person Information", e);
        }
        if (person != null) {
            if (person.getExternalIdentifiers().containsKey("systemKey")) {
                isStudent = true;
            }
        }
         return isStudent;
    }

}
