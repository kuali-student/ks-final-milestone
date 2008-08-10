package org.kuali.student.ui.personidentity.client.service;

import java.util.List;

import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.poc.common.ws.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCreateInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCriteria;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PersonIdentityServiceAsync {

	public void getTime(AsyncCallback callback);
	public void findCreatablePersonTypes(AsyncCallback callback);
	public void createPerson(GwtPersonCreateInfo arg0, List<String> arg1, AsyncCallback callback);
	public void fetchFullPersonInfo(String personId, AsyncCallback callback);
	
	public void fetchAllPeopleIds(AsyncCallback callback);
	
	public void searchForPeople(GwtPersonCriteria pCriteria, AsyncCallback callback);
	public void searchForPeople(String searchString, AsyncCallback callback);
	public void updatePerson(GwtPersonInfo pInfo, AsyncCallback callback);
	public void findPersonAttributeSetTypes(AsyncCallback callback);
	public void fetchPersonType(String personTypeKey, AsyncCallback<PersonTypeInfo> callback);
	
	public void deletePerson(String p_id, AsyncCallback callback);
	public void validate(GwtPersonCreateInfo value, AsyncCallback callback);
}
