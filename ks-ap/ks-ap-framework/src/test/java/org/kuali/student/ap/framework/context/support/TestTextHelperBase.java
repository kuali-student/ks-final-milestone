package org.kuali.student.ap.framework.context.support;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.TextHelper;
import org.kuali.student.r2.common.dto.LocaleInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 1/13/14
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class TestTextHelperBase {

    protected TextHelper th = null;

    public abstract LocaleInfo getLocaleInfo();
    public abstract String getPrefix();

    @Before
    public void setUp() throws Throwable {
        DefaultKsapContext.before("student1", getLocaleInfo());
        th = KsapFrameworkServiceLocator.getTextHelper();
    }

    @After
    public void tearDown() throws Throwable {
        DefaultKsapContext.after();
    }

    @Test
    public void testIsThisThingOn() throws Exception {
        assertNotNull("KSAP Context not found",
                KsapFrameworkServiceLocator.getContext());
        assertNotNull("KSAP TextHelper not found",
                th);
    }

    @Test
    public void testSimpleTextLookup() throws Exception {
        String value = th.getText("simpleText");
        assertEquals(getPrefix() + "Simple Text", value);
    }

    @Test
    public void testDefaultLookup() throws Exception {
        String value = th.getText("doesnt.exist", "Default Text");
        assertEquals("Default Text", value);
    }

    @Test
    public void testLookupWithSubstitution() throws Exception {
        String value = th.getFormattedMessage("substitutionText", "blue");
        assertEquals(getPrefix() + "My favorite color is blue.", value);
    }

    @Test
    public void testLookupWithMultipleSubstitution() throws Exception {
        String value = th.getFormattedMessage("multipleSubstitutionText", "Apples", "Bananas");
        assertEquals(getPrefix() + "I like to eat Apples and Bananas.", value);
    }

    @Test
    public void testOnlyExistsInDefaultLocale() throws Exception {
        String value = th.getText("testDefaultOnly");
        assertEquals("In Default Locale Only.", value);
    }

    @Test
    public void testLookupWithDefaultLocaleFallback() throws Exception {
        String value = th.getText("defaultPropsOnlyNoDB");
        assertEquals("This is only in the properties file and not in the DB.", value);
    }

    @Test
    public void testLookupWithLanguageLocaleFallback() throws Exception {
        String value = th.getText("langLocalePropsOnlyNoDB");
        assertEquals(getPrefix() + "This is also only in the properties file and not in the DB.", value);
    }

    @Test
    public void testKeyDoesntExist() throws Exception {
        String key = "doesnt.exist";
        String baseName = ConfigContext.getCurrentContextConfig().getProperty(TextHelper.CONFIG_RESOURCE_BUNDLE_NAMES);
        String value = th.getText(key);

        assertEquals("\\[missing key (mre): " + baseName + " " + key + "\\]", value);
    }

    @Test
    public void getRBKeys() {

    }

}
