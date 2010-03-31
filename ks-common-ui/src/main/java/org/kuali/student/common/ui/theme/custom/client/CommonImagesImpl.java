package org.kuali.student.common.ui.theme.custom.client;

import org.kuali.student.common.ui.client.theme.CommonImages;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.theme.standard.client.KSClientBundle;

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
	public KSImage getSpacer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KSImage getCurriculumManagementImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KSImage getDropDownIconBlack() {
		return null;
	}

	@Override
	public KSImage getDropDownIconCustom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KSImage getDropDownIconWhite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KSImage getLightBulbIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KSImage getSearchIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KSImage getProgressSpinner() {
		return new KSImage(KSClientBundle.INSTANCE.spinner());
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
	public KSImage getSpacerIcon() {
		return new KSImage(KSClientBundle.INSTANCE.spacerIcon());
	}

	@Override
	public KSImage getDisclosureClosedIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KSImage getDisclosureOpenedIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KSImage getFooterImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KSImage getWarningDiamondIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
