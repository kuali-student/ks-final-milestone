package edu.umd.ks.poc.lum.web.scat.client;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.widgets.EditLabel;

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

import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.web.scat.client.service.ScatRpcService;

public class ScatDetails extends Composite{

    VerticalPanel root = new VerticalPanel();
    
    EditLabel       scatTableDesc = new EditLabel();
    
    boolean loaded = false;
    
    ScatTableInfo scatTable = null;
    
    FlexTable details = new FlexTable();
    
    TextBox tbxCode = new TextBox();
    TextBox tbxShortName = new TextBox();
    TextBox tbxLongName = new TextBox();
    
    public ScatDetails(ScatTableInfo scatTable) {
    	this.scatTable=scatTable;
    	super.initWidget(root);
    }
    
    protected void onLoad() {
        super.onLoad();

        if (!loaded) {
            loaded = true;
            root.add(new Label("Edit Scat table: "+scatTable.getTableDescription()));
            root.add(getAddScatWidget(this.scatTable.getTableId()));
            root.add(details);
            redrawDetails();
            
        }
    }
    
    private void redrawDetails(){
    	ScatRpcService.Util.getInstance().findScats(this.scatTable.getTableId(), new AsyncCallback<List<ScatInfo>>(){
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onSuccess(List<ScatInfo> result) {
				details.clear();
				int i = 0;
				for(ScatInfo scatInfo:result){
					details.setWidget(i, 0, new Label(scatInfo.getCode()));
					details.setWidget(i, 1, new Label(scatInfo.getShortDesc()));
					details.setWidget(i, 2, new Label(scatInfo.getLongDesc()));
					details.setWidget(i, 3, getRemoveScatWidget(scatInfo.getId()));
					i++;
				}

			}

    		
    	});
    }
    
	private Widget getRemoveScatWidget(final String id) {
		Button removeButton = new Button("Remove");
		removeButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				List<String> removeList = new ArrayList<String>();
				removeList.add(id);
				ScatRpcService.Util.getInstance().removeScatCodesFromScatTable(scatTable.getTableId(), removeList, new AsyncCallback<Integer>(){
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
					public void onSuccess(Integer result) {
						redrawDetails();
					}
				});
			}
		});
		return removeButton;
	}
	
	private Widget getAddScatWidget(final String id){
		FlexTable addScatTable = new FlexTable();
		
		addScatTable.setWidget(0, 0, new Label("Code:"));
		addScatTable.setWidget(0, 1, new Label("Short Desc:"));
		addScatTable.setWidget(0, 2, new Label("Long Desc:"));
		addScatTable.setWidget(1, 0, tbxCode);
		addScatTable.setWidget(1, 1, tbxShortName);
		addScatTable.setWidget(1, 2, tbxLongName);
		Button btnAddScat = new Button("Add");
		btnAddScat.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				List<ScatInfo> scatInfoList = new ArrayList<ScatInfo>();
				ScatInfo scat = new ScatInfo();
				scat.setCode(tbxCode.getText());
				scat.setShortDesc(tbxShortName.getText());
				scat.setLongDesc(tbxLongName.getText());
				scatInfoList.add(scat);
				ScatRpcService.Util.getInstance().addScatCodesToScatTable(id, scatInfoList, new AsyncCallback<Integer>(){
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					public void onSuccess(Integer result) {
						tbxCode.setText("");
						tbxShortName.setText("");
						tbxLongName.setText("");
						redrawDetails();
					}
					
				});
			}
		});
		addScatTable.setWidget(1, 3, btnAddScat);
		return addScatTable;
	}

}
