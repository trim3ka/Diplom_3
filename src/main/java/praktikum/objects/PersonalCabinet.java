package praktikum.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalCabinet extends BasePage {
    public PersonalCabinet(WebDriver driver) {
        super(driver);
    }

    // Локаторы
    private By headerAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private By loginPageEmailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private By loginPagePasswordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    public static By loginTitle = By.xpath("//h2[text()='Вход']");
    private By loginButton = By.xpath("//button[text()='Войти']");
    private By profileField = By.xpath("//a[text()='Профиль']");

    // Методы
    public void clickHeaderAccountButton() {
        driver.findElement(headerAccountButton).click();
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public boolean isAccountButtonVisible() {
        return driver.findElement(headerAccountButton).isDisplayed();
    }

    public void enterEmailLogin(String email) {
        driver.findElement(loginPageEmailField).sendKeys(email);
    }

    public void enterPasswordLogin(String password) {
        driver.findElement(loginPagePasswordField).sendKeys(password);
    }

    // Метод авторизации
    public void loginForm(String email, String password) {
        enterEmailLogin(email);
        enterPasswordLogin(password);
        clickLoginButton();
    }

    public boolean isProfileFieldVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileField));
            return true;
    }
}