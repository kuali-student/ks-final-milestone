package org.kuali.student.ui.personidentity.client.service;

import java.util.List;

import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCreateInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCriteria;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface PersonIdentityService extends RemoteService {

    public static final String SERVICE_URI = "PersonIdentityService";

    public static class Util {

        public static PersonIdentityServiceAsync getInstance() {

            PersonIdentityServiceAsync instance = (PersonIdentityServiceAsync) GWT
                    .create(PersonIdentityService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }
    
	public String getTime();
	
	public List<PersonTypeDisplay> findCreatablePersonTypes() throws SerializableException;
	
	public String createPerson(GwtPersonCreateInfo arg0, List<String> arg1) throws SerializableException;
	
	public GwtPersonInfo fetchFullPersonInfo(String personId) throws SerializableException;
	
	public List<String> fetchAllPeopleIds() throws  SerializableException;
	
	public List<GwtPersonInfo> searchForPeople(GwtPersonCriteria pCriteria) throws  SerializableException; 
	public List<GwtPersonInfo> searchForPeople(String searchString) throws  SerializableException;
	public boolean updatePerson(GwtPersonInfo pInfo) throws  SerializableException;
	public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypes() throws  Exception;
	
	public PersonTypeInfo fetchPersonType(String personTypeKey) throws Exception;
	
	public boolean deletePerson(String arg0) throws SerializableException;
	public ValidationResult validate(GwtPersonCreateInfo value);
}
