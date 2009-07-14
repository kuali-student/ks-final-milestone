package org.kuali.student.common.ui.client.configurableui.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.ConfigurableLayoutSection;
import org.kuali.student.common.ui.client.configurable.LayoutSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Holder;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NameSection <T extends Idable> extends ConfigurableLayoutSection<T> {
    protected final VerticalPanel panel = new VerticalPanel();
    private final Label sectionTitleLabel = new Label();
    private final Label instructionsLabel = new Label();

    private final KSFormLayoutPanel form = new KSFormLayoutPanel();
    private final SimplePanel buttonPanel = new SimplePanel();
    
    private final List<ConfigurableField<T>> fields = new ArrayList<ConfigurableField<T>>();

    public NameSection(){
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

    @Override
    public ConfigurableLayoutSection<T> addField(ConfigurableField<T> field) {
//    private final Label firstNameLabel = new Label();
//        private final Label lastNameLabel = new Label();
        
        fields.add(field);
        form.addFormField(field.getFormField());
        return this;
    }


    @Override
    public void validate(final Callback<ValidationResultInfo.ErrorLevel> callback) {
        final Iterator<ConfigurableField<T>> itr = fields.iterator();
        if (itr.hasNext()) {
            final Holder<ValidationResultInfo.ErrorLevel> holder = new Holder<ValidationResultInfo.ErrorLevel>(ErrorLevel.OK);
            while (itr.hasNext()) {
                itr.next().getFormField().validate(new Callback<ValidationResultContainer>() {
                    @Override
                    public void exec(ValidationResultContainer result) {
                        holder.set(ValidationResultInfo.ErrorLevel.max(holder.get(), result.getErrorLevel()));
                        if (itr.hasNext()) {
                            itr.next().getFormField().validate(this);
                        } else {
                            callback.exec(holder.get());
                        }
                    }
                });
            }
        } else {
            callback.equals(ValidationResultInfo.ErrorLevel.OK);
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
    
    public void addSectionButtons(Widget buttons){
        this.buttonPanel.setWidget(buttons);
    }
    
    public Widget getSectionButtons(){
        return buttonPanel.getWidget();
    }
    
    public void setEditMode(EditMode mode){
        this.form.setEditMode(mode);
    }

    public ConfigurableLayoutSection<T> addSection(ConfigurableLayoutSection<T> section) {
        return null;
    }   

}
