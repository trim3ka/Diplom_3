package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import praktikum.api.ApiClient;
import praktikum.model.DriverExtension;
import praktikum.objects.Register;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConstructorBurgerTest {


    @RegisterExtension
    private final DriverExtension extension = new DriverExtension();
    private Register register;
    ValidatableResponse newUser;
    private String accessToken;
    private String testEmail;
    private String testPassword;
    private String testName;

    @BeforeEach
    public void setUp() {
        WebDriver driver = extension.getDriver();
        driver.get(Constants.BASE_URL);
        register = new Register(driver);

        testName = "mv_000";
        testEmail = "1_000@ya.ru";
        testPassword = "111111";

        newUser = ApiClient.getNewUser(testName, testEmail, testPassword);

        var response = newUser
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .extract().body().jsonPath();

        accessToken = response.getString("accessToken");

        register.getLoginUser().clickLoginAccountButton();//Клик на "Войти в аккаунт"
        register.getAccount().loginForm(testEmail, testPassword); //Авторизация
        register.getAccount().clickHeaderAccountButton(); //Переход в личный кабинет
    }

    @Test
    void fromAccountToConstructorButton() {

        //Проверка, что мы в личном кабинете, виден раздел "Профиль"
        assertTrue(register.getAccount().isProfileFieldVisible(),
                "После авторизации и входа в Личный кабинет видно поле \"Профиль\"");
        //Клик на "Конструктор"
        register.getConstructorBurger().clickConstructorButton();
        //Проверка, что мы в Конструкторе после авторизации - видна кнопка "Оформить заказ"
        assertTrue(register.getLoginUser().isPlaceAnOrderButtonVisible(),
                "После авторизации видна кнопка \"Оформить заказ\"");
    }

    @Test
    void fromAccountToLogoButton() {

        //Проверка, что мы в личном кабинете, виден раздел "Профиль"
        assertTrue(register.getAccount().isProfileFieldVisible(),
                "После авторизации и входа в Личный кабинет видно поле \"Профиль\"");
        //Клик на Лого
        register.getConstructorBurger().clickLogoButton();
        //Проверка, что мы в Конструкторе после авторизации - видна кнопка "Оформить заказ"
        assertTrue(register.getLoginUser().isPlaceAnOrderButtonVisible(),
                "После авторизации видна кнопка \"Оформить заказ\"");
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            ApiClient.deleteUser(accessToken)
                    .assertThat()
                    .statusCode(202)
                    .body("success", equalTo(true))
                    .body("message", equalTo("User successfully removed"));
        }
    }
}