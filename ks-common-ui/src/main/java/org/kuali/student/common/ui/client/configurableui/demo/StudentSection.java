package org.kuali.student.common.ui.client.configurableui.demo;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.ConfigurableLayoutSection;
import org.kuali.student.common.ui.client.configurable.LayoutSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StudentSection <T extends Idable> extends ConfigurableLayoutSection<T> {
    protected final VerticalPanel panel = new VerticalPanel();
    private final Label sectionTitleLabel = new Label();
    private final Label instructionsLabel = new Label();
    private final SimplePanel buttonPanel = new SimplePanel();
    private final KSFormLayoutPanel form = new KSFormLayoutPanel();
    private final List<ConfigurableField<T>> fields = new ArrayList<ConfigurableField<T>>();

    public StudentSection(){
        super.initWidget(panel);
        panel.add(sectionTitleLabel);
        panel.add(instructionsLabel);
        
        
        panel.add(form);        
        panel.add(buttonPanel);
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

    public ConfigurableLayoutSection<T> addSection(ConfigurableLayoutSection<T> section) {
        super.addSection(section);
        panel.add(section);
        return null;
        
    }
    @Override
    public ConfigurableLayoutSection<T> addField(ConfigurableField<T> field) {
        fields.add(field);
        form.addFormField(field.getFormField());
        return this;
    }

    @Override
    public void populate() {
        super.populateChildSection();
        
    }

    @Override
    public void updateObject() {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        
    }

    @Override
    public void validate(Callback<ValidationResultInfo.ErrorLevel> callback) {
      super.validateChildSection(callback);
        
    }

}
