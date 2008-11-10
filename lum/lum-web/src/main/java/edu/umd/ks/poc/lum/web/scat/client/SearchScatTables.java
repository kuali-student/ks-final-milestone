package edu.umd.ks.poc.lum.web.scat.client;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.scat.client.service.ScatRpcService;

public class SearchScatTables extends Composite{
	VerticalPanel root = new VerticalPanel();
    TextBox scatTableDesc = null;
    Button btnSearch = null;
    FlexTable resultTable = new FlexTable();
    public static class ScatDetailsEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(ScatDetailsEvent.class, new ScatDetailsEvent().getHierarchy());
        }

        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(ScatDetailsEvent.class);
        }
    }

    static {
        new ScatDetailsEvent();
    }
    public SearchScatTables() {
        super.initWidget(root);
    }
    
	boolean loaded = false;
    protected void onLoad() {    
        super.onLoad();
        if(!loaded){
        	loaded=true;
            FlexTable fTable = new FlexTable();
            
            scatTableDesc = new TextBox();
            btnSearch = new Button("Search");
            
            btnSearch.addClickListener(new ClickListener(){                
                public void onClick(Widget sender) {
                    ScatRpcService.Util.getInstance().searchScats(scatTableDesc.getText(), new AsyncCallback<List<ScatTableInfo>>(){
						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}
						public void onSuccess(List<ScatTableInfo> result) {
							buildResults(result);
						}
                    });
                }
            });
            
            fTable.setWidget(0, 0, new Label("Table Description Search"));
            fTable.setWidget(0, 1, scatTableDesc);
            fTable.setWidget(0, 2, btnSearch);
            
            root.add(fTable);
            root.add(resultTable);
        }
    }
    
	private void buildResults(List<ScatTableInfo> scatTableInfoList) {
		// TODO Auto-generated method stub
		resultTable.clear();
		int i = 0;
		for(ScatTableInfo scatTableInfo:scatTableInfoList){
			resultTable.setWidget(i, 0, new Label(scatTableInfo.getTableDescription()));
			resultTable.setWidget(i, 1, getScatDetailsWidget(scatTableInfo));
			i++;
		}
	}

	private Widget getScatDetailsWidget(final ScatTableInfo scatTableInfo) {
		Button removeButton = new Button("Details");
		removeButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				GlobalEventDispatcher.getInstance().fireEvent(ScatDetailsEvent.class, scatTableInfo);
			}
		});
		return removeButton;
	}    
}
