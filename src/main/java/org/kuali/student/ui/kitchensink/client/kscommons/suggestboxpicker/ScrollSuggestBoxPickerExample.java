package org.kuali.student.ui.kitchensink.client.kscommons.suggestboxpicker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.common.ui.client.widgets.list.testData.Color;
import org.kuali.student.common.ui.client.widgets.pagetable.AbstractTableSelectable;
import org.kuali.student.common.ui.client.widgets.pagetable.GenericTableModel;
import org.kuali.student.common.ui.client.widgets.pagetable.PagingScrollTableBuilder;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSListItemsSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.ui.kitchensink.client.kscommons.dto.ColorColumnDefinition;
import org.kuali.student.ui.kitchensink.client.kscommons.dto.ColorDTOs;
import org.kuali.student.ui.kitchensink.client.kscommons.dto.TypeColumnDefinition;
import org.kuali.student.ui.kitchensink.client.kscommons.dto.WarmthColumnDefinition;
import org.kuali.student.ui.kitchensink.client.kscommons.suggestboxpicker.KSSuggestBoxPicker;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBoxWAdvSearch;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow.SearchParameter;


import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.user.client.ui.SimplePanel;

public class ScrollSuggestBoxPickerExample extends AbstractTableSelectable<Color>{
    
    private SimplePanel panel = new SimplePanel();
    Comparator<Color> colorCMP = new Comparator<Color>(){
        @Override
        public int compare(Color c1, Color c2) {
            return c1.getColor().compareToIgnoreCase(c2.getColor());
        }
    };
    public ScrollSuggestBoxPickerExample(){

        
        KSListItemsSuggestOracle oracle = new KSListItemsSuggestOracle();
        oracle.setListItems(tableItems);
        pagingScrollTable = new PagingScrollTableBuilder<Color>().tablePixelSize(220, 100).
        columnDefinitions(createColumnDefinitions()).
        build(new GenericTableModel<Color>(new ColorDTOs().getColors()));
        KSSuggestBox sb = new KSSuggestBox(oracle);
        List<SearchParameter> params = new ArrayList<SearchParameter>();
        params.add(new SearchParameter("Color", "Color"));
        params.add(new SearchParameter("Warmth", "Warmth"));
        List<String> enumeratedValues = new ArrayList<String>();
        enumeratedValues.add("Primary");
        enumeratedValues.add("Secondary");
        enumeratedValues.add("None");
        params.add(new SearchParameter("Type", "Type", enumeratedValues));
        
        KSSuggestBoxWAdvSearch suggest = new KSSuggestBoxWAdvSearch(sb, new KSAdvancedSearchWindow(params));
        KSSuggestBoxPicker<Color> sbp = new KSSuggestBoxPicker<Color>(suggest, pagingScrollTable);
        panel.setWidget(sbp);
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
    @Override
    protected List<AbstractColumnDefinition<Color, ?>> createColumnDefinitions() {
        List<AbstractColumnDefinition<Color, ?>> columnDefs = new ArrayList<AbstractColumnDefinition<Color, ?>>();
        String colorHdr = "Color";
        columnDefs.add((AbstractColumnDefinition<Color, ?>) new ColorColumnDefinition(colorHdr));
        
        String warmthHdr = "Warmth";
        columnDefs.add((AbstractColumnDefinition<Color, ?>) new WarmthColumnDefinition(warmthHdr));
        String typeHdr = "Type";
        columnDefs.add((AbstractColumnDefinition<Color, ?>) new TypeColumnDefinition(typeHdr));
        return columnDefs;
    }
}
