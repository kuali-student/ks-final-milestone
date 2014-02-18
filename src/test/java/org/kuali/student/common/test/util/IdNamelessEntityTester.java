/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.common.test.util;

import org.kuali.student.r2.common.dto.IdEntityInfo;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;

/**
 * Helps test id nameless enttities
 * @author nwright
 */
public class IdNamelessEntityTester {

    public void check(IdNamelessEntityInfo expected, IdNamelessEntityInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
    }

    public void checkListOfInfoAgainstListOfIds (List<? extends IdNamelessEntityInfo> infos, List<String> ids) {
         for (IdNamelessEntityInfo info: infos) {
             assertTrue(ids.contains(info.getId()));
         }
    }
}
