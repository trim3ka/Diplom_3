package praktikum;

import org.openqa.selenium.WebDriver;

public class MainPage {
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    //открыли главную страницу
    public void openMainPage() {
        driver.get(Constants.BASE_URL);
    }
}