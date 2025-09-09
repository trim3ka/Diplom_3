package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import praktikum.api.ApiClient;
import praktikum.model.DriverExtension;
import praktikum.objects.Register;
import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SwitchToConstructorTest {

    @RegisterExtension
    private final DriverExtension extension = new DriverExtension();
    private Register register;
    private String accessToken;
    private UserCreated testUser;

    @BeforeEach
    public void setUp() {
        WebDriver driver = extension.getDriver();
        driver.get(Constants.BASE_URL);
        register = new Register(driver);

        // Генерация случайного пользователя
        testUser = UserCreated.random();

        ValidatableResponse newUser = ApiClient.getNewUser(
                testUser.getName(),
                testUser.getEmail(),
                testUser.getPassword()
        );

        var response = newUser
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .extract().body().jsonPath();

        accessToken = response.getString("accessToken");

        // Клик на "Войти в аккаунт"
        register.getMainPage().clickLoginAccountButton();
        // Авторизация
        register.getLoginUser().loginForm(testUser.getEmail(), testUser.getPassword());
        // Переход в личный кабинет
        register.getMainPage().clickHeaderAccountButton();
    }

    @Test
    @DisplayName("Переход из \"Личного кабинета\" в Конструктор через клик на Конструктор")
    void fromAccountToConstructorButton() {
        assertTrue(register.getAccount().isProfileFieldVisible(),
                "После авторизации и входа в Личный кабинет видно поле \"Профиль\"");

        register.getMainPage().clickConstructorButton();

        assertTrue(register.getMainPage().isPlaceAnOrderButtonVisible(),
                "После авторизации видна кнопка \"Оформить заказ\"");
    }

    @Test
    @DisplayName("Переход из \"Личного кабинета\" в Конструктор через клик на Лого")
    void fromAccountToLogoButton() {
        assertTrue(register.getAccount().isProfileFieldVisible(),
                "После авторизации и входа в Личный кабинет видно поле \"Профиль\"");

        register.getMainPage().clickLogoButton();

        assertTrue(register.getMainPage().isPlaceAnOrderButtonVisible(),
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