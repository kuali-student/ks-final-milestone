package org.kuali.student.ap.framework.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * May be used with auto-wiring annotations to indicate an environment resource
 * that does not need to be provided to a Rice module.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version 0.4.5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OptionalResource {

}
