/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import java.util.List;

import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeEvent;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListenerProxy;
import org.kuali.student.commons.ui.widgets.DateBox;
import org.kuali.student.ui.personidentity.client.ModelState;
import org.kuali.student.ui.personidentity.client.controller.PersonIdentityController;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.table.client.FixedWidthFlexTable;
import com.google.gwt.widgetideas.table.client.FixedWidthGrid;
import com.google.gwt.widgetideas.table.client.ScrollTable;
import com.google.gwt.widgetideas.table.client.SelectionGrid;
import com.google.gwt.widgetideas.table.client.overrides.FlexTable.FlexCellFormatter;

/**
 * @author Garey
 *
 */
public class GridResult extends VerticalPanel {

	ScrollTable scrollTable = null;
	FixedWidthFlexTable	headerTable = null;
	FixedWidthFlexTable	footerTable = null;
	FixedWidthGrid		dataTable = null;
	
	
	private PropertyChangeListener listener  
	= new PropertyChangeListenerProxy(
            "searchResult",
            new PropertyChangeListener() {
                public void propertyChange(
                    PropertyChangeEvent propertyChangeEvent) {
                    //updateView( (List<GwtPersonInfo>)propertyChangeEvent.getNewValue());
                	updateView( ModelState.getInstance().getSearchResult());
                }
            });
	
	public GridResult(){
		super();
		
		ModelState.getInstance().addPropertyChangeListener(listener);
		dataTable = this.createDataTable();
		headerTable = this.createHeaderTable();
		
		scrollTable = new ScrollTable(dataTable, headerTable);
	    
	    // Set some options in the scroll table
	    scrollTable.setSortingEnabled(true);
		scrollTable.setCellPadding(3);
		scrollTable.setCellSpacing(1);
		scrollTable.setSize("100%", "100%");
		// will work with snapshot of incubator
	    //scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FIXED_WIDTH);
	    //scrollTable.setScrollPolicy(ScrollTable.ScrollPolicy.HORIZONTAL);
	    
	    
	    
	    this.add(scrollTable);
	}
	
		
	public void updateView(List<GwtPersonInfo> pList){
		dataTable.clear();		
		for(int i =0; i < pList.size(); i++){
			GwtPersonInfo pInfo = pList.get(i);
			dataTable.setWidget(i, 0, new PersonInfoPidWidget(pInfo));
			dataTable.setHTML(i, 1, pInfo.getPreferredName().getPersonTitle());
			dataTable.setHTML(i, 2, pInfo.getPreferredName().getSurname());
			dataTable.setHTML(i, 3, pInfo.getPreferredName().getGivenName());
			dataTable.setHTML(i, 4, pInfo.getPreferredName().getMiddleName());
			String dob = "";
			if(pInfo.getBirthDate() != null)
				dob = DateBox.formatter.format(pInfo.getBirthDate());
			dataTable.setHTML(i, 5, dob);
			dataTable.setHTML(i, 6, pInfo.getCitizenship().getCountryOfCitizenshipCode());
		}
	}
	
	private FixedWidthGrid createDataTable() {
	    // Create a new table
	    final FixedWidthGrid dataTable = new FixedWidthGrid();
	    dataTable.addTableSelectionListener(new TableSelectionListenerProxy(){
			public void onCellClicked(int row, int cell) {
				
				PersonInfoPidWidget pWid = (PersonInfoPidWidget)dataTable.getWidget(row, 0);
				ModelState.getInstance().setCurrPerson(pWid.getPInfo());		
				PersonIdentityController.viewPersonDetailsScreen();
				
			}		
			public void onRowsSelected(int firstRow, int numRows) {
				// TODO Auto-generated method stub
				
			}});

	    // Set some options in the data table
	    //dataTable.setSelectionPolicy(SelectionGrid.SelectionPolicy.MULTI_ROW);	    		
	    dataTable.setSelectionPolicy(SelectionGrid.SELECTION_POLICY_MULTI_ROW);
	    dataTable.setHeight("100%");
	   

	    // Return the data table
	    return dataTable;
	  }

	  private FixedWidthFlexTable createHeaderTable() {
		    // Create a new table
		    FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
		    FlexCellFormatter formatter = headerTable.getFlexCellFormatter();

		    // Level 1 headers
		    headerTable.setHTML(0, 0, "<b>Person Information</b>");
		    formatter.setColSpan(0, 0, 7);
		    formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		    // Level 2 headers
		    headerTable.setHTML(1, 0, "<b>Person ID</b>");
		    formatter.setRowSpan(1, 0, 2);
		    formatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		    headerTable.setHTML(1, 1, "<b>General</b>");
		    formatter.setColSpan(1, 1, 7);
		    formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);


		    // Level 3 headers
		    headerTable.setHTML(2, 0, "Title");
		    formatter.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		    headerTable.setHTML(2, 1, "Last Name");
		    formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);
		    headerTable.setHTML(2, 2, "First Name");
		    formatter.setHorizontalAlignment(2, 2, HasHorizontalAlignment.ALIGN_CENTER);
		    headerTable.setHTML(2, 3, "Middle Name");
		    formatter.setHorizontalAlignment(2, 3, HasHorizontalAlignment.ALIGN_CENTER);
		    headerTable.setHTML(2, 4, "DOB");
		    formatter.setHorizontalAlignment(2, 4, HasHorizontalAlignment.ALIGN_CENTER);
		    headerTable.setHTML(2, 5, "Citizenship");
		    formatter.setHorizontalAlignment(2, 5, HasHorizontalAlignment.ALIGN_CENTER);

		    // Return the header table
		    return headerTable;
		  }

	  class PersonInfoPidWidget extends VerticalPanel{
		  Label pId = null;
		  GwtPersonInfo pInfo = null;
		  
		  PersonInfoPidWidget(GwtPersonInfo pInfo){
			  super();
			  this.pInfo = pInfo;
			  pId = new Label(pInfo.getPersonId());
			  this.add(pId);
		  }

		public Label getPId() {
			return pId;
		}

		public void setPId(Label id) {
			pId = id;
		}

		public GwtPersonInfo getPInfo() {
			return pInfo;
		}

		public void setPInfo(GwtPersonInfo info) {
			pInfo = info;
		}
		  
	  }

}
