package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.ConfigurableLayoutSection;
import org.kuali.student.common.ui.client.configurable.LayoutSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.user.client.ui.Frame;

public class IFrameSection<T extends Idable> extends SimpleConfigurableSection<T>{
	
	protected final Frame frame = new Frame();
	private String url;
	public IFrameSection() {
		super();
		frame.setSize("720px", "500px");
		this.panel.add(frame);
	}

	public LayoutSection<T> setUrl(String url){
		this.url = url;
		frame.setUrl(url);
		return this;
	}
	
	@Override
	public ConfigurableLayoutSection<T> addField(
			ConfigurableField<T> field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void populate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject() {
		// TODO Auto-generated method stub
		frame.setUrl(url);
	}

	@Override
	public void validate(Callback<ValidationResultInfo.ErrorLevel> callback) {
		// TODO Auto-generated method stub
		
	}

}
