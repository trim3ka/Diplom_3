package praktikum.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.Constants;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Локаторы главной страницы
    private final By loginAccountButton = By.xpath("//button[text()='Войти в аккаунт']"); //Кнопка "Войти в аккаунт"
    private final By placeAnOrderButton = By.xpath("//button[text()='Оформить заказ']");
    private final By headerAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By constructorButton = By.xpath("//p[text()='Конструктор']");
    private final By logoButton = By.className("AppHeader_header__logo__2D0X2");
    private final By activeBunTab = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Булки']/..");
    private final By activeSauceTab = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Соусы']/..");
    private final By activeFillingTab = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Начинки']/..");
    private final By inactiveBunTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and not(contains(@class, 'tab_tab_type_current__2BEPc'))]//span[text()='Булки']/..");
    private final By inactiveSauceTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and not(contains(@class, 'tab_tab_type_current__2BEPc'))]//span[text()='Соусы']/..");
    private final By inactiveFillingTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and not(contains(@class, 'tab_tab_type_current__2BEPc'))]//span[text()='Начинки']/..");

    //Открытие главной страницы
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
    //Ожидание видимости кнопки "Личный кабинет"
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

    // Проверка что раздел "Булки" выбран
    public boolean isBunTabActive() {
        return isElementDisplayed(activeBunTab);
    }

    // Проверка что раздел "Булки" неактивен
    public boolean isBunTabInactive() {
        return isElementDisplayed(inactiveBunTab);
    }

    //Клик на раздел "Булки"
    public void clickBunTab() {
        driver.findElement(inactiveBunTab).click();
    }

    // Проверка что раздел "Соусы" выбран
    public boolean isSauceTabActive() {
        return isElementDisplayed(activeSauceTab);
    }

    // Проверка что раздел "Соусы" неактивен
    public boolean isSauceTabInactive() {
        return isElementDisplayed(inactiveSauceTab);
    }

    //Клик на раздел "Соусы"
    public void clickSauceTab() {
        driver.findElement(inactiveSauceTab).click();
    }

    // Проверка что раздел "Начинки" выбран
    public boolean isFillingTabActive() {
        return isElementDisplayed(activeFillingTab);
    }

    // Проверка что раздел "Начинки" неактивен
    public boolean isFillingTabInactive() {
        return isElementDisplayed(inactiveFillingTab);
    }

    //Клик на раздел "Начинки"
    public void clickFillingTab() {
        driver.findElement(inactiveFillingTab).click();
    }

    private boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}