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

package org.kuali.student.lum.common.client.lo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.configurable.mvc.CanProcessValidationResults;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * This class manages the users interactions when building/updating Learning
 * Objectives within the context of managing CLUs. It allows the user to type in
 * LO text directly or execute a search and select one or more of the returned
 * LOs.
 * 
 * Users can then re-organize LOs on the screen including altering the sequence
 * and creating sub LOs
 * 
 * @author Kuali Student Team
 * 
 */
public class LOBuilder extends VerticalSection implements HasValue<List<OutlineNode<LOPicker>>>, CanProcessValidationResults {

	private static String type;
	private static String state;
	private static String repoKey;
	private static String messageGroup;
    private static String startOfPath;
    private static String endOfPath = "loInfo/descr/plain";
    private static String middleOfPath = "loDisplayInfoList";
	HorizontalPanel searchMainPanel = new HorizontalPanel();
	KSPicker searchWindow;

	LearningObjectiveList loList;
	KSLabel instructions;
	
	private static int loListDescLength;
	
	private boolean onTheFlyValidation = true;

	protected LOBuilder() {
	}

    public LOBuilder(String luType, String luState, String luGroup, String loRepoKey, String queryPathStart, final Metadata metadata) {
		super();
		
		loListDescLength = metadata.getProperties().get("plain").getConstraints().get(0).getMaxLength();
		
		type = luType;
		state = luState;
		repoKey = loRepoKey;
		messageGroup = luGroup;
        startOfPath = queryPathStart;       

        if (metadata.getInitialLookup() != null) {
            searchWindow = new KSPicker(metadata.getInitialLookup(), metadata.getAdditionalLookups());
            searchWindow.addValuesChangeHandler(new ValueChangeHandler<List<String>>() {
                public void onValueChange(ValueChangeEvent<List<String>> event) {
                    List<String> selection = event.getValue();
                    loList.addSelectedLOs(selection);
                }
            });
            searchMainPanel.add(searchWindow);
        }
        
        Metadata descMeta = new Metadata();
        descMeta = metadata.getProperties().get("plain");

		instructions = new KSLabel(getLabel(LUUIConstants.LO_INSTRUCTIONS_KEY, descMeta));

        loList = GWT.create(LearningObjectiveList.class);
        loList.setLoInfoMaxLength(loListDescLength);

		searchMainPanel.addStyleName("KS-LOBuilder-Search-Panel");

        loList.addStyleName(LUUIConstants.STYLE_SECTION);
        loList.addStyleName(LUUIConstants.STYLE_SECTION_DIVIDER);

		instructions.addStyleName("KS-LOBuilder-Instructions");

        this.addWidget(searchMainPanel);
        this.addWidget(instructions);
        this.addSection(loList);
        
	}

	/**
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object,
	 *      boolean)
	 */
	@Override
	public void setValue(List<OutlineNode<LOPicker>> value, boolean fireEvents) {
		setValue(value);
	}

	/**
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(List<OutlineNode<LOPicker>> data) {
		loList.setValue(data);
	}

	/**
	 * @see com.google.gwt.user.client.ui.HasValue#getValue()
	 */
	@Override
	public List<OutlineNode<LOPicker>> getValue() {
		return loList.getValue();
	}

	/**
	 * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
	 */
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<OutlineNode<LOPicker>>> handler) {
		return loList.addValueChangeHandler(handler);
	}
	
    private static String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey);
    }

    private static String getLabel(String labelKey, Metadata metadata) {
        return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey, metadata);
    }

	/**
	 * @return the type
	 */
	public static String getType() {
		return type;
	}

	/**
	 * @return the state
	 */
	public static String getState() {
		return state;
	}

	public static String getRepoKey() {
		return repoKey;
	}

	/**
	 * @return the messageGroup
	 */
	public static String getMessageGroup() {
		return messageGroup;
	}

    public static class LearningObjectiveList extends VerticalSection implements HasValue<List<OutlineNode<LOPicker>>> {
		OutlineNodeModel<LOPicker> outlineModel = new OutlineNodeModel<LOPicker>();
        protected KSButton addNew;
        OutlineManager outlineComposite;
        
        private int loInfoMaxLength = 0;
		
		SelectionChangeHandler loPickerChangeHandler = new SelectionChangeHandler(){
			public void onSelectionChange(SelectionChangeEvent event) {
				fireChangeEvent();
			}			
		};
		
        public LearningObjectiveList() {
            addNew = new KSButton(getLabel(LUUIConstants.LEARNING_OBJECTIVE_ADD_LABEL_KEY), ButtonStyle.SECONDARY,
                    new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            setValue(getValue());
                            appendLO("");
                            reDraw();
                        }
                    });
			
            addNew.addStyleName("KS-LOBuilder-New");

			outlineModel.addChangeHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {
                    reDraw();
					fireChangeEvent();
				}
			});

            initEmptyLoList();
