/*
 * Copyright 2008 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.ui.personidentity.client.view;

import java.util.Comparator;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.tables.ModelTableColumn;
import org.kuali.student.commons.ui.widgets.tables.PagingModelTable;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Garey don't forget to fill this in.
 * 
 * @author Kuali Student Team
 */
public class PersonSearchResultTable extends PagingModelTable<GwtPersonInfo> {

    ViewMetaData metadata;
    Messages messages;

    boolean loaded = false;

    public void onLoad() {
        if (!loaded) {
            loaded = true;
            metadata = ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME);
            messages = metadata.getMessages();
            
            // P_ID
            super.addColumn(new ModelTableColumn<GwtPersonInfo>() {
                Comparator<GwtPersonInfo> comparator = new Comparator<GwtPersonInfo>() {
                    public int compare(GwtPersonInfo o1, GwtPersonInfo o2) {
                        // Doubt this will actually work.
                        return String.CASE_INSENSITIVE_ORDER.compare(o1.getPersonId(), o2.getPersonId());
                    }
                };

                public String getColumnHeader() {
                    return messages.get("personId");
                }

                public Comparator<GwtPersonInfo> getColumnSortComparator() {
                    return comparator;
                }

                public String getColumnValue(GwtPersonInfo modelObject) {
                    return modelObject.getPersonId();
                }

                public Widget getColumnWidget(GwtPersonInfo modelObject) {
                    return null;
                }
            });

            // Last Name
            super.addColumn(new ModelTableColumn<GwtPersonInfo>() {
                Comparator<GwtPersonInfo> comparator = new Comparator<GwtPersonInfo>() {
                    public int compare(GwtPersonInfo o1, GwtPersonInfo o2) {
                        // Doubt this will actually work.
                        return String.CASE_INSENSITIVE_ORDER.compare(o1.getPreferredName().getSurname(), o2.getPreferredName().getSurname());
                    }
                };

                public String getColumnHeader() {
                    return messages.get("lastName");
                }

                public Comparator<GwtPersonInfo> getColumnSortComparator() {
                    return comparator;
                }

                public String getColumnValue(GwtPersonInfo modelObject) {
                    return modelObject.getPreferredName().getSurname();
                }

                public Widget getColumnWidget(GwtPersonInfo modelObject) {
                    return null;
                }
            });

            // First Name
            super.addColumn(new ModelTableColumn<GwtPersonInfo>() {
                Comparator<GwtPersonInfo> comparator = new Comparator<GwtPersonInfo>() {
                    public int compare(GwtPersonInfo o1, GwtPersonInfo o2) {
                        // Doubt this will actually work.
                        return String.CASE_INSENSITIVE_ORDER.compare(o1.getPreferredName().getGivenName(), o2.getPreferredName().getGivenName());
                    }
                };

                public String getColumnHeader() {
                    return messages.get("firstName");
                }

                public Comparator<GwtPersonInfo> getColumnSortComparator() {
                    return comparator;
                }

                public String getColumnValue(GwtPersonInfo modelObject) {
                    return modelObject.getPreferredName().getGivenName();
                }

                public Widget getColumnWidget(GwtPersonInfo modelObject) {
                    return null;
                }
            });
        }
        super.onLoad();
    }
}
