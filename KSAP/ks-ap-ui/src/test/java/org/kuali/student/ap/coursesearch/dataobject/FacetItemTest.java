package org.kuali.student.ap.coursesearch.dataobject;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class FacetItemTest {
    @Test
    public void testCompareTo() throws Exception {
        FacetItem itemA = new FacetItem( "key", "A" );
        FacetItem itemB = new FacetItem( "key", "B" );

        assertTrue( itemA.compareTo(itemB) < 0 );
        assertTrue( itemA.compareTo( null ) > 0 );
        assertTrue( itemB.compareTo( itemA ) > 0 );
        assertTrue( itemB.compareTo( itemB ) == 0 );
    }
}
