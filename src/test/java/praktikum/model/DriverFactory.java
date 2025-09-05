package praktikum.model;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import praktikum.MainPage;


public class DriverFactory {
    private WebDriver driver;
    private MainPage mainPage;

    public void initDriver() throws Exception {
        if ("firefox".equals(System.getProperty("browser"))) {
            setupFirefox();
        } else {
            setupChrome();
        }
    }

    public void setupChrome() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        initializeBrowser();
    }

    public void setupFirefox() throws Exception {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        initializeBrowser();
    }

    private void initializeBrowser() {
        mainPage = new MainPage(driver);
        mainPage.openMainPage();
    }

    public WebDriver getDriver() {
        return driver;
    }
}