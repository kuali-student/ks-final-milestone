package org.kuali.student.common.ui.theme.custom.client;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.theme.CommonWidgets;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.user.client.ui.Widget;

public class CommonWidgetsImpl implements CommonWidgets{

	@Override
	public List<KSLabel> getHeaderCustomLinks() {
		List<KSLabel> links = new ArrayList<KSLabel>();
		return links;
	}

	@Override
	public Widget getHeaderWidget() {
		return new KSImage(KSClientBundle.INSTANCE.headerImage());
	}

}
