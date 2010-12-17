package ca.ubc.student.kuali.lum.lu.ui.client;

import org.kuali.student.common.ui.theme.standard.client.KSClientBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ClientBundle.Source;
public interface CDMClientBundle extends KSClientBundle {
	public static final CDMClientBundle INSTANCE =  GWT.create(CDMClientBundle.class);
	
	@Source("ca/ubc/student/kuali/lum/lu/ui/public/images/ubc_logo.gif")
	public ImageResource headerImage();
	
}
