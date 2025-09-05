package praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static praktikum.Constants.LOGIN_PAGE_URL;

public class RegisterPage {
    private final WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    // Локаторы
    private By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private By registerLink = By.xpath("//a[text()='Зарегистрироваться']");

    // Локаторы полей страницы Регистрации  (/register)
    private By nameField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private By registerButton = By.xpath("//button[text()='Зарегистрироваться']");

    // Локаторы для страницы логина (после регистрации)  (/login)
    private By loginPageEmailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private By loginPagePasswordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private By loginTitle = By.xpath("//h2[text()='Вход']");

    // Методы для главной страницы
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public boolean isLoginButtonVisible() {
        return driver.findElement(loginButton).isDisplayed();
    }

    // Методы для страницы логина
    public void clickRegisterLink() {
        driver.findElement(registerLink).click();
    }


    // Методы для страницы регистрации
    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public boolean isRegisterButtonDisplayed() {
        return driver.findElement(registerButton).isDisplayed();
    }

    public boolean isRegisterFormDisplayed() {
        return driver.findElement(nameField).isDisplayed() &&
                driver.findElement(emailField).isDisplayed() &&
                driver.findElement(passwordField).isDisplayed() &&
                driver.findElement(registerButton).isDisplayed();
    }

    // Метод регистрации
    public void register(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegisterButton();
    }

    // Ожидание видимости страницы авторизации "Вход" после регистрации
    public void waitLoginPageVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(LOGIN_PAGE_URL));
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(loginTitle));
    }

    // Методы для проверки успешной регистрации (редирект на логин "Вход")
    public boolean isLoginPageDisplayed() {
        return driver.getCurrentUrl().equals(LOGIN_PAGE_URL);
    }

    // Проверка предзаполненных полей
    public String getPreFilledEmail() {
        return driver.findElement(loginPageEmailField).getAttribute("value");
    }

    public String getPreFilledPassword() {
        return driver.findElement(loginPagePasswordField).getAttribute("value");
    }

    // Проверка, что поля предзаполнены правильными данными
    public boolean areFieldsPreFilledCorrectly(String expectedEmail, String expectedPassword) {
        return expectedEmail.equals(getPreFilledEmail()) &&
                expectedPassword.equals(getPreFilledPassword());
    }
}