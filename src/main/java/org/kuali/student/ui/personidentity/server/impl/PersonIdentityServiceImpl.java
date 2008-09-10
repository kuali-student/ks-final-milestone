package org.kuali.student.ui.personidentity.server.impl;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.CircularReferenceException;
import org.kuali.student.poc.common.ws.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.poc.common.ws.exceptions.ReadOnlyException;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCreateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCriteria;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationUpdateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.ValidationError;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCreateInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCriteria;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;
import org.kuali.student.ui.personidentity.client.model.validators.PersonCreateValidator;
import org.kuali.student.ui.personidentity.client.service.PersonIdentityService;
import org.kuali.student.ui.personidentity.server.convert.GwtException;
import org.kuali.student.ui.personidentity.server.convert.PersonServiceConverter;

import com.google.gwt.user.client.rpc.SerializableException;

public class PersonIdentityServiceImpl implements PersonIdentityService {

    private PersonService personService;
    public static int DEFAULT_SEARCH_PAGE_SIZE = 25;

    private GwtPersonCriteria prevCriteria = null;
    private List<String> searchResult = null;

    public List<GwtPersonInfo> searchForPeople(GwtPersonCriteria criteria) throws SerializableException {
        return this.searchForPeople(criteria, DEFAULT_SEARCH_PAGE_SIZE, 0);
    }

    protected List<GwtPersonInfo> getPage(int pageSize, int page) throws SerializableException {

        List<GwtPersonInfo> lRet = null;
        if (this.searchResult == null || this.searchResult.isEmpty()) {
            lRet = null;
        } else {
            int rSize = this.searchResult.size();
            int low = (page - 1) * pageSize;
            if(page == 0) low = 0;
            int high = low + (pageSize - 1);
            if(high > rSize) high = rSize -1;

            List<String> subSet = this.searchResult.subList(low, high);
            try {
                List<PersonInfo> pList = this.personService.findPeopleByPersonIds(subSet);
                if (pList != null) {
                    lRet = new Vector<GwtPersonInfo>();
                    for (PersonInfo pCurr : pList) {
                        lRet.add(PersonServiceConverter.convert(pCurr));
                    }
                }
            } catch (Exception ex) {
                throw GwtException.toGWT(ex);
            }

        }

        return lRet;
    }

    /*
     * (non-Javadoc)
     * @seeorg.kuali.student.ui.personidentity.client.service.PersonIdentityService#searchForPeople(org.kuali.student.ui.
     * personidentity.client.model.GwtPersonCriteria)
     */
    public List<GwtPersonInfo> searchForPeople(GwtPersonCriteria criteria, int pageSize, int page) throws SerializableException {
        List<GwtPersonInfo> pRet = null;
        List<PersonInfo> pList = null;

        try {

            if(this.prevCriteria != null && this.prevCriteria.equals(criteria) 
                    && this.searchResult != null && !this.searchResult.isEmpty()){
               pRet = this.getPage(pageSize, page); 
            }else{
                this.prevCriteria = criteria;
                this.searchResult = personService.searchForPersonIds(PersonServiceConverter.convert(criteria));
                pRet = this.getPage(pageSize, page);
            }
            
            
            /*
            pList = personService.searchForPeople(PersonServiceConverter.convert(criteria));
            if (pList != null) {
                pRet = new Vector<GwtPersonInfo>();
                for (PersonInfo pCurr : pList) {
                    pRet.add(PersonServiceConverter.convert(pCurr));
                }
            }
            */
        } catch (Exception ex) {
            throw GwtException.toGWT(ex);
        }

        return pRet;
    }

    /*
     * parses the search string into a GwtPersonCriteria
     */

