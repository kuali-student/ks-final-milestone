package org.kuali.student.common.uif.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupInputField;
import org.kuali.rice.krad.lookup.LookupView;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.control.TextControl;
import org.kuali.rice.krad.uif.element.Link;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.student.common.uif.service.KSLookupable;
import org.kuali.student.common.uif.view.KSLookupView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

/**
 * Base class for the KS lookupable implementation.
 *
 * @author Kuali Student Team
 */
public class KSLookupableImpl extends LookupableImpl implements KSLookupable {

    public static final Logger logger = LoggerFactory.getLogger(KSLookupableImpl.class);

    /**
     * Override this method to set the title that is used as a tooltip on the select action as null as the user
     * does not need to see the ids.
     *
     * @param returnLink
     * @param model
     */
    @Override
    public void buildReturnUrlForResult(Link returnLink, Object model) {
        super.buildReturnUrlForResult(returnLink, model);
        returnLink.setTitle(null);
    }

    public void generateLookupResultsNotFoundMessage(Message message,Object model) {

        LookupForm form = (LookupForm) model;

        if(form.getLookupResults() != null && form.getLookupResults().isEmpty()){

            logger.debug("Generating No results found error.");

            LookupView view = (LookupView)message.getContext().get(UifConstants.ContextVariableNames.VIEW);
            String defaultInfo = generateDefaultLookupResultsNotFoundMessage(message, model, view);

            message.setMessageText(defaultInfo);
        }

    }

    protected String generateDefaultLookupResultsNotFoundMessage(Message message, Object model, LookupView view){

        LookupForm form = (LookupForm) model;

        String infoMessage = (String)((Map)message.getContext().get(UifConstants.ContextVariableNames.CONFIG_PROPERTIES)).get("info.lookup.no.result");

        if (StringUtils.isBlank(infoMessage)){
            logger.warn("Key 'info.lookup.no.result' not found.");
            infoMessage = "Your search, \"{0}\", did not match any records.";
        }

        StringBuilder criteriaFieldValues = new StringBuilder();

        for(Component criteriaComponent : view.getCriteriaGroup().getItems()) {
            if(criteriaComponent instanceof LookupInputField && ((LookupInputField) criteriaComponent).getControl() instanceof TextControl) {

                LookupInputField inputField = (LookupInputField)criteriaComponent;

                String criteriaValue = form.getLookupCriteria().get(inputField.getPropertyName());

                if (StringUtils.isNotBlank(criteriaValue)){
                    criteriaFieldValues.append(criteriaValue + ", ");
                }

            }
        }

        String criteriaValues = StringUtils.removeEnd(criteriaFieldValues.toString(), ", ");

        MessageFormat messageFormat = new MessageFormat(infoMessage);
        String[] args = { criteriaValues };

        return messageFormat.format(args);
    }

}
