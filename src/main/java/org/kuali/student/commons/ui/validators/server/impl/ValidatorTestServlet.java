package org.kuali.student.commons.ui.validators.server.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.commons.ui.validators.client.Validator;
import org.kuali.student.commons.ui.validators.client.ValidatorDefinition;
import org.kuali.student.commons.ui.validators.client.ValidatorService;
import org.kuali.student.commons.ui.validators.server.ServerValidator;

/**
 * Just used to test server validator. TODO move into a JUnit test
 */
public class ValidatorTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ValidatorService impl = new ValidatorServiceImpl();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();

        ValidatorDefinition def = impl.getValidatorDefinition("number");
        List<Object> values = new ArrayList<Object>();
        values.add("adsf");
        values.add(1);
        values.add(50);
        values.add(999);
        values.add(";1;1;1");
        values.add("");

        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("minValue", "10");
        attributes.put("maxValue", "100");
        attributes.put("required", "true");

        Map<String, String> m = new HashMap<String, String>();

        m.put("failedMinLength", "${valueName} must be at least ${minLength} characters.");
        m.put("failedMaxLength", "${valueName} cannot be more than ${maxLength} characters.");
        m.put("failedRange", "${valueName} must be between ${minValue} and ${maxValue}");
        m.put("failedMinValue", "${valueName} must be at least ${minValue}");
        m.put("failedMaxValue", "${valueName} cannot be more than ${maxValue}");
        m.put("isRequired", "${valueName} is required");
        m.put("mustBeNumeric", "${valueName} must be numeric");

        Messages messages = new Messages("testmessages", m);

        for (Object o : values) {
            Validator cv = new ServerValidator(def.getScript(), attributes);
            cv.setMessages(messages);
            ValidationResult result = cv.validate(o);
            String msgs = "";
            for (String s : result.getMessages()) {
                msgs += s + "; ";
            }
            out.println(o.toString() + " = " + result.getErrorLevel().toString() + "; " + msgs);
        }

        out.flush();
        out.close();
    }

}
