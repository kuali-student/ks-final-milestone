package org.kuali.student.ap.framework.context.support;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.r2.common.dto.ContextInfo;

/**
 * Provides minimal context support for interaction with KS services.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version ksap-0.1.1
 */
public class DefaultKsapContext implements KsapContext {

	private static final ThreadLocal<ContextInfo> TL_CTX = new ThreadLocal<ContextInfo>();

	/**
	 * Servlet filter for integrating HTTP authentication and KIM principal
	 * details with a thread-local KS context.
	 * 
	 * @param req
	 *            The servlet request.
	 * @param resp
	 *            The servlet response.
	 * @param fc
	 *            The active filter chain.
	 */
	public static class Filter implements javax.servlet.Filter {

		@Override
		public void destroy() {
		}

		@Override
		public void init(FilterConfig fc) throws ServletException {
		}

		@Override
		public void doFilter(ServletRequest req, ServletResponse resp,
				FilterChain fc) throws IOException, ServletException {
			try {
				before(KimApiServiceLocator
						.getIdentityService()
						.getPrincipalByPrincipalName(
								((HttpServletRequest) req).getRemoteUser())
						.getPrincipalId());
				fc.doFilter(req, resp);
			} finally {
				after();
			}
		}
	}

	public static void before(String principalId) {
		assert TL_CTX.get() == null : TL_CTX.get();
		ContextInfo ksctx = new ContextInfo();
		ksctx.setAuthenticatedPrincipalId(principalId);
		ksctx.setPrincipalId(principalId);
		ksctx.setCurrentDate(new Date());
		ksctx.getAttributes().clear();
		TL_CTX.set(ksctx);
	}

	public static void after() {
		TL_CTX.remove();
	}

	@Override
	public ContextInfo getContextInfo() {
		ContextInfo rv = TL_CTX.get();
		assert rv != null : "Filter is not active";
		return rv;
	}

}