package praktikum.objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PersonalAccount extends BasePage {
    public PersonalAccount(WebDriver driver) {
        super(driver);
    }

    //Локаторы страницы Личного кабинета
    private final By profileField = By.xpath("//a[text()='Профиль']");
    private final By logoutButton = By.xpath("//button[text()='Выход']");

    @Step("Проверка видимости кнопки 'Выход'")
    public boolean isLogoutButtonVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton));
        return true;
    }

    @Step("Клик на кнопку 'Выход' в Личном кабинете")
    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }

    @Step("Проверка видимости поля 'Профиль'")
    public boolean isProfileFieldVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileField));
        return true;
    }
}