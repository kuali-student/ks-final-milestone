package org.kuali.student.ap.framework.context.support;

import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 1/13/14
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:textHelper-test-context.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestTextHelper_fr extends TestTextHelperBase {

    @Override
    public String getPrefix() {
        return "fr_";
    }

    @Override
    public LocaleInfo getLocaleInfo() {
        LocaleInfo locale = new LocaleInfo();
        locale.setLocaleLanguage("fr");
        locale.setLocaleRegion("");
        locale.setLocaleVariant("");
        return locale;
    }
}
