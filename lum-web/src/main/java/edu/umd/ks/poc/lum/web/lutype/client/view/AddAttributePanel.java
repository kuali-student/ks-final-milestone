package edu.umd.ks.poc.lum.web.lutype.client.view;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuTypeInfo;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.lu.client.service.LuRpcService;

public class AddAttributePanel extends Composite {

    String  luComment = null;
    String  luDesc = null;
    
    LuTypeInfo currLuType;
    
    VerticalPanel    root = new VerticalPanel();
    public AddAttributePanel(String luTypeDesc, String luTypeComment) {
        super.initWidget(root);
        this.luComment = luTypeComment;
        this.luDesc = luTypeDesc;
    }
    FlexTable attributes = null;
    HorizontalPanel top       = null;
    FlexTable       fTblTop = null;
    
    Label luTypeComment    = new Label();
    Label luTypeDesc    = new Label();
    AttributeCreator attributeCreator = new AttributeCreator();
    //Button  save          = null;
    
    boolean loaded = false;
    
    public static class AttributeCreateEvent extends MVCEvent {    	
        static {
            EventTypeRegistry.register(AttributeCreateEvent.class, new AttributeCreateEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(AttributeCreateEvent.class);
        }
    }
    static {
        new AttributeCreateEvent(); 
    }
    public static class AttributeRemoveEvent extends MVCEvent {    	
        static {
            EventTypeRegistry.register(AttributeRemoveEvent.class, new AttributeRemoveEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(AttributeRemoveEvent.class);
        }
    }
    static {
        new AttributeRemoveEvent(); 
    }
    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    @Override
    protected void onLoad() {
        super.onLoad();
        
        if(!loaded){
            loaded = true;

            attributes = new FlexTable();
            top  = new HorizontalPanel();
            fTblTop = new FlexTable();
            
            luTypeComment = new Label();
            luTypeDesc = new Label();
            
            luTypeComment.setText(this.luComment);
            luTypeDesc.setText(this.luDesc);
           
            
            fTblTop.setWidget(0, 0,new Label( "Lu Type Name:"));
            fTblTop.setWidget(0, 1,luTypeDesc);
            fTblTop.setWidget(1, 0,new Label( "Lu Type Description:"));
            fTblTop.setWidget(1, 1,luTypeComment);
            
            top.add(fTblTop);
            
            root.add(top);
            root.add(new HTML("<HR/>"));
            root.add(attributeCreator);
            root.add(new HTML("<HR/>"));
            root.add(attributes);
            getLuAttrTypesFromLuTypeId(currLuType.getLuTypeKey());
            
            
            
        }
        
    }

	public void setCurrLuType(LuTypeInfo currLuType) {
		 this.currLuType = currLuType;
	     this.luComment = currLuType.getUpdateUserComment();
	     this.luDesc = currLuType.getDescription();
		 this.attributeCreator.luTypeId = currLuType.getLuTypeKey();
	}

	private void getLuAttrTypesFromLuTypeId(String luTypeId) {

		LuRpcService.Util.getInstance().findLuAttributeTypesForLuTypeId(
				luTypeId, new AsyncCallback<List<LuAttributeTypeInfo>>() {

					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					public void onSuccess(List<LuAttributeTypeInfo> result) {
						attributes.clear();
						attributes.setWidget(0, 0, new HTML("<b>Name</b>"));
						attributes.setWidget(0, 1, new HTML("<b>Description</b>"));
						attributes.setWidget(0, 2, new HTML("<b>Action</b>"));
						int i=1;
						for (final LuAttributeTypeInfo attrType : result) {
							attributes.setWidget(i, 0, new Label(attrType.getName()));
							attributes.setWidget(i, 1, new Label(attrType.getDataType()));
							Hyperlink link = new Hyperlink();
							link.setText("remove");
							link.addClickListener(new ClickListener(){
								public void onClick(Widget sender) {
									//send a remove event
									GlobalEventDispatcher.getInstance().fireEvent(
											AttributeRemoveEvent.class, attrType.getId());
								}
							});
							attributes.setWidget(i, 2, link);
							i++;
						}
						
					}

				});

	}

	public void refresh() {
		getLuAttrTypesFromLuTypeId(currLuType.getLuTypeKey());
		this.luTypeDesc.setText(this.luDesc);
		this.luTypeComment.setText(this.luComment);
		attributeCreator.refresh();
	}
    
    
}
