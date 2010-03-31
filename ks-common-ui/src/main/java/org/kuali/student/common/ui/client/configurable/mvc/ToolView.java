package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSImage;

public interface ToolView extends View{
	public void setController(Controller controller);
	public Enum<?> getViewEnum();
	public KSImage getImage();
}
