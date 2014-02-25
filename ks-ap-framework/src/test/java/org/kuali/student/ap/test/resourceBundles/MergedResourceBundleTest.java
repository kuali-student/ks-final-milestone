package org.kuali.student.ap.test.resourceBundles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.ap.i18n.DBResourceBundleControlImpl;
import org.kuali.student.ap.i18n.LocaleUtil;
import org.kuali.student.ap.i18n.MergedPropertiesResourceBundleImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 2/19/14
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:textHelper-test-context.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class MergedResourceBundleTest {

    private static final String BUNDLE1 = "META-INF/ks-ap/bundles/test";
    private static final String BUNDLE2 = "META-INF/ks-ap/bundles/anotherTest";
    private static final String BUNDLE3 = "META-INF/ks-ap/bundles/dupKey";

    private Locale locale = null;
    ResourceBundle rb = null;

    @Before
    public void setUp() throws Throwable {
        DefaultKsapContext.before("student1");
        locale = getLocale();
        rb = ResourceBundle.getBundle(DBResourceBundleControlImpl.class.getName(), locale,
                new DBResourceBundleControlImpl("ksap", KsapFrameworkServiceLocator.getContext().getContextInfo()));

    }

    @After
    public void tearDown() throws Throwable {
        DefaultKsapContext.after();
    }

    @Test (expected = RuntimeException.class)
    public void testGetBundleWithDupes() {
        List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();

        bundles.add(rb);
        bundles.add(ResourceBundle.getBundle(BUNDLE1, locale));
        bundles.add(ResourceBundle.getBundle(BUNDLE2, locale));
        bundles.add(ResourceBundle.getBundle(BUNDLE3, locale));

        MergedPropertiesResourceBundleImpl mprb = new MergedPropertiesResourceBundleImpl(bundles, locale);
    }

    @Test
    public void testGetBundleWithNoDupes() {
        List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();

        bundles.add(rb);
        bundles.add(ResourceBundle.getBundle(BUNDLE1, locale));
        bundles.add(ResourceBundle.getBundle(BUNDLE2, locale));

        MergedPropertiesResourceBundleImpl mprb = new MergedPropertiesResourceBundleImpl(bundles, locale);

        String value = mprb.getString("testNormal");
        assertEquals("Expected value", value);
    }

    private Locale getLocale() {
        KsapContext ksapCtx = KsapFrameworkServiceLocator.getContext();
        ContextInfo contextInfo = ksapCtx.getContextInfo();
        return LocaleUtil.localeInfo2Locale(contextInfo.getLocale());
    }
}
