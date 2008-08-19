/*
 * Copyright 2008 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.ui.personidentity.client.view.lu;

import java.util.Comparator;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.tables.ModelTableColumn;
import org.kuali.student.commons.ui.widgets.tables.PagingModelTable;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Garey don't forget to fill this in.
 * 
 * @author Kuali Student Team
 */
public class CourseScheduleTable extends PagingModelTable<GwtLuiInfo> {

    ViewMetaData metadata;
    Messages messages;

    boolean loaded = false;
    DateTimeFormat df = DateTimeFormat.getShortDateFormat();

    public void onLoad() {
        if (!loaded) {
            loaded = true;
            metadata = ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME);
            messages = metadata.getMessages();
            super.showPageBar(false);
            // Course Title
            super.addColumn(new ModelTableColumn<GwtLuiInfo>() {
                Comparator<GwtLuiInfo> comparator = new Comparator<GwtLuiInfo>() {
                    public int compare(GwtLuiInfo o1, GwtLuiInfo o2) {
                        // Doubt this will actually work.
                        return String.CASE_INSENSITIVE_ORDER.compare(o1.getCluInfo().getCluShortName() + o1.getLuiCode(), 
                        		o2.getCluInfo().getCluShortName() + o1.getLuiCode());
                    }
                };

                public String getColumnHeader() {
                    return messages.get("title");
                }

                public Comparator<GwtLuiInfo> getColumnSortComparator() {
                    return comparator;
                }

                public String getColumnValue(GwtLuiInfo modelObject) {
                    return modelObject.getCluInfo().getCluShortName() + " - " + modelObject.getLuiCode();
                }

                public Widget getColumnWidget(GwtLuiInfo modelObject) {
                    return null;
                }
            });
            
            //full name
            super.addColumn(new ModelTableColumn<GwtLuiInfo>() {
                Comparator<GwtLuiInfo> comparator = new Comparator<GwtLuiInfo>() {
                    public int compare(GwtLuiInfo o1, GwtLuiInfo o2) {
                        // Doubt this will actually work.
                        return String.CASE_INSENSITIVE_ORDER.compare(o1.getCluInfo().getCluLongName(), 
                        		o2.getCluInfo().getCluLongName());
                    }
                };

                public String getColumnHeader() {
                    return messages.get("fullTitle");
                }

                public Comparator<GwtLuiInfo> getColumnSortComparator() {
                    return comparator;
                }

                public String getColumnValue(GwtLuiInfo modelObject) {
                    return modelObject.getCluInfo().getCluLongName();
                }

                public Widget getColumnWidget(GwtLuiInfo modelObject) {
                    return null;
                }
            });
            
            //Credits
            super.addColumn(new ModelTableColumn<GwtLuiInfo>() {
                Comparator<GwtLuiInfo> comparator = new Comparator<GwtLuiInfo>() {
                    public int compare(GwtLuiInfo o1, GwtLuiInfo o2) {
                        // Doubt this will actually work.
                        return String.CASE_INSENSITIVE_ORDER.compare(o1.getCluInfo().getAttributes().get("Credits"), 
                        		o2.getCluInfo().getAttributes().get("Credits"));
                    }
                };

                public String getColumnHeader() {
                    return messages.get("credits");
                }

                public Comparator<GwtLuiInfo> getColumnSortComparator() {
                    return comparator;
                }

                public String getColumnValue(GwtLuiInfo modelObject) {
                    return modelObject.getCluInfo().getAttributes().get("Credits");
                }

                public Widget getColumnWidget(GwtLuiInfo modelObject) {
                    return null;
                }
            });
            
            //Days and Times - this information does not exist, faked
            super.addColumn(new ModelTableColumn<GwtLuiInfo>() {
                Comparator<GwtLuiInfo> comparator = new Comparator<GwtLuiInfo>() {
                    public int compare(GwtLuiInfo o1, GwtLuiInfo o2) {
                        // Doubt this will actually work.
                        return 0;
                    }
                };

                public String getColumnHeader() {
                    return messages.get("daysTimes");
                }

                public Comparator<GwtLuiInfo> getColumnSortComparator() {
                    return comparator;
                }

                public String getColumnValue(GwtLuiInfo modelObject) {
                    return "DD 0:00-1:00\nDD 0:00-1:00";
                }

                public Widget getColumnWidget(GwtLuiInfo modelObject) {
                    return null;
                }
            });
            
            //Instructor - this information does not exist, faked
            super.addColumn(new ModelTableColumn<GwtLuiInfo>() {
                Comparator<GwtLuiInfo> comparator = new Comparator<GwtLuiInfo>() {
                    public int compare(GwtLuiInfo o1, GwtLuiInfo o2) {
                        // Doubt this will actually work.
                        return 0;
                    }
                };

                public String getColumnHeader() {
                    return messages.get("instructor");
                }

                public Comparator<GwtLuiInfo> getColumnSortComparator() {
                    return comparator;
                }

                public String getColumnValue(GwtLuiInfo modelObject) {
                    return "";
                }

                public Widget getColumnWidget(GwtLuiInfo modelObject) {
                    return null;
                }
            });
            
            //startDate
            super.addColumn(new ModelTableColumn<GwtLuiInfo>() {
                Comparator<GwtLuiInfo> comparator = new Comparator<GwtLuiInfo>() {
                    public int compare(GwtLuiInfo o1, GwtLuiInfo o2) {
                        // Doubt this will actually work.
                        return o1.getCluInfo().getEffectiveStartDate().compareTo(o2.getCluInfo().getEffectiveStartDate());
                    }
                };

                public String getColumnHeader() {
                    return messages.get("startDate");
                }

                public Comparator<GwtLuiInfo> getColumnSortComparator() {
                    return comparator;
                }

                public String getColumnValue(GwtLuiInfo modelObject) {
                    return df.format(modelObject.getCluInfo().getEffectiveStartDate());
                }

                public Widget getColumnWidget(GwtLuiInfo modelObject) {
                    return null;
                }
            });
            
            //endDate
            super.addColumn(new ModelTableColumn<GwtLuiInfo>() {
                Comparator<GwtLuiInfo> comparator = new Comparator<GwtLuiInfo>() {
                    public int compare(GwtLuiInfo o1, GwtLuiInfo o2) {
                        // Doubt this will actually work.
                        return o1.getCluInfo().getEffectiveEndDate().compareTo(o2.getCluInfo().getEffectiveEndDate());
                    }
                };

                public String getColumnHeader() {
                    return messages.get("EndDate");
                }

                public Comparator<GwtLuiInfo> getColumnSortComparator() {
                    return comparator;
                }

                public String getColumnValue(GwtLuiInfo modelObject) {
                    return df.format(modelObject.getCluInfo().getEffectiveEndDate());
                }

                public Widget getColumnWidget(GwtLuiInfo modelObject) {
                    return null;
                }
            });


        }
        super.onLoad();
    }
}