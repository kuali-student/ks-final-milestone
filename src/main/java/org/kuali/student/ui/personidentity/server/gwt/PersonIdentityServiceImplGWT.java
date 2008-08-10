package org.kuali.student.ui.personidentity.server.gwt;

import java.util.List;

import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.core.spring.BeanFactory;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCreateInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCriteria;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;
import org.kuali.student.ui.personidentity.client.model.validators.PersonCreateValidator;
import org.kuali.student.ui.personidentity.client.service.PersonIdentityService;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class PersonIdentityServiceImplGWT extends RemoteServiceServlet
		implements PersonIdentityService {

	private static final long serialVersionUID = 822326113643928855L;
	
	private PersonIdentityService serviceImpl = (PersonIdentityService) BeanFactory.getInstance().getBean("personIdentityService");
	

	public List<GwtPersonInfo> searchForPeople(GwtPersonCriteria criteria)
			throws SerializableException {
		return this.serviceImpl.searchForPeople(criteria);
	}
	
	public List<GwtPersonInfo> searchForPeople(String criteria)
			throws SerializableException {
		return this.serviceImpl.searchForPeople(criteria);
	}

	public List<String> fetchAllPeopleIds() throws SerializableException {
		return this.serviceImpl.fetchAllPeopleIds();
	}

	public GwtPersonInfo fetchFullPersonInfo(String personId)
			throws SerializableException {
		return this.serviceImpl.fetchFullPersonInfo(personId);
	}

	public String createPerson(GwtPersonCreateInfo arg0, List<String> arg1)
			throws SerializableException {
		return this.serviceImpl.createPerson(arg0, arg1);
	}

	public List<PersonTypeDisplay> findCreatablePersonTypes()
			throws SerializableException {

		return this.serviceImpl.findCreatablePersonTypes();
	}


	/**
	 * @return the serviceImpl
	 */
	public PersonIdentityService getServiceImpl() {
		return serviceImpl;
	}

	/**
	 * @param serviceImpl the serviceImpl to set
	 */
	public void setServiceImpl(PersonIdentityService serviceImpl) {
		this.serviceImpl = serviceImpl;
	}

	public String getTime() {

		return serviceImpl.getTime();
	}

	
	public boolean updatePerson(GwtPersonInfo info)
			throws SerializableException {
		
		return serviceImpl.updatePerson(info);
	}

	
	public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypes()
			throws  Exception {

		
		return serviceImpl.findPersonAttributeSetTypes();
	}

	public PersonTypeInfo fetchPersonType(String personTypeKey)
			throws Exception {
		return serviceImpl.fetchPersonType(personTypeKey);
	}

   
    public boolean deletePerson(String arg0) throws SerializableException {
        return serviceImpl.deletePerson(arg0);
    }

    
    public ValidationResult validate(GwtPersonCreateInfo value) {
        return serviceImpl.validate(value);
    }

  

    
   

}
