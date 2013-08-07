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

package org.kuali.student.lum.ui.theme.standard.client;

import com.google.gwt.core.client.GWT;

import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface LumClientBundle extends ClientBundleWithLookup{
	public static final LumClientBundle INSTANCE =  GWT.create(LumClientBundle.class);

	@Source("org/kuali/student/lum/ui/theme/standard/public/css/LumLandingPage.css")
	@CssResource.NotStrict
	public CssResource lumLandingPageCss();
	
	@Source("org/kuali/student/lum/ui/theme/standard/public/css/LumMain.css")
	@CssResource.NotStrict
	public CssResource lumMainCss();

	@Source("org/kuali/student/lum/ui/theme/standard/public/css/Course.css")
	@CssResource.NotStrict
	public CssResource courseCss();

	@Source("org/kuali/student/lum/ui/theme/standard/public/css/Program.css")
	@CssResource.NotStrict
	public CssResource programCss();

	@Source("org/kuali/student/lum/ui/theme/standard/public/images/gradcap-1.png")
	@CssResource.NotStrict
	public ImageResource curriculumManagementImage();
	
	@Source("org/kuali/student/lum/ui/theme/standard/public/images/gear.png")
	@CssResource.NotStrict
	public ImageResource analyzeCurriculumImage();
	
	@Source("org/kuali/student/lum/ui/theme/standard/public/images/plus-circle.png")
	@CssResource.NotStrict
	public ImageResource proposeCurriculumImage();

	@Source("org/kuali/student/lum/ui/theme/standard/public/css/PagingScrollTable.css")
	@CssResource.NotStrict
	public CssResource pagingScrollTableCss();

}
