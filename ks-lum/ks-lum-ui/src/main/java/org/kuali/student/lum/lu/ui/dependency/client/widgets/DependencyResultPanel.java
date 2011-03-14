package org.kuali.student.lum.lu.ui.dependency.client.widgets;

import java.util.HashMap;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.search.CollapsablePanel;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class DependencyResultPanel extends FlowPanel{

	protected KSLabel headerLabel = new KSLabel();
	
	protected VerticalFieldLayout dependencySectionContainer = new VerticalFieldLayout();
	
	protected HashMap<String, VerticalFieldLayout> dependencySections = new HashMap<String, VerticalFieldLayout>();
	protected HashMap<String, DependencyTypeSection> dependencyTypeSections = new HashMap<String, DependencyTypeSection>();

	
	public static class DependencyTypeSection extends CollapsablePanel{

		VerticalFieldLayout content = new VerticalFieldLayout();
		String label; 
		
		int numItems = 0;
		
		public DependencyTypeSection(String label) {
			this.label = label;
			this.init(label, content, isOpen, true, ImagePosition.ALIGN_LEFT);
    		this.addStyleName("ks-menu-layout-rightColumn");
			content.addStyleName("KS-Indent");
		}
						
		public void finalize(){
			this.getLabel().setText(label + "(" + numItems + ")");
		}
		
		public void addDependencyItem(String label, Widget widget){
			numItems++;
			CollapsablePanel depItem = new CollapsablePanel(label, widget, false, true, ImagePosition.ALIGN_LEFT);
			content.addWidget(depItem);			
		}
	}
	
	public DependencyResultPanel(){
		this.add(dependencySectionContainer);
		dependencySectionContainer.addWidget(headerLabel);
	}
	
	public void addSection(String sectionName, String sectionTitle){
    	VerticalFieldLayout section = new VerticalFieldLayout();
    	section.setLayoutTitle(SectionTitle.generateH4Title(sectionTitle));  
    	dependencySectionContainer.addWidget(section);
    	dependencySections.put(sectionName, section);
    }
    
    /**
     * @param section
     * @param collapsableSectionName
     */
	public DependencyTypeSection addDependencyTypeSection(String sectionName, String sectionTypeName, String sectionTitle){
		String typeSectionKey = sectionName + "." + sectionTypeName;
    	DependencyTypeSection typeSection = new DependencyTypeSection(sectionTitle);
    	dependencyTypeSections.put(typeSectionKey, typeSection);
    	VerticalFieldLayout section = dependencySections.get(sectionName);
    	section.addWidget(typeSection);
    	return typeSection;
    }
	
	public DependencyTypeSection getDependencyTypeSection(String sectionName, String sectionTypeName){
		String typeSectionKey = sectionName + "." + sectionTypeName;
		return dependencyTypeSections.get(typeSectionKey);	
	}
	
    public void setHeaderTitle(String title){
    	dependencySectionContainer.setLayoutTitle(SectionTitle.generateH2Title(title));
    }
    
    /**
     * 
     */
    public void finalize(){
    	
    }
}
