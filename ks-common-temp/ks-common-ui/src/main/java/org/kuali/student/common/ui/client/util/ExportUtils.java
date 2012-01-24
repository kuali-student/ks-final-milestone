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
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTable;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableModel;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;

public class ExportUtils {
    public static final String PDF = "PDF";
    public static final String DOC = "DOC";
    public static final String XLS = "XLS";

    public static ExportElement getExportItemDetails(ExportElement exportItem, Widget fieldWidget, boolean setFirstFieldValue, String viewName, String sectionName) {
        ArrayList<ExportElement> subExportElements = new ArrayList<ExportElement>();
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
        } else if (fieldWidget instanceof KSPicker) {
            KSPicker picker = (KSPicker) fieldWidget;
            if (picker.getInputWidget() instanceof HasText) {
                HasText item = (HasText) picker.getInputWidget();
                fieldValue = item.getText();
            } else if (picker.getInputWidget() instanceof KSLabelList) {
                KSLabelList widget = (KSLabelList) picker.getInputWidget();
                List<String> selected = widget.getSelectedItems();
                for (int j = 0; j <  selected.size(); j++) {
                    if (fieldValue == null) {
                        fieldValue = new String(selected.get(j));
                    } else {
                        fieldValue = fieldValue + ", " + selected.get(j);
                    }
                }
            }
        } else if (fieldWidget instanceof ListBox) {
            ListBox listBox = (ListBox) fieldWidget;
            fieldValue = listBox.getItemText(listBox.getSelectedIndex());
        } else if (fieldWidget instanceof SectionTitle) {
            SectionTitle sectionTitle = (SectionTitle) fieldWidget;            
            fieldValue = sectionTitle.getElement().getInnerText();
        } else if (fieldWidget instanceof ComplexPanel) {
                subExportElements = ExportUtils.getDetailsForWidget(fieldWidget, subExportElements, viewName, sectionName);
        } else
        {
            // logger.warn(exportItem.getFieldLabel() + " Fieldwidget not catered for : class type = " +
            // fieldWidget.getClass().getName());
        } 
        if (setFirstFieldValue) {
            exportItem.setFieldValue(fieldValue);
        } else {
            exportItem.setFieldValue2(fieldValue);
        }
        if (subExportElements != null && subExportElements.size() > 0) {
            exportItem.setSubset(subExportElements);
        }

//                System.out.println(exportItem.getSectionName() + " : Label = " + exportItem.getFieldLabel() + " fieldValue : " + exportItem.getFieldValue() + " fieldValue2 : " + exportItem.getFieldValue2());
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

    public static void handleExportClickEvent(Controller currentController, String format, String reportTitle) {
        ArrayList<ExportElement> exportElements = new ArrayList<ExportElement>();
//        System.out.println("currentController is /:" + currentController.getClass().getName());
        exportElements = currentController.getExportElementsFromView();
//        System.out.println(currentController.getTitle());
//        System.out.println(currentController.getCurrentView().getName());
        if (exportElements != null && exportElements.size() > 0) {
            debutExportElementsArray(exportElements);
            currentController.doReportExport(exportElements, format, reportTitle);
        }
    }

    private static void debutExportElementsArray(ArrayList<ExportElement> exportElements) {
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        for (int i = 0; i < exportElements.size(); i++) {
            System.out.println(exportElements.get(i).printLine());
            debutExportElementsArraySubList(exportElements.get(i).getSubset());
        }
    }

    private static void debutExportElementsArraySubList(List<ExportElement> exportElements) {
        if (exportElements != null) {
            System.out.println("Sub list : ");
            for (int j = 0; j < exportElements.size(); j++) {
                ExportElement element = exportElements.get(j);
                System.out.println(element.printLine());
                debutExportElementsArraySubList(element.getSubset());
                
            }
        }
    }

