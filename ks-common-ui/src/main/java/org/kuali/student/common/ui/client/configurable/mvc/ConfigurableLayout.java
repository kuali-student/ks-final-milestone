package org.kuali.student.common.ui.client.configurable.mvc;


public interface ConfigurableLayout {
	
    public void addSection(String[] hierarchy, SectionView section);

    public void addTool(ToolView tool);
}
