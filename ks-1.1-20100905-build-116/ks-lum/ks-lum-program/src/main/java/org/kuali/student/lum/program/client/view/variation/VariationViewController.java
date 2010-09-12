package org.kuali.student.lum.program.client.view.variation;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramController;

import com.google.gwt.core.client.GWT;

public class VariationViewController extends ProgramController{

	public VariationViewController(DataModel programModel) {
		super(programModel);
        configurer = GWT.create(VariationViewConfigurer.class);
	}

    @Override
    protected void configureView() {
        super.configureView();
        initialized = true;
    }
}
