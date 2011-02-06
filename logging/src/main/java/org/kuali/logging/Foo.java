package org.kuali.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Foo {
    private static final Log log = LogFactory.getLog(Foo.class);

    public void bar() {
        log.info("hello world");
    }
}
