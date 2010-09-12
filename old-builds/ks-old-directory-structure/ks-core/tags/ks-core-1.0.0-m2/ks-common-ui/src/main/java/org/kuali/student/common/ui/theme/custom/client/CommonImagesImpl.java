package org.kuali.student.common.ui.theme.custom.client;

import org.kuali.student.common.ui.client.theme.CommonImages;
import org.kuali.student.common.ui.client.widgets.KSImage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

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
    public KSImage getSearchIcon() {
        return null;
    }
	
	

}
