/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImageResource.ImageOptions;
import com.google.gwt.libideas.resources.client.ImageResource.RepeatStyle;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle.Resource;


public interface KSCommonResources extends ImmutableResourceBundle{
    public static final KSCommonResources INSTANCE =  (KSCommonResources) GWT.create(KSCommonResources.class);

    @Resource("org/kuali/student/common/ui/public/KSGeneral.css")
	public CssResource generalCss();
    @Resource("org/kuali/student/common/ui/public/KSAccordionMenu.css")
	public CssResource accordionMenuCss();    
    @Resource("org/kuali/student/common/ui/public/KSAccordionPanel.css")
	public CssResource accordionPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSBlockingProgressIndicator.css")
    public CssResource breadcrumbCss();
    @Resource("org/kuali/student/common/ui/public/KSBreadcrumb.css")
	public CssResource blockingProgressIndicatorCss();
    @Resource("org/kuali/student/common/ui/public/KSButton.css")
	public CssResource buttonCss();
    @Resource("org/kuali/student/common/ui/public/KSCheckbox.css")
	public CssResource checkboxCss();
    @Resource("org/kuali/student/common/ui/public/KSDatePicker.css")
	public CssResource datepickerCss();
    @Resource("org/kuali/student/common/ui/public/KSDisclosureSection.css")
	public CssResource disclosureSectionCss();
    @Resource("org/kuali/student/common/ui/public/KSDropDown.css")
    public CssResource ErrorDialogCss();
    @Resource("org/kuali/student/common/ui/public/KSErrorDialog.css")
	public CssResource dropDownCss();
    @Resource("org/kuali/student/common/ui/public/KSFloatPanel.css")
	public CssResource floatPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSFormLayout.css")
	public CssResource formLayoutCss();
    @Resource("org/kuali/student/common/ui/public/KSHelpLink.css")
	public CssResource headerCss();
    @Resource("org/kuali/student/common/ui/public/Header.css")
    public CssResource helpLinkCss();
    @Resource("org/kuali/student/common/ui/public/KSImage.css")
	public CssResource imageCss();
    @Resource("org/kuali/student/common/ui/public/KSInfoDialogPanel.css")
	public CssResource infoDialogPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSModalDialogPanel.css")
	public CssResource modalDialogPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSLabel.css")
	public CssResource labelCss();
    @Resource("org/kuali/student/common/ui/public/KSListBox.css")
	public CssResource listBoxCss();
    @Resource("org/kuali/student/common/ui/public/KSRadioButton.css")
	public CssResource radioButtonCss();
    @Resource("org/kuali/student/common/ui/public/KSResizablePanel.css")
	public CssResource resizablePanelCss();
    @Resource("org/kuali/student/common/ui/public/KSRichTextEditor.css")
	public CssResource richTextEditorCss();
    @Resource("org/kuali/student/common/ui/public/KSTable.css")
	public CssResource tableCss();
    @Resource("org/kuali/student/common/ui/public/KSTextArea.css")
	public CssResource textAreaCss();
    @Resource("org/kuali/student/common/ui/public/KSTextBox.css")
	public CssResource textBoxCss();
    @Resource("org/kuali/student/common/ui/public/KSDialogPanel.css")
	public CssResource dialogPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSProgressIndicator.css")
	public CssResource progressIndicatorCss();
    @Resource("org/kuali/student/common/ui/public/KSConfirmButtonPanel.css")
	public CssResource confirmButtonPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSConfirmationDialog.css")
	public CssResource confirmationDialogCss();
    @Resource("org/kuali/student/common/ui/public/KSScrollTable.css")
    public CssResource scrollTableCss();
    @Resource("org/kuali/student/common/ui/public/KSSidebar.css")
	public CssResource sidebarCss();
    @Resource("org/kuali/student/common/ui/public/KSToolTip.css")
	public CssResource toolTipCss();
    @Resource("org/kuali/student/common/ui/public/KSBasicMenu.css")
    public CssResource basicMenuCss();
    @Resource("org/kuali/student/common/ui/public/KSPickList.css")
    public CssResource pickListCss();
    @Resource("org/kuali/student/common/ui/public/KSSelectableTableList.css")
    public CssResource selectableTableListCss();
    
    @Resource("org/kuali/student/common/ui/public/KSAdvancedSearchWindow.css")
    public CssResource advancedSearchWindowCss();
    @Resource("org/kuali/student/common/ui/public/KSSuggestBoxPicker.css")
    public CssResource suggestBoxPickerCss();
    @Resource("org/kuali/student/common/ui/public/KSButtonLayout.css")
    public CssResource buttonLayoutCss();
    
    @Resource("org/kuali/student/common/ui/public/KSCustomFlowPanel.css")
    public CssResource flowPanelCss();
    
    @Resource("org/kuali/student/common/ui/public/KSMultiplicity.css")
    public CssResource multiplicityCss();
    
    @Resource("org/kuali/student/common/ui/public/CommentTool.css")
    public CssResource commentToolCss();   
    
    @Resource("org/kuali/student/common/ui/public/KSSectionTitle.css")
    public CssResource sectionTitleCss(); 

    @Resource("org/kuali/student/common/ui/public/DebugPanel.css")
    public CssResource debugPanelCss();

	@Resource("org/kuali/student/common/ui/public/KSRequiredMarker.css")
	public CssResource requiredMarkerCss();
	
	@Resource("org/kuali/student/common/ui/public/UploadProgressBar.css")
	public CssResource uploadProgressBarCss(); 

	@Resource("org/kuali/student/common/ui/public/KSValidationLayout.css")
	public CssResource validationLayoutCss(); 

}
