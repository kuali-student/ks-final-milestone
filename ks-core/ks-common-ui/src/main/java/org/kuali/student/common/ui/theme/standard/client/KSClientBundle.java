/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.theme.standard.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

public interface KSClientBundle extends ClientBundleWithLookup{
	public static final KSClientBundle INSTANCE =  GWT.create(KSClientBundle.class);

    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSGeneral.css")
	@CssResource.NotStrict
	public CssResource generalCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSBlockingProgressIndicator.css")
	@CssResource.NotStrict
	public CssResource blockingProgressIndicatorCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSCheckbox.css")
	@CssResource.NotStrict
	public CssResource checkboxCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSDatePicker.css")
	@CssResource.NotStrict
	public CssResource datepickerCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSDropDown.css")
    @CssResource.NotStrict
	public CssResource ErrorDialogCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSErrorDialog.css")
	@CssResource.NotStrict
	public CssResource dropDownCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSHelpLink.css")
	@CssResource.NotStrict
	public CssResource headerCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/Header.css")
    @CssResource.NotStrict
	public CssResource helpLinkCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSImage.css")
	@CssResource.NotStrict
	public CssResource imageCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSLabel.css")
	@CssResource.NotStrict
	public CssResource labelCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSRadioButton.css")
	@CssResource.NotStrict
	public CssResource radioButtonCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSTable.css")
	@CssResource.NotStrict
	public CssResource tableCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSTextArea.css")
	@CssResource.NotStrict
	public CssResource textAreaCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSTextBox.css")
	@CssResource.NotStrict
	public CssResource textBoxCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSDialogPanel.css")
	@CssResource.NotStrict
	public CssResource dialogPanelCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSProgressIndicator.css")
	@CssResource.NotStrict
	public CssResource progressIndicatorCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSScrollTable.css")
    @CssResource.NotStrict
	public CssResource scrollTableCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSBasicMenu.css")
    @CssResource.NotStrict
	public CssResource basicMenuCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSSelectableTableList.css")
    @CssResource.NotStrict
	public CssResource selectableTableListCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSButtonLayout.css")
    @CssResource.NotStrict
	public CssResource buttonLayoutCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSCustomFlowPanel.css")
    @CssResource.NotStrict
	public CssResource flowPanelCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSMultiplicity.css")
    @CssResource.NotStrict
	public CssResource multiplicityCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/CommentTool.css")
    @CssResource.NotStrict
	public CssResource commentToolCss();
    @Source("org/kuali/student/common/ui/theme/standard/public/css/KSSectionTitle.css")
    @CssResource.NotStrict
	public CssResource sectionTitleCss();
	@Source("org/kuali/student/common/ui/theme/standard/public/css/UploadProgressBar.css")
	@CssResource.NotStrict
	public CssResource uploadProgressBarCss();
	@Source("org/kuali/student/common/ui/theme/standard/public/css/KSTabPanel.css")
	@CssResource.NotStrict
	public CssResource tabPanelCss();
	@Source("org/kuali/student/common/ui/theme/standard/public/css/KSTitleContainer.css")
	@CssResource.NotStrict
	public CssResource titleContainerCss();
	@Source("org/kuali/student/common/ui/theme/standard/public/css/KSWrapper.css")
	@CssResource.NotStrict
	public CssResource wrapperCss();
	@Source("org/kuali/student/common/ui/theme/standard/public/css/KSLandingPage.css")
	@CssResource.NotStrict
	public CssResource landingPageCss();
	@Source("org/kuali/student/common/ui/theme/standard/public/css/KSActionItemList.css")
	@CssResource.NotStrict
	public CssResource actionItemListCss();
	@Source("org/kuali/student/common/ui/theme/standard/public/css/GwtDefault.css")
	@CssResource.NotStrict
	public CssResource gwtDefaultCss();
	@Source("org/kuali/student/common/ui/theme/standard/public/css/KSSection.css")
	@CssResource.NotStrict
	public CssResource sectionCss();
	@Source("org/kuali/student/common/ui/theme/standard/public/css/KSPicker.css")
	@CssResource.NotStrict
	public CssResource pickerCss();
	@Source("org/kuali/student/common/ui/theme/standard/public/css/MenuLayout.css")
	@CssResource.NotStrict
	public CssResource menuLayoutCss();

