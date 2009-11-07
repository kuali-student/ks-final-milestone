package org.kuali.student.core.organization.ui.client.mvc.view;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.VerticalSection;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.Type;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSTextBox;

public class OrgConfigurerFactory {

	public enum OrgSections {
		ORG_INFO, ORG_RELATIONS, POSITIONS, PERSON_RELATIONS
	}

	private static final String STYLE_SECTION_DIVIDER = "STYLE_SECTION_DIVIDER";
	private static final String STYLE_SECTION = "STYLE_SECTION";
	
	public static VerticalSection getOrgInfoSection(){
        VerticalSection section = new VerticalSection();
        section.addStyleName(STYLE_SECTION_DIVIDER);
        section.addStyleName(STYLE_SECTION);
        section.setSectionTitle(SectionTitle.generateH1Title("SomeTitle"));
        section.addField(new FieldDescriptor("type", "Type", Type.STRING, new OrgTypePicker()));
        section.addField(new FieldDescriptor("longName", "Name", Type.STRING));
        section.addField(new FieldDescriptor("shortName", "Abbreviation", Type.STRING));
        section.addField(new FieldDescriptor("longDesc", "Description", Type.STRING, new KSTextBox()));
        section.addField(new FieldDescriptor("effectiveDate", "Effective Date", Type.DATE, new KSDatePicker()));
        section.addField(new FieldDescriptor("expirationDate", "Expiration Date", Type.DATE, new KSDatePicker()));
        return section;
	}

}
