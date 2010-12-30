package org.kuali.maven.mojo;

import org.apache.commons.logging.Log;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.SystemStreamLog;

/**
 * A utility class that sets up logging for maven plugins so that any logging calls issued via Jakarta's Commons Logging
 * or Log4j get routed through the maven logging system. This allows mojo's to invoke logic from other libraries and get
 * the logging statements issued by those libraries issued to maven's console output
 */
public class MavenLogger extends AppenderSkeleton implements Log {

    private static org.apache.maven.plugin.logging.Log systemStreamLog = new SystemStreamLog();
    private static org.apache.maven.plugin.logging.Log mavenLog = systemStreamLog;

    public MavenLogger() {
        this(null);
    }

    /**
     * Jakarta Commons Logging requires a constructor that takes a string
     */
    public MavenLogger(final String name) {
        super();
    }

    /**
     * Start logging for this plugin
     */
    public static void startPluginLog(final AbstractMojo mojo) {
        mavenLog = mojo.getLog();
    }

    /**
     * End logging for this plugin
     */
    public static void endPluginLog(final AbstractMojo mojo) {
        mavenLog = systemStreamLog;
    }

    // Log4j methods

    protected Throwable getThrowable(final LoggingEvent event) {
        if (event.getThrowableInformation() == null) {
            return null;
        }
        return event.getThrowableInformation().getThrowable();
    }

    @Override
    protected void append(final LoggingEvent event) {
        Level level = event.getLevel();
        if (Level.DEBUG.equals(level) && !(mavenLog.isDebugEnabled())) {
            return;
        }

        if (Level.TRACE.equals(level) && !(mavenLog.isDebugEnabled())) {
            return;
        }

        String text = this.layout.format(event);
        Throwable throwable = getThrowable(event);
        if (Level.DEBUG.equals(level) || Level.TRACE.equals(level)) {
            if (throwable == null) {
                debug(text);
            } else {
                debug(text, throwable);
            }
        } else if (Level.INFO.equals(level)) {
            if (throwable == null) {
                info(text);
            } else {
                info(text, throwable);
            }
        } else if (Level.WARN.equals(level)) {
            if (throwable == null) {
                warn(text);
            } else {
                warn(text, throwable);
            }
        } else if (Level.ERROR.equals(level) || Level.FATAL.equals(level)) {
            if (throwable == null) {
                error(text);
            } else {
                error(text, throwable);
            }
        } else {
            if (throwable == null) {
                error(text);
            } else {
                error(text, throwable);
            }
        }
    }

    @Override
    public void close() {
        mavenLog = null;
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    // Jakarta Commons Logging methods
    @Override
    public boolean isDebugEnabled() {
        return mavenLog.isDebugEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return mavenLog.isErrorEnabled();
    }

    @Override
    public boolean isFatalEnabled() {
        return mavenLog.isErrorEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return mavenLog.isInfoEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return mavenLog.isDebugEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return mavenLog.isWarnEnabled();
    }

    @Override
    public void trace(final Object message) {
        mavenLog.debug(getString(message));
    }

    @Override
    public void trace(final Object message, final Throwable t) {
        mavenLog.debug(getString(message), t);

    }

    @Override
    public void debug(final Object message) {
        mavenLog.debug(getString(message));
    }

    @Override
    public void debug(final Object message, final Throwable t) {
        mavenLog.debug(getString(message), t);
    }

    @Override
    public void info(final Object message) {
        mavenLog.info(getString(message));
    }

    @Override
    public void info(final Object message, final Throwable t) {
        mavenLog.info(getString(message), t);
    }

    @Override
    public void warn(final Object message) {
        mavenLog.warn(getString(message));
    }

    @Override
    public void warn(final Object message, final Throwable t) {
        mavenLog.warn(getString(message), t);
    }

    @Override
    public void error(final Object message) {
        mavenLog.error(getString(message));
    }

    @Override
    public void error(final Object message, final Throwable t) {
        mavenLog.error(getString(message), t);
    }

    @Override
    public void fatal(final Object message) {
        mavenLog.error(getString(message));
    }

    @Override
    public void fatal(final Object message, final Throwable t) {
        mavenLog.error(getString(message), t);
    }

    /**
     * Check for null then call toString on the object
     */
    protected String getString(final Object message) {
        if (message == null) {
            return null;
        }
        return message.toString();
    }

}
