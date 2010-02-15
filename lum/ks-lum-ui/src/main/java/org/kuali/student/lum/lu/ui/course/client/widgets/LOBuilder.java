/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * This class manages the users interactions when building/updating Learning Objectives within the
 * context of managing CLUs.  It allows the user to type in LO text directly or execute a search and
 * select one or more of the returned LOs.
 *
 * Users can then re-organize LOs on the screen including altering the sequence and creating sub LOs
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 *
 */
public class LOBuilder extends Composite  implements HasModelDTOValue {
    
    private static String type;
    private static String state;
    private static String messageGroup;
    
    LOSearchWindow searchWindow;   

    VerticalPanel main = new VerticalPanel();

    HorizontalPanel searchMainPanel = new HorizontalPanel();
    SimplePanel searchSpacerPanel = new SimplePanel();
    HorizontalPanel searchLinkPanel = new HorizontalPanel();


    VerticalPanel loPanel = new VerticalPanel();

    KSLabel searchLink;
    LearningObjectiveList loList;
    KSLabel instructions ;

    private static final int NUM_INITIAL_LOS = 5;



    protected LOBuilder() {
        //TODO: should this be an error?  Can we set realistic defaults?

    }

    public LOBuilder(String luType, String luState, String luGroup) {
        super();
        initWidget(main);

        type = luType;
        state = luState;
        messageGroup = luGroup;

        ClickHandler searchClickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (searchWindow == null) {
                    
                    ConfirmCancelGroup buttons = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

                        @Override
                        public void exec(ConfirmCancelEnum result) {
                            switch(result){
                                case CONFIRM:
                                    loList.addSelectedLOs(searchWindow.getLoSelections());
                                    searchWindow.hide();
                                    break;
                                case CANCEL:
                                    searchWindow.hide();
                                    break;
                            }
                        }
                    });

