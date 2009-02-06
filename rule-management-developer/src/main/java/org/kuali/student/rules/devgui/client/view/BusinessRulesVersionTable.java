package org.kuali.student.rules.devgui.client.view;

import java.util.Comparator;

import org.kuali.student.commons.ui.widgets.tables.ModelTableColumn;
import org.kuali.student.commons.ui.widgets.tables.PagingModelTable;
import org.kuali.student.rules.devgui.client.GuiUtil;
import org.kuali.student.rules.devgui.client.model.RulesVersionInfo;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Widget;

public class BusinessRulesVersionTable extends PagingModelTable<RulesVersionInfo> {

    public static final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("HH:mm MMM d, yyyy");
    private boolean loaded = false;

    @Override
    public void onLoad() {
        if (!loaded) {
            loaded = true;
            // Rule Status
            super.addColumn(new ModelTableColumn<RulesVersionInfo>(){
                Comparator<RulesVersionInfo> comparator = new 
                Comparator<RulesVersionInfo>() {
                    public int compare(RulesVersionInfo o1, RulesVersionInfo o2) {
                        int result = 0;
                        result = o1.getStatus().compareTo(o2.getStatus());
                        return result;
                    }
                };
                public String getColumnHeader() {
                    return "Status";
                }
                public Comparator<RulesVersionInfo> getColumnSortComparator() {
                    return comparator;
                }
                public String getColumnValue(RulesVersionInfo modelObject) {
                    return modelObject.getStatus();
                }
                public Widget getColumnWidget(RulesVersionInfo modelObject) {
                    return null;
                }
            });
            // Effective Date
            super.addColumn(new ModelTableColumn<RulesVersionInfo>() {
                Comparator<RulesVersionInfo> comparator = new 
                Comparator<RulesVersionInfo>() {
                    public int compare(RulesVersionInfo o1, RulesVersionInfo o2) {
                        int result = o1.getEffectiveDate().compareTo(o2.getEffectiveDate());
                        return result;
                    }
                };
                public String getColumnHeader() {
                    return "Effective";
                }
                public Comparator<RulesVersionInfo> getColumnSortComparator() {
                    return comparator;
                }
                public String getColumnValue(RulesVersionInfo modelObject) {
                    return dateFormatter.format(modelObject.getEffectiveDate());
                }
                public Widget getColumnWidget(RulesVersionInfo modelObject) {
                    return null;
                }
            });
            // Expiration Date
            super.addColumn(new ModelTableColumn<RulesVersionInfo>() {
                Comparator<RulesVersionInfo> comparator = new 
                Comparator<RulesVersionInfo>() {
                    public int compare(RulesVersionInfo o1, RulesVersionInfo o2) {
                        int result = o1.getExpirationDate().compareTo(o2.getExpirationDate());
                        return result;
                    }
                };
                public String getColumnHeader() {
                    return "Expiration";
                }
                public Comparator<RulesVersionInfo> getColumnSortComparator() {
                    return comparator;
                }
                public String getColumnValue(RulesVersionInfo modelObject) {
                    return dateFormatter.format(modelObject.getExpirationDate());
                }
                public Widget getColumnWidget(RulesVersionInfo modelObject) {
                    return null;
                }
            });
            super.onLoad();
        }
    }
        
}
