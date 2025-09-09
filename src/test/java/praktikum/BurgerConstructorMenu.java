package praktikum;

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
        assertTrue(register.getMainPage().isBunTabActive(),
                "Изначально раздел 'Булки' должен быть активным");
        assertFalse(register.getMainPage().isSauceTabActive(),
                "Изначально раздел 'Соусы' должен быть неактивным");
        assertFalse(register.getMainPage().isFillingTabActive(),
                "Изначально раздел 'Начинки' должен быть неактивным");

        assertTrue(register.getMainPage().isSauceTabInactive(),
                "Раздел 'Соусы' должен быть неактивным перед кликом");

        register.getMainPage().clickSauceTab();

        assertTrue(register.getMainPage().isSauceTabActive(),
                "После клика раздел 'Соусы' должен быть активным");
        assertFalse(register.getMainPage().isBunTabActive(),
                "После активации 'Соусы' раздел 'Булки' должен быть неактивным");
        assertFalse(register.getMainPage().isFillingTabActive(),
                "После активации 'Соусы' раздел 'Начинки' должен быть неактивным");
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки' и проверка активации")
    void switchToFillingSection() {
        assertTrue(register.getMainPage().isBunTabActive(),
                "Изначально раздел 'Булки' должен быть активным");
        assertFalse(register.getMainPage().isSauceTabActive(),
                "Изначально раздел 'Соусы' должен быть неактивным");
        assertFalse(register.getMainPage().isFillingTabActive(),
                "Изначально раздел 'Начинки' должен быть неактивным");

        assertTrue(register.getMainPage().isFillingTabInactive(),
                "Раздел 'Начинки' должен быть неактивным перед кликом");

        register.getMainPage().clickFillingTab();

        assertTrue(register.getMainPage().isFillingTabActive(),
                "После клика раздел 'Начинки' должен быть активным");
        assertFalse(register.getMainPage().isBunTabActive(),
                "После активации 'Начинки' раздел 'Булки' должен быть неактивным");
        assertFalse(register.getMainPage().isSauceTabActive(),
                "После активации 'Начинки' раздел 'Соусы' должен быть неактивным");
    }

    @Test
    @DisplayName("Переход между разделами 'Булки' -> 'Соусы' -> 'Булки'")
    void switchBetweenBunAndSauceSections() {
        assertTrue(register.getMainPage().isBunTabActive(),
                "Изначально раздел 'Булки' должен быть активным");
        assertFalse(register.getMainPage().isSauceTabActive(),
                "Изначально раздел 'Соусы' должен быть неактивным");
        assertFalse(register.getMainPage().isFillingTabActive(),
                "Изначально раздел 'Начинки' должен быть неактивным");

        // Клик на "Соусы"
        assertTrue(register.getMainPage().isSauceTabInactive(),
                "Раздел 'Соусы' должен быть неактивным перед кликом");

        register.getMainPage().clickSauceTab();

        assertTrue(register.getMainPage().isSauceTabActive(),
                "После клика раздел 'Соусы' должен быть активным");
        assertTrue(register.getMainPage().isBunTabInactive(),
                "После активации 'Соусы' раздел 'Булки' должен быть неактивным");

        // Возврат к вкладке "Булки"
        register.getMainPage().clickBunTab();

        assertTrue(register.getMainPage().isBunTabActive(),
                "раздел 'Булки' должен быть активным");
        assertFalse(register.getMainPage().isSauceTabActive(),
                "После активации 'Булки' раздел 'Соусы' должен быть неактивным");
        assertFalse(register.getMainPage().isFillingTabActive(),
                "После активации 'Булки' раздел 'Начинки' должен быть неактивным");
    }
}