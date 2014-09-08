package org.kuali.student.lum.program.client.major.edit;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * @author Igor
 */
public class ManagingBodiesEditConfiguration extends AbstractSectionConfiguration {

    public ManagingBodiesEditConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.MANAGE_BODIES_EDIT, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_MANAGINGBODIES), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {

        HorizontalSection section;
        
        section = new HorizontalSection();
        FieldDescriptor cou = configurer.addField(section, ProgramConstants.CURRICULUM_OVERSIGHT_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_CURRICULUMOVERSIGHTUNIT));
        FieldDescriptor cod = configurer.addField(section, ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_CURRICULUMOVERSIGHTDIVISION));
        rootSection.addSection(section);
        
        section = new HorizontalSection();
        FieldDescriptor sou = configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_STUDENTOVERSIGHTUNIT));
        FieldDescriptor sod = configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_STUDENTOVERSIGHTDIVISION));
        rootSection.addSection(section);
        
        section = new HorizontalSection();
        FieldDescriptor du = configurer.addField(section, ProgramConstants.DEPLOYMENT_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_DEPLOYMENTUNIT));
        FieldDescriptor dd = configurer.addField(section, ProgramConstants.DEPLOYMENT_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_DEPLOYMENTDIVISION));
        rootSection.addSection(section);
        
        section = new HorizontalSection();
        FieldDescriptor fru = configurer.addField(section, ProgramConstants.FINANCIAL_RESOURCES_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALRESOURCESUNIT));
        FieldDescriptor frd = configurer.addField(section, ProgramConstants.FINANCIAL_RESOURCES_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALRESOURCESDIVISION));
        rootSection.addSection(section);
        
        section = new HorizontalSection();
        FieldDescriptor fcu = configurer.addField(section, ProgramConstants.FINANCIAL_CONTROL_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALCONTROLUNIT));
        FieldDescriptor fcd = configurer.addField(section, ProgramConstants.FINANCIAL_CONTROL_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALCONTROLDIVISION));
        rootSection.addSection(section);

        final KSCheckBox unitCheckBox = new KSCheckBox("I want the \"Add to List\" button to populate all units below");//Make a message
        final KSCheckBox divisionCheckBox = new KSCheckBox("I want the \"Add to List\" button to populate all divisions below");//Make a message
        unitCheckBox.setValue(true);
        divisionCheckBox.setValue(true);
        final KSSelectedList[] unitSelects = new KSSelectedList[]{(KSSelectedList) sou.getFieldWidget(),
        		(KSSelectedList) du.getFieldWidget(),(KSSelectedList) fru.getFieldWidget(),(KSSelectedList) fcu.getFieldWidget()};
        final KSSelectedList[] divisionSelects = new KSSelectedList[]{(KSSelectedList) sod.getFieldWidget(),
        		(KSSelectedList) dd.getFieldWidget(),(KSSelectedList) frd.getFieldWidget(),(KSSelectedList) fcd.getFieldWidget()};

        rootSection.addStyleName("KS-Dropdown-Short");
        
        //get a handle to the add to list button, chain in adding to all fds 
        if(cou.getFieldWidget() instanceof KSSelectedList){
        	final KSSelectedList couWidget = (KSSelectedList)cou.getFieldWidget();
        	
        	couWidget.getMainPanel().insert(unitCheckBox, 1);
        	
        	//While testing the permission this broke... I am not sure why it broke but this element
        	// returns a null when doing the getAddItemButton, this might be because with CheckPermissions
        	// set to true certain Model data is not passed thru to the clients .... need Daniel and Will's
        	// help to investigate and make certain that that is not the case
        	
        	if (couWidget.getAddItemButton() != null){
        	couWidget.getAddItemButton().addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					if(unitCheckBox.getValue()){
						KSItemLabel lastAdded = couWidget.getSelectedItems().get(couWidget.getSelectedItems().size()-1);
	                    for(KSSelectedList select:unitSelects){
	                        select.addItem(lastAdded.getKey(), lastAdded.getDisplayText());
                        }
	                }
				}
        	});
        	}
        	//Also add a hook into the advanced search
        	if (couWidget.getPicker() != null){
        	final Callback<List<SelectedResults>> originalAdvancedSearchCallback = couWidget.getPicker().getAdvancedSearchCallback();
        	
        	couWidget.getPicker().setAdvancedSearchCallback(new Callback<List<SelectedResults>>() {
                public void exec(List<SelectedResults> results) {
                	//Chain the original call and add the new item(s) to the rest of the selects
                	originalAdvancedSearchCallback.exec(results);
                    if (unitCheckBox.getValue() && results.size() > 0) {
						for (SelectedResults res : results) {
                            for(KSSelectedList select:unitSelects){
    	                        select.addItem(res.getReturnKey(), res.getDisplayKey());
                            }
    	                }
                    }
                }
            });
        	
        }
        }
        if(cod.getFieldWidget() instanceof KSSelectedList){
        	final KSSelectedList codWidget = (KSSelectedList)cod.getFieldWidget();
        	codWidget.getMainPanel().insert(divisionCheckBox, 1);
        	if (codWidget.getAddItemButton() != null){
        	codWidget.getAddItemButton().addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					if(divisionCheckBox.getValue()){
						KSItemLabel lastAdded = codWidget.getSelectedItems().get(codWidget.getSelectedItems().size()-1);
	                    for(KSSelectedList select:divisionSelects){
	                        select.addItem(lastAdded.getKey(), lastAdded.getDisplayText());
                        }
	                }
				}
        	});
        	}
        }
        

    }

}
