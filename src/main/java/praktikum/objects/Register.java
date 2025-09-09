package praktikum.objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Register extends BasePage {
    private final PersonalAccount personalAccount;
    private final LogInUser logInUser;
    private final MainPage mainPage;
    private final ForgotPassword forgotPassword;

    public Register(WebDriver driver) {
        super(driver);
        this.personalAccount = new PersonalAccount(driver);
        this.logInUser = new LogInUser(driver);
        this.mainPage = new MainPage(driver);
        this.forgotPassword = new ForgotPassword(driver);
    }

    // Локаторы страницы "Регистрация"
    private final By registerTitle = By.xpath("//h2[text()='Регистрация']");
    private final By nameField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginHyperlink = By.xpath("//a[text()='Войти']");
    private final By passwordErrorField = By.xpath(".//p[text()='Некорректный пароль']");

    @Step("Ввод имени в поле регистрации: {name}")
    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    @Step("Ввод email в поле регистрации: {email}")
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Ввод password в поле регистрации")
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Клик на кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    @Step("Проверка видимости формы регистрации")
    public boolean isRegisterFormDisplayed() {
        return driver.findElement(nameField).isDisplayed() &&
                driver.findElement(emailField).isDisplayed() &&
                driver.findElement(passwordField).isDisplayed() &&
                driver.findElement(registerButton).isDisplayed();
    }

    @Step("Регистрация пользователя: имя = {name}, email = {email}")
    public void register(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegisterButton();
    }

    @Step("Клик на 'Войти' на странице формы регистрации")
    public void clickLoginHyperlink() {
        driver.findElement(loginHyperlink).click();
    }

    @Step("Получение текста ошибки валидации пароля")
    public String getPasswordErrorText() {
        return driver.findElement(passwordErrorField).getText();
    }

    public PersonalAccount getAccount() {
        return personalAccount;
    }

    public LogInUser getLoginUser() {
        return logInUser;
    }

    public MainPage getMainPage() {
        return mainPage;
    }

    public ForgotPassword getForgotPassword() {
        return forgotPassword;
    }
}