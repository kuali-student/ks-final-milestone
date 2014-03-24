package org.kuali.student.lum.ui.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GoogleTest {

    @Test
    public void simpleTest() throws Exception {
        WebDriver driver = new FirefoxDriver();
        driver.get("http://www.google.com");
        String title = driver.getTitle();
        Assert.assertEquals("Google", title);
        driver.quit();
    }
}
