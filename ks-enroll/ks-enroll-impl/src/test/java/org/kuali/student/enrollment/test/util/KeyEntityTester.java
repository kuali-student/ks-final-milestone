/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.test.util;

import org.kuali.student.r2.common.dto.IdEntityInfo;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.kuali.student.r2.common.dto.KeyEntityInfo;

/**
 * Helps test id enttities
 * @author nwright
 */
public class KeyEntityTester {

    public void check(KeyEntityInfo expected, KeyEntityInfo actual) {
        assertEquals(expected.getKey(), actual.getKey());
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
    }

    public void checkListOfInfoAgainstListOfKeys (List<? extends KeyEntityInfo> infos, List<String> keys) {
         for (KeyEntityInfo info: infos) {
             assertTrue(keys.contains(info.getKey()));
         }
    }
}
