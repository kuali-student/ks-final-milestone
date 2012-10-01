/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.test.util;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import static org.junit.Assert.*;

/**
 * Helps test id enttities
 * @author nwright
 */
public class KeyEntityTester {

    public void check(KeyEntityInfo expected, KeyEntityInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
    }
}
