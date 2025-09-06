package praktikum.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ConstructorBurger extends BasePage {
    public ConstructorBurger(WebDriver driver) {
        super(driver);
    }

    // Локаторы
    private By constructorButton = By.xpath("//p[text()='Конструктор']");
    private By logoButton = By.className("AppHeader_header__logo__2D0X2");


    //Клик на Конструктор
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    //Клик на Лого
    public void clickLogoButton() {
        driver.findElement(logoButton).click();
    }
}
