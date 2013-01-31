package org.kuali.student.myplan.utils;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.type.EntityTypeContactInfo;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.myplan.plan.PlanConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class UserSessionServiceMockImpl implements UserSessionService {


    private static final Logger logger = Logger.getLogger(UserSessionHelper.class);

    private IdentityService identityService;
    private PersonService personService;

    @Override
    public synchronized IdentityService getIdentityService() {
        if (identityService == null) {
            identityService = KimApiServiceLocator.getIdentityService();
        }
        return identityService;
    }

    @Override
    public synchronized PersonService getPersonService() {
        if (personService == null) {
            personService = KimApiServiceLocator.getPersonService();
        }
        return personService;
    }

    @Override
    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Override
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public synchronized ContextInfo makeContextInfoInstance() {
        ContextInfo contextInfo = new ContextInfo();
        Person user = GlobalVariables.getUserSession().getPerson();
        contextInfo.setPrincipalId(user.getPrincipalId());
        return contextInfo;
    }

    @Override
    public synchronized boolean isAdviser() {
        return false;
    }

    @Override
    public synchronized String getStudentId() {
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

    @Override
    public synchronized String getStudentName() {
        UserSession session = GlobalVariables.getUserSession();

        return  session.getPerson().getFirstName().substring(0, 1).toUpperCase() + session.getPerson().getFirstName().substring(1, session.getPerson().getFirstName().length()) + " " + session.getPerson().getLastName().substring(0, 1).toUpperCase();
    }

    @Override
    public synchronized String getName(String principleId) {
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

    @Override
    public synchronized String getNameCapitalized( String principleId )
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

    @Override
    public String capitalize( String value )
    {
        if( value == null ) return null;
        if( value.length() == 0 ) return value;
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    @Override
    public synchronized String getMailAddress(String principleId) {
        try{
            Person user = GlobalVariables.getUserSession().getPerson();
            String emailAddress = user.getEmailAddress();

            Entity entity = getIdentityService().getEntityByPrincipalId(principleId);
            if(entity==null){
                return null;
            }
            List<EntityTypeContactInfo> contactInfos = entity.getEntityTypeContactInfos();
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

    @Override
    public synchronized String getAuditSystemKey() {
        return getStudentId();
    }

    @Override
    public synchronized boolean isStudent(){
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
