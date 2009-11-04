package org.kuali.student.common.ui.theme.standard.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.DataResource;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface KSClientBundle extends ImmutableResourceBundle{
	public static final KSClientBundle INSTANCE =  GWT.create(KSClientBundle.class);

    @Resource("../public/css/KSGeneral.css")
	public CssResource generalCss();
    @Resource("../public/css/KSAccordionMenu.css")
	public CssResource accordionMenuCss();    
    @Resource("../public/css/KSAccordionPanel.css")
	public CssResource accordionPanelCss();
    @Resource("../public/css/KSBlockingProgressIndicator.css")
    public CssResource breadcrumbCss();
    @Resource("../public/css/KSBreadcrumb.css")
	public CssResource blockingProgressIndicatorCss();
    @Resource("../public/css/KSButton.css")
	public CssResource buttonCss();
    @Resource("../public/css/KSCheckbox.css")
	public CssResource checkboxCss();
    @Resource("../public/css/KSDatePicker.css")
	public CssResource datepickerCss();
    @Resource("../public/css/KSDisclosureSection.css")
	public CssResource disclosureSectionCss();
    @Resource("../public/css/KSDropDown.css")
    public CssResource ErrorDialogCss();
    @Resource("../public/css/KSErrorDialog.css")
	public CssResource dropDownCss();
    @Resource("../public/css/KSFloatPanel.css")
	public CssResource floatPanelCss();
    @Resource("../public/css/KSFormLayout.css")
	public CssResource formLayoutCss();
    @Resource("../public/css/KSHelpLink.css")
	public CssResource headerCss();
    @Resource("../public/css/Header.css")
    public CssResource helpLinkCss();
    @Resource("../public/css/KSImage.css")
	public CssResource imageCss();
    @Resource("../public/css/KSInfoDialogPanel.css")
	public CssResource infoDialogPanelCss();
    @Resource("../public/css/KSModalDialogPanel.css")
	public CssResource modalDialogPanelCss();
    @Resource("../public/css/KSLabel.css")
	public CssResource labelCss();
    @Resource("../public/css/KSListBox.css")
	public CssResource listBoxCss();
    @Resource("../public/css/KSRadioButton.css")
	public CssResource radioButtonCss();
    @Resource("../public/css/KSResizablePanel.css")
	public CssResource resizablePanelCss();
    @Resource("../public/css/KSRichTextEditor.css")
	public CssResource richTextEditorCss();
    @Resource("../public/css/KSTable.css")
	public CssResource tableCss();
    @Resource("../public/css/KSTextArea.css")
	public CssResource textAreaCss();
    @Resource("../public/css/KSTextBox.css")
	public CssResource textBoxCss();
    @Resource("../public/css/KSDialogPanel.css")
	public CssResource dialogPanelCss();
    @Resource("../public/css/KSProgressIndicator.css")
	public CssResource progressIndicatorCss();
    @Resource("../public/css/KSConfirmButtonPanel.css")
	public CssResource confirmButtonPanelCss();
    @Resource("../public/css/KSConfirmationDialog.css")
	public CssResource confirmationDialogCss();
    @Resource("../public/css/KSScrollTable.css")
    public CssResource scrollTableCss();
    @Resource("../public/css/KSSidebar.css")
	public CssResource sidebarCss();
    @Resource("../public/css/KSToolTip.css")
	public CssResource toolTipCss();
    @Resource("../public/css/KSBasicMenu.css")
    public CssResource basicMenuCss();
    @Resource("../public/css/KSPickList.css")
    public CssResource pickListCss();
    @Resource("../public/css/KSSelectableTableList.css")
    public CssResource selectableTableListCss();
    @Resource("../public/css/KSAdvancedSearchWindow.css")
    public CssResource advancedSearchWindowCss();
    @Resource("../public/css/KSSuggestBoxPicker.css")
    public CssResource suggestBoxPickerCss();
    @Resource("../public/css/KSButtonLayout.css")
    public CssResource buttonLayoutCss();
    @Resource("../public/css/KSCustomFlowPanel.css")
    public CssResource flowPanelCss();
    @Resource("../public/css/KSMultiplicity.css")
    public CssResource multiplicityCss();
    @Resource("../public/css/CommentTool.css")
    public CssResource commentToolCss();   
    @Resource("../public/css/KSSectionTitle.css")
    public CssResource sectionTitleCss(); 
    @Resource("../public/css/DebugPanel.css")
    public CssResource debugPanelCss();
	@Resource("../public/css/KSRequiredMarker.css")
	public CssResource requiredMarkerCss();
	@Resource("../public/css/UploadProgressBar.css")
	public CssResource uploadProgressBarCss(); 
	@Resource("../public/css/KSValidationLayout.css")
	public CssResource validationLayoutCss();
	@Resource("../public/css/KSTabPanel.css")
	public CssResource tabPanelCss();
	@Resource("../public/css/KSTitleContainer.css")
	public CssResource titleContainerCss();
	@Resource("../public/css/KSWrapper.css")
	public CssResource wrapperCss();
	
	 //Common Images***************************************************
	
	@Resource("../public/images/common/kru_banner2.jpg")
	public ImageResource headerImage();
	
    // set the resource value to an image of a question mark
    @Resource("../public/images/common/help-16x16.png")
    public ImageResource helpIcon();
    
    // set the resource value to an image of a green checkmark
    @Resource("../public/images/common/agt_action_success.png")
    public ImageResource okIcon();
    
    // set the resource value to an image of a red stop sign with x
    @Resource("../public/images/common/agt_stop.png")
    public ImageResource errorIcon();
    
    // set the resource value to an image of a yellow triangle with exclamation point
    @Resource("../public/images/common/messagebox_warning.png")
    public ImageResource warningIcon();
    
    @Resource("../public/images/common/asterisk_orange.png")
    public ImageResource asterisk();
    
    @Resource("../public/images/common/blue-gradient.jpg")
    public DataResource blueGradient();
    
    @Resource("../public/css/border-radius.htc")
    public DataResource ieRadius();
    
    @Resource("../public/images/common/14_pencil.png")
    public ImageResource editComment();
    
    @Resource("../public/images/common/fileclose.png")
    public ImageResource deleteComment();
    
    @Resource("../public/images/richTextEditor/bold.gif")
    public ImageResource bold();

    @Resource("../public/images/richTextEditor/createLink.gif")
    public ImageResource createLink();

    @Resource("../public/images/richTextEditor/hr.gif")
    public ImageResource hr();

    @Resource("../public/images/richTextEditor/indent.gif")
    public ImageResource indent();

    @Resource("../public/images/richTextEditor/insertImage.gif")
    public ImageResource insertImage();

    @Resource("../public/images/richTextEditor/italic.gif")
    public ImageResource italic();

    @Resource("../public/images/richTextEditor/justifyCenter.gif")
    public ImageResource justifyCenter();

    @Resource("../public/images/richTextEditor/justifyLeft.gif")
    public ImageResource justifyLeft();

    @Resource("../public/images/richTextEditor/justifyRight.gif")
    public ImageResource justifyRight();

    @Resource("../public/images/richTextEditor/ol.gif")
    public ImageResource ol();

    @Resource("../public/images/richTextEditor/outdent.gif")
    public ImageResource outdent();

    @Resource("../public/images/richTextEditor/removeFormat.gif")
    public ImageResource removeFormat();

    @Resource("../public/images/richTextEditor/removeLink.gif")
    public ImageResource removeLink();

    @Resource("../public/images/richTextEditor/strikeThrough.gif")
    public ImageResource strikeThrough();

    @Resource("../public/images/richTextEditor/subscript.gif")
    public ImageResource subscript();

    @Resource("../public/images/richTextEditor/superscript.gif")
    public ImageResource superscript();

    @Resource("../public/images/richTextEditor/ul.gif")
    public ImageResource ul();

    @Resource("../public/images/richTextEditor/underline.gif")
    public ImageResource underline();
    
    @Resource("../public/images/richTextEditor/popout.png")
    public ImageResource popout();
    

}
