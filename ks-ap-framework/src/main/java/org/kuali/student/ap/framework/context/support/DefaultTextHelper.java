package org.kuali.student.ap.framework.context.support;

import java.io.Serializable;

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
import org.kuali.student.r2.common.messages.service.MessageService;

public class DefaultTextHelper implements TextHelper, Serializable {

	private static final long serialVersionUID = -616654137052936870L;

	private String messageGroup;

	public String getMessageGroup() {
		return messageGroup;
	}

	public void setMessageGroup(String messageGroup) {
		this.messageGroup = messageGroup;
	}

	@Override
	public String getText(String messageCode) {
		MessageService msg = KsapFrameworkServiceLocator.getMessageService();
		KsapContext ksapCtx = KsapFrameworkServiceLocator.getContext();
		ContextInfo ctx = ksapCtx.getContextInfo();
		LocaleInfo locale = ctx.getLocale();
		try {
			return msg.getMessage(locale, messageGroup, messageCode, ctx)
					.getValue();
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("MSG lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("MSG lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalStateException("MSG lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("MSG lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("MSG lookup failure", e);
		}
	}

}
