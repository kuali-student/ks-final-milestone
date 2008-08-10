package org.kuali.student.ui.personidentity.client.view.lu;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.tables.ModelTableColumn;
import org.kuali.student.commons.ui.widgets.tables.PagingModelTable;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;

import com.google.gwt.user.client.ui.Widget;

public class CourseSearchResultTable extends PagingModelTable<GwtLuiInfo> {
    ViewMetaData metadata;
    Messages messages;
    boolean loaded = false;

    public void onLoad() {
        if (!loaded) {
            loaded = true;
            metadata = ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME);
            messages = metadata.getMessages();

            super.addColumn(new ModelTableColumn<GwtLuiInfo>() {

                Comparator<GwtLuiInfo> comparator = new Comparator<GwtLuiInfo>() {
                    public int compare(GwtLuiInfo o1, GwtLuiInfo o2) {

                        return String.CASE_INSENSITIVE_ORDER.compare(o1.getCluInfo().getCluShortName(), o2.getCluInfo().getCluShortName());
                    }
                };

                public String getColumnHeader() {
                    return messages.get("cluName");
                }

                @Override
                public Comparator<GwtLuiInfo> getColumnSortComparator() {

                    return comparator;
                }

                @Override
                public String getColumnValue(GwtLuiInfo modelObject) {
                    // TODO Garey - THIS METHOD NEEDS JAVADOCS
                    return modelObject.getCluInfo().getCluShortName();
                }

                @Override
                public Widget getColumnWidget(GwtLuiInfo modelObject) {
                    // TODO Garey - THIS METHOD NEEDS JAVADOCS
                    return null;
                }
            });

            super.addColumn(new ModelTableColumn<GwtLuiInfo>() {

                Comparator<GwtLuiInfo> comparator = new Comparator<GwtLuiInfo>() {
                    public int compare(GwtLuiInfo o1, GwtLuiInfo o2) {

                        return String.CASE_INSENSITIVE_ORDER.compare(o1.getCluInfo().getDescription(), o2.getCluInfo().getDescription());
                    }
                };

                @Override
                public String getColumnHeader() {
                    // TODO Garey - THIS METHOD NEEDS JAVADOCS
                    return messages.get("description");
                }

                @Override
                public Comparator<GwtLuiInfo> getColumnSortComparator() {
                    // TODO Garey - THIS METHOD NEEDS JAVADOCS
                    return comparator;
                }

                @Override
                public String getColumnValue(GwtLuiInfo modelObject) {
                    // TODO Garey - THIS METHOD NEEDS JAVADOCS
                    return modelObject.getCluInfo().getDescription();
                }

                @Override
                public Widget getColumnWidget(GwtLuiInfo modelObject) {
                    // TODO Garey - THIS METHOD NEEDS JAVADOCS
                    return null;
                }
            });

            super.addColumn(new ModelTableColumn<GwtLuiInfo>() {

                Comparator<GwtLuiInfo> comparator = new Comparator<GwtLuiInfo>() {
                    public int compare(GwtLuiInfo o1, GwtLuiInfo o2) {
                        String val1 = o1.getCluInfo().getAttributes().get("Grading Method");
                        String val2 = o2.getCluInfo().getAttributes().get("Grading Method");

                        return String.CASE_INSENSITIVE_ORDER.compare(val1, val2);
                    }
                };

                @Override
                public String getColumnHeader() {

                    return messages.get("gradingMethod");
                }

                @Override
                public Comparator<GwtLuiInfo> getColumnSortComparator() {

                    return comparator;
                }

                @Override
                public String getColumnValue(GwtLuiInfo modelObject) {

                    return modelObject.getCluInfo().getAttributes().get("Grading Method");
                }

                @Override
                public Widget getColumnWidget(GwtLuiInfo modelObject) {

                    return null;
                }
            });

            super.addColumn(new ModelTableColumn<GwtLuiInfo>() {

                Comparator<GwtLuiInfo> comparator = new Comparator<GwtLuiInfo>() {
                    public int compare(GwtLuiInfo o1, GwtLuiInfo o2) {
                        String val1 = o1.getCluInfo().getAttributes().get("Credits");
                        String val2 = o2.getCluInfo().getAttributes().get("Credits");

                        // Doubt this will actually work.
                        return String.CASE_INSENSITIVE_ORDER.compare(val1, val2);
                    }
                };

                @Override
                public String getColumnHeader() {

                    return messages.get("credits");
                }

                @Override
                public Comparator<GwtLuiInfo> getColumnSortComparator() {

                    return comparator;
                }

                @Override
                public String getColumnValue(GwtLuiInfo modelObject) {

                    return modelObject.getCluInfo().getAttributes().get("Credits");
                }

                @Override
                public Widget getColumnWidget(GwtLuiInfo modelObject) {

                    return null;
                }
            });
        }
        super.onLoad();
    }

}
