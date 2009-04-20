package org.kuali.student.common.ui.client.configurable;

public abstract class ConfigurableLayoutSection<T extends Object> extends LayoutSection<T>{
	public abstract ConfigurableLayoutSection<T> addField(ConfigurableField<T> field);
}
