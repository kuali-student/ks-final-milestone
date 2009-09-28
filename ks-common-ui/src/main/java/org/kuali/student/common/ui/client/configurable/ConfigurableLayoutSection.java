package org.kuali.student.common.ui.client.configurable;

import org.kuali.student.core.dto.Idable;

@Deprecated
public abstract class ConfigurableLayoutSection<T extends Idable> extends LayoutSection<T>{
	public abstract ConfigurableLayoutSection<T> addField(ConfigurableField<T> field);

}
