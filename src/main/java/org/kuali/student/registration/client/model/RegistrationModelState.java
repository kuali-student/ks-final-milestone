/**
 * 
 */
package org.kuali.student.registration.client.model;

import java.util.List;

import org.kuali.student.commons.ui.propertychangesupport.BasePropertyChange;



/**
 * @author Garey
 *
 */
public class RegistrationModelState extends BasePropertyChange {

	
	List<GwtLuiPersonRelationDisplay> currUserRelations = null;
	
	//List<GwtLuiInfo>	currUserBasket		= null;
	//List<GwtLuiInfo>	currUserCourses		= null; // registered courses
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
	//public List<GwtLuiInfo> getCurrUserBasket() {
	//	return currUserBasket;
	//}

	/**
	 * @param currUserBasket the currUserBasket to set
	 */
	/*public void setCurrUserBasket(List<GwtLuiInfo> currUserBasket) {
		List<GwtLuiInfo> old = this.currUserBasket;
		this.currUserBasket = currUserBasket;
		changes.firePropertyChange("currUserBasket", old, currUserBasket);
		
	}*/

	/**
	 * @return the currUserCourses
	 */
	/*public List<GwtLuiInfo> getCurrUserCourses() {
		return currUserCourses;
	}*/

	/**
	 * @param currUserCourses the currUserCourses to set
	 */
	/*public void setCurrUserCourses(List<GwtLuiInfo> currUserCourses) {		
		List<GwtLuiInfo> old = this.currUserCourses;
		this.currUserCourses = currUserCourses;
		changes.firePropertyChange("currUserCourses", old, currUserCourses);
	}*/

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
