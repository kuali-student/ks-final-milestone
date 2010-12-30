package org.kuali.logging;

public class Bar {
    public static void main(final String[] args) {
        try {
            new Foo().bar();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
