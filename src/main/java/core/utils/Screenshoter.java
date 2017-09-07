package core.utils;

import core.driver.singleton.WebDriverSingleton;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sergey_Poritskiy on 6/15/2017.
 */
public class Screenshoter {
    private static final String SCREENSHOTS_NAME_TPL = "screenshots/src";

    public static void takeScreenshot() {
        WebDriver driver = WebDriverSingleton.getWebDriverInstance();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = SCREENSHOTS_NAME_TPL + System.nanoTime();
            File copy = new File(screenshotName + ".png");
            FileUtils.copyFile(screenshot, copy);
            MyLogger.logger.info("Saved screenshot: " + screenshotName);
        } catch (Exception e) {
            MyLogger.logger.error("Failed to make screenshot");
        }
    }
}