    public static ArrayList<ExportElement> getDetailsForWidget(SummaryTableSection tableSection, ArrayList<ExportElement> exportElements) {
        SummaryTable sumTable = tableSection.getSummaryTable();
        SummaryTableModel model = sumTable.getModel();
        List<SummaryTableBlock> tableSectionList = model.getSectionList();
        for (int i = 0; i < tableSectionList.size(); i++) {
            SummaryTableBlock item = tableSectionList.get(i);
            String blockName = item.getTitle();
            List<SummaryTableRow> rowItems = item.getSectionRowList();
            for (int j = 0; j < rowItems.size(); j++) {
                ExportElement element = new ExportElement();
                SummaryTableRow row = rowItems.get(j);
                element.setSectionName(blockName);
                element.setViewName(blockName);
                element.setFieldLabel(row.getTitle());
                element.setMandatory(row.isRequired());
                Widget fdWidget = row.getCell1();
                if (row.isShown()) {
                    if (fdWidget != null) {
                        //
                        if (fdWidget instanceof KSListPanel) {
                            ArrayList<ExportElement> subExportElements = new ArrayList<ExportElement>();
                            subExportElements = ExportUtils.getDetailsForWidget(fdWidget, subExportElements, blockName, blockName);
                            element.setSubset(subExportElements);
                        } else {
                        //
                        element = ExportUtils.getExportItemDetails(element,fdWidget,true, blockName, blockName);
                        }
                    } else {
                        if (row.getTitle() != null) {
                            element.setFieldLabel(row.getTitle());
                        }
                    }
                //
                    Widget fdWidget2 = row.getCell2();
                    if (fdWidget2 != null) {
                        element = ExportUtils.getExportItemDetails(element,fdWidget2,false, blockName, blockName);
                                       
                    } else {
                        if (row.getTitle() != null) {
                            element.setFieldLabel(row.getTitle());
                        }
                    }
                    if (element != null && element.getViewName() != null) {
                        exportElements.add(element);
                    }
                }
            }
        }
        return exportElements;
    }

    public static ArrayList<ExportElement> getDetailsForWidget(Widget currentViewWidget, ArrayList<ExportElement> exportElements, String viewName, String sectionName) {
        if (currentViewWidget instanceof Section) {
            Section widgetHasFields = (Section) currentViewWidget;
            List<FieldDescriptor> widgetFields = widgetHasFields.getFields();
            for (FieldDescriptor field : widgetFields) {
                ExportElement exportItem = new ExportElement();
                exportItem.setSectionName(sectionName + viewName);
                exportItem.setViewName(sectionName + viewName);
                exportItem.setFieldLabel(field.getFieldLabel());
                Widget fieldWidget = field.getFieldElement().getFieldWidget();

                exportElements.add(getExportItemDetails(exportItem, fieldWidget, true, viewName, sectionName));
            }
        } else if (currentViewWidget instanceof KSListPanel) {
            KSListPanel ksListPanelWidget = (KSListPanel) currentViewWidget;
            WidgetCollection children = ksListPanelWidget.getChildren();
            for (int i = 0; i < children.size(); i++) {
                Widget child = children.get(i);

                ExportElement exportItem = new ExportElement();
                exportItem.setSectionName(sectionName + viewName);
                exportItem.setViewName(sectionName + viewName);
                exportItem.setFieldLabel("");
                exportElements.add(getExportItemDetails(exportItem, child, true, viewName, sectionName));
            }
            
        } else if (currentViewWidget instanceof ComplexPanel){
            ComplexPanel complexPanel = (ComplexPanel) currentViewWidget;
            for (int i = 0; i < complexPanel .getWidgetCount(); i++) {
                Widget child = complexPanel .getWidget(i);
                ExportElement exportItem = new ExportElement();
                exportItem.setSectionName(sectionName + viewName + " Complexpanel sublist");
                exportItem.setViewName(sectionName + viewName + " Complexpanel sublist");
                ExportElement exportItemDetails = getExportItemDetails(exportItem, child, true, viewName, sectionName);
                if (exportItemDetails.getFieldValue() != null || (exportItemDetails.getSubset() != null && exportItemDetails.getSubset().size() > 0)) {
                    exportElements.add(exportItemDetails);                    
                }
            }

            

        }  
        else {
        
            System.out.println("ExportUtils does not cater for this type..." + currentViewWidget.getClass().getName());
                        
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
                exportItem.setSectionName(sectionName + viewName);
                exportItem.setViewName(sectionName + viewName);
                exportItem.setFieldLabel(field.getFieldLabel());
                Widget fieldWidget = field.getFieldElement().getFieldWidget();

                exportElements.add(getExportItemDetails(exportItem, fieldWidget,true,viewName, sectionName));
            }
            if ((currentViewWidget instanceof BaseSection) && (widgetHasFields.getFields().size() == 0)) {
                BaseSection bSection = (BaseSection) currentViewWidget;
                ExportElement exportItem = new ExportElement();
                exportItem.setSectionName(sectionName + viewName);
                exportItem.setViewName(sectionName + viewName);
                exportItem.setFieldLabel("???00");
                exportItem = getExportItemDetails(exportItem, bSection.getLayout(), true, viewName, sectionName);
                exportElements.add(exportItem);
                
            }
//        } else { // Debugging
//            System.out.println("ExportUtils.getExportElementsFromView is not implemented for your View, either implement it here or do " + "not call the ExportUtils.getExportElementsFromView but implement it directly on your view");
        }
        return exportElements;
    }
    
}