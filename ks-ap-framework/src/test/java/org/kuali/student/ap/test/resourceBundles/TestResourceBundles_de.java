package org.kuali.student.ap.test.resourceBundles;

import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test class for de locales
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testKsapFrameworkInit-context.xml" })
public class TestResourceBundles_de extends ResourceBundlesTestBase {

    @Override
    public String getPrefix() {
        //Not setting a prefix since we have no de bundles.  Testing that it defaults back to the default (en) locale.
        return "";
    }

    @Override
    public LocaleInfo getLocaleInfo() {
        LocaleInfo locale = new LocaleInfo();
        locale.setLocaleLanguage("de");
        locale.setLocaleRegion("");
        locale.setLocaleVariant("");
        return locale;
    }
}
