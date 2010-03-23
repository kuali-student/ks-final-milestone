package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.View;

public interface ConfigurableLayout {
	public void addStartSection(SectionView section);
	
    public void addSection(String[] hierarchy, SectionView section);

    public void addTool(ToolView tool);
}
