package org.kuali.student.ap.i18n;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.kuali.student.r2.common.util.constants.MessageServiceConstants;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * This class provides a ResourceBundle.Control implementation that uses the {@link MessageService} and the Locale from a {@link ContextInfo}
 * @author Chris Maurer <chmaurer@iu.edu>
 */
public class DBResourceBundleControlImpl extends ResourceBundle.Control {

    private static final Logger LOG = Logger.getLogger(DBResourceBundleControlImpl.class);

    private static final String FORMAT_DB = "DB";

    private String messageGroup;
    private MergedPropertiesResourceBundleImpl parent;
    private ContextInfo contextInfo;
    private MessageService messageService;

    public DBResourceBundleControlImpl(String messageGroup, ContextInfo contextInfo, MergedPropertiesResourceBundleImpl parent) {
        this.messageGroup = messageGroup;
        this.parent = parent;
        this.contextInfo = contextInfo;
    }

    @Override
    public List<String> getFormats(String baseName) {
        return Collections.singletonList(FORMAT_DB);
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
            throw new NullPointerException();
        }
        if (!format.equals(FORMAT_DB)) {
            return null;
        }
        Properties p = new Properties();

        if(contextInfo == null){
            contextInfo = new ContextInfo();
        }
        LocaleInfo localeInfo = LocaleHelper.locale2LocaleInfo(locale);
        List<MessageInfo> messages = new ArrayList<MessageInfo>();
        LOG.debug("Attempting to create a DBResourceBundleImpl with locale:" + locale.toString() + " and parent:" + parent);
        try {
            messages = getMessageService().getMessagesByGroup(localeInfo, messageGroup, contextInfo);
        } catch (Exception e) {
            LOG.error("Unable to load messages with the group: " + messageGroup + " and locale: " + locale.toString(), e);
        }

        for (MessageInfo mi : messages) {
            LOG.debug(mi.getLocale().toString() + "-" + mi.getMessageKey() + "->" + mi.getValue());
            p.setProperty(mi.getMessageKey(), mi.getValue());
        }

        return new DBResourceBundleImpl(p, locale, parent);
    }

    private MessageService getMessageService() {
        if (messageService == null) {
            messageService = GlobalResourceLoader.getService(new QName(MessageServiceConstants.NAMESPACE, "MessageService"));
        }
        return messageService;
    }
}
