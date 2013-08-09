/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.common.client.widgets;

import java.util.List;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.impl.KSDropDownImpl;
import org.kuali.student.common.ui.client.widgets.list.KSRadioButtonList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.SearchResultListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.KSRadioButtonListImpl;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.statement.ui.client.widgets.rules.AccessWidgetValue;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

public class GradeWidget extends Composite implements AccessWidgetValue, HasDataValue {

    protected static final String GRADE_TYPE_ID_CELL_KEY = "lrc.resultColumn.resultComponent.id";
    protected static final String GRADE_TYPE_NAME_CELL_KEY = "lrc.resultColumn.resultComponent.name";
    private FlowPanel layout = new FlowPanel();
    KSPicker gradeTypeWidget;
    KSPicker gradeWidget = null;
    Metadata gradeMetadata;
    private String initialGradeValue = null;

    public GradeWidget() {
        this.initWidget(layout);
    }

    @Override
    public void initWidget(List<Metadata> fieldsMetadata) {
        layout.clear();
        gradeTypeWidget = (KSPicker) DefaultWidgetFactory.getInstance().getWidget(fieldsMetadata.get(0));
        layout.add(gradeTypeWidget);
        gradeMetadata = fieldsMetadata.get(1);
        setupHandlers();
    }

    private void setupHandlers() {

        ((KSRadioButtonList)gradeTypeWidget.getInputWidget()).addSelectionChangeHandler(new SelectionChangeHandler() {

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                String gradeTypeSelected = ((KSRadioButtonListImpl)event.getWidget()).getSelectedItem();
                gradeMetadata.getInitialLookup().getParams().get(0).setDefaultValueList(null);
                gradeMetadata.getInitialLookup().getParams().get(0).setDefaultValueString(gradeTypeSelected);
                
                addGradeListWidget(true, !event.isUserInitiated());
            }
        });
    }

    private void addGradeListWidget(boolean enabled, boolean addInitialValueCallback) {
        if (gradeWidget != null) {
            layout.remove(gradeWidget);
        }
        
        // this callback ensures the initial grade value is selected if it should be set
        // since the values are populated async, a widget ready callback must be used
        if(addInitialValueCallback) {
            ((KSSelectItemWidgetAbstract)gradeWidget.getInputWidget()).addWidgetReadyCallback(new Callback<Widget>() {
            
                @Override
                public void exec(Widget result) {
                    gradeWidget.setValue(initialGradeValue);
                    KSDropDownImpl theWidget = (KSDropDownImpl) result;
                    theWidget.selectItem(initialGradeValue);
                }
            });
        }
        
        gradeWidget = (KSPicker) DefaultWidgetFactory.getInstance().getWidget(gradeMetadata);
        ((KSDropDown)gradeWidget.getInputWidget()).setEnabled(enabled);
        layout.add(gradeWidget);
    }

	@Override
	public void addValueChangeCallback(Callback<Data.Value> callback) {
	}

	@Override
	public void setValue(Data.Value value) {
	    
	    if(value != null && value.toString() != null) {
	        initialGradeValue = value.toString();
	    }
	    
        addGradeListWidget((initialGradeValue != null), false);
        gradeWidget.setValue(value);
	    
		// if setting an initial value that is not the blank default (i.e. populating the grade widget from a previously saved value)
		if(initialGradeValue != null) {
		    
		    // add a callback to select the grade type after the grade dropdown has been initialized
	        final String selectedGradeValue = value.toString();
		
    		Callback<Widget> afterInitCallback = new Callback<Widget>(){
                @Override
                public void exec(Widget widget) {
    
                    List<SearchResultRowInfo> gradeItems = ((SearchResultListItems) ((KSSelectItemWidgetAbstract)gradeWidget.getInputWidget()).getListItems()).getReadOnlyResults();
                    
                    // find the selected row for the grade
                    SearchResultRowInfo selectedGradeRow = null;
                    String selectedGradeTypeId = null;
                    for(SearchResultRowInfo row : gradeItems) {
                        for(SearchResultCellInfo cell : row.getCells()) {
                            if(cell.getValue().equals(selectedGradeValue)) {
                                selectedGradeRow = row;
                            }
                            
                            // get the grade type id from any row  
                            // If the right row is found, this value will contain the grade type we are looking for
                            if(cell.getKey().equals(GRADE_TYPE_ID_CELL_KEY)) {
                                selectedGradeTypeId = cell.getValue();
                            }
                            
                        }
                        
                        // once the grade row is found, break
                        if(selectedGradeRow != null) {
                            break;
                        }
                    }
                    
                    // make sure to check for null, though it would indicate bad data if true
                    if(selectedGradeRow == null) {
                        return;
                    }
                    
                    // now we can select the grade type in the grade type widget
                    ((KSSelectItemWidgetAbstract)gradeTypeWidget.getInputWidget()).selectItem(selectedGradeTypeId);
                    
                }
            };
    		
    		((KSSelectItemWidgetAbstract)gradeWidget.getInputWidget()).addWidgetReadyCallback(afterInitCallback);
		}
	}

    @Override
    public void getValue(Callback<String> doneSaveCallback) { 
    }

    @Override
    public void setValue(String id) {
    }

    @Override
    public Data.Value getValue() {
        return (gradeWidget != null ? gradeWidget.getValue() : null);
    }
}
