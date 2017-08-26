package core.driver.decorator;

import core.utils.Logger;
import core.utils.WebElementsUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

/**
 * Created by User on 19.08.2017.
 */
public class WebDriverDecorator implements WebDriver {

    protected WebDriver driver;

    public WebDriverDecorator(WebDriver driver) {
        this.driver = driver;
    }

    public void get(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public WebElement findElement(By by) {
        List<WebElement> elements = driver.findElements(by);
        WebElement firstElement;
        if (elements.size() != 0) {
            Logger.debug("Finding element");
            firstElement = elements.get(0);
            WebElementsUtils.executeJavaScript(driver, by, "arguments[0].style.backgroundColor = '"+ "yellow" + "'");
        } else {
            Logger.error("element not found");
            return null;
        }
        return firstElement;
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public void close() {
        driver.close();
    }

    public void quit() {
        Logger.info("Browser will be closed now...");
        driver.quit();
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    public Navigation navigate() {
        return driver.navigate();
    }

    public Options manage() {
        return driver.manage();
    }
}
