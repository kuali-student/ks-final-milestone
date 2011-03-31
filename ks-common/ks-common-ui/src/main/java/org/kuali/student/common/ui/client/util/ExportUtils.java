/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;

import com.google.gwt.user.client.ui.HasText;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;

public class ExportUtils {
    public static final String PDF = "PDF";
    public static final String DOC = "DOC";
    public static final String XLS = "XLS";

    public static ExportElement getExportItemDetails(ExportElement exportItem, Widget fieldWidget, boolean setFirstFieldValue) {
        String fieldValue = null;
        if (fieldWidget instanceof HasText) {
            HasText itemHasTextValue = (HasText) fieldWidget;
            fieldValue = itemHasTextValue.getText();
            
        } else if (fieldWidget instanceof KSLabel) {
            // ignore labels... as we're not interested
        } else if (fieldWidget instanceof KSSelectedList) {
            KSSelectedList selectedList = (KSSelectedList) fieldWidget;
            List<KSItemLabel> selectedItems = selectedList.getSelectedItems();
            String values = new String();
            for (int j = 0; j < selectedItems.size(); j++) {
                values = selectedItems.get(j).getDisplayText();
            }
            fieldValue = values;
        } else if (fieldWidget instanceof KSPicker) { // Similart to KSSSelectedList
            KSPicker picker = (KSPicker) fieldWidget;
            picker.getValue();
            picker.getElement();
            fieldValue = picker.getDisplayValue();      
        } else if (fieldWidget instanceof ListBox) {
            ListBox listBox = (ListBox) fieldWidget;
            fieldValue = listBox.getItemText(listBox.getSelectedIndex());
        } else {
            // logger.warn(exportItem.getFieldLabel() + " Fieldwidget not catered for : class type = " +
            // fieldWidget.getClass().getName());
        }
        if (setFirstFieldValue) {
            exportItem.setFieldValue(fieldValue);
        } else {
            exportItem.setFieldValue2(fieldValue);
        }
//        System.out.println(exportItem.getSectionName() + " : Label = " + exportItem.getFieldLabel() + " fieldValue : " + exportItem.getFieldValue() + " fieldValue2 : " + exportItem.getFieldValue2());
        return exportItem;
    }

    /**
     * This method gets the current controller based on the widget that was passed to it.
     * 
     * @param theWidget
     * @return currentController
     */
    public static Controller getController(Widget theWidget) {
        // TODO Nina - This can't be the correct way of getting handle to Controller, isn't there a better way??
        if (theWidget != null) {

            if (theWidget instanceof Controller) {
                Controller controller = (Controller) theWidget;
                return controller;
            } else {
                return getController(theWidget.getParent());
            }
        } else {
            return null;
        }
    }

    public static void handleExportClickEvent(Controller currentController, String format) {
        ArrayList<ExportElement> exportElements = new ArrayList<ExportElement>();
//        System.out.println("currentController is /:" + currentController.getClass().getName());
        exportElements = currentController.getExportElementsFromView();
        if (exportElements != null && exportElements.size() > 0) {
            currentController.doReportExport(exportElements, format);
        }
    }

    public static ArrayList<ExportElement> getDetailsForWidget(Widget currentViewWidget, ArrayList<ExportElement> exportElements, String viewName, String sectionName) {
        if (currentViewWidget instanceof Section) {
            Section widgetHasFields = (Section) currentViewWidget;
            List<FieldDescriptor> widgetFields = widgetHasFields.getFields();
            for (FieldDescriptor field : widgetFields) {
                ExportElement exportItem = new ExportElement();
                exportItem.setSectionName(sectionName);
                exportItem.setViewName(viewName);
                exportItem.setFieldLabel(field.getFieldLabel());
                Widget fieldWidget = field.getFieldElement().getFieldWidget();

                exportElements.add(getExportItemDetails(exportItem, fieldWidget, true));
            }
        } else if (currentViewWidget instanceof KSListPanel) {
            KSListPanel ksListPanelWidget = (KSListPanel) currentViewWidget;
            WidgetCollection children = ksListPanelWidget.getChildren();
            for (int i = 0; i < children.size(); i++) {
                Widget child = children.get(i);

                ExportElement exportItem = new ExportElement();
                exportItem.setSectionName("Subsection");
                exportItem.setViewName("Subsection");
                exportItem.setFieldLabel("Label");
                exportElements.add(getExportItemDetails(exportItem, child, true));
            }
            
        }
        return exportElements;
    }

    private static String getViewName(Widget currentViewWidget) {
        if (currentViewWidget instanceof View) {
            View currentView = (View) currentViewWidget;
            return currentView.getName();
        }
        return null;
    }

    public static ArrayList<ExportElement> getExportElementsFromView(Widget currentViewWidget, ArrayList<ExportElement> exportElements, String viewName, String sectionName) {
        if (exportElements == null) {
            exportElements = new ArrayList<ExportElement>();
        }
        if (currentViewWidget instanceof VerticalSectionView) {
            Section widgetHasFields = (Section) currentViewWidget;
            List<FieldDescriptor> widgetFields = widgetHasFields.getFields();
            for (FieldDescriptor field : widgetFields) {
                ExportElement exportItem = new ExportElement();
                exportItem.setSectionName(sectionName);
                exportItem.setViewName(viewName);
                exportItem.setFieldLabel(field.getFieldLabel());
                Widget fieldWidget = field.getFieldElement().getFieldWidget();

                exportElements.add(getExportItemDetails(exportItem, fieldWidget,true));
            }
//        } else { // Debugging
//            System.out.println("ExportUtils.getExportElementsFromView is not implemented for your View, either implement it here or do " + "not call the ExportUtils.getExportElementsFromView but implement it directly on your view");
        }
        return exportElements;
    }

}
