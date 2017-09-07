package test.runner;

import test.listeners.TestListener;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dina_Abdykasheva on 9/7/2017.
 */
public class TestRunner {
    public static void main(String[] args) {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG tng = new TestNG();
        tng.addListener(tla);
        tng.addListener(new TestListener());

        XmlSuite suite = new XmlSuite();
        suite.setName("GMailTest");

        List<String> files = new ArrayList<>();
        files.addAll(new ArrayList<String>() {{
            add ("src/scenarios/GMailTest.xml");
        }});

        suite.setSuiteFiles(files);

        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);

        tng.setXmlSuites(suites);

        tng.run();
    }
}