    public List<GwtPersonInfo> searchForPeople(String searchString) throws SerializableException {

        List<GwtPersonInfo> lRet = new Vector<GwtPersonInfo>();
        StringTokenizer st = new StringTokenizer(searchString);
        Vector<String> tokens = new Vector<String>();
        while (st.hasMoreTokens()) {
            tokens.add(st.nextToken());
        }

        if (tokens.size() == 0) {
            List<GwtPersonInfo> tmp = this.searchForPeople(new GwtPersonCriteria());
            if (tmp != null) {
                lRet.addAll(tmp);
            }
        } else if (tokens.size() == 1) {
            GwtPersonCriteria pc = new GwtPersonCriteria();
            pc.setFirstName(tokens.get(0));
            List<GwtPersonInfo> tmp = this.searchForPeople(pc);
            if (tmp != null) {
                lRet.addAll(tmp);
            }

            pc = new GwtPersonCriteria();
            pc.setLastName(tokens.get(0));
            tmp = this.searchForPeople(pc);
            if (tmp != null) {
                // check for duplicate results
                for (GwtPersonInfo info : tmp) {
                    if (!lRet.contains(info)) {
                        lRet.add(info);
                    }
                }
                // lRet.addAll(tmp);
            }

        } else if (tokens.size() > 1) {
            GwtPersonCriteria pc = new GwtPersonCriteria();
            pc.setFirstName(tokens.get(0));
            pc.setLastName(tokens.get(1));
            List<GwtPersonInfo> tmp = this.searchForPeople(pc);
            if (tmp != null) {
                lRet.addAll(tmp);
            }

            pc = new GwtPersonCriteria();
            pc.setLastName(tokens.get(0));
            pc.setFirstName(tokens.get(1));
            tmp = this.searchForPeople(pc);
            if (tmp != null) {
                for (GwtPersonInfo info : tmp) {
                    if (!lRet.contains(info)) {
                        lRet.add(info);
                    }
                }
                // lRet.addAll(tmp);
            }

        }

        // if(lRet.isEmpty()) lRet = null;

        return lRet;
    }

    public String getTime() {
        String out = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.FULL).format(new Date());