//			reDraw();
		}

		protected void initEmptyLoList(){
			List<String> list = new ArrayList<String>();
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			addSelectedLOs(list);			
		}
		
		protected void fireChangeEvent(){
			ValueChangeEvent.fire(this, outlineModel.getOutlineNodes());
		}
		
		public List<OutlineNode<LOPicker>> getValue() {
			return outlineModel.getOutlineNodes();
		}

		public void setValue(List<OutlineNode<LOPicker>> value) {
			outlineModel.clearNodes();
			outlineModel.getOutlineNodes().addAll(value);

			if (value == null || value.isEmpty()){
				initEmptyLoList();
			} else {
				//Add selection change handler to LOPickers if not set
				for (OutlineNode<LOPicker> node:value){
					LOPicker picker = node.getUserObject();
					if (!picker.hasChangeHandler()){
						picker.addSelectionChangeHandler(loPickerChangeHandler);
					}
				}
				
				reDraw();
			}
		}
		
        public void setLoInfoMaxLength(int loInfoMaxLength) {
            this.loInfoMaxLength = loInfoMaxLength;
        }

        private void appendLO(String loValue) {
			OutlineNode<LOPicker> aNode = new OutlineNode<LOPicker>();
			LOPicker newPicker = new LOPicker(messageGroup, type, state, repoKey, this.loInfoMaxLength);

			newPicker.addSelectionChangeHandler(loPickerChangeHandler);
			newPicker.setLOText(loValue);
			aNode.setUserObject(newPicker);
			aNode.setModel(outlineModel);

			outlineModel.addOutlineNode(aNode);
		}

		// add one or more description by going through existing LO box and
		// populating the empty ones
		// if not enough empty LO boxes then add new ones
		public void addSelectedLOs(List<String> loDescription) {

			List<OutlineNode<LOPicker>> existingLOs = outlineModel.getOutlineNodes();

			int ix = existingLOs.size();
			for (String strValue : loDescription) {

				boolean foundEmptyBox = false;
				
				for(int i=0;i<ix;i++)
				{
					if (existingLOs.get(i).getUserObject().getLOText().trim()
							.length() == 0) {
						existingLOs.get(i).getUserObject().setLOText(strValue);
						foundEmptyBox = true;
						i=ix;
					}
				}

				// we didn't find empty LO box so add a new one
				if (foundEmptyBox == false) {
					appendLO(strValue);
				}
			}
			reDraw();
		}

		private void reDraw() {
            if (null != outlineComposite) {
                this.removeSection(outlineComposite);
            }
            this.removeWidget(addNew); // no error if it's not currently there
            outlineComposite = new OutlineManager(startOfPath, middleOfPath, endOfPath);
            outlineComposite.setValue(outlineModel);
            this.addSection(outlineComposite);
            this.addWidget(addNew);
			outlineComposite.render();
		}

		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<OutlineNode<LOPicker>>> handler) {
			return addHandler(handler, ValueChangeEvent.getType());
		}
		
		public SelectionChangeHandler getChangeHandlerForLOPicker(){
			return loPickerChangeHandler;
		}

		@Override
		public void setValue(List<OutlineNode<LOPicker>> value,
				boolean fireEvents) {
			setValue(value);
		}
	}

    @Override
    public ErrorLevel processValidationResults(FieldDescriptor fd, List<ValidationResultInfo> results) {
        return processValidationResults(fd, results, true);
    }

    @Override
    public ErrorLevel processValidationResults(FieldDescriptor fd, List<ValidationResultInfo> results, boolean clearErrors) {
        
        ErrorLevel status = ErrorLevel.OK;
        
        for (Section section : getSections()) {
            ErrorLevel level = section.processValidationResults(results, clearErrors);
            if (level.getLevel() > status.getLevel()) {
                status = level;
            }
        }
        return status;
    }
    
    public static int getLoListDescLength() {
        return loListDescLength; 
        
    }

    @Override
    public boolean doesOnTheFlyValidation() {      
        return onTheFlyValidation;
    }

    @Override
    public void Validate(ValidateRequestEvent event, List<ValidationResultInfo> result) {

        if (event.getFieldDescriptor().hasHadFocus()) {
            Map<String, FieldElement> loFieldModelMapping = doLOFieldModelMapping();

            for (int i = 0; i < result.size(); i++) {
                ValidationResultInfo vr = result.get(i);
                FieldElement element = loFieldModelMapping.get(vr.getElement());

                if (element != null) {
                    element.clearValidationErrors();
                    element.processValidationResult(vr);
                }
            }
        }

    }
    
    private Map<String, FieldElement> doLOFieldModelMapping() {
        Map<String, FieldElement> loFieldModelMapping = new HashMap<String, FieldElement>();

        int z = 0;

        for (int i = 0; i < this.getValue().size(); i++) {
            String startPath = startOfPath + "/";
            String endPathFormatted = "/" + "loInfo/desc/formatted";
            String endPathPlain = "/" + "loInfo/desc/plain";

            this.getFields().get(i).getFieldElement().clearValidationErrors();
            
            String desc = this.getValue().get(i).getUserObject().getLOText();
            int indentLevel = this.getValue().get(i).getIndentLevel();
            List<LoCategoryInfo> categories = this.getValue().get(i).getUserObject().getLoCategories();

            if (desc != null && desc.trim().length() > 0 || indentLevel > 0 || categories != null && !categories.isEmpty()) {
                loFieldModelMapping.put(startPath + z + endPathFormatted, this.getFields().get(i).getFieldElement());
                loFieldModelMapping.put(startPath + z++ + endPathPlain, this.getFields().get(i).getFieldElement());
            }

        }        
        return loFieldModelMapping;
    }   

}
