package com.redhat.qe.kiali.ui.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.redhat.qe.kiali.rest.KialiRestClient;
import com.redhat.qe.kiali.ui.KialiDriverUI;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jeeva Kandasamy (jkandasa)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DriverFactory {
    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static AtomicBoolean tearDown = new AtomicBoolean(false);
    private static KialiDriverUI driverUI = null;
    private static KialiRestClient restClient = null;

    public static KialiDriverUI driverUI() {
        return driverUI;
    }

    public static void initialize(String suiteName) throws MalformedURLException {
        if (!initialized.get()) {
            String username = "jdoe";
            String password = "password";
            String hostname = System.getProperty("kialiHostname", "localhost");

            String remoteDriver = System.getProperty("seleniumGrid", "http://localhost:4444/wd/hub");

            //String remoteDriver = "http://localhost:4444/wd/hub";

            // load UI driver
            DesiredCapabilities caps = DesiredCapabilities.chrome();

            caps.setCapability("platform", "Linux");

            // saucelabs configurations
            //caps.setCapability("platform", "Windows 7");
            //caps.setCapability("name", "saucelabs demo");
            //caps.setCapability("tunnelIdentifier", "zalenium");

            caps.setCapability("zal:name", suiteName);
            caps.setCapability("zal:tz", "Asia/Kolkata");
            caps.setCapability("zal:screenResolution", "1920x1080");
            caps.setCapability("zal:idleTimeout", "90");
            caps.setCapability("zal:recordVideo", "true");

            driverUI = new KialiDriverUI(new URL(remoteDriver), caps);

            // launch the application and maximize the screen
            driverUI.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driverUI.get("http://" + username + ":" + password + "@" + hostname);

            driverUI.manage().window().maximize();

            // load REST client
            restClient = new KialiRestClient("http://" + hostname, username, password);
        }
    }

    public static KialiRestClient restClient() {
        return restClient;
    }

    public static void tearDown() {
        if (!tearDown.get()) {
            driverUI.quit();
        }
    }
}
