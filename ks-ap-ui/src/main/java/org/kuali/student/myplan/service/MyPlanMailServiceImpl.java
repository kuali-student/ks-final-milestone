package org.kuali.student.myplan.service;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.mail.*;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.exception.InvalidAddressException;

import org.kuali.rice.krad.util.GlobalVariables;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Wrapper service for the Rice mailer which allows the mailer to be put
 * in "test" mode where messages will only be sent to the logged in user.
 */
public class MyPlanMailServiceImpl implements MyPlanMailService {

    private final Logger logger = Logger.getLogger(MyPlanMailServiceImpl.class);

    static final boolean IS_EMAIL_TEST_MODE_DEFAULT = true;
    private Mailer mailer;

    /**
     * Spring-injected Mailer.
     */
    public void setMailer(Mailer mailer) {
        this.mailer = mailer;
    }

    /**
     * Reads the mode from a system property.
     */
    private boolean isTestMode() {
        return ConfigContext.getCurrentContextConfig().getBooleanProperty(MODE_CONFIG_PROPERTY_NAME, IS_EMAIL_TEST_MODE_DEFAULT);
    }

    @Override
    public void sendMessage(MailMessage message) throws InvalidAddressException, MessagingException {
        List<String> bccAddresses = new ArrayList<String>(message.getBccAddresses());
        List<String> toAddresses = new ArrayList<String>(message.getToAddresses());
        List<String> ccAddresses = new ArrayList<String>(message.getCcAddresses());
        EmailFrom from = new EmailFrom(message.getFromAddress());
        EmailBcList bcc = new EmailBcList(bccAddresses);
        EmailToList to = new EmailToList(toAddresses);
        EmailCcList cc = new EmailCcList(ccAddresses);
        EmailSubject subject = new EmailSubject(message.getSubject());
        EmailBody body = new EmailBody(message.getMessage());
        if (isTestMode()) {
            List<String> toAddress = new ArrayList<String>();
            toAddress.add(ConfigContext.getCurrentContextConfig().getProperty(TEST_MODE_TO_ADDRESS));
            to.setToAddress(toAddress);
        } else {
            logger.info(String.format("Sending e-mail with no address substitutions in message [%s] from [%s].", message.getSubject(), message.getFromAddress()));
        }
        mailer.sendEmail(from, to, subject, body, cc, bcc, true);
    }


}
