package org.kuali.student.common.ui.theme.custom.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.DataResource;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface KSClientBundle extends ImmutableResourceBundle{
	public static final KSClientBundle INSTANCE =  GWT.create(KSClientBundle.class);

    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSGeneral.css")
	public CssResource generalCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSAccordionMenu.css")
	public CssResource accordionMenuCss();    
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSAccordionPanel.css")
	public CssResource accordionPanelCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSBlockingProgressIndicator.css")
    public CssResource breadcrumbCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSBreadcrumb.css")
	public CssResource blockingProgressIndicatorCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSButton.css")
	public CssResource buttonCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSCheckbox.css")
	public CssResource checkboxCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSDatePicker.css")
	public CssResource datepickerCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSDisclosureSection.css")
	public CssResource disclosureSectionCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSDropDown.css")
    public CssResource ErrorDialogCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSErrorDialog.css")
	public CssResource dropDownCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSFloatPanel.css")
	public CssResource floatPanelCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSFormLayout.css")
	public CssResource formLayoutCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSHelpLink.css")
	public CssResource headerCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/Header.css")
    public CssResource helpLinkCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSImage.css")
	public CssResource imageCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSInfoDialogPanel.css")
	public CssResource infoDialogPanelCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSModalDialogPanel.css")
	public CssResource modalDialogPanelCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSLabel.css")
	public CssResource labelCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSListBox.css")
	public CssResource listBoxCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSRadioButton.css")
	public CssResource radioButtonCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSResizablePanel.css")
	public CssResource resizablePanelCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSRichTextEditor.css")
	public CssResource richTextEditorCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSTable.css")
	public CssResource tableCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSTextArea.css")
	public CssResource textAreaCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSTextBox.css")
	public CssResource textBoxCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSDialogPanel.css")
	public CssResource dialogPanelCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSProgressIndicator.css")
	public CssResource progressIndicatorCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSConfirmButtonPanel.css")
	public CssResource confirmButtonPanelCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSConfirmationDialog.css")
	public CssResource confirmationDialogCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSScrollTable.css")
    public CssResource scrollTableCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSSidebar.css")
	public CssResource sidebarCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSToolTip.css")
	public CssResource toolTipCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSBasicMenu.css")
    public CssResource basicMenuCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSPickList.css")
    public CssResource pickListCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSSelectableTableList.css")
    public CssResource selectableTableListCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSAdvancedSearchWindow.css")
    public CssResource advancedSearchWindowCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSSuggestBoxPicker.css")
    public CssResource suggestBoxPickerCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSButtonLayout.css")
    public CssResource buttonLayoutCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSCustomFlowPanel.css")
    public CssResource flowPanelCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSMultiplicity.css")
    public CssResource multiplicityCss();
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/CommentTool.css")
    public CssResource commentToolCss();   
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/KSSectionTitle.css")
    public CssResource sectionTitleCss(); 
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/DebugPanel.css")
    public CssResource debugPanelCss();
	@Resource("org/kuali/student/common/ui/theme/custompublic/css/KSRequiredMarker.css")
	public CssResource requiredMarkerCss();
	@Resource("org/kuali/student/common/ui/theme/custompublic/css/UploadProgressBar.css")
	public CssResource uploadProgressBarCss(); 
	@Resource("org/kuali/student/common/ui/theme/custompublic/css/KSValidationLayout.css")
	public CssResource validationLayoutCss();
	@Resource("org/kuali/student/common/ui/theme/custompublic/css/KSTabPanel.css")
	public CssResource tabPanelCss();
	@Resource("org/kuali/student/common/ui/theme/custompublic/css/KSTitleContainer.css")
	public CssResource titleContainerCss();
	@Resource("org/kuali/student/common/ui/theme/custompublic/css/KSWrapper.css")
	public CssResource wrapperCss();
	
	 //Common Images***************************************************
	
	@Resource("org/kuali/student/common/ui/theme/custompublic/images/common/KS_logo_white_transparent.png")
	public ImageResource headerImage();
	
    // set the resource value to an image of a question mark
    @Resource("org/kuali/student/common/ui/theme/custompublic/images/common/help-16x16.png")
    public ImageResource helpIcon();
    
    // set the resource value to an image of a green checkmark
    @Resource("org/kuali/student/common/ui/theme/custompublic/images/common/agt_action_success.png")
    public ImageResource okIcon();
    
    // set the resource value to an image of a red stop sign with x
    @Resource("org/kuali/student/common/ui/theme/custompublic/images/common/agt_stop.png")
    public ImageResource errorIcon();
    
    // set the resource value to an image of a yellow triangle with exclamation point
    @Resource("org/kuali/student/common/ui/theme/custompublic/images/common/messagebox_warning.png")
    public ImageResource warningIcon();
    
    @Resource("org/kuali/student/common/ui/theme/custompublic/images/common/asterisk_orange.png")
    public ImageResource asterisk();
    
    @Resource("org/kuali/student/common/ui/theme/custompublic/images/common/blue-gradient.jpg")
    public DataResource blueGradient();
    
    @Resource("org/kuali/student/common/ui/theme/custompublic/css/border-radius.htc")
    public DataResource ieRadius();
    
    @Resource("org/kuali/student/common/ui/theme/custompublic/images/common/14_pencil.png")
    public ImageResource editComment();
    
    @Resource("org/kuali/student/common/ui/theme/custompublic/images/common/fileclose.png")
    public ImageResource deleteComment();
    
    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/bold.gif")
    public ImageResource bold();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/createLink.gif")
    public ImageResource createLink();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/hr.gif")
    public ImageResource hr();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/indent.gif")
    public ImageResource indent();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/insertImage.gif")
    public ImageResource insertImage();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/italic.gif")
    public ImageResource italic();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/justifyCenter.gif")
    public ImageResource justifyCenter();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/justifyLeft.gif")
    public ImageResource justifyLeft();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/justifyRight.gif")
    public ImageResource justifyRight();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/ol.gif")
    public ImageResource ol();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/outdent.gif")
    public ImageResource outdent();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/removeFormat.gif")
    public ImageResource removeFormat();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/removeLink.gif")
    public ImageResource removeLink();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/strikeThrough.gif")
    public ImageResource strikeThrough();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/subscript.gif")
    public ImageResource subscript();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/superscript.gif")
    public ImageResource superscript();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/ul.gif")
    public ImageResource ul();

    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/underline.gif")
    public ImageResource underline();
    
    @Resource("org/kuali/student/common/ui/theme/custompublic/images/richTextEditor/popout.png")
    public ImageResource popout();
    

}
