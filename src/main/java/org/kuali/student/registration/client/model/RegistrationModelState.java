/**
 * 
 */
package org.kuali.student.registration.client.model;

import java.util.List;

import org.kuali.student.commons.ui.propertychangesupport.BasePropertyChange;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;



/**
 * @author Garey
 *
 */
public class RegistrationModelState extends BasePropertyChange {

	
	List<GwtLuiPersonRelationDisplay> currUserRelations = null;
	List<GwtLuiInfo>	currUserBasket		= null;
	List<GwtLuiInfo>	currUserCourses		= null; // registered courses
	//List<String>		currUserBasketRelationIds	= null; // registered courses
	
	
	protected static RegistrationModelState instance = null;
	/**
	 * 
	 */
	protected RegistrationModelState() {
		super();
	}
	
	public static RegistrationModelState getInstance(){
		return instance == null ? instance = new RegistrationModelState() : instance;
	}

	/**
	 * @return the currUserBasket
	 */
	public List<GwtLuiInfo> getCurrUserBasket() {
		return currUserBasket;
	}

	/**
	 * @param list the currUserBasket to set
	 */
	public void setCurrUserBasket(List<GwtLuiInfo> list) {
		List<GwtLuiInfo> old = this.currUserBasket;
		this.currUserBasket = list;
		changes.firePropertyChange("currUserBasket", old, list);
		
	}

	/**
	 * @return the currUserCourses
	 */
	public List<GwtLuiInfo> getCurrUserCourses() {
		return currUserCourses;
	}

	/**
	 * @param currUserCourses the currUserCourses to set
	 */
	public void setCurrUserCourses(List<GwtLuiInfo> currUserCourses) {		
		List<GwtLuiInfo> old = this.currUserCourses;
		this.currUserCourses = currUserCourses;
		if(!(old.containsAll(currUserCourses) && old.size() == currUserCourses.size()))
		{
			changes.firePropertyChange("currUserCourses", old, currUserCourses);
		}
	}

	/**
	 * @return the currUserRelations
	 */
	
	public List<GwtLuiPersonRelationDisplay> getCurrUserRelations() {
		return currUserRelations;
	}
	

	/**
	 * @param currUserRelations the currUserRelations to set
	 */
	public void setCurrUserRelations(
			List<GwtLuiPersonRelationDisplay> currUserRelations) {
		List<GwtLuiPersonRelationDisplay> old = this.currUserRelations;
		this.currUserRelations = currUserRelations;
		changes.firePropertyChange("currUserRelations", old, currUserRelations);
	}

}
