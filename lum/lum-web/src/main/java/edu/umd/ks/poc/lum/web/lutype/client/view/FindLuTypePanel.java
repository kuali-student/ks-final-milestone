package edu.umd.ks.poc.lum.web.lutype.client.view;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.lu.dto.LuTypeInfo;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.lu.client.service.LuRpcService;

public class FindLuTypePanel extends Composite {
	VerticalPanel root = new VerticalPanel();
	private boolean loaded;
	private TextBox searchString = new TextBox();
	private Button searchButton = new Button();
    FlexTable   fTable = new FlexTable();
    FlexTable   resultsTable = new FlexTable();
    String luTypeKey;
    
    public static class LuTypeSelectEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(LuTypeSelectEvent.class, new LuTypeSelectEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuTypeSelectEvent.class);
        }
    }
    static {
        new LuTypeSelectEvent();
    }
    
	/**
	 * @return the luTypeKey
	 */
	public String getLuTypeKey() {
		return luTypeKey;
	}

	/**
	 * @param luTypeKey the luTypeKey to set
	 */
	public void setLuTypeKey(String luTypeKey) {
		this.luTypeKey = luTypeKey;
	}

	/**
	 * 
	 */
	public FindLuTypePanel() {
		super();
		super.initWidget(root);
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		if (!loaded) {
			loaded = true;

			searchButton.setText("Search");

			fTable.setWidget(0, 0, searchString);
	        fTable.setWidget(0, 1, searchButton);       
	        
	        root.add(fTable);
	        root.add(resultsTable);
			searchButton.addClickListener(new ClickListener(){

				public void onClick(Widget sender) {
					// TODO Auto-generated method stub
					LuRpcService.Util.getInstance().searchForLuTypesByDescription(searchString.getText(), new AsyncCallback<List<LuTypeInfo>>(){

						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						public void onSuccess(List<LuTypeInfo> result) {
							resultsTable.clear();
							int i = 0;
							for(final LuTypeInfo luTypeInfo:result){
								final Hyperlink link = new Hyperlink(luTypeInfo.getDescription(), "luTypeInfoId");
								link.addClickListener(new ClickListener(){
									public void onClick(Widget sender) {
										GlobalEventDispatcher.getInstance().fireEvent(LuTypeSelectEvent.class, luTypeInfo.getLuTypeKey());
									}
								});
								resultsTable.setWidget(i,0,link);
								resultsTable.setWidget(i,1,new Label(luTypeInfo.getCreateUserComment()));
								i++;
							}
							
						}
						
					});
				}
				
			});
		}

	}
}
