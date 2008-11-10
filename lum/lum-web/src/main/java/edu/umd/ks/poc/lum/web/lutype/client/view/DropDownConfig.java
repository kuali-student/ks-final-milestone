package edu.umd.ks.poc.lum.web.lutype.client.view;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.web.scat.client.ScatSearch;

public class DropDownConfig extends Composite{



    DecoratorPanel base = new DecoratorPanel();
    FlexTable   fTable = new FlexTable();
    TextBox     scatCode = new TextBox();
    final DialogBox dBox = new DialogBox();
    Hyperlink scatSearch = new Hyperlink("search", "search");
    boolean loaded = false;       
    
    public DropDownConfig() {
        super.initWidget(base);
    }
    
    protected void onLoad() {     
        super.onLoad();
        if(!loaded){
            loaded = true;
            
        
        
        fTable.setWidget(0, 0, new Label("SCAT Code"));
        fTable.setWidget(0, 1, scatCode);       
        fTable.setWidget(0, 2, scatSearch);
        
        
        scatSearch.addClickListener(new ClickListener(){

			
			public void onClick(Widget sender) {
				
				ScatSearch scatSearch = new ScatSearch();
				dBox.setText("Scat Table Search");
				dBox.setWidget(scatSearch);
				dBox.center();
				dBox.show();
				
			}});
        
        
        base.add(fTable);
        }
    }

	/**
	 * @return the scatCode
	 */
	public TextBox getScatCode() {
		return scatCode;
	}

	/**
	 * @param scatCode the scatCode to set
	 */
	public void setScatCode(TextBox scatCode) {
		this.scatCode = scatCode;
	}

}
