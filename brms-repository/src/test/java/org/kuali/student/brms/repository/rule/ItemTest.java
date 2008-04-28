package org.kuali.student.brms.repository.rule;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.kuali.student.brms.repository.rule.ItemImpl;

public class ItemTest {

    @Test
    public void testNullName() {
        try {
            RuleFactory.getInstance().createRule( null );
            fail( "Should not be able to create an item with a null name" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNullUuid() {
        try {
            RuleFactory.getInstance().createRule( null, "ruleName" );
            fail( "Should not be able to create an item with a null UUID" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNullUuidAndName() {
        try {
            RuleFactory.getInstance().createRule( null, null );
            fail( "Should not be able to create an item with a null UUID and a null name" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNameEquals() {
        Rule rule1 = RuleFactory.getInstance().createRule( "itemName" );
        ItemImpl item1 = (ItemImpl) rule1;
        Rule rule2 = RuleFactory.getInstance().createRule( "itemName" );
        ItemImpl item2 = (ItemImpl) rule2;

        assertEquals( item1, item2 );
    }

    @Test
    public void testNameNotEquals() {
        Rule rule1 = RuleFactory.getInstance().createRule( "itemName1" );
        ItemImpl item1 = (ItemImpl) rule1;
        Rule rule2 = RuleFactory.getInstance().createRule( "itemName2" );
        ItemImpl item2 = (ItemImpl) rule2;

        assertFalse( item1.equals( item2 ) );
    }

    @Test
    public void testNameUuidEquals() {
        Rule rule1 = RuleFactory.getInstance().createRule( "123", "itemName" );
        ItemImpl item1 = (ItemImpl) rule1;
        Rule rule2 = RuleFactory.getInstance().createRule( "123", "itemName" );
        ItemImpl item2 = (ItemImpl) rule2;

        assertEquals( item1, item2 );
    }

    @Test
    public void testNameUuidNotEquals() {
        Rule rule1 = RuleFactory.getInstance().createRule( "123", "itemName1" );
        ItemImpl item1 = (ItemImpl) rule1;
        Rule rule2 = RuleFactory.getInstance().createRule( "987", "itemName2" );
        ItemImpl item2 = (ItemImpl) rule2;

        assertFalse( item1.equals( item2 ) );
    }
}