        return out;
    }

    public List<PersonTypeDisplay> findPersonTypes() throws Exception {
        return personService.findPersonTypes();
    }

    public String createPersonTypeInfo(PersonTypeInfo pInfo) throws Exception {
        return personService.createPersonTypeInfo(pInfo);
    }

    public boolean assignPersonType(Long arg0, Long arg1) throws AlreadyExistsException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return false;
    }

    public long createAttributeDefinition(PersonAttributeTypeInfo arg0) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return 0;
    }

    public String createPerson(GwtPersonCreateInfo arg0, List<String> arg1) throws SerializableException {

        String lRet = null;

        try {
            lRet = personService.createPerson(PersonServiceConverter.convert(arg0), arg1);
        } catch (Exception ex) {
            throw GwtException.toGWT(ex);
        }

        return lRet;
    }

    public String createPersonAttributeSetType(PersonAttributeSetTypeInfo arg0) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, Exception {

        return personService.createPersonAttributeSetType(arg0);

    }

    public Long createPersonRelation(Long arg0, Long arg1, Long arg2, PersonRelationCreateInfo arg3) throws AlreadyExistsException, DoesNotExistException, CircularReferenceException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean deletePerson(String arg0) throws SerializableException {
        boolean bRet = false;
        try {
            bRet = personService.deletePerson(arg0);
        } catch (Exception ex) {
            throw GwtException.toGWT(ex);
        }

        return bRet;
    }

    public boolean deletePersonRelation(Long arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return false;
    }

    public PersonAttributeSetTypeInfo fetchPersonAttributeSetType(Long arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    public PersonInfo fetchPersonInfoByPersonAttributeSetTypes(Long arg0, List<Long> arg1) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public PersonInfo fetchPersonInfoByPersonType(Long arg0, Long arg1) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public PersonRelationInfo fetchPersonRelation(Long arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PersonRelationTypeDisplay> fetchPersonRelationTypes() throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    public PersonTypeInfo fetchPersonType(String personTypeKey) throws Exception {
        return personService.fetchPersonType(personTypeKey);
    }

    public Long fetchReplacementPersonId(Long arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PersonTypeDisplay> findCreatablePersonTypes() throws SerializableException {
        List<PersonTypeDisplay> lRet = null;
        try {

            // List<PersonTypeDisplay> pd = personService.findCreatablePersonTypes();
            lRet = personService.findCreatablePersonTypes();
            /*
             * if(pd != null){ lRet = new Vector<GwtPersonTypeDisplay>(); for(int i=0;i<pd.size();i++){ lRet.add(
             * PersonServiceConverter.convert(pd.get(i))); } }
             */
        } catch (Exception ex) {
            throw GwtException.toGWT(ex);
        }
        return lRet;
    }

    public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypes() throws Exception {

        return personService.findPersonAttributeSetTypes();
    }

    public List<Long> findPersonAttributeSetTypesForPerson(Long arg0) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypesForPersonType(Long arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<String> findPersonIdsForPersonType(String arg0, PersonCriteria arg1) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, Exception {

        return personService.findPersonIdsForPersonType(arg0, arg1);
    }

    public List<Long> findPersonPersonRelationIds(Long arg0, Long arg1) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PersonRelationDisplay> findPersonRelations(Long arg0, Long arg1) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Long> findPersonTypesForPerson(Long arg0) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isPersonType(Long arg0, Long arg1) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean removePersonType(Long arg0, Long arg1) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return false;
    }

    public List<PersonInfo> searchForPeople(PersonCriteria arg0) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, Exception {

        return personService.searchForPeople(arg0);
    }

    public List<PersonInfo> searchForPeopleByPersonAttributeSetType(Long arg0, PersonCriteria arg1) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<String> fetchAllPeopleIds() throws SerializableException {
        List<String> lRet = new Vector<String>();

        try {
            List<PersonTypeDisplay> pList = this.findPersonTypes();

            if (pList != null && pList.size() > 0) {
                for (int i = 0; i < pList.size(); i++) {
                    String pKey = pList.get(i).getId();
                    lRet.addAll(this.findPersonIdsForPersonType(pKey, null));
                }
            }
        } catch (Exception ex) {
            throw GwtException.toGWT(ex);
        }
        return lRet;
    }

    public List<PersonInfo> searchForPeopleByPersonType(String arg0, PersonCriteria arg1) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, Exception {

        return personService.searchForPeopleByPersonType(arg0, arg1);

    }

    public List<PersonDisplay> searchForPeopleByRelation(PersonRelationCriteria arg0) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Long> searchForPersonIds(PersonCriteria arg0) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Long> searchForPersonIdsByRelation(PersonRelationCriteria arg0) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PersonRelationDisplay> searchForPersonRelations(PersonRelationCriteria arg0) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean updatePersonRelation(Long arg0, PersonRelationUpdateInfo arg1) throws DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return false;
    }

    public ValidationError validatePersonInfoForPersonType(Long arg0, Long arg1) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    public GwtPersonInfo fetchFullPersonInfo(String personId) throws SerializableException {

        GwtPersonInfo pRet = null;
        try {

            pRet = PersonServiceConverter.convert(personService.fetchFullPersonInfo(personId));
        } catch (Exception ex) {
            throw GwtException.toGWT(ex);
        }
        return pRet;
    }

    public boolean updatePerson(GwtPersonInfo info) throws SerializableException {

        boolean bRet = false;

        try {

            bRet = this.personService.updatePerson(info.getPersonId(), PersonServiceConverter.convertPersonUpdateInfo(info));
        } catch (Exception ex) {
            throw GwtException.toGWT(ex);
        }

        return bRet;
    }

    /**
     * @return the personService
     */
    public PersonService getPersonService() {
        return personService;
    }

    /**
     * @param service
     *            the personService to set
     */
    public void setPersonService(PersonService service) {
        personService = service;
    }

    public ValidationResult validate(GwtPersonCreateInfo value) {
        PersonCreateValidator pValid = new PersonCreateValidator();

        return pValid.validate(value);
    }

}
