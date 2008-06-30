package org.kuali.student.commons.ui.validators.client;

import com.google.gwt.core.client.EntryPoint;

public class ValidatorsEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		// do nothing
	}
//	public static final String TEST_SCRIPT = 
//		"var valueName = getAttributeString('valueName');\n" + 
//		"var minLength = getAttributeNumber('minLength', 0);\n" + 
//		"var value = getValueString();\n" + 
//		"\n" + 
//		"addWarning('Minimum length for ' + valueName + ' is ' + minLength + ' and you entered ' + value);\n" + 
//		"\n" + 
//		"if (value.length < minLength) {\n" + 
//		"	addError('DOH! ' + value + ' is less than ' + minLength + ' characters long!');\n" + 
//		"}\n";
//	final TextArea text = new TextArea();
//	
//	public void onModuleLoad() {
//		text.setHeight("480px");
//		text.setWidth("640px");
//		RootPanel.get().add(text);
//		
//		Map<String, String> m = new HashMap<String, String>();
//		m.put("minLength", "4");
//		
//		ClientValidator v = new ClientValidator(TEST_SCRIPT, m);
//		ValidationResult result = v.validate("a", "Some Code");
//		dump(result);
//		
//		result = v.validate("bork", "Some Code");
//		dump(result);
//	}
//
//	private void dump(ValidationResult result) {
//		text.setText(text.getText() + result.getErrorLevel().toString() + "\n");
//		for (String s : result.getMessages()) {
//			text.setText(text.getText() + s + "\n");
//		}
//		text.setText(text.getText() +"**************************************\n");
//	}
}
