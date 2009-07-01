package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Controller;

public abstract class Section extends LayoutSectionView implements ConfigurableLayoutSection{
    

    protected ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
    protected Map<Enum<?>, NestedSection> sections = new HashMap<Enum<?>, NestedSection>();
    protected List<Object> orderedLayoutList = new ArrayList<Object>();

    public Section(Controller controller, Enum<?> viewEnum, String viewName) {
        super(controller, viewEnum, viewName);
    }

    public Section(Enum<?> viewEnum, String viewName) {
        super(viewEnum, viewName);
    }

    @Override
    public void addSection(Enum<?> subSectionName, NestedSection section) {
        sections.put(subSectionName, section);
        orderedLayoutList.add(section);
    }
    
    @Override
    public void addField(FieldDescriptor fieldDescriptor) {
        fields.add(fieldDescriptor);
        orderedLayoutList.add(fieldDescriptor);
    }
    
}
