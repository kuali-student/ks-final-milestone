/**
 * 
 */
package org.kuali.student.ui.personidentity.client.model.lu;

import java.util.List;

import org.kuali.student.commons.ui.propertychangesupport.BasePropertyChange;

/**
 * @author Garey
 *
 */
public class LuModelState extends BasePropertyChange{
	
	private		static	LuModelState 			instance;
	
	protected			List <GwtLuiInfo>	searchResults;
	protected			GwtLuiInfo				currLui;
	protected			GwtCluInfo				currClu;
				
	/**
	 * 
	 */
	protected LuModelState() {
		super();
	}
	
	public static LuModelState getInstance() {
		return instance == null ? instance = new LuModelState() : instance;
	}

	/**
	 * @return the currClu
	 */
	public GwtCluInfo getCurrClu() {
		return currClu;
	}

	/**
	 * @param currClu the currClu to set
	 */
	public void setCurrClu(GwtCluInfo currClu) {		
		GwtCluInfo old = this.currClu;
		this.currClu = currClu;
		changes.firePropertyChange("currClu", old, currClu);
	}

	/**
	 * @return the searchResults
	 */
	public List<GwtLuiInfo> getSearchResults() {
		return searchResults;
	}

	/**
	 * @param searchResults the searchResults to set
	 */
	public void setSearchResults(List<GwtLuiInfo> searchResults) {
		List<GwtLuiInfo> old = this.searchResults;
		this.searchResults = searchResults;
		changes.firePropertyChange("searchResult", old, searchResults);
	}

	/**
	 * @return the currLui
	 */
	public GwtLuiInfo getCurrLui() {
		return currLui;
	}

	/**
	 * @param currLui the currLui to set
	 */
	public void setCurrLui(GwtLuiInfo currLui) {
		GwtLuiInfo old = this.currLui;
		this.currLui = currLui;
		changes.firePropertyChange("currLui", old, currLui);
	}
	
	
	
}
