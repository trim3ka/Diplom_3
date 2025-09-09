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

class LogInUserTest {
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
    }

    @Test
    @DisplayName("Авторизация пользователя через кнопку \"Войти в аккаунт\"")
    void loginWithLoginAccountButton() {
        register.getMainPage().clickLoginAccountButton();
        register.getLoginUser().loginForm(testUser.getEmail(), testUser.getPassword());

        assertTrue(register.getMainPage().isPlaceAnOrderButtonVisible(),
                "После авторизации видна кнопка \"Оформить заказ\"");
    }

    @Test
    @DisplayName("Авторизация пользователя через кнопку \"Личный кабинет\"")
    void loginWithHeaderAccountButton() {
        register.getMainPage().clickHeaderAccountButton();
        register.getLoginUser().loginForm(testUser.getEmail(), testUser.getPassword());

        assertTrue(register.getMainPage().isPlaceAnOrderButtonVisible(),
                "После авторизации видна кнопка \"Оформить заказ\"");
    }

    @Test
    @DisplayName("Авторизация пользователя через кнопку \"Войти\" в форме регистрации")
    void loginWithButtonInRegistrationForm() {
        register.getMainPage().clickHeaderAccountButton();
        register.getLoginUser().clickRegisterLink();
        register.clickLoginHyperlink();
        register.getLoginUser().loginForm(testUser.getEmail(), testUser.getPassword());

        assertTrue(register.getMainPage().isPlaceAnOrderButtonVisible(),
                "После авторизации видна кнопка \"Оформить заказ\"");
    }

    @Test
    @DisplayName("Авторизация пользователя через кнопку \"Войти\" в форме восстановления пароля")
    void loginWithButtonInForgotPasswordForm() {
        register.getMainPage().clickHeaderAccountButton();
        register.getLoginUser().clickForgotPassword();
        register.getForgotPassword().clickloginLinkFromPageForgotPassword();
        register.getLoginUser().loginForm(testUser.getEmail(), testUser.getPassword());

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