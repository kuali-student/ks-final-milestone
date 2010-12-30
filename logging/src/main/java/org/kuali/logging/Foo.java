package org.kuali.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Foo {
    private static final Log log = LogFactory.getLog(Foo.class);

    public void bar() {
        log.info("hello world");
    }

    public static void main(final String[] args) {
        try {
            Log log = LogFactory.getLog(Foo.class);
            log.info(log.getClass().getCanonicalName());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
