package org.kuali.student.lum.ui.theme.standard.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface LumClientBundle extends ImmutableResourceBundle{
	public static final LumClientBundle INSTANCE =  GWT.create(LumClientBundle.class);

	@Resource("org/kuali/student/lum/ui/theme/standard/public/css/LumLandingPage.css")
	public CssResource lumLandingPageCss();
	
	@Resource("org/kuali/student/lum/ui/theme/standard/public/css/LumMain.css")
	public CssResource lumMainCss();
    
	@Resource("org/kuali/student/lum/ui/theme/standard/public/images/gradcap-1.png")
	public ImageResource curriculumManagementImage();
	
	@Resource("org/kuali/student/lum/ui/theme/standard/public/images/gear.png")
	public ImageResource analyzeCurriculumImage();
	
	@Resource("org/kuali/student/lum/ui/theme/standard/public/images/plus-circle.png")
	public ImageResource proposeCurriculumImage();

}
