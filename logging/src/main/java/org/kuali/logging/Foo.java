package org.kuali.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Foo {
    private static final Log log = LogFactory.getLog(Foo.class);

    public static void main(final String[] args) {
        try {
            log.info("hello world");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
