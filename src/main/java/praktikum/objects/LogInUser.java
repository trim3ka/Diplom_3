package praktikum.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LogInUser extends BasePage {

    public LogInUser(WebDriver driver) {
        super(driver);
    }

    // Локаторы
    private By loginAccountButton = By.xpath("//button[text()='Войти в аккаунт']"); //Кнопка "Войти в аккаунт"
    private By forgotPassword = By.xpath("//a[text()='Восстановить пароль']");
    private By loginHyperlink = By.xpath("//a[text()='Войти']"); //гиперссылка "Войти" на стр Восстановления пароля и формы регистрации
    private By placeAnOrderButton = By.xpath("//button[text()='Оформить заказ']");


    //Клик на "Войти в аккаунт"
    public void clickLoginAccountButton() {
        driver.findElement(loginAccountButton).click();
    }

    //Клик на "Восстановить пароль"
    public void clickForgotPassword() {
        driver.findElement(forgotPassword).click();
    }

    //Клик на "Войти" на странице Восстановления пароля и формы регистрации
    public void clickLoginHyperlink() {
        driver.findElement(loginHyperlink).click();
    }

    //Видимость кнопки "Оформить заказ"
    public boolean isPlaceAnOrderButtonVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(placeAnOrderButton));
        return true;
    }
}
