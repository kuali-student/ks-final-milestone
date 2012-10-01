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
import org.kuali.student.common.ui.client.configurable.mvc.sections.WarnContainer;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.reporting.ReportExportWidget;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.ULPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTable;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableModel;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableRow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;

/**
 * 
 * This is a description of what this class does - pctsw don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class ExportUtils {
    public static final String PDF = "PDF";
    public static final String DOC = "DOC";
    public static final String XLS = "XLS";
    public static final String XML = "XML";
    public static final String RTF = "RTF";
    public static final String TEXT = "TEXT";
    /**
     * 
     * Inspect the given widget for the value and add it to the export element object.
     * 
     * @param exportItem
     * @param fieldWidget
     * @param setFirstFieldValue
     * @param viewName
     * @param sectionName
     * @return
     */
    public static ExportElement getExportItemDetails(ExportElement exportItem, Widget fieldWidget, boolean setFirstFieldValue, String viewName, String sectionName) {
        
        // Do not display the widget data if it is not visible on the screen.
        if (!fieldWidget.getParent().getElement().getStyle().getDisplay().equals("none")){
            
            if (fieldWidget instanceof HasHTML) {
                // HasHTML...
                HasHTML itemHasHTML = (HasHTML) fieldWidget;
                setFieldValue(exportItem, setFirstFieldValue, itemHasHTML.getHTML());
                
            } else if (fieldWidget instanceof HasText) {
                // Hastext...
                HasText itemHasTextValue = (HasText) fieldWidget;
                setFieldValue(exportItem, setFirstFieldValue, itemHasTextValue.getText());

            } else if (fieldWidget instanceof KSSelectedList) {
                KSSelectedList selectedList = (KSSelectedList) fieldWidget;
                List<KSItemLabel> selectedItems = selectedList.getSelectedItems();
                String values = new String();
                for (int j = 0; j < selectedItems.size(); j++) {
                    values = selectedItems.get(j).getDisplayText();
                }
                setFieldValue(exportItem, setFirstFieldValue, values);
                
            } else if (fieldWidget instanceof KSPicker) {
                KSPicker picker = (KSPicker) fieldWidget;
                if (picker.getInputWidget() instanceof HasText) {
                    HasText item = (HasText) picker.getInputWidget();
                    setFieldValue(exportItem, setFirstFieldValue, item.getText());
                } else if (picker.getInputWidget() instanceof KSLabelList) {
                    String fieldValue = null;
                    KSLabelList widget = (KSLabelList) picker.getInputWidget();
                    List<String> selected = widget.getSelectedItemsForExport();
                    for (int j = 0; j < selected.size(); j++) {
                        if (fieldValue == null) {
                            fieldValue = new String(selected.get(j));
                        } else {
                            fieldValue = fieldValue + ", " + selected.get(j);
                        }
                    }
                    setFieldValue(exportItem, setFirstFieldValue, fieldValue);
                }
                
            } else if (fieldWidget instanceof ListBox) {
                // Get the selected element from a list box.
                ListBox listBox = (ListBox) fieldWidget;
                setFieldValue(exportItem, setFirstFieldValue, listBox.getItemText(listBox.getSelectedIndex()));
            
            } else if (fieldWidget instanceof SectionTitle) {
                try {
                    // Retrieve the title from sections.
                    SectionTitle sectionTitle = (SectionTitle) fieldWidget;
                    setFieldValue(exportItem, setFirstFieldValue, sectionTitle.getExportFieldValue());
                    // If the value does not already contain the bold html tags, set the print type to bold.
                    if (!exportItem.getValue().contains("<b>")){
                        exportItem.setPrintType(ExportElement.BOLD);
                    }
                } catch (Exception e) {
                    // ignore, section tile interface problem - only in debugging.");
                }
                
            } else if (fieldWidget instanceof SummaryTable) {
                // Call custom details for widget method for summary tables.
                if (fieldWidget.isVisible()){
                    exportItem.setSubset(ExportUtils.getDetailsForWidget((SummaryTable)fieldWidget));
                }
        
            } else if (fieldWidget instanceof ReportExportWidget) {
                // Retrieve custom implementation data of report export widget.
        	    if (fieldWidget.isVisible()){
                    ReportExportWidget widget = (ReportExportWidget) fieldWidget;
                    if (widget.isExportElement() ) {
                        exportItem.setSubset(widget.getExportElementSubset(exportItem));
                        setFieldValue(exportItem, setFirstFieldValue, widget.getExportFieldValue());
                    }
                }
        
            } else if (fieldWidget instanceof ComplexPanel) {
                // Retrieve child elements from complex panel.
                if(fieldWidget.isVisible()) {
                    exportItem.setSubset(ExportUtils.getDetailsForWidget(fieldWidget, viewName, sectionName));
                }

            } else if (fieldWidget instanceof FieldElement) {
                // Retrieve subset from the widget on the field element.
                if(fieldWidget.isVisible()) {
                    Widget widget = ((FieldElement)fieldWidget).getFieldWidget();
                    exportItem = getExportItemDetails(exportItem, widget, true, viewName, sectionName);
                    exportItem.setPrintType(ExportElement.PARAGRAPH);
                }

            } else {
                // don't set anything
            }
        
            
        }
        return exportItem;
    }


    /**
     * 
     * This method sets the extracted string value.
     * 
     * @param exportItem
     * @param setFirstFieldValue
     * @param fieldValue
     */
    private static void setFieldValue(ExportElement exportItem, boolean setFirstFieldValue, String fieldValue) {
        if (setFirstFieldValue) {
            exportItem.setFieldValue(fieldValue);
        } else {
            exportItem.setFieldValue2(fieldValue);
        }
    }
    
    

    /**
     * This method gets the current controller based on the widget that was passed to it.
     * 
     * @param theWidget
     * @return currentController
     */
    public static Controller getController(Widget theWidget) {
        // TODO Nina - This can't be the correct way of getting handle to
        // Controller, isn't there a better way??
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

    /**
     * Handles the export click event.
     * 
     * @param currentController
     * @param format
     * @param reportTitle
     */
    public static void handleExportClickEvent(Controller currentController, String format, String reportTitle) {
        List<ExportElement> exportElements = new ArrayList<ExportElement>();
        exportElements = currentController.getExportElementsFromView();
        if (exportElements != null && exportElements.size() > 0) {
            currentController.doReportExport(exportElements, format, reportTitle);
        }
    }

    /**
     * 
     * Retrieve the sub elements from the summary table on a table section.
     * 
     * @param tableSection
     * @param exportElements
     * @return
     */
    public static List<ExportElement> getDetailsForWidget(SummaryTable sumTable) {
        List<ExportElement> exportElements = new ArrayList<ExportElement>();
        SummaryTableModel model = sumTable.getModel();
        
        // Loop through the different sections. 
        for (SummaryTableBlock block : model.getSectionList()) {
            String blockName = block.getTitle();
            
            // Loop through all the rows.
            for (SummaryTableRow row : block.getSectionRowList()) {
                ExportElement element = GWT.create(ExportElement.class);
				element.setSectionName(blockName);
				element.setViewName(blockName);
				element.setFieldLabel(row.getTitle());
				element.setMandatory(row.isRequired());
                
				// Only add elements if row is visible.
                if (row.isShown()) {
                    // Add the first element of the row.
                    element = setSummaryElement(blockName, row, element, row.getCell1(), true);
                    // Add the second element of the row.
                    element = setSummaryElement(blockName, row, element, row.getCell2(), false);
                    // All element to list.
                    if (element != null && element.getViewName() != null) {
                        exportElements.add(element);
                    }
                }
                
            }
        }
        return exportElements;
    }

    /**
     * Set the details for the summary element.
     * 
     * @param blockName
     * @param row
     * @param element
     * @param fdWidget
     * @param setFirstFieldValue
     * @return
     */
    private static ExportElement setSummaryElement(String blockName, SummaryTableRow row, ExportElement element, Widget fdWidget, boolean setFirstFieldValue) {
        if (fdWidget != null) {
            if (fdWidget instanceof KSListPanel) {
                element.setSubset(ExportUtils.getDetailsForWidget(fdWidget, blockName, blockName));
            } else {
                element = ExportUtils.getExportItemDetails(element, fdWidget, setFirstFieldValue, blockName, blockName);
            }
        } else {
            if (row.getTitle() != null) {
                element.setFieldLabel(row.getTitle());
            }
        }
        return element;
    }

    /**
     * 
     * Retrieves the sub elements from a container widget.
     * 
     * @param currentViewWidget
     * @param viewName
     * @param sectionName
     * @return
     */
    public static List<ExportElement> getDetailsForWidget(Widget currentViewWidget, String viewName, String sectionName) {
        List<ExportElement> childElements = new ArrayList<ExportElement>();
        if (!currentViewWidget.getParent().getElement().getStyle().getDisplay().equals("none")){
            if (currentViewWidget instanceof Section) {
                Section widgetHasFields = (Section) currentViewWidget;
                List<FieldDescriptor> widgetFields = widgetHasFields.getFields();
                for (FieldDescriptor field : widgetFields) {
                    ExportElement exportItem = createExportElement(viewName, sectionName, childElements, field.getFieldElement().getFieldWidget());
                    exportItem.setFieldLabel(field.getFieldLabel());
                }
            } else if (currentViewWidget instanceof KSListPanel) {
                KSListPanel ksListPanelWidget = (KSListPanel) currentViewWidget;
                WidgetCollection children = ksListPanelWidget.getChildren();
                for (int i = 0; i < children.size(); i++) {
                    createExportElement(viewName, sectionName, childElements, children.get(i));
                }

            } else if (currentViewWidget instanceof ULPanel) {
                ComplexPanel complexPanel = (ComplexPanel) currentViewWidget;
                if (complexPanel.isVisible()){
                    for (int i = 0; i < complexPanel.getWidgetCount(); i++) {
                        Widget child = complexPanel.getWidget(i);
                        if (child instanceof FlowPanel){
                            List<ExportElement> subset = ExportUtils.getDetailsForWidget(child, viewName, sectionName);
                            if (subset != null && subset.size() > 0){
                                subset.get(0).setPrintType(ExportElement.LIST);
                                childElements.addAll(subset);
                            }
                            
                        } else if (!(child instanceof KSButton)
                                && !(child instanceof WarnContainer)) {
                            ExportElement exportItem = createExportElement(viewName, sectionName, childElements, child);
                            exportItem.setPrintType(ExportElement.LIST);
                        }
                    }
                }
                
            } else if (currentViewWidget instanceof ComplexPanel) {
                ComplexPanel complexPanel = (ComplexPanel) currentViewWidget;
                if (complexPanel.isVisible()){
                    for (int i = 0; i < complexPanel.getWidgetCount(); i++) {
                        Widget child = complexPanel.getWidget(i);
                        if (child instanceof FlowPanel){
                            List<ExportElement> subset = ExportUtils.getDetailsForWidget(child, viewName, sectionName);
                            if (subset != null && subset.size() > 0){
                                childElements.addAll(subset);
                            }
                            
                        } else if (!(child instanceof KSButton)
                                && !(child instanceof WarnContainer)) {
                            createExportElement(viewName, sectionName, childElements, child);
                        }
                    }
                }
            } else {

                System.out.println("ExportUtils does not cater for this type..." + currentViewWidget.getClass().getName());

            }
        }
        return childElements;
    }

    // Added for 2nd widget (side-by-side comparison)
    public static List<ExportElement> getDetailsForWidget(ExportElement element, Widget currentViewWidget, boolean setFirstFieldValue, String viewName, String sectionName) {
        List<ExportElement> childElements = new ArrayList<ExportElement>();
        if (!currentViewWidget.getParent().getElement().getStyle().getDisplay().equals("none")){
            if (currentViewWidget instanceof Section) {
                Section widgetHasFields = (Section) currentViewWidget;
                List<FieldDescriptor> widgetFields = widgetHasFields.getFields();
                 for (FieldDescriptor field : widgetFields) {
                     ExportElement exportItem = createExportElement(viewName, sectionName, childElements, field.getFieldElement().getFieldWidget());
                     exportItem.setFieldLabel(field.getFieldLabel());
                }
            } else if (currentViewWidget instanceof KSListPanel) {
                KSListPanel ksListPanelWidget = (KSListPanel) currentViewWidget;
                WidgetCollection children = ksListPanelWidget.getChildren();
                for (int i = 0; i < children.size(); i++) {
                	if (!element.getSubset().isEmpty() && i < element.getSubset().size()) 
                		createExportElementOld(element.getSubset().get(i), viewName, sectionName, childElements, children.get(i)); 
                	else	
                		createExportElement2(viewName, sectionName, childElements, children.get(i));
                }
            } else if (currentViewWidget instanceof ULPanel) {
                ComplexPanel complexPanel = (ComplexPanel) currentViewWidget;
                if (complexPanel.isVisible()){
                    for (int i = 0; i < complexPanel.getWidgetCount(); i++) {
                        Widget child = complexPanel.getWidget(i);
                        if (child instanceof FlowPanel){
                            List<ExportElement> subset = ExportUtils.getDetailsForWidget(child, viewName, sectionName);
                            if (subset != null && subset.size() > 0){
                                subset.get(0).setPrintType(ExportElement.LIST);
                                childElements.addAll(subset);
                            }
                            
                        } else if (!(child instanceof KSButton)
                                && !(child instanceof WarnContainer)) {
                            ExportElement exportItem = createExportElement(viewName, sectionName, childElements, child);
                            exportItem.setPrintType(ExportElement.LIST);
                        }
                    }
                }
                
            } else if (currentViewWidget instanceof ComplexPanel) {
                ComplexPanel complexPanel = (ComplexPanel) currentViewWidget;
                if (complexPanel.isVisible()){
                    for (int i = 0; i < complexPanel.getWidgetCount(); i++) {
                        Widget child = complexPanel.getWidget(i);
                        if (child instanceof FlowPanel){
                            List<ExportElement> subset = ExportUtils.getDetailsForWidget(child, viewName, sectionName);
                            if (subset != null && subset.size() > 0){
                                childElements.addAll(subset);
                            }
                            
                        } else if (!(child instanceof KSButton)
                                && !(child instanceof WarnContainer)) {
                            createExportElement(viewName, sectionName, childElements, child);
                        }
                    }
                }
            } else {

                System.out.println("ExportUtils does not cater for this type..." + currentViewWidget.getClass().getName());

            }
        }
        return childElements;
    }

    public static ArrayList<ExportElement> getExportElementsFromView(Widget currentViewWidget, ArrayList<ExportElement> exportElements, String viewName, String sectionName) {
        if (exportElements == null) {
            exportElements = new ArrayList<ExportElement>();
        }
        if (currentViewWidget.getParent() == null || !currentViewWidget.getParent().getElement().getStyle().getDisplay().equals("none")) {
            if (currentViewWidget instanceof VerticalSectionView) {
                Section widgetHasFields = (Section) currentViewWidget;
                List<FieldDescriptor> widgetFields = widgetHasFields.getFields();
                for (FieldDescriptor field : widgetFields) {
                    Widget child = field.getFieldElement().getFieldWidget();
                    ExportElement exportItem = createExportElement(viewName, sectionName, exportElements, child);
                    exportItem.setFieldLabel(field.getFieldLabel());
                }
                if ((currentViewWidget instanceof BaseSection) && (widgetHasFields.getFields().size() == 0)) {
                    BaseSection bSection = (BaseSection) currentViewWidget;
                    createExportElement(viewName, sectionName, exportElements, bSection.getLayout());
                }
            }
        }
        return exportElements;
    }
    
    /**
     * 
     * Creates a new export element with its sub elements.
     * 
     * @param viewName
     * @param sectionName
     * @param childElements
     * @param child
     * @return
     */
    private static ExportElement createExportElement(String viewName, String sectionName, List<ExportElement> childElements, Widget child) {
        ExportElement exportItem = GWT.create(ExportElement.class);
        exportItem.setSectionName(sectionName);
        exportItem.setViewName(viewName);
                            
        exportItem = getExportItemDetails(exportItem, child, true, viewName, sectionName);
        ExportUtils.addElementToElementArray(childElements, exportItem);
        return exportItem;
    }

    private static ExportElement createExportElement2(String viewName, String sectionName, List<ExportElement> childElements, Widget child) {
        ExportElement exportItem = GWT.create(ExportElement.class);
        exportItem.setSectionName(sectionName);
        exportItem.setViewName(viewName);
                            
        exportItem = getExportItemDetails(exportItem, child, false, viewName, sectionName);
        ExportUtils.addElementToElementArray(childElements, exportItem);
        return exportItem;
    }

    private static ExportElement createExportElementOld(ExportElement exportItem, String viewName, String sectionName, List<ExportElement> childElements, Widget child) {
        exportItem = getExportItemDetails(exportItem, child, false, viewName, sectionName);
        ExportUtils.addElementToElementArray(childElements, exportItem);
        return exportItem;
    }

    // Only add element if it is not null
    public static List<ExportElement> addElementToElementArray(List<ExportElement> elementArray, ExportElement element) {
        if (element.getFieldLabel() != null || element.getFieldValue() != null || element.getFieldValue2() != null || (element.getSubset() != null && element.getSubset().size() > 0)) {
            elementArray.add(element);
        }
        return elementArray;
    }

}