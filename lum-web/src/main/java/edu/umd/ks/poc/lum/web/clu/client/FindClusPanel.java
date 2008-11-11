package edu.umd.ks.poc.lum.web.clu.client;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.lu.dto.CluDisplay;
import edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuTypeInfo;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.lu.client.service.LuRpcService;
import edu.umd.ks.poc.lum.web.lu.server.gwt.LuRpcServiceGwt;

public class FindClusPanel extends Composite {
    boolean loaded = false;
    
    VerticalPanel root = new VerticalPanel();
    ListBox       luTypes = null;
    FlexTable cluTable = new FlexTable();
    

    public static class CluDetailsEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(CluDetailsEvent.class, new CluDetailsEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(CluDetailsEvent.class);
        }
    }
    
    public static class LuTypesResponseEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(LuTypesResponseEvent.class, new LuTypesResponseEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuTypesResponseEvent.class);
        }
    }
    public static class LuTypeSelectEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(LuTypeSelectEvent.class, new LuTypeSelectEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuTypeSelectEvent.class);
        }
    }
    
    static {
        new LuTypesResponseEvent();
        new LuTypeSelectEvent();
        new CluDetailsEvent();
    }
    
    public FindClusPanel() {
        super();
        initWidget(root);
    }


    protected void onLoad() {
        super.onLoad();
        
        if(!loaded){            
            loaded = true;
            luTypes = new ListBox();
            
            luTypes.addChangeListener(new ChangeListener(){
				public void onChange(Widget sender) {
					GlobalEventDispatcher.getInstance().fireEvent(LuTypeSelectEvent.class, luTypes.getValue(luTypes.getSelectedIndex()));
				}
            }); 
            root.add(luTypes);
            root.add(cluTable);
            addListeners();
            
            try {
                LuRpcService.Util.getInstance().findLuTypes(new AsyncCallback<List<LuTypeInfo>>() {

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(List<LuTypeInfo> result) {
                    	GlobalEventDispatcher.getInstance().fireEvent(LuTypesResponseEvent.class, result);
                    }
                });
            } catch (SerializableException e) {
                e.printStackTrace();
            }
        }
    }
    protected void addListeners(){
        GlobalEventDispatcher.getInstance().addListener(LuTypesResponseEvent.class, new MVCEventListener() {
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                final List<LuTypeInfo> infoList = (List<LuTypeInfo>)data;
                luTypes.clear();
                luTypes.addItem("Please Select");
                luTypes.setSelectedIndex(0);
                for(LuTypeInfo info : infoList){                    
                    luTypes.addItem(info.getDescription(), info.getLuTypeKey());
                }

            }
        });
        GlobalEventDispatcher.getInstance().addListener(LuTypeSelectEvent.class, new MVCEventListener() {
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
            	//Load the Lu Attribute Types and attach a panel. 
            	try {
					LuRpcServiceGwt.Util.getInstance().findClusForLuType((String)data, new AsyncCallback<List<CluDisplay>>(){

						public void onFailure(Throwable caught) {

						}

						public void onSuccess(List<CluDisplay> result) {
							cluTable.clear();
							int i = 0;
							for(CluDisplay clu:result){
								cluTable.setWidget(i,0,getCluLink(clu));
								i++;
							}
							
						}
					});
				} catch (SerializableException e) {
					e.printStackTrace();
				}
            }
        });
    }
	private Widget getCluLink(final CluDisplay clu) {
		Hyperlink link = new Hyperlink(clu.getCluShortName(),"clu");
		link.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				CluWorkflowComposite cwc = new CluWorkflowComposite();
				cwc.setCluId(clu.getCluId());
				GlobalEventDispatcher.getInstance().fireEvent(CluDetailsEvent.class, cwc);
			}
		});
		return link;
	}
}
