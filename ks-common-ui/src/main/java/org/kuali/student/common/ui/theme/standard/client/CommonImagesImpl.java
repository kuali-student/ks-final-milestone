package org.kuali.student.common.ui.theme.standard.client;

import org.kuali.student.common.ui.client.theme.CommonImages;
import org.kuali.student.common.ui.client.widgets.KSImage;

public class CommonImagesImpl implements CommonImages{
	@Override
	public KSImage getAsterisk() {
		return new KSImage(KSClientBundle.INSTANCE.asterisk());
	}

	@Override
	public KSImage getHelpIcon() {
		return new KSImage(KSClientBundle.INSTANCE.helpIcon());
	}

	@Override
	public KSImage getDeleteCommentIcon() {
		return new KSImage(KSClientBundle.INSTANCE.deleteComment());
	}

	@Override
	public KSImage getEditCommentIcon() {
		return new KSImage(KSClientBundle.INSTANCE.editComment());
	}

	@Override
	public KSImage getErrorIcon() {
		return new KSImage(KSClientBundle.INSTANCE.errorIcon());
	}

	@Override
	public KSImage getOkIcon() {
		return new KSImage(KSClientBundle.INSTANCE.okIcon());
	}


	@Override
	public KSImage getWarningIcon() {
		return new KSImage(KSClientBundle.INSTANCE.warningIcon());
	}
	
	
    @Override
	public KSImage getWarningDiamondIcon() {
		return new KSImage(KSClientBundle.INSTANCE.warningDiamondIcon());
	}

	@Override
	public KSImage getProgressSpinner() {
		return new KSImage(KSClientBundle.INSTANCE.spinner());
	}

	@Override
	public KSImage getSpacer() {
		return new KSImage(KSClientBundle.INSTANCE.spacer());
	}
	
	@Override
	public KSImage getCurriculumManagementImage() {
		return new KSImage(KSClientBundle.INSTANCE.curriculumManagementImage());
	}

	@Override
	public KSImage getDropDownIconBlack() {
		return new KSImage(KSClientBundle.INSTANCE.dropdownBlack());
	}

	@Override
	public KSImage getDropDownIconCustom() {
		return new KSImage(KSClientBundle.INSTANCE.dropdownCustom());
	}

	@Override
	public KSImage getDropDownIconWhite() {
		return new KSImage(KSClientBundle.INSTANCE.dropdownWhite());
	}

	@Override
	public KSImage getLightBulbIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KSImage getSearchIcon() {
		return new KSImage(KSClientBundle.INSTANCE.rightMagnifierIcon());
	}

	@Override
	public KSImage getAnalysisIcon() {
		return new KSImage(KSClientBundle.INSTANCE.analysisIcon());
	}

	@Override
	public KSImage getCommentIcon() {
		return new KSImage(KSClientBundle.INSTANCE.commentIcon());
	}

	@Override
	public KSImage getDocumentIcon() {
		return new KSImage(KSClientBundle.INSTANCE.documentIcon());
	}

	@Override
	public KSImage getPersonIcon() {
		return new KSImage(KSClientBundle.INSTANCE.personIcon());
	}
	
	@Override
	public KSImage getSpacerIcon(){
		return new KSImage(KSClientBundle.INSTANCE.spacerIcon());
	}
	
	@Override
	public KSImage getApplicationIcon(){
		return new KSImage(KSClientBundle.INSTANCE.applicationIcon());		
	}

	@Override
	public KSImage getBookIcon() {
		return new KSImage(KSClientBundle.INSTANCE.bookIcon());
	}

	@Override
	public KSImage getNodeIcon() {
		return new KSImage(KSClientBundle.INSTANCE.nodeIcon());	
	}

	@Override
	public KSImage getPeopleIcon() {
		return new KSImage(KSClientBundle.INSTANCE.peopleIcon());
	}

	@Override
	public KSImage getDisclosureClosedIcon() {
		return new KSImage(KSClientBundle.INSTANCE.disclosureClosedIcon());
	}

	@Override
	public KSImage getDisclosureOpenedIcon() {
		return new KSImage(KSClientBundle.INSTANCE.disclosureOpenedIcon());
	}

	@Override
	public KSImage getFooterImage() {
		return new KSImage(KSClientBundle.INSTANCE.footerImage());
	}
	
	
}
