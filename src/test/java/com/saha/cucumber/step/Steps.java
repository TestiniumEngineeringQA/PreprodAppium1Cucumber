package com.saha.cucumber.step;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class Steps {

    public static final String hubURL = "http://172.25.1.12:4444/wd/hub";
    protected static AppiumDriver<MobileElement> driver;


    @Before
    public static void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (!StringUtils.isEmpty(System.getProperty("key"))) {
            capabilities.setCapability("key", System.getProperty("key"));
            if (System.getProperty("platform").equals("ANDROID")) {
                capabilities
                        .setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                                "com.gratis.android");

                capabilities
                        .setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                                "com.app.gratis.ui.splash.SplashActivity");
                capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANDROID);
                driver = new AndroidDriver<MobileElement>(new URL(hubURL), capabilities);
            } else {
                capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.IOS);
                //capabilities.setCapability("autoAcceptAlerts",true);
                capabilities.setCapability("bundleId", "com.pharos.Gratis");
                driver = new IOSDriver<MobileElement>(new URL(hubURL), capabilities);
            }
        }
    }

    @After
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * @param seconds
     */
    @Given("^Wait (\\d+) seconds$")
    public void waitSeconds(int seconds) {
        wait(seconds);
    }

    @Given("^Element with id \"([^\"]*)\" is clicked$")
    public void clickElementById(String elementId) {
        WebElement element = driver.findElement(By.id(elementId));
        element.click();
    }

    @Given("^Element with xpath \"([^\"]*)\" is clicked$")
    public void clickElementByXpath(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        element.click();
    }



    public void wait(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
