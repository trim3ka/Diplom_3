package praktikum;

import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import praktikum.model.DriverExtension;
import praktikum.objects.Register;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BurgerConstructorMenu {

    @RegisterExtension
    private final DriverExtension extension = new DriverExtension();
    private Register register;

    @BeforeEach
    public void setUp() {
        WebDriver driver = extension.getDriver();
        driver.get(Constants.BASE_URL);
        register = new Register(driver);
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы' и проверка активации")
    void switchToSauceSection() {
        verifyInitialState();
        verifySauceTabIsInactive();
        clickSauceTab();
        verifySauceTabIsActive();
        verifyOtherTabsAreInactiveAfterSauceClick();
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки' и проверка активации")
    void switchToFillingSection() {
        verifyInitialState();
        verifyFillingTabIsInactive();
        clickFillingTab();
        verifyFillingTabIsActive();
        verifyOtherTabsAreInactiveAfterFillingClick();
    }

    @Test
    @DisplayName("Переход между разделами 'Булки' -> 'Соусы' -> 'Булки'")
    void switchBetweenBunAndSauceSections() {
        verifyInitialState();

        // Клик на "Соусы"
        verifySauceTabIsInactive();
        clickSauceTab();
        verifySauceTabIsActive();
        verifyBunTabIsInactiveAfterSauceClick();

        // Возврат к вкладке "Булки"
        clickBunTab();
        verifyBunTabIsActive();
        verifyOtherTabsAreInactiveAfterBunClick();
    }

    @Step("Проверка начального состояния - раздел 'Булки' активен")
    private void verifyInitialState() {
        assertTrue(register.getMainPage().isBunTabActive(),
                "Изначально раздел 'Булки' должен быть активным");
        assertFalse(register.getMainPage().isSauceTabActive(),
                "Изначально раздел 'Соусы' должен быть неактивным");
        assertFalse(register.getMainPage().isFillingTabActive(),
                "Изначально раздел 'Начинки' должен быть неактивным");
    }

    @Step("Проверка что раздел 'Соусы' неактивен")
    private void verifySauceTabIsInactive() {
        assertTrue(register.getMainPage().isSauceTabInactive(),
                "Раздел 'Соусы' должен быть неактивным перед кликом");
    }

    @Step("Проверка что раздел 'Начинки' неактивен")
    private void verifyFillingTabIsInactive() {
        assertTrue(register.getMainPage().isFillingTabInactive(),
                "Раздел 'Начинки' должен быть неактивным перед кликом");
    }

    @Step("Клик на раздел 'Соусы'")
    private void clickSauceTab() {
        register.getMainPage().clickSauceTab();
    }

    @Step("Клик на раздел 'Начинки'")
    private void clickFillingTab() {
        register.getMainPage().clickFillingTab();
    }

    @Step("Клик на раздел 'Булки'")
    private void clickBunTab() {
        register.getMainPage().clickBunTab();
    }

    @Step("Проверка что раздел 'Соусы' стал активен")
    private void verifySauceTabIsActive() {
        assertTrue(register.getMainPage().isSauceTabActive(),
                "После клика раздел 'Соусы' должен быть активным");
    }

    @Step("Проверка что раздел 'Начинки' стал активен")
    private void verifyFillingTabIsActive() {
        assertTrue(register.getMainPage().isFillingTabActive(),
                "После клика раздел 'Начинки' должен быть активным");
    }

    @Step("Проверка что раздел 'Булки' стал активен")
    private void verifyBunTabIsActive() {
        assertTrue(register.getMainPage().isBunTabActive(),
                "раздел 'Булки' должен быть активным");
    }

    @Step("Проверка что другие разделы неактивны после активации 'Соусы'")
    private void verifyOtherTabsAreInactiveAfterSauceClick() {
        assertFalse(register.getMainPage().isBunTabActive(),
                "После активации 'Соусы' раздел 'Булки' должен быть неактивным");
        assertFalse(register.getMainPage().isFillingTabActive(),
                "После активации 'Соусы' раздел 'Начинки' должен быть неактивным");
    }

    @Step("Проверка что другие разделы неактивны после активации 'Начинки'")
    private void verifyOtherTabsAreInactiveAfterFillingClick() {
        assertFalse(register.getMainPage().isBunTabActive(),
                "После активации 'Начинки' раздел 'Булки' должен быть неактивным");
        assertFalse(register.getMainPage().isSauceTabActive(),
                "После активации 'Начинки' раздел 'Соусы' должен быть неактивным");
    }

    @Step("Проверка что другие разделы неактивны после активации 'Булки'")
    private void verifyOtherTabsAreInactiveAfterBunClick() {
        assertFalse(register.getMainPage().isSauceTabActive(),
                "После активации 'Булки' раздел 'Соусы' должен быть неактивным");
        assertFalse(register.getMainPage().isFillingTabActive(),
                "После активации 'Булки' раздел 'Начинки' должен быть неактивным");
    }

    @Step("Проверка что раздел 'Булки' стал неактивен после активации 'Соусы'")
    private void verifyBunTabIsInactiveAfterSauceClick() {
        assertTrue(register.getMainPage().isBunTabInactive(),
                "После активации 'Соусы' раздел 'Булки' должен быть неактивным");
    }
}