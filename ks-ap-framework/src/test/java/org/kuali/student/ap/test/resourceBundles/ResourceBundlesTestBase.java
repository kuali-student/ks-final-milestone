package org.kuali.student.ap.test.resourceBundles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.i18n.KualiResourceBundle;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.r2.common.dto.LocaleInfo;

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

    //ResourceLoader rl = null;
    KualiResourceBundle rl = null;

    @Before
    public void setUp() throws Throwable {
        DefaultKsapContext.before("student1", getLocaleInfo());
        //rl = new ResourceLoader("META-INF/ks-ap/bundles/test");
        rl = new KualiResourceBundle("META-INF/ks-ap/bundles/test");
    }

    @After
    public void tearDown() throws Throwable {
        DefaultKsapContext.after();
    }

    @Test
    public void testIsThisThingOn() {
        assertNotNull("ResourceLoader not found", rl);
    }

    @Test
    public void testNormal() {
        String value = rl.getString("testNormal");
        assertEquals(getPrefix() + "Expected value", value);
    }

    @Test
    public void testDefault() {
        String value = rl.getString("doesnt.exist", "default value");
        assertEquals("default value", value);
    }

    @Test
    public void testSubstitution() {
        String value = rl.getFormattedMessage("testSubstitution", "on");
        assertEquals(getPrefix() + "Is this thing on?", value);
    }

    @Test
    public void testMultipleSubstitutions() {
        String value = rl.getFormattedMessage("testMultiple", "Apples", "Bananas");
        assertEquals(getPrefix() + "Apples and Bananas", value);
    }

    /**
     * If
     */
    @Test
    public void testOnlyExistsInDefaultLocale() {
        String value = rl.getString("testDefaultOnly");
        assertEquals("In Default Locale Only", value);
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
