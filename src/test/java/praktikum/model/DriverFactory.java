package praktikum.model;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import praktikum.objects.MainPage;


public class DriverFactory {
    private WebDriver driver;
    private MainPage mainPage;

//    public void initDriver() throws Exception {
//        if ("firefox".equals(System.getProperty("browser"))) {
//            setupFirefox();
//        } else {
//            setupChrome();
//        }
//    }

    public void initDriver() throws Exception {
        if ("yandex".equals(System.getProperty("browser"))) {
            initYandex();
        } else {
            setupChrome();
        }
    }

    public void setupChrome() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        initializeBrowser();
    }

    private void initYandex() {
        WebDriverManager.chromedriver().driverVersion(System.getProperty("driver.version")).setup();

        var options = new ChromeOptions();
        options.setBinary(System.getProperty("webdriver.yandex.bin"));

        driver = new ChromeDriver(options);
    }

    private void initializeBrowser() {
        mainPage = new MainPage(driver);
        mainPage.openMainPage();
    }

    public WebDriver getDriver() {
        return driver;
    }
}