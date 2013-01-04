package org.kuali.student.lum.ui.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {

    @Test
    public void loginTest() {
        String baseUrl = System.getProperty("selenium.baseurl");
        WebDriver driver = null;
        try {
            driver = new FirefoxDriver();
            driver.get(baseUrl + "/login.jsp");
            WebElement logonUserName = driver.findElement(By.id("j_username"));
            WebElement logonPassword = driver.findElement(By.id("j_password"));
            typeValue(logonUserName, "admin");
            typeValue(logonPassword, "admin");
            logonPassword.submit();
            Wait<WebDriver> wait = new WebDriverWait(driver, 30);
            ExpectedCondition<Boolean> condition = new TitlePresentCondition("Kuali Student: Home");
            wait.until(condition);
            System.out.println("Title:" + driver.getTitle());
        } finally {
            quitQuietly(driver);
        }
    }

    protected void quitQuietly(WebDriver driver) {
        if (driver == null) {
            return;
        } else {
            driver.quit();
        }
    }

    protected void typeValue(final WebElement webElement, CharSequence... keysToSend) {
        webElement.clear();
        webElement.sendKeys(keysToSend);
    }
}
