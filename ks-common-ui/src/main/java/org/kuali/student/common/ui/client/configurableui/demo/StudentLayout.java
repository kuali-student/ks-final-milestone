package org.kuali.student.common.ui.client.configurableui.demo;

import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.LayoutSection;
import org.kuali.student.core.dto.Idable;


public class StudentLayout <T extends Idable> extends ConfigurableLayout<T> {

    
    
    
    
    @Override
    public ConfigurableLayout<T> addSection(String[] hierarchy, LayoutSection<T> section) {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public void render() {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        
    }

    @Override
    public void selectSection(String... hierarchy) {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        
    }

    
}
