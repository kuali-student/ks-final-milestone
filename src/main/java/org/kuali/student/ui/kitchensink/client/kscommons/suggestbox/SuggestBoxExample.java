package org.kuali.student.ui.kitchensink.client.kscommons.suggestbox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.common.ui.client.widgets.list.testData.Color;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSListItemsSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;


public class SuggestBoxExample extends Composite{
    
    private SimplePanel panel = new SimplePanel();
    public SuggestBoxExample(){
        colors.add(new Color("1", "Blue", "Cool", "Primary"));
        colors.add(new Color("2", "Red", "Warm", "Primary"));
        colors.add(new Color("3", "Orange", "Warm", "Secondary"));
        colors.add(new Color("4", "Yellow", "Warm", "Primary"));
        colors.add(new Color("5", "Green", "Cool", "Secondary"));
        colors.add(new Color("6", "Purple", "Cool", "Secondary"));
        colors.add(new Color("7", "Black", "Neutral", "None"));
        tableItems.setModel(colors);
        tableItems.setComparator(new Comparator<Color>(){

            @Override
            public int compare(Color c1, Color c2) {
                return c1.getColor().compareToIgnoreCase(c2.getColor());
            }
        });
        
        KSListItemsSuggestOracle oracle = new KSListItemsSuggestOracle();
        oracle.setListItems(tableItems);
        KSSuggestBox sb = new KSSuggestBox(oracle);
        panel.setWidget(sb);
        this.initWidget(panel);
    }
    
    private Model<Color> colors = new Model<Color>();
    
    private ModelListItems<Color> tableItems = new ModelListItems<Color>(){
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
