package org.kuali.student.ap.framework.context.support;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.kuali.i18n.DBResourceBundleControlImpl;
import org.kuali.i18n.KualiResourceBundle;
import org.kuali.i18n.KualiResourceBundleImpl;
import org.kuali.i18n.LocaleHelper;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.ap.framework.context.TextHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.service.MessageService;

public class DefaultTextHelper implements TextHelper, Serializable {

	private static final long serialVersionUID = -616654137052936870L;

	private String messageGroup;
    private String baseName;

    private KualiResourceBundle krb = null;

	public String getMessageGroup() {
        if (messageGroup == null)
            throw new IllegalArgumentException("messageGroup must be set");
        return messageGroup;
	}

	public void setMessageGroup(String messageGroup) {
		this.messageGroup = messageGroup;
	}

    public String getBaseName() {
        if (baseName == null)
            throw new IllegalArgumentException("baseName must be set");
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    @Override
    public String getText(String messageCode) {
        String value;
        try {
            value = getBundle().getString(messageCode);
        } catch (MissingResourceException mre) {
            value = "\\[missing key (mre): " + baseName + " " + messageCode + "\\]";
        }
        return value;
    }

    @Override
    public String getText(String messageCode, String defaultValue) {
        return getBundle().getString(messageCode, defaultValue);
    }

    @Override
    public String getFormattedMessage(String key, Object... args) {
        return getBundle().getFormattedMessage(key, args);
    }

    private KualiResourceBundle getBundle() {
        KsapContext ksapCtx = KsapFrameworkServiceLocator.getContext();
        LocaleInfo locale = ksapCtx.getContextInfo().getLocale();
        if (krb == null)
            krb = new KualiResourceBundleImpl(getBaseName());

        KualiResourceBundle drb = (KualiResourceBundle) ResourceBundle.getBundle("org.kuali.i18n.DBResourceBundleImpl", LocaleHelper.localeInfo2Locale(locale), new DBResourceBundleControlImpl(getMessageGroup(), krb));

        return drb;
    }

}
