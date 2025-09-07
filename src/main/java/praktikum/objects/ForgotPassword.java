package praktikum.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPassword extends BasePage {

    public ForgotPassword(WebDriver driver) {
        super(driver);
    }

    // Локаторы старинцы "Восстановление пароля"
    private By loginLinkFromPageForgotPassword = By.xpath("//a[text()='Войти']");

    //Клик на "Войти" на странице Восстановления пароля и формы регистрации
    public void clickloginLinkFromPageForgotPassword() {
        driver.findElement(loginLinkFromPageForgotPassword).click();
    }
}
