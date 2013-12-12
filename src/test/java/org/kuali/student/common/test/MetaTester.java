/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.common.test;

import org.kuali.student.r2.common.dto.MetaInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class MetaTester {

    public void checkAfterCreate(MetaInfo actual) {
        assertNotNull(actual);
        assertNotNull(actual.getCreateId());
        assertNotNull(actual.getCreateTime());
        assertNotNull(actual.getVersionInd());
    }

    public void checkAfterUpdate(MetaInfo expected, MetaInfo actual) {
        assertNotNull(actual);
        assertEquals(expected.getCreateId(), actual.getCreateId());
        new TimeTester ().check (expected.getCreateTime(), actual.getCreateTime());
        assertNotNull(actual.getUpdateId());
        assertNotNull(actual.getUpdateTime());
        assertNotSame(expected.getVersionInd(), actual.getVersionInd());
    }

    public void checkAfterGet(MetaInfo expected, MetaInfo actual) {
        assertNotNull(actual);
        assertEquals(expected.getCreateId(), actual.getCreateId());
        new TimeTester ().check(expected.getCreateTime(), actual.getCreateTime());
        assertEquals(expected.getUpdateId(), actual.getUpdateId());
        new TimeTester ().check(expected.getUpdateTime(), actual.getUpdateTime());
        assertEquals (expected.getVersionInd(), actual.getVersionInd());
    }
}
