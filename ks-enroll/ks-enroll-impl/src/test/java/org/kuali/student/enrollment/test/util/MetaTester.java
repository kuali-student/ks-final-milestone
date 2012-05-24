/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.test.util;

import static org.junit.Assert.*;
import org.kuali.student.r2.common.dto.MetaInfo;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class MetaTester {

    public void checkAfterCreate(MetaInfo info) {
        assertNotNull(info);
        assertNotNull(info.getCreateId());
        assertNotNull(info.getCreateTime());
        assertNotNull(info.getVersionInd());
    }

    public void checkAfterUpdate(MetaInfo orig, MetaInfo info) {
        assertNotNull(info);
        assertEquals(orig.getCreateId(), info.getCreateId());
        assertEquals(orig.getCreateTime(), info.getCreateTime());
        assertNotNull(info.getUpdateId());
        assertNotNull(info.getUpdateTime());
        assertNotSame(orig.getVersionInd(), info.getVersionInd());
    }

    public void checkAfterGet(MetaInfo orig, MetaInfo info) {
        assertNotNull(info);
        assertEquals(orig.getCreateId(), info.getCreateId());
        assertEquals(orig.getCreateTime(), info.getCreateTime());
        assertEquals(orig.getUpdateId(), info.getUpdateId());
        assertEquals(orig.getUpdateTime(), info.getUpdateTime());
        assertEquals (orig.getVersionInd(), info.getVersionInd());
    }
}
