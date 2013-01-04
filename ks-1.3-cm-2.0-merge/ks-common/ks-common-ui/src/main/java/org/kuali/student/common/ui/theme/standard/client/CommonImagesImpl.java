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

import org.kuali.student.common.ui.client.theme.CommonImages;

import com.google.gwt.user.client.ui.Image;

@Deprecated
public class CommonImagesImpl implements CommonImages{
	@Override
	public Image getAsterisk() {
		return new Image(KSClientBundle.INSTANCE.asterisk());
	}

	@Override
	public Image getHelpIcon() {
		return new Image(KSClientBundle.INSTANCE.helpIcon());
	}

	@Override
	public Image getDeleteCommentIcon() {
		return new Image(KSClientBundle.INSTANCE.deleteComment());
	}

	@Override
	public Image getEditCommentIcon() {
		return new Image(KSClientBundle.INSTANCE.editComment());
	}

	@Override
	public Image getErrorIcon() {
		return new Image(KSClientBundle.INSTANCE.errorIcon());
	}

	@Override
	public Image getOkIcon() {
		return new Image(KSClientBundle.INSTANCE.okIcon());
	}


	@Override
	public Image getWarningIcon() {
		return new Image(KSClientBundle.INSTANCE.warningIcon());
	}
	
	
    @Override
	public Image getWarningDiamondIcon() {
		return new Image(KSClientBundle.INSTANCE.warningDiamondIcon());
	}

	@Override
	public Image getProgressSpinner() {
		return new Image(KSClientBundle.INSTANCE.spinner());
	}

	@Override
	public Image getSpacer() {
		return new Image(KSClientBundle.INSTANCE.spacer());
	}
	
	@Override
	public Image getCurriculumManagementImage() {
		return new Image(KSClientBundle.INSTANCE.curriculumManagementImage());
	}

	@Override
	public Image getDropDownIconBlack() {
		return new Image(KSClientBundle.INSTANCE.dropdownBlack());
	}

	@Override
	public Image getDropDownIconCustom() {
		return new Image(KSClientBundle.INSTANCE.dropdownCustom());
	}

	@Override
	public Image getDropDownIconWhite() {
		return new Image(KSClientBundle.INSTANCE.dropdownWhite());
	}

	@Override
	public Image getLightBulbIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getSearchIcon() {
		return new Image(KSClientBundle.INSTANCE.rightMagnifierIcon());
	}

	@Override
	public Image getAnalysisIcon() {
		return new Image(KSClientBundle.INSTANCE.analysisIcon());
	}

	@Override
	public Image getCommentIcon() {
		return new Image(KSClientBundle.INSTANCE.commentIcon());
	}

	@Override
	public Image getDocumentIcon() {
		return new Image(KSClientBundle.INSTANCE.documentIcon());
	}

	@Override
	public Image getPersonIcon() {
		return new Image(KSClientBundle.INSTANCE.personIcon());
	}
	
	@Override
	public Image getSpacerIcon(){
		return new Image(KSClientBundle.INSTANCE.spacerIcon());
	}
	
	@Override
	public Image getApplicationIcon(){
		return new Image(KSClientBundle.INSTANCE.applicationIcon());		
	}

	@Override
	public Image getBookIcon() {
		return new Image(KSClientBundle.INSTANCE.bookIcon());
	}

	@Override
	public Image getNodeIcon() {
		return new Image(KSClientBundle.INSTANCE.nodeIcon());	
	}

	@Override
	public Image getPeopleIcon() {
		return new Image(KSClientBundle.INSTANCE.peopleIcon());
	}

	@Override
	public Image getDisclosureClosedIcon() {
		return new Image(KSClientBundle.INSTANCE.disclosureClosedIcon());
	}

	@Override
	public Image getDisclosureOpenedIcon() {
		return new Image(KSClientBundle.INSTANCE.disclosureOpenedIcon());
	}

	@Override
	public Image getFooterImage() {
		return new Image(KSClientBundle.INSTANCE.footerImage());
	}
	
	public Image getRiceIcon() {
		return new Image(KSClientBundle.INSTANCE.riceIcon());
	}
	
}
