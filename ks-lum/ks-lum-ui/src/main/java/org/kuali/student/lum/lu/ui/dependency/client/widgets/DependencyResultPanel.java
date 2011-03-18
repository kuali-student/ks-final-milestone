package org.kuali.student.lum.lu.ui.dependency.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.search.CollapsablePanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class DependencyResultPanel extends FlowPanel{

	protected KSLabel headerLabel = new KSLabel();
	
	protected VerticalFieldLayout dependencySectionContainer = new VerticalFieldLayout();
	
	protected HashMap<String, DependencySection> dependencySections = new HashMap<String, DependencySection>();
	protected HashMap<String, DependencyTypeSection> dependencyTypeSections = new HashMap<String, DependencyTypeSection>();

	
	public DependencyResultPanel(){
		this.add(dependencySectionContainer);
		dependencySectionContainer.addWidget(headerLabel);
		dependencySectionContainer.addStyleName("ks-dependency-results");
	}
	
	public void addSection(String sectionName, String sectionTitle){
    	DependencySection section = new DependencySection(sectionTitle);  
    	dependencySectionContainer.add(section);
    	dependencySections.put(sectionName, section);
    }
    
    /**
     * @param section
     * @param collapsableSectionName
     */
	public DependencyTypeSection addDependencyTypeSection(String sectionName, String sectionTypeName, Widget sectionHeader){
		String typeSectionKey = sectionName + "." + sectionTypeName;
    	DependencyTypeSection typeSection = new DependencyTypeSection(sectionHeader);
    	dependencyTypeSections.put(typeSectionKey, typeSection);
    	DependencySection section = dependencySections.get(sectionName);
    	section.addWidget(typeSection);
    	return typeSection;
    }
	
	public DependencyTypeSection getDependencyTypeSection(String sectionName, String sectionTypeName){
		String typeSectionKey = sectionName + "." + sectionTypeName;
		return dependencyTypeSections.get(typeSectionKey);	
	}
	
    public void setHeaderTitle(String title){
    	//TODO: This may need to be replaced with document to surface handle print/export
    	SectionTitle sectionTitle = SectionTitle.generateH2Title(title);
		dependencySectionContainer.setLayoutTitle(sectionTitle);
		dependencySectionContainer.underlineTitle(true);
    	
    	KSButton expandLabel = new KSButton("Expand All", ButtonStyle.DEFAULT_ANCHOR);				
		KSButton collapseLabel = new KSButton("Collapse All", ButtonStyle.DEFAULT_ANCHOR);

		FlowPanel expandArea = new FlowPanel();
		expandArea.add(expandLabel);
		expandArea.add(new SpanPanel(" | "));
		expandArea.add(collapseLabel);
		dependencySectionContainer.addWidget(expandArea);
		
		expandLabel.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				for (DependencySection section:dependencySections.values()){
					section.expandAll();
				}
			}		
		});		

		collapseLabel.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				for (DependencySection section:dependencySections.values()){
					section.collapseAll();
				}
			}		
		});
				
    }
    
    /**
     * Call this to finalize the dependency widgets once all data has been loaded.
     */
    public void finishLoad(){
    	//This is to update the counts for dependencies
    	for (DependencyTypeSection typeSection:dependencyTypeSections.values()){
    		typeSection.finishLoad();
    	}
    	
    	//Don't show any sections that don't have dependencies
    	for (DependencySection section:dependencySections.values()){
    		if (section.dependencyTypeSections.size() <= 0){
    			section.setVisible(false);
    		}
    	}
    }

	public static class DependencySection extends VerticalFieldLayout{
		protected FlowPanel header = new FlowPanel(); 

		List<DependencyTypeSection> dependencyTypeSections = new ArrayList<DependencyTypeSection>();
		
		public DependencySection(String title){
	    	SectionTitle sectionTitle = SectionTitle.generateH4Title(title);	    	
	    	sectionTitle.addStyleName("ks-dependency-section-title");
	    	header.add(sectionTitle);
	    	header.addStyleName("header-underline");
	    	header.addStyleName("ks-dependency-section-header");
	    	this.setTitleWidget(header);
	    	this.addStyleName("ks-dependency-section");
	    	
			KSButton expandLabel = new KSButton("Expand All", ButtonStyle.DEFAULT_ANCHOR);				
			KSButton collapseLabel = new KSButton("Collapse All", ButtonStyle.DEFAULT_ANCHOR);
			
			expandLabel.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					DependencySection.this.expandAll();
				}		
			});		

			collapseLabel.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					DependencySection.this.collapseAll();
				}		
			});		

			header.add(expandLabel);
			header.add(new SpanPanel(" | "));
			header.add(collapseLabel);
		}
				
		
		@Override
		public String addWidget(Widget widget) {
			dependencyTypeSections.add((DependencyTypeSection)widget);
			return super.addWidget(widget);
		}


		public void expandAll(){
			for (DependencyTypeSection typeSection:dependencyTypeSections){
				if (!typeSection.isOpen()){
					typeSection.open();
				}
			}
		}
		
		public void collapseAll(){
			for (DependencyTypeSection typeSection:dependencyTypeSections){
				typeSection.close();
			}			
		}
	}

	public static class DependencyTypeSection extends CollapsablePanel{

		protected FlowPanel content = new FlowPanel();
		KSLabel countLabel = new KSLabel();
		
		List<CollapsablePanel> dependencyItems = new ArrayList<CollapsablePanel>();
		
		public DependencyTypeSection(Widget label) {
			this.init(label, content, isOpen, true, ImagePosition.ALIGN_LEFT);
    		content.addStyleName("ks-dependency-type-section");
			super.linkPanel.add(countLabel);
		}
						
		public void finishLoad(){
			int numItems = dependencyItems.size();
			countLabel.setText("(" + numItems + "):");
			if (numItems <= 5){
				super.content.setVisible(true);
				super.isOpen = true;
				super.setImageState();
			}
		}
		
		public void addDependencyItem(Widget label, Widget details){
			CollapsablePanel depItem = new CollapsablePanel(label, details, false, true, ImagePosition.ALIGN_LEFT);
			content.add(depItem);
			dependencyItems.add(depItem);
		}

		public void expandAll(){
			for (CollapsablePanel depItem:dependencyItems){
				if (!depItem.isOpen()){
					depItem.open();
				}
			}
		}
		
		public void collapseAll(){
			for (CollapsablePanel depItem:dependencyItems){
				depItem.close();
			}			
		}
	}

}
