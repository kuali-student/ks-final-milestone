package org.kuali.student.common.ui.client.event;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.mvc.UncheckedApplicationEvent;

public class SectionUpdateEvent extends UncheckedApplicationEvent<SectionUpdateHandler>{
    public static final Type<SectionUpdateHandler> TYPE = new Type<SectionUpdateHandler>();
	private Section section;
	
	public void setSection(Section section){
		this.section = section;
	}
	
	public Section getSection(){
		return section;
	}

	@Override
	protected void dispatch(SectionUpdateHandler handler) {
		handler.onSectionUpdate(this);
	}

	@Override
	public Type<SectionUpdateHandler> getAssociatedType() {
		return TYPE;
	}

}
