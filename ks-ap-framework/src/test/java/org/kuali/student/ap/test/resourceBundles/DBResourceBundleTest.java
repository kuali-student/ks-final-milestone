package org.kuali.student.ap.test.resourceBundles;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.ap.i18n.DBResourceBundleControlImpl;
import org.kuali.student.ap.i18n.LocaleUtil;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 2/12/14
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:textHelper-test-context.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class DBResourceBundleTest {
    ResourceBundle rb = null;

    private static final Logger LOG = Logger.getLogger(DBResourceBundleTest.class);

    @Before
    public void setUp() throws Throwable {
        DefaultKsapContext.before("student1", getLocaleInfo());
        rb = initializeResourceBundle();
    }

    @After
    public void tearDown() throws Throwable {
        DefaultKsapContext.after();
    }

    private ResourceBundle initializeResourceBundle() {
        ResourceBundle rb = ResourceBundle.getBundle(DBResourceBundleControlImpl.class.getName(), LocaleUtil.localeInfo2Locale(getLocaleInfo()),
                new DBResourceBundleControlImpl("ksap", KsapFrameworkServiceLocator.getContext().getContextInfo(), null));
        return rb;
    }

    private LocaleInfo getLocaleInfo() {
        LocaleInfo locale = new LocaleInfo();
        locale.setLocaleLanguage("en");
        locale.setLocaleRegion("");
        locale.setLocaleVariant("");
        return locale;
    }

    @Test
    public void testIsThisThingOn() {
        assertNotNull("ResourceLoader not found", rb);
    }

    @Test
    public void testSimpleTextLookup() throws Exception {
        String value = rb.getString("simpleText");
        assertEquals("Simple Text", value);
    }

    @Test
    public void testClearCache() throws Exception {
        //System.out.println("Begin...");
        LOG.debug("Begin...");
        rb = initializeResourceBundle();
        String value = rb.getString("simpleText");
        assertEquals("Simple Text", value);

        //Clear the cache
        ResourceBundle.clearCache();
        LOG.debug("Cache has been cleared...");

        rb = initializeResourceBundle();
        //Run test again...
        value = rb.getString("simpleText");
        assertEquals("Simple Text", value);
        LOG.debug("End...");
    }
}
