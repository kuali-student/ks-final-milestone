/**
 * 
 */
package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.removeinm4;


import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue;
import org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValueBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.lum.lu.assembly.data.client.LoModelDTO;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.ui.course.client.widgets.LOBuilder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasValue;

/**
 * @author Jim Tomlinson
 * 
 * This whole thing's a complete HACK. Remove in M4 when we have an
 * LoAssembler
 *
 */
public class HasModelDTOValueWidgetBinding implements ModelWidgetBinding<HasModelDTOValue> {

	public static HasModelDTOValueWidgetBinding INSTANCE = new HasModelDTOValueWidgetBinding();

	/*
	@Override
	public void setModelValue(HasModelDTOValue widget, DataModel model, String path) {
		int i = 0;
	}

	@Override
	public void setWidgetValue(HasModelDTOValue widget, DataModel model, String path) {
		int i = 0;
	}
	*/
	private HasModelDTOValueWidgetBinding() {}
	
    @Override
    public void setModelValue(HasModelDTOValue object, DataModel model, String path) {        
        Object value = object.getValue();
        
        if (value instanceof ModelDTOValue) {
        	((LuData) model.getRoot()).setLoModelDTO(new LoModelDTO((ModelDTOValue) value));
        }
    }

    @Override
    public void setWidgetValue(HasModelDTOValue object, DataModel model, String path) {
    	LoModelDTO dto = ((LuData) model.getRoot()).getLoModelDTO();
		if (null != dto) {
    		((LOBuilder) object).setValue(dto.get(LoModelDTO.LO_KEY));
		}
    }
}
