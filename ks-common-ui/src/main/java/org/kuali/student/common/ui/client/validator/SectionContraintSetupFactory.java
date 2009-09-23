package org.kuali.student.common.ui.client.validator;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.Section;
import org.kuali.student.common.validator.ConstraintDataProvider;
import org.kuali.student.common.validator.ConstraintSetupFactory;
import org.kuali.student.core.dictionary.dto.ObjectStructure;

public class SectionContraintSetupFactory implements ConstraintSetupFactory {
    private Section section;
    private ConstraintDataProvider provider;

    public SectionContraintSetupFactory(Section sec, ConstraintDataProvider provider) {
        section = sec;
        this.provider = provider;
    }

    @Override
    public ConstraintDataProvider getDataProvider(Object obj) {

        ConstraintDataProvider mdp = new SectionConstraintDataProvider(section, provider);
        mdp.initialize(obj);

        return mdp;
    }

    @Override
    public ObjectStructure getObjectStructure(String key) {
        return Application.getApplicationContext().getDictionaryData(key);
    }

}
