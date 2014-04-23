package org.kuali.student.ap.test.resourceBundles;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.ap.i18n.LocaleUtil;
import org.kuali.student.ap.i18n.MergedPropertiesResourceBundleControlImpl;
import org.kuali.student.ap.i18n.MergedPropertiesResourceBundleImpl;
import org.kuali.student.r2.common.dto.LocaleInfo;
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
 * Date: 2/12/14
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:textHelper-test-context.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class ResourceBundleWithMultipleLocalesTest {
    ResourceBundle rb = null;

    private static final Logger LOG = Logger.getLogger(ResourceBundleWithMultipleLocalesTest.class);

    private static final String TEXT = "This will get overridden by a different local";
    private static final String TEXT2 = "This is also only in the properties file and not in the DB.";
    private static final String BUNDLE1 = "META-INF/ks-ap/bundles/test";
    private static final String BUNDLE2 = "META-INF/ks-ap/bundles/anotherTest";


    @Before
    public void setUp() throws Throwable {
        //DefaultKsapContext.before("student1");
    }

    @After
    public void tearDown() throws Throwable {
        DefaultKsapContext.after();
    }

    @Test
    public void testStuff() throws Exception {
        TestLocale en = new BaseLocale("en", "", "", "");
        TestLocale fr = new BaseLocale("fr", "", "", "fr_");
        TestLocale frFR = new BaseLocale("fr", "FR", "", "fr_FR_");


        //First run
        LOG.debug("First run...");
        doTest(en);
        doTest(fr);
        doTest(frFR);

        //Run them again to test some load/cache issues
        LOG.debug("Second run...");
        doTest(en);
        doTest(fr);
        doTest(frFR);

        //Run them again to test some load/cache issues
        LOG.debug("Third run...");
        doTest(en);
        doTest(fr);
        doTest(frFR);

    }

    private void doTest(TestLocale locale)   {
        DefaultKsapContext.before("student1", locale.getLocaleInfo());
        Locale loc = LocaleUtil.localeInfo2Locale(locale.getLocaleInfo());

        List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();

        bundles.add(ResourceBundle.getBundle(BUNDLE1, loc));
        bundles.add(ResourceBundle.getBundle(BUNDLE2, loc));

        MergedPropertiesResourceBundleImpl.Control control = new MergedPropertiesResourceBundleControlImpl(bundles);
        rb = ResourceBundle.getBundle(MergedPropertiesResourceBundleImpl.class.getName(), loc, control);
        outputCandidateLocales(BUNDLE1, control.getCandidateLocales(BUNDLE1, loc));
        outputCandidateLocales(BUNDLE2, control.getCandidateLocales(BUNDLE2, loc));

        String value = rb.getString("testRegionOverride");
        assertEquals(locale.getText(TEXT), value);

        String value2 = rb.getString("langLocalePropsOnlyNoDB");
        assertEquals(locale.getText(TEXT2), value2);

        DefaultKsapContext.after();
    }

    private void outputCandidateLocales(String baseName, List<Locale> locales) {
        StringBuffer sb = new StringBuffer("");
        sb.append(baseName + ":");
        for (Locale locale : locales) {
            sb.append("\n\t" + locale.toString());
        }
        LOG.debug(sb.toString());
    }

    private interface TestLocale {
        public LocaleInfo getLocaleInfo();
        public String getText(String text);
    }

    private class BaseLocale implements TestLocale {
        private String language;
        private String region;
        private String variant;
        private String prefix;

        public BaseLocale(String language, String region, String variant, String prefix) {
            this.language = language;
            this.region = region;
            this.variant = variant;
            this.prefix = prefix;
        }
        public String getText(String text) {
            return prefix + text;
        }

        public LocaleInfo getLocaleInfo() {
            LocaleInfo locale = new LocaleInfo();
            locale.setLocaleLanguage(language);
            locale.setLocaleRegion(region);
            locale.setLocaleVariant(variant);
            return locale;
        }
    }
}
