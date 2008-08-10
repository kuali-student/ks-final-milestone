/**
 * 
 */
package org.kuali.student.ui.personidentity.client;
import java.util.List;

import org.kuali.student.commons.ui.propertychangesupport.BasePropertyChange;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDisplay;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;

/**
 * @author Garey
 *
 */
public class ModelState extends BasePropertyChange {

	private static ModelState instance;
	
	List<GwtPersonInfo>			searchResult = null;
	GwtPersonInfo				currPerson	 = null;
	List<PersonTypeDisplay> 	personTypeKeys = null;
	
	GwtPersonInfo				currUser	 = null;	
	
	
	/**
	 * 
	 */
	protected ModelState() {
		super();
	}
	
	public static ModelState getInstance() {
		return instance == null ? instance = new ModelState() : instance;
	}

	public List<GwtPersonInfo> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<GwtPersonInfo> searchResult) {
		List<GwtPersonInfo> old = this.searchResult;
		this.searchResult = searchResult;
		changes.firePropertyChange("searchResult", old, searchResult);
	}

	public List<PersonTypeDisplay> getPersonTypeKeys() {
		return personTypeKeys;
	}
	
	public void setPersonTypeKeys(List<PersonTypeDisplay> 	personTypeKeys){
		List<PersonTypeDisplay> 	old = this.personTypeKeys;
		this.personTypeKeys = personTypeKeys;
		changes.firePropertyChange("personTypeKeys", old, personTypeKeys);
	}
	
	public GwtPersonInfo getCurrPerson() {
		return currPerson;
	}

	public void setCurrPerson(GwtPersonInfo currPerson) {
		GwtPersonInfo old = this.currPerson;
		this.currPerson = currPerson;				
		changes.firePropertyChange("currPerson", old, currPerson);
		
	}

	/**
	 * @return the currUser
	 */
	public GwtPersonInfo getCurrUser() {
		return currUser;
	}

	/**
	 * @param currUser the currUser to set
	 */
	public void setCurrUser(GwtPersonInfo currUser) {
		GwtPersonInfo old = this.currUser;
		this.currUser = currUser;				
		changes.firePropertyChange("currUser", old, currUser);
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	
	
}
