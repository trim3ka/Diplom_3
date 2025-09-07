package praktikum.model;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import praktikum.objects.MainPage;

public class DriverExtension implements BeforeEachCallback, AfterEachCallback {
    private final DriverFactory factory = new DriverFactory();
    private MainPage mainPage;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        factory.initDriver();
        mainPage = new MainPage(factory.getDriver());
        mainPage.openMainPage();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (factory.getDriver() != null) {
            factory.getDriver().quit();
        }
    }

    public WebDriver getDriver() {
        return factory.getDriver();
    }

    public MainPage getMainPage() {
        return mainPage;
    }
}