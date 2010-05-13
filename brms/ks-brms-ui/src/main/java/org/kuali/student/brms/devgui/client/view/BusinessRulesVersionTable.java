/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.devgui.client.view;

import java.util.Comparator;

import org.kuali.student.commons.ui.widgets.tables.ModelTableColumn;
import org.kuali.student.commons.ui.widgets.tables.PagingModelTable;
import org.kuali.student.brms.devgui.client.GuiUtil;
import org.kuali.student.brms.devgui.client.model.RulesVersionInfo;

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
