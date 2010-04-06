package org.kuali.student.common.ui.client.configurable.mvc.sections;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite.StyleType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;

public class RemovableItemWithHeader extends MultiplicityItem{

	private MultiplicityHeader header;
	private FlowPanel layout = new FlowPanel();
	private FlowPanel body = new FlowPanel();
	private StyleType style;
	private String itemLabel;
    private boolean readOnly=false;
	private boolean loaded = false;
	
	
    public void isReadOnly(boolean readOnly){
        this.readOnly=readOnly;
    }

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	public RemovableItemWithHeader(StyleType style){
		this.style = style;
		this.initWidget(layout);
		
	}
	
	public RemovableItemWithHeader(StyleType style, boolean readOnly){
		this.style = style;
		this.readOnly = readOnly;
		this.initWidget(layout);
		
	}
	
	@Override
	public void clear() {
		// TODO We need a clear/redraw interface to redraw decorated widget
        loaded = false;
		
	}
	
    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

	@Override
	public void redraw() {
		//TODO replace with icon link implementation
		if (!loaded){
			layout.clear();
	    	if(style == StyleType.TOP_LEVEL){
	    		SectionTitle title = SectionTitle.generateH4Title(itemLabel);
	    		title.addStyleName("ks-form-bordered-header-title");
	    		header = new MultiplicityHeader(title, readOnly);
	    		header.setStyleName("ks-form-bordered-header");
	    		layout.setStyleName("ks-form-bordered");
	    		body.setStyleName("ks-form-bordered-body");
	    	}
	    	else if(style == StyleType.SUB_LEVEL){
	    		SectionTitle title = SectionTitle.generateH5Title(itemLabel);
	    		title.addStyleName("ks-form-course-format-activity-header-title");
	    		header = new MultiplicityHeader(title, readOnly);
	    		header.setStyleName("ks-form-course-format-activity-header");
	    		layout.setStyleName("ks-form-course-format-activity");
	    	}
	    	
	    	//This check is to disable delete of items in case of readonly mode.
	    	if(!readOnly){
	    	    header.addDeleteHandler(new ClickHandler() {
	                public void onClick(ClickEvent event) {
	                    getRemoveCallback().exec(RemovableItemWithHeader.this);
	                }
	            });
	    	}
	    	
	    	layout.add(header);
	    	body.add(this.getItemWidget());
	    	layout.add(body);
		}
		
        if (this.getItemWidget() instanceof Section){
            ((Section)this.getItemWidget()).redraw();
        }
		
	}

}
