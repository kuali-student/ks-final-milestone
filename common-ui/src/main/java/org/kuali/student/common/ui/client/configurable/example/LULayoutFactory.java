package org.kuali.student.common.ui.client.configurable.example;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.LayoutConfigurationException;
import org.kuali.student.common.ui.client.configurable.PropertyBinding;
import org.kuali.student.common.ui.client.configurable.example.dto.MockCluInfo;
import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.validator.DictionaryConstraint;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;

import com.google.gwt.user.client.ui.TextBox;

public class LULayoutFactory {
	private final ObjectStructure structure;
	private final Validator validator;
	private final Map<String, Map<String, Field>> indexedFields = new HashMap<String, Map<String,Field>>();
	
	public LULayoutFactory(ObjectStructure structure, Validator validator) {
		this.structure = structure;
		this.validator = validator;
	}
	
	public ConfigurableLayout<MockCluInfo> getLayout(String type, String state) {
		if (type.equalsIgnoreCase("course") && state.equalsIgnoreCase("proposed")) {
			return getProposedCourseLayout(getFields(type, state));
		} else {
			throw new LayoutConfigurationException("Invalid type/state: " + type + ", " + state);
		}
	}

	private Map<String, Field> getFields(String type, String state) {
		Map<String, Field> result = indexedFields.get(type.toLowerCase() + ":" + state.toLowerCase());
		
		if (result == null) {
			for (Type t : structure.getType()) {
				if (t.getKey().equalsIgnoreCase(type)) {
					for (State s : t.getState()) {
						if (s.getKey().equalsIgnoreCase(state)) {
							result = new HashMap<String, Field>();
							for (Field f : s.getField()) {
								result.put(f.getKey(), f);
							}
							indexedFields.put(type.toLowerCase() + ":" + state.toLowerCase(), result);
						}
					}
					break;
				}
			}
		}
		
		return result;
	}

	

	private ConfigurableLayout<MockCluInfo> getProposedCourseLayout(
			final Map<String, Field> fields) {
		return new DefaultCreateUpdateLayout<MockCluInfo>() {{
			addSection(new String[] {"Proposal Information", "Author + Collaborators"}, 
					new SimpleConfigurableSection<MockCluInfo>()
					.addField(new ConfigurableField<MockCluInfo>()
						.setBinding(new PropertyBinding<MockCluInfo>() {
							@Override
							public Object getValue(MockCluInfo object) {
								// TODO figure out how to get the originating faculty member
								return "Bob";
							}
							@Override
							public void setValue(MockCluInfo object, Object value) {
								// TODO figure out which field is the originating faculty member
							}
						})
						.setFormField(new KSFormField("originatingFacultyMember", "Originating Faculty Member")
							.setWidget(new TextBox())
							.setHelpInfo(new HelpInfo()
								.setId("myhelpid")
								.setShortVersion("help short version")
								.setTitle("help title")
								.setUrl("http://www.google.com/")
							)
							.addConstraint(new DictionaryConstraint(validator, fields.get("originatingFacultyMember")))
						)
					)
					.addField(new ConfigurableField<MockCluInfo>()
						.setBinding(new PropertyBinding<MockCluInfo>() {
							@Override
							public Object getValue(MockCluInfo object) {
								// TODO figure out how to get the administrative delegate
								return "Jim";
							}
							@Override
							public void setValue(MockCluInfo object, Object value) {
								// TODO figure out which field is the administrative delegate
							}
						})
						.setFormField(new KSFormField("administrativeDelegate", "Administrative Delegate")
							.setWidget(new TextBox())
							.setHelpInfo(new HelpInfo()
								.setId("myhelpid")
								.setShortVersion("help short version")
								.setTitle("help title")
								.setUrl("http://www.google.com/")
							)
							.addConstraint(new DictionaryConstraint(validator, fields.get("administrativeDelegate")))
						)
					)
					.setSectionTitle("Author + Collaborators")
					.setInstructions("Instructions go here...")
						
			);
			

			addSection(new String[] {"Proposal Information", "Governance"}, 
					new SimpleConfigurableSection<MockCluInfo>()
					.addField(new ConfigurableField<MockCluInfo>()
						.setBinding(new PropertyBinding<MockCluInfo>() {
							@Override
							public Object getValue(MockCluInfo object) {
								// TODO figure out how to get the curriculum oversight
								return "graduate";
							}
							@Override
							public void setValue(MockCluInfo object, Object value) {
								// TODO figure out which field is the curriculum oversight
							}
						})
						.setFormField(new KSFormField("curriculumOversight", "Curriculum Oversight")
							.setWidget(new TextBox())
							.setHelpInfo(new HelpInfo()
								.setId("myhelpid")
								.setShortVersion("help short version")
								.setTitle("help title")
								.setUrl("http://www.google.com/")
							)
							.addConstraint(new DictionaryConstraint(validator, fields.get("curriculumOversight")))
						)
					)
					.addField(new ConfigurableField<MockCluInfo>()
						.setBinding(new PropertyBinding<MockCluInfo>() {
							@Override
							public Object getValue(MockCluInfo object) {
								// finally, a field with an obvious cluinfo mapping
								return object.getAdminOrg();
							}
							@Override
							public void setValue(MockCluInfo object, Object value) {
								object.setAdminOrg(value.toString());
							}
						})
						.setFormField(new KSFormField("adminOrg", "Administering Organization")
							.setWidget(new TextBox())
							.setHelpInfo(new HelpInfo()
								.setId("myhelpid")
								.setShortVersion("help short version")
								.setTitle("help title")
								.setUrl("http://www.google.com/")
							)
							.addConstraint(new DictionaryConstraint(validator, fields.get("adminOrg")))
						)
					)
					.setSectionTitle("Governance")
					.setInstructions("Instructions go here...")
						
			);

		}};
	}


	
}
