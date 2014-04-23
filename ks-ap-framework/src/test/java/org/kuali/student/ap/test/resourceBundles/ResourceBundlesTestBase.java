package org.kuali.student.ap.test.resourceBundles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.ap.i18n.LocaleUtil;
import org.kuali.student.r2.common.dto.LocaleInfo;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 1/9/14
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ResourceBundlesTestBase {

    ResourceBundle rb = null;

    @Before
    public void setUp() throws Throwable {
        DefaultKsapContext.before("student1", getLocaleInfo());
        rb = ResourceBundle.getBundle("META-INF/ks-ap/bundles/test", LocaleUtil.localeInfo2Locale(getLocaleInfo()));
    }

    @After
    public void tearDown() throws Throwable {
        DefaultKsapContext.after();
    }

    @Test
    public void testIsThisThingOn() {
        assertNotNull("ResourceLoader not found", rb);
    }

    @Test
    public void testNormal() {
        String value = rb.getString("testNormal");
        assertEquals(getPrefix() + "Expected value", value);
    }

    @Test
    public void testOnlyExistsInDefaultLocale() {
        String value = rb.getString("testDefaultOnly");
        assertEquals("In Default Locale Only.", value);
    }

    @Test
    public void testRegionOverride() {
        String value = rb.getString("testRegionOverride");
        assertEquals(getPrefix() + "This will get overridden by a different local", value);
    }

    @Test(expected = MissingResourceException.class)
    public void testDoesntExist() {
        String value = rb.getString("testDoesntExist");
    }

    @Test
    public void testGetKeys() {
        Enumeration<String> keys = rb.getKeys();
        while (keys.hasMoreElements()) {
            System.out.println(keys.nextElement());
        }
    }

    /**
     * Get the prefix that will be used to verify the test results
     * @return
     */
    public abstract String getPrefix();

    /**
     * Get the locale that is to be used for the test case
     * @return
     */
    public abstract LocaleInfo getLocaleInfo();
}
