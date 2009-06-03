package org.kuali.student.common.ui.client.configurable;

import org.kuali.student.core.dto.Idable;

public abstract class ConfigurableLayoutGroup <T extends Idable> extends LayoutGroup<T>{
    public abstract ConfigurableLayoutGroup<T> addField(ConfigurableField<T> field);
    public abstract ConfigurableLayoutGroup<T> addGroup(ConfigurableLayoutGroup<T> field);
}
