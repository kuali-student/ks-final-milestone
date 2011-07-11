package org.kuali.student.lum.program.client.core.edit;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * @author Igor
 */
public class CoreManagingBodiesEditConfiguration extends AbstractSectionConfiguration {

    public CoreManagingBodiesEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.MANAGE_BODIES_EDIT, ProgramProperties.get().program_menu_sections_managingBodies(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
    	HorizontalSection section;
        
        section = new HorizontalSection();
        FieldDescriptor cou = configurer.addField(section, ProgramConstants.CURRICULUM_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightUnit()));
        FieldDescriptor cod = configurer.addField(section, ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightDivision()));
        rootSection.addSection(section);
        
        section = new HorizontalSection();
        FieldDescriptor sou = configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_studentOversightUnit()));
        FieldDescriptor sod = configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_studentOversightDivision()));
        rootSection.addSection(section);
        
        final KSCheckBox unitCheckBox = new KSCheckBox("I want the \"Add to List\" button to populate all units below");//Make a message
        final KSCheckBox divisionCheckBox = new KSCheckBox("I want the \"Add to List\" button to populate all divisions below");//Make a message
        unitCheckBox.setValue(true);
        divisionCheckBox.setValue(true);
        final KSSelectedList[] unitSelects = new KSSelectedList[]{(KSSelectedList) sou.getFieldWidget()};
        final KSSelectedList[] divisionSelects = new KSSelectedList[]{(KSSelectedList) sod.getFieldWidget()};

        rootSection.addStyleName("KS-Dropdown-Short");
        
        //get a handle to the add to list button, chain in adding to all fds 
        if(cou.getFieldWidget() instanceof KSSelectedList){
        	final KSSelectedList couWidget = (KSSelectedList)cou.getFieldWidget();
        	
        	couWidget.getMainPanel().insert(unitCheckBox, 1);
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
        	
        	//Also add a hook into the advanced search
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
        
        if(cod.getFieldWidget() instanceof KSSelectedList){
        	final KSSelectedList codWidget = (KSSelectedList)cod.getFieldWidget();
        	codWidget.getMainPanel().insert(divisionCheckBox, 1);
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
