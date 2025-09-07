package praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Локаторы главной страницы
    private By loginAccountButton = By.xpath("//button[text()='Войти в аккаунт']"); //Кнопка "Войти в аккаунт"
    private By placeAnOrderButton = By.xpath("//button[text()='Оформить заказ']");
    private By headerAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private By constructorButton = By.xpath("//p[text()='Конструктор']");
    private By logoButton = By.className("AppHeader_header__logo__2D0X2");
    private By ingredientSection = By.xpath("//h2[text()='Начинки']");

    //открыли главную страницу
    public void openMainPage() {
        driver.get(Constants.BASE_URL);
    }

    //Клик на "Войти в аккаунт"
    public void clickLoginAccountButton() {
        driver.findElement(loginAccountButton).click();
    }

    //Видимость кнопки "Оформить заказ"
    public boolean isPlaceAnOrderButtonVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(placeAnOrderButton));
        return true;
    }

    //Клик по кнопке "Личный кабинет" в хэдере
    public void clickHeaderAccountButton() {
        driver.findElement(headerAccountButton).click();
    }

    public boolean isAccountButtonVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerAccountButton));
        return true;
    }


    //Клик на Конструктор
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    //Клик на Лого
    public void clickLogoButton() {
        driver.findElement(logoButton).click();
    }

}