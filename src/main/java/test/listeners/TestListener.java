package test.listeners;

import core.utils.Screenshoter;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * Created by Dina_Abdykasheva on 9/7/2017.
 */
public class TestListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult result) {
        Screenshoter.takeScreenshot();
    }
}
