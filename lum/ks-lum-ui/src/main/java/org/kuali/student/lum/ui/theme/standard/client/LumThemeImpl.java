package org.kuali.student.lum.ui.theme.standard.client;

import org.kuali.student.lum.lu.ui.main.client.theme.LumCss;
import org.kuali.student.lum.lu.ui.main.client.theme.LumImages;
import org.kuali.student.lum.lu.ui.main.client.theme.LumTheme;
import org.kuali.student.lum.lu.ui.main.client.theme.LumWidgets;

import com.google.gwt.core.client.GWT;

public class LumThemeImpl implements LumTheme{

	private final LumImages lumImages = GWT.create(LumImagesImpl.class);
	private final LumWidgets lumWidgets = GWT.create(LumWidgetsImpl.class);
	private final LumCss lumCss = GWT.create(LumCssImpl.class);
	
	@Override
	public LumCss getLumCss() {
		return lumCss;
	}

	@Override
	public LumImages getLumImages() {
		return lumImages;
	}

	@Override
	public LumWidgets getLumWidgets() {
		return lumWidgets;
	}

}
