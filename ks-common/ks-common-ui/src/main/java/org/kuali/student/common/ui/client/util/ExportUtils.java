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

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;

import com.google.gwt.user.client.ui.HasText;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ExportUtils {
    // TODO Nina - Can't run logger in GWT as it looks for the source code of log4j???
//    final static Logger logger = Logger.getLogger(ExportUtils.class);
    public static final String PDF = "PDF";
    public static final String DOC = "DOC";
    public static final String XLS = "XLS";

    private static ExportElement getExportItemDetails(ExportElement exportItem, Widget fieldWidget) {
        if (fieldWidget instanceof KSLabel) {
            // ignore labels... as we're not interested
        } else if (fieldWidget instanceof HasText) {
            HasText itemHasTextValue = (HasText) fieldWidget;
            exportItem.setFieldValue(itemHasTextValue.getText());
            //                        
        } else if (fieldWidget instanceof KSSelectedList) {
            KSSelectedList selectedList = (KSSelectedList) fieldWidget;
            List<KSItemLabel> selectedItems = selectedList.getSelectedItems();
            String values = new String();
            for (int j = 0; j < selectedItems.size(); j++) {
                // values = values + selectedItems.get(i).getValue();
                values = selectedItems.get(j).getDisplayText();
            }
            exportItem.setFieldValue(values);
            //
        } else if (fieldWidget instanceof KSPicker) { // Similart to KSSSelectedList
            KSPicker picker = (KSPicker) fieldWidget;
            exportItem.setFieldValue(picker.getDisplayValue());
            //                        
        } else if (fieldWidget instanceof ListBox) {
            ListBox listBox = (ListBox) fieldWidget;
            exportItem.setFieldValue(listBox.getItemText(listBox.getSelectedIndex()));
        } else {
//            logger.warn(exportItem.getFieldLabel() + " Fieldwidget not catered for : class type = " + fieldWidget.getClass().getName());
        }
//        logger.warn(exportItem.getSectionName() + " : Label = " + exportItem.getFieldLabel() + " fieldValue : " + exportItem.getFieldValue());
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

    public static void handleExportClickEvent(Controller currentController) {
//        logger.info("ExportUtils.handleExportClickEvent onClick on Jasper Print Button...");
        View currentView = currentController.getCurrentView();

        ArrayList<ExportElement> exportElements = new ArrayList<ExportElement>();
//        logger.debug("currentController is /:" + currentController.getClass().getName());
        exportElements = currentController.getExportElementsFromView();
        if (exportElements != null) {
//            logger.debug("ExportElement array.xize = " + exportElements.size());

        } else {
//            logger.debug("ExportElement array.xize = null");
        }
        currentController.doReportExport(exportElements);
    }

    private static ArrayList<ExportElement> getDetailsForWidget(Widget currentViewWidget, ArrayList<ExportElement> exportElements, String viewName, String sectionName) {
        if (currentViewWidget instanceof Section) {
            Section widgetHasFields = (Section) currentViewWidget;
            List<FieldDescriptor> widgetFields = widgetHasFields.getFields();
            for (FieldDescriptor field : widgetFields) {
                ExportElement exportItem = new ExportElement();
                exportItem.setSectionName(sectionName);
                exportItem.setViewName(viewName);
                exportItem.setFieldLabel(field.getFieldLabel());
                Widget fieldWidget = field.getFieldElement().getFieldWidget();

                exportElements.add(getExportItemDetails(exportItem, fieldWidget));
            }
            // cause a problem on the serverside for jasper not sure why
            // List<Section> subSections = widgetHasFields.getSections();
            // for (Section section : subSections) {
            // ExportElement exportItem = new ExportElement();
            // if (section instanceof Widget) {
            // Widget sectionWidget = (Widget) section;
            // exportItem.setViewName(getViewName(sectionWidget));
            // }
            // System.out.println("Section : " + section.getClass().getName());
            // List<ExportElement> subList = getDetailsForWidget((Widget) section, exportElements, exportItem.getViewName(),
            // sectionName);
            // exportItem.setSubset(subList);
            // exportElements.add(exportItem);
            // }
            //
            // NINA not working at all on client side something is null
            // FieldLayout sectionLayout = widgetHasFields.getLayout();
            // LayoutController layoutController = widgetHasFields.getLayoutController();
            // Widget currentLayoutControllerView = (Widget) layoutController.getCurrentView();
            // ExportElement exportItem = new ExportElement();
            // if (currentLayoutControllerView instanceof Widget) {
            // Widget sectionWidget = (Widget) currentLayoutControllerView;
            // exportItem.setViewName(getViewName(sectionWidget));
            // }
            // System.out.println("Section : " + currentLayoutControllerView.getClass().getName());
            //
            // List<ExportElement> subList = getDetailsForWidget((Widget) currentLayoutControllerView, exportElements,
            // exportItem.getViewName(), sectionName);
            // exportItem.setSubset(subList);
            // exportElements.add(exportItem);

        } else {
            System.out.println("what to do if it's not a section...");
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

                exportElements.add(getExportItemDetails(exportItem, fieldWidget));
            }
            // not working yet...
            // List<Section> subSections = widgetHasFields.getSections();
            // for (Section section : subSections) {
            // ExportElement exportItem = new ExportElement();
            // if (section instanceof Widget) {
            // Widget sectionWidget = (Widget) section;
            // exportItem.setViewName(getViewName(sectionWidget));
            // }
            // System.out.println("Section : " + section.getClass().getName());
            // List<ExportElement> subList = getDetailsForWidget((Widget) section, exportElements, exportItem.getViewName(),
            // sectionName);
            // exportItem.setSubset(subList);
            // exportElements.add(exportItem);
            // }
            //
            // FieldLayout sectionLayout = widgetHasFields.getLayout();
            // LayoutController layoutController = widgetHasFields.getLayoutController();
            // Widget currentLayoutControllerView = (Widget) layoutController.getCurrentView();
            // ExportElement exportItem = new ExportElement();
            // if (currentLayoutControllerView instanceof Widget) {
            // Widget sectionWidget = (Widget) currentLayoutControllerView;
            // exportItem.setViewName(getViewName(sectionWidget));
            // }
            // System.out.println("Section : " + currentLayoutControllerView.getClass().getName());
            //
            // List<ExportElement> subList = getDetailsForWidget((Widget) currentLayoutControllerView, exportElements,
            // exportItem.getViewName(), sectionName);
            // exportItem.setSubset(subList);
            // exportElements.add(exportItem);

        } else {
//            logger.warn("ExportUtils.getExportElementsFromView is not implemented for your View, either implement it here or do " + "not call the ExportUtils.getExportElementsFromView but implement it directly on your view");
        }
        return exportElements;
    }

}