                    searchWindow = new LOSearchWindow(messageGroup, type, state, buttons ); 


                }
                else {
                    searchWindow.reset();
                }
                searchWindow.show();
            }
        };


        instructions = new KSLabel(getLabel(LUConstants.LO_INSTRUCTIONS_KEY));
        searchLink = new KSLabel(getLabel(LUConstants.LO_SEARCH_LINK_KEY));
        searchLink.addClickHandler(searchClickHandler);
        KSImage searchImage = Theme.INSTANCE.getCommonImages().getSearchIcon();
        searchImage.addClickHandler(searchClickHandler);

        searchLinkPanel.add(searchImage);
        searchLinkPanel.add(searchLink);
        searchSpacerPanel.add(new KSLabel(""));

        searchMainPanel.add(searchSpacerPanel);
        searchMainPanel.add(searchLinkPanel);

        loList = new LearningObjectiveList();
        loPanel.add(loList);

        searchImage.addStyleName("KS-LOBuilder-Search-Image");
        searchLink.addStyleName("KS-LOBuilder-Search-Link");
        searchSpacerPanel.addStyleName("KS-LOBuilder-Spacer-Panel");
        searchMainPanel.addStyleName("KS-LOBuilder-Search-Panel");
        loPanel.addStyleName("KS-LOBuilder-LO-Panel");
        instructions.addStyleName("KS-LOBuilder-Instructions");

        main.add(searchMainPanel);
        main.add(instructions);
        main.add(loPanel);

    }
    
    @Override
    public void setValue(ModelDTOValue modelDTOValue) {
        loList.setValue(modelDTOValue);

    }

    @Override
    public void setValue(ModelDTOValue value, boolean fireEvents) {
        // setValue(value, fireEvents); // methinks this would blow the stack :)
        setValue(value);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public ModelDTOValue getValue() {
        return loList.getValue();
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#updateModelDTO(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void updateModelDTOValue() {
        loList.updateModelDTOValue();
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {
        return loList.addValueChangeHandler(handler);
    }


    private static String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey);
    }

    public static class LearningObjectiveList extends Composite /*implements HasModelDTOValue*/{
        protected List<ModelDTO> modelDTOList = new ArrayList<ModelDTO>();
        OutlineNodeModel outlineModel = new OutlineNodeModel();
        OutlineManager outlineComposite = new OutlineManager();
        VerticalPanel mainPanel = new VerticalPanel();
        
        public LearningObjectiveList(){
            mainPanel.add(outlineComposite);
            KSLabel addnew = new KSLabel("Add new Learnging Objective");
            addnew.addStyleName("KS-LOBuilder-New");
            mainPanel.add(addnew);
            addnew.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                    setValue(getValue()); 
                    List<String> list = new ArrayList<String>();
                    list.add("");
                    addSelectedLOs(list);
                }
            });
            super.initWidget(mainPanel);
            
            outlineComposite.setModel(outlineModel);
            outlineModel.addChangeHandler(new ChangeHandler() {
              public void onChange(ChangeEvent event) {
                outlineComposite.render();
              }
            });
            
            List<String> list = new ArrayList<String>();
            list.add("");
            addSelectedLOs(list);
        }
     
        public ModelDTOValue getValue() {
            ModelDTOValue.ListType list = new ModelDTOValue.ListType();
            modelDTOList = new ArrayList<ModelDTO>();
            // get from outline model
            OutlineNode[] outlineNodes = outlineModel.toOutlineNodes(); 
            for(OutlineNode outlineNode: outlineNodes){
                ModelDTO modelDTO = new ModelDTO();
                ModelDTOValue.StringType str = new ModelDTOValue.StringType();
                str.set(((LOPicker)outlineNode.getUserObject()).getLOText());
                modelDTO.put("value", str);
                
                ModelDTOValue.ModelDTOType lo = new ModelDTOValue.ModelDTOType();
                Object possibleLo = outlineNode.getOpaque();
                if (null != possibleLo && possibleLo instanceof ModelDTO) {
                	lo.set((ModelDTO) possibleLo);
                	modelDTO.put("lo", lo);
                }

                ModelDTOValue.IntegerType intT = new ModelDTOValue.IntegerType();
                intT.set(modelDTOList.size());
                modelDTO.put("sequence",intT);
                
                intT = new ModelDTOValue.IntegerType();
                intT.set(outlineNode.getIndentLevel());
                modelDTO.put("level",intT);
                modelDTOList.add(modelDTO);
            }
            // fill the list of ModelDTO to ModelDTOValue.ModelDTOType 
            List<ModelDTOValue> valueList = new ArrayList<ModelDTOValue>();
            for(ModelDTO dto:modelDTOList){
                ModelDTOValue.ModelDTOType dtoValue = new ModelDTOValue.ModelDTOType();
                dtoValue.set(dto);
                valueList.add(dtoValue);
            }
            list.set(valueList);

            return list;
        }
        public void setValue(ModelDTOValue value) {
            ModelDTOValue.ListType list = (ModelDTOValue.ListType) value;
            modelDTOList = new ArrayList<ModelDTO>();
            // fill the ModelDTOValue.ModelDTOType to List<ModelDTO>
            // when the server hasn't been called yet, there's not list in LoModelDTO
            if (null != list) {
	            for(ModelDTOValue dto : list.get()){
	                ModelDTOValue.ModelDTOType dtoType = (ModelDTOValue.ModelDTOType)dto;
	                modelDTOList.add(dtoType.get());
	            }
            }
            reDraw();
        }

        public void addSelectedLOs(List<String> loDescription) {
            for(String strValue:loDescription){
                ModelDTO modelDTO = new ModelDTO();
                ModelDTOValue.StringType str = new ModelDTOValue.StringType();
                str.set(strValue);
                modelDTO.put("value", str);

                ModelDTOValue.IntegerType intT = new ModelDTOValue.IntegerType();
                intT.set(new Integer( modelDTOList.size()));
                modelDTO.put("sequence",intT);
                
                intT = new ModelDTOValue.IntegerType();
                intT.set(0);
                modelDTO.put("level",intT);
                
                modelDTOList.add(modelDTO);
            }
            reDraw();
        }
        private void reDraw(){
          outlineModel.clearNodes();
          for (int i = 0; i < modelDTOList.size(); i++) {
            OutlineNode aNode = new OutlineNode();
            aNode.setModel(outlineModel);
            aNode.setUserObject(new LOPicker());
            
            String strvalue = ((ModelDTOValue.StringType)modelDTOList.get(i).get("value")).get();
            int level = ((ModelDTOValue.IntegerType)modelDTOList.get(i).get("level")).get();
           // int sequence = ((ModelDTOValue.IntegerType)modelDTOList.get(i).get("sequence")).get();
            ModelDTOValue possibleLoValue = (ModelDTOValue.ModelDTOType) modelDTOList.get(i).get("lo");
            Object possibleLo = null;
            if (null != possibleLoValue) {
            	possibleLo = ((ModelDTOValue.ModelDTOType) possibleLoValue).get();
            }
            // the LO from server should be in the right order
            ((LOPicker)aNode.getUserObject()).setLOText(strvalue);
            if (null != possibleLo) {
            	aNode.setOpaque(possibleLo);
            }
            aNode.setIndentLevel(level);
            outlineModel.addOutlineNode(aNode);
          }

          outlineComposite.render();
          
        }
        public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {
//            for (HasModelDTOValue widget : modelDTOValueWidgets) {
  //              widget.addValueChangeHandler(handler);
    //        }
            return new NOOPListValueChangeHandler();
        }
        public void updateModelDTOValue() {
        	// M3 - update?
        }

//        public void setValue(ModelDTOValue value, boolean fireEvents) {
//        }
            
        private class NOOPListValueChangeHandler implements HandlerRegistration {
            public void removeHandler() {
            }
        }  
    }
}
