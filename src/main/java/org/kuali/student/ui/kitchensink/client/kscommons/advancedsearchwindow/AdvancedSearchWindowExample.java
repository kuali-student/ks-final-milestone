package org.kuali.student.ui.kitchensink.client.kscommons.advancedsearchwindow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.common.ui.client.widgets.list.testData.Color;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSListItemsSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBoxPicker;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBoxWAdvSearch;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow.SearchParameter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdvancedSearchWindowExample extends Composite{
    
    private VerticalPanel panel = new VerticalPanel();
    private KSAdvancedSearchWindow searchWindow;
    private KSLabel result = new KSLabel();
    
    public AdvancedSearchWindowExample(){
        colors.add(new Color("1", "Blue", "Cool", "Primary"));
        colors.add(new Color("2", "Red", "Warm", "Primary"));
        colors.add(new Color("3", "Orange", "Warm", "Secondary"));
        colors.add(new Color("4", "Yellow", "Warm", "Primary"));
        colors.add(new Color("5", "Green", "Cool", "Secondary"));
        colors.add(new Color("6", "Purple", "Cool", "Secondary"));
        colors.add(new Color("7", "Black", "Neutral", "None"));
        dataItems.setModel(colors);
        List<SearchParameter> params = new ArrayList<SearchParameter>();
        params.add(new SearchParameter("Color", "Color"));
        params.add(new SearchParameter("Warmth", "Warmth"));
        List<String> enumeratedValues = new ArrayList<String>();
        enumeratedValues.add("Primary");
        enumeratedValues.add("Secondary");
        enumeratedValues.add("None");
        params.add(new SearchParameter("Type", "Type", enumeratedValues));
        searchWindow = new KSAdvancedSearchWindow(params);
        searchWindow.addConfirmHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                searchWindow.hide();
                String resultString = "You Selected: ";
                for(String id: searchWindow.getSelectedIds()){
                    resultString = resultString + "\n" + dataItems.getItemText(id);
                    
                }
                result.setText(resultString);
            }
        });
        KSButton showSearch = new KSButton("Show Search", new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                searchWindow.show();
                
            }
        });
        panel.add(showSearch);
        panel.add(result);
        this.initWidget(panel);
    }
    
    private Model<Color> colors = new Model<Color>();
    
    private ModelListItems<Color> dataItems = new ModelListItems<Color>(){
        @Override
        public List<String> getAttrKeys() {
            List<String> attributes = new ArrayList<String>();
            attributes.add("Color");
            attributes.add("Warmth");
            attributes.add("Type");
            return attributes;
        }

        @Override
        public String getItemAttribute(String id, String attrkey) {
            String value = null;
            for(Color c: colors.getValues()){
                if(c.getId().equals(id)){
                    if(attrkey.equals("Color")){
                        value = c.getColor();
                    }
                    else if(attrkey.equals("Warmth")){
                        value = c.getWarmth();
                    }
                    else if(attrkey.equals("Type")){
                        value = c.getType();
                    }
                    break;
                }
            }
            return value;
        }

        @Override
        public String getItemText(String id) {
            String value = null;
            for(Color c: colors.getValues()){
                if(c.getId().equals(id)){
                    value = c.getColor();
                    break;
                }
            }
            return value;
        }
    };
    
}
