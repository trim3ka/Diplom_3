package praktikum.objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPassword extends BasePage {

    public ForgotPassword(WebDriver driver) {
        super(driver);
    }

    // Локаторы старинцы "Восстановление пароля"
    private final By loginLinkFromPageForgotPassword = By.xpath("//a[text()='Войти']");

    @Step("Клик на 'Войти' на странице Восстановления пароля")
    public void clickloginLinkFromPageForgotPassword() {
        driver.findElement(loginLinkFromPageForgotPassword).click();
    }
}
