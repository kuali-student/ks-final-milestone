/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.test.util;

import static org.junit.Assert.*;
import org.kuali.student.r2.common.dto.RichTextInfo;

/**
 * Helps test rich text
 * @author nwright
 */
public class RichTextTester {

    public void check(RichTextInfo expected, RichTextInfo actual) {
        if (expected == null) {
            if (actual == null) {
                return;
            } else {
                if (actual.getPlain() == null && actual.getFormatted() == null) {
                    fail("expected null but found an empty structure " + actual);
                    return;
                }
                fail("expected null but found not null " + actual);
                return;
            }
        } else {
            if (actual == null) {
                if (expected.getPlain() == null && expected.getFormatted() == null) {
                    fail("expected empty structure but found null ");
                    return;
                }
                fail("expected not null but found null " + expected);
                return;
            } else {
                assertEquals(expected.getPlain(), actual.getPlain());
                assertEquals(expected.getFormatted(), actual.getFormatted());
            }
        }
    }
}
