package org.kuali.student.lum.ui.theme.standard.client;

import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.lum.lu.ui.main.client.theme.LumImages;
import org.kuali.student.lum.lu.ui.main.client.theme.LumTheme;

public class LumImagesImpl implements LumImages{

	@Override
	public KSImage getAddProposalImage() {
		return new KSImage(LumClientBundle.INSTANCE.proposeCurriculumImage());
	}

	@Override
	public KSImage getAnalyzeCurriculumImage() {
		return new KSImage(LumClientBundle.INSTANCE.analyzeCurriculumImage());
	}

	@Override
	public KSImage getCurriculumManagementImage() {
		return new KSImage(LumClientBundle.INSTANCE.curriculumManagementImage());
	}

}