	//Reset Css
	@Source("org/kuali/student/common/ui/theme/standard/public/css/Reset.css")
	@CssResource.NotStrict
	public CssResource resetCss();

	//Font & Settings Css
	@Source("org/kuali/student/common/ui/theme/standard/public/css/InitFonts.css")
	@CssResource.NotStrict
	public CssResource fontCss();

	//Common Images***************************************************

	@Source("org/kuali/student/common/ui/theme/standard/public/images/common/kru_logo.png")
	public ImageResource headerImage();

	@Source("org/kuali/student/common/ui/theme/standard/public/images/common/powered_by_ks.png")
	public ImageResource footerImage();

	@Source("org/kuali/student/common/ui/theme/standard/public/images/common/gradcap-1.png")
	public ImageResource curriculumManagementImage();

	@Source("org/kuali/student/common/ui/theme/standard/public/images/common/spacer.gif")
	public ImageResource spacer();

    // set the resource value to an image of a question mark
    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/question.png")
    public ImageResource helpIcon();

    // set the resource value to an image of a green checkmark
    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/agt_action_success.png")
    public ImageResource okIcon();

    // set the resource value to an image of a red stop sign with x
    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/agt_stop.png")
    public ImageResource errorIcon();

    // set the resource value to an image of a yellow triangle with exclamation point
    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/messagebox_warning.png")
    public ImageResource warningIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/exclamation-diamond-frame.png")
    public ImageResource warningDiamondIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/asterisk_orange.png")
    public ImageResource asterisk();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/twiddler3.gif")
    public ImageResource spinner();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/blue-gradient.jpg")
    public DataResource blueGradient();

    @Source("org/kuali/student/common/ui/theme/standard/public/css/border-radius.htc")
    public DataResource ieRadius();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/14_pencil.png")
    public ImageResource editComment();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/fileclose.png")
    public ImageResource deleteComment();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/dropdown_black.gif")
    public ImageResource dropdownBlack();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/dropdown_white.gif")
    public ImageResource dropdownWhite();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/dropdown_blue.gif")
    public ImageResource dropdownCustom();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/magnifier-right.png")
    public ImageResource rightMagnifierIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/magnifier-left.png")
    public ImageResource leftMagnifierIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/light-bulb.png")
    public ImageResource lightBulbIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/question.png")
    public ImageResource questionIcon();

    //Rich Text Editor Images***************************************************

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/bold.gif")
    public ImageResource bold();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/createLink.gif")
    public ImageResource createLink();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/hr.gif")
    public ImageResource hr();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/indent.gif")
    public ImageResource indent();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/insertImage.gif")
    public ImageResource insertImage();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/italic.gif")
    public ImageResource italic();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/justifyCenter.gif")
    public ImageResource justifyCenter();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/justifyLeft.gif")
    public ImageResource justifyLeft();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/justifyRight.gif")
    public ImageResource justifyRight();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/ol.gif")
    public ImageResource ol();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/outdent.gif")
    public ImageResource outdent();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/removeFormat.gif")
    public ImageResource removeFormat();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/removeLink.gif")
    public ImageResource removeLink();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/strikeThrough.gif")
    public ImageResource strikeThrough();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/subscript.gif")
    public ImageResource subscript();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/superscript.gif")
    public ImageResource superscript();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/ul.gif")
    public ImageResource ul();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/underline.gif")
    public ImageResource underline();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/popout.png")
    public ImageResource popout();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/user-white.png")
    public ImageResource personIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/documents-stack.png")
    public ImageResource documentIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/balloon.png")
    public ImageResource commentIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/flask.png")
    public ImageResource analysisIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/application-list.png")
    public ImageResource applicationIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/book-open.png")
    public ImageResource bookIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/spacer_icon.png")
    public ImageResource spacerIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/node.png")
    public ImageResource nodeIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/users.png")
    public ImageResource peopleIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/users.png")
    public ImageResource disclosureOpenedIcon();

    @Source("org/kuali/student/common/ui/theme/standard/public/images/common/users.png")
    public ImageResource disclosureClosedIcon();

}
