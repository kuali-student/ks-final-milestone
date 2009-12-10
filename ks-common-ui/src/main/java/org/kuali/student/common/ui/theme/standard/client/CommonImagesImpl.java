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
	public KSImage getProgressSpinner() {
		return new KSImage(KSClientBundle.INSTANCE.spinner());
	}

	@Override
    public KSImage getWarningIcon() {
        return new KSImage(KSClientBundle.INSTANCE.warningIcon());
    }

    @Override
    public KSImage getSpacer() {
        return new KSImage(KSClientBundle.INSTANCE.spacer());
    }

    @Override
    public KSImage getSearchIcon() {
        return new KSImage(KSClientBundle.INSTANCE.rightMagnifierIcon());
    }
}
