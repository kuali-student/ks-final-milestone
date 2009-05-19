package org.kuali.student.common.ui.client.configurable.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.ConfigurableLayoutGroup;
import org.kuali.student.common.ui.client.configurable.ConfigurableLayoutSection;
import org.kuali.student.common.ui.client.configurable.LayoutSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Holder;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.kuali.student.core.validation.dto.ValidationResult.ErrorLevel;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimpleConfigurableSection<T extends Idable> extends ConfigurableLayoutSection<T> {
	private final VerticalPanel panel = new VerticalPanel();
	private final Label sectionTitleLabel = new Label();
	private final Label instructionsLabel = new Label();
	private final KSFormLayoutPanel form = new KSFormLayoutPanel();
	
	private final List<ConfigurableField<T>> fields = new ArrayList<ConfigurableField<T>>();
	
	public SimpleConfigurableSection() {
		super.initWidget(panel);
		panel.add(sectionTitleLabel);
		panel.add(instructionsLabel);
		panel.add(form);
	}
	@Override
	public LayoutSection<T> setInstructions(String instructions) {
		instructionsLabel.setText(instructions);
		return super.setInstructions(instructions);
	}

	@Override
	public LayoutSection<T> setSectionTitle(String sectionTitle) {
		sectionTitleLabel.setText(sectionTitle);
		return super.setSectionTitle(sectionTitle);
	}

	@Override
	public ConfigurableLayoutSection<T> addField(ConfigurableField<T> field) {
		fields.add(field);
		form.addFormField(field.getFormField());
		return this;
	}

	@Override
	public void validate(final Callback<ErrorLevel> callback) {
		final Iterator<ConfigurableField<T>> itr = fields.iterator();
		if (itr.hasNext()) {
			final Holder<ErrorLevel> holder = new Holder<ErrorLevel>(ErrorLevel.OK);
			while (itr.hasNext()) {
				itr.next().getFormField().validate(new Callback<ValidationResult>() {
					@Override
					public void exec(ValidationResult result) {
						holder.set(ErrorLevel.max(holder.get(), result.getErrorLevel()));
						if (itr.hasNext()) {
							itr.next().getFormField().validate(this);
						} else {
							callback.exec(holder.get());
						}
					}
				});
			}
		} else {
			callback.equals(ValidationResult.OK);
		}
	}

	@Override
	public void populate() {
		T bound = getParentLayout().getObject();
		for (ConfigurableField<T> cf : fields) {
			cf.getFormField().setValue(cf.getBinding().getValue(bound));
		}
	}

	@Override
	public void updateObject() {
		T bound = getParentLayout().getObject();
		for (ConfigurableField<T> cf : fields) {
			cf.getBinding().setValue(bound, cf.getFormField().getValue());
		}
	}
    @Override
    public ConfigurableLayoutSection<T> addGroup(ConfigurableLayoutGroup<T> field) {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        return null;
    }
}
