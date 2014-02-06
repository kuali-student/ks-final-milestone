package org.kuali.student.ap.framework.config;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateResolver;

/**
 * Default no-op implementation. Extend this class and override
 * {@link #getSuggestedValue(DependencyDescriptor, String, AbstractBeanDefinition, boolean)}
 * for further overrides.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version 0.4.5
 * @deprecated
 */
// DO NOT USE @Singleton - delegate is controlled externally
public class BaseAutowireCandidateResolver implements
		AutowireCandidateResolver, Serializable {

	private static final long serialVersionUID = -4984096607252167318L;

	private AutowireCandidateResolver delegate;

	public BaseAutowireCandidateResolver() {
	}

	public BaseAutowireCandidateResolver(AutowireCandidateResolver delegate) {
		this.delegate = delegate;
	}

	public void setDelegate(AutowireCandidateResolver delegate) {
		this.delegate = delegate;
	}

	/**
	 * Override this Method to provide custom auto-wiring behavior.
	 * 
	 * @param descriptor
	 *            The Spring dependency description.
	 * @param beanName
	 *            The name of the bean requiring the environment reference.
	 * @param definition
	 *            The bean's bean definition.
	 * @param optional
	 *            True if the resource resource has been marked as optional,
	 *            false if not. When marked as optional, this method should be
	 *            fail-safe and return null rather than throw an exception if
	 *            the resource is not available.
	 * @return The resource value, resolved from the environment, or null if not
	 *         available and option is true.
	 * @throws IllegalArgumentException
	 *             If an external container (not Spring aware) resource is not
	 *             available and optional is false.
	 * @throws BeanCreationException
	 *             If an internal resource is not available and optional is
	 *             false.
	 */
	protected Object getSuggestedValue(DependencyDescriptor descriptor,
			String beanName, AbstractBeanDefinition definition, boolean optional) {
		return null;
	}

	@Override
	public boolean isAutowireCandidate(BeanDefinitionHolder bdHolder,
			DependencyDescriptor descriptor) {
		return delegate != null
				&& delegate.isAutowireCandidate(bdHolder, descriptor);
	}

	@Override
	public final Object getSuggestedValue(DependencyDescriptor descriptor) {
		boolean optional = false;
		for (Annotation a : descriptor.getAnnotations())
			optional = optional || (a instanceof OptionalResource);
		Object rv = null;
		RiceBeanFactory.PopulatingBean spb = RiceBeanFactory
				.getPopulatingBean();
		assert spb != null;
		if (spb != null) {
			rv = getSuggestedValue(descriptor, spb.getBeanName(),
					spb.getDefinition(), optional);
		}
		if (rv == null && delegate != null)
			try {
				rv = delegate.getSuggestedValue(descriptor);
			} catch (BeanCreationException e) {
				if (optional)
					rv = RiceBeanFactory.missingOptionalDependency(
							"Bean not found resolving dependency "
									+ descriptor.getDependencyName() + " "
									+ descriptor.getDependencyType(), e,
							descriptor.getDependencyType());
				else
					throw e;
			} catch (IllegalArgumentException e) {
				if (optional)
					rv = RiceBeanFactory.missingOptionalDependency(
							"Illegal argument resolving dependency "
									+ descriptor.getDependencyName() + " "
									+ descriptor.getDependencyType(), e,
							descriptor.getDependencyType());
				else
					throw e;
			}
		return rv;
	}
}
