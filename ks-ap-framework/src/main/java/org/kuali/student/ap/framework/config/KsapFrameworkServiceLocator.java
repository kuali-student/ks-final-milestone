package org.kuali.student.ap.framework.config;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.service.CourseService;

/**
 * Convenience factory for acquiring KSAP provided service.
 * 
 * <p>
 * Remote services commonly used by ks-ap are also provided by this locator.
 * </p>
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version ksap-0.1.1
 */
public final class KsapFrameworkServiceLocator {

	/**
	 * Private constructor.
	 */
	private KsapFrameworkServiceLocator() {
	}

	/**
	 * Get a local service.
	 * 
	 * @param serviceName The
	 *            local service name.
	 * @return The local service.
	 * @throws AssertionError
	 *             If assertions are enabled and the service doesn't exist.
	 */
	private static <T> T getLocalService(String serviceName) {
		T rv = GlobalResourceLoader.getService(serviceName);
		assert rv != null : serviceName + " not defined in Rice";
		return rv;
	}

	/**
	 * Get the ks-core remote ATP service.
	 * 
	 * @return The ks-core remote ATP service.
	 */
	public static AtpService getAtpService() {
		return getLocalService("ksCoreAtpService");
	}

	/**
	 * Get the ks-core remote message service.
	 * 
	 * @return The ks-core remote message service.
	 */
	public static MessageService getMessageService() {
		return getLocalService("ksCoreMessageService");
	}

	/**
	 * Get the ks-enroll remote course offering service.
	 * 
	 * @return The ks-enroll remote course offering service.
	 */
	public static CourseOfferingService getCourseOfferingService() {
		return getLocalService("ksEnrollCourseOfferingService");
	}

	/**
	 * Get the ks-enroll remote academic calendar service.
	 * 
	 * @return The ks-enroll remote message service.
	 */
	public static AcademicCalendarService getAcademicCalendarService() {
		return getLocalService("ksEnrollAcalService");
	}

	/**
	 * Get the ks-lum remote course service.
	 * 
	 * @return The ks-lum remote course service.
	 */
	public static CourseService getCourseService() {
		return getLocalService("ksLumCourseService");
	}

	/**
	 * Get the ks-lum remote clu service.
	 * 
	 * @return The ks-lum remote clu service.
	 */
	public static CluService getCluService() {
		return getLocalService("ksLumCluService");
	}

	/**
	 * Get the KSAP context provider.
	 * 
	 * @return The KSAP context provider.
	 */
	public static KsapContext getContext() {
		return getLocalService("ksapContext");
	}

}
