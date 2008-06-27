package org.kuali.student.commons.ui.validators.client;

import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.validators.client.lib.BasicNumericValidator;
import org.kuali.student.commons.ui.validators.client.lib.BasicStringValidator;


public class ClientValidatorFactory {
//	static {
//		ObjectFactory.register(BasicNumericValidator.class.getName(), new ObjectCreator() {
//			public Object create() {
//				return new BasicNumericValidator();
//			}
//		});
//		ObjectFactory.register(BasicStringValidator.class.getName(), new ObjectCreator() {
//			public Object create() {
//				return new BasicStringValidator();
//			}
//		});
//	}
	
	public static Validator getValidator(String className) {
		// TODO rewrite validators
		return null;
		//return (Validator) ObjectFactory.create(className);
	}
	public static Validator getValidator(String className, Messages messages, Map<String, String> attributes) {
		Validator result = getValidator(className);
		if (result != null) {
			result.setMessages(messages);
			result.setAttributes(attributes);
		}
		return result;
	}
}
