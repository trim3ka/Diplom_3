package praktikum.objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LogInUser extends BasePage {

    public LogInUser(WebDriver driver) {
        super(driver);
    }

    // Локаторы старинцы "Вход"
    private final By loginTitle = By.xpath("//h2[text()='Вход']");
    private final By loginPageEmailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By loginPagePasswordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private final By forgotPassword = By.xpath("//a[text()='Восстановить пароль']");

    @Step("Ввод email в поле авторизации: {email}")
    public void enterEmailLogin(String email) {
        driver.findElement(loginPageEmailField).sendKeys(email);
    }

    @Step("Ввод пароля в поле авторизации")
    public void enterPasswordLogin(String password) {
        driver.findElement(loginPagePasswordField).sendKeys(password);
    }

    @Step("Клик на кнопку 'Войти'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Авторизация пользователя: email = {email}")
    public void loginForm(String email, String password) {
        enterEmailLogin(email);
        enterPasswordLogin(password);
        clickLoginButton();
    }

    @Step("Клик на 'Восстановить пароль'")
    public void clickForgotPassword() {
        driver.findElement(forgotPassword).click();
    }

    @Step("Ожидание видимости страницы авторизации 'Вход'")
    public void waitLoginPageVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginTitle));
    }

    @Step("Клик на ссылку 'Зарегистрироваться'")
    public void clickRegisterLink() {
        driver.findElement(registerLink).click();
    }

    @Step("Проверка видимости страницы авторизации")
    public boolean isLoginPageVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginTitle));
        return true;
    }
}