package praktikum.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static praktikum.Constants.LOGIN_PAGE_URL;

public class Register extends BasePage {
    private final PersonalCabinet personalCabinet;

    public Register(WebDriver driver) {
        super(driver);
        this.personalCabinet = new PersonalCabinet(driver);
    }

    // Локаторы
    private By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private By nameField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private By passwordErrorField = By.xpath(".//p[text()='Некорректный пароль']");
    private By registerButton = By.xpath("//button[text()='Зарегистрироваться']");

    // Методы для страницы регистрации
    public void clickRegisterLink() {
        driver.findElement(registerLink).click();
    }

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
        wait.until(ExpectedConditions.urlToBe(LOGIN_PAGE_URL));
        wait.until(ExpectedConditions.visibilityOfElementLocated(PersonalCabinet.loginTitle));
    }

    public boolean isLoginPageDisplayed() {
        return driver.getCurrentUrl().equals(LOGIN_PAGE_URL);
    }

    public String getPasswordErrorText() {
        return driver.findElement(passwordErrorField).getText();
    }

    public PersonalCabinet getAccount() {
        return personalCabinet;
    }
}