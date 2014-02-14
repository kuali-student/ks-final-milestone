package org.kuali.student.ap.test.resourceBundles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.ap.i18n.DBResourceBundleControlImpl;
import org.kuali.student.ap.i18n.DBResourceBundleImpl;
import org.kuali.student.ap.i18n.LocaleHelper;
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
    DBResourceBundleImpl rb = null;

    @Before
    public void setUp() throws Throwable {
        DefaultKsapContext.before("student1", getLocaleInfo());
        rb = (DBResourceBundleImpl) ResourceBundle.getBundle(DBResourceBundleControlImpl.class.getName(), LocaleHelper.localeInfo2Locale(getLocaleInfo()),
                new DBResourceBundleControlImpl("ksap", KsapFrameworkServiceLocator.getContext().getContextInfo(), null));
    }

    @After
    public void tearDown() throws Throwable {
        DefaultKsapContext.after();
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
}
