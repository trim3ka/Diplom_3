package praktikum;

import io.qameta.allure.Step;
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
        clickLoginAccountButton();
        fillLoginForm(testUser.getEmail(), testUser.getPassword());
        verifyLoginSuccess();
    }

    @Test
    @DisplayName("Авторизация пользователя через кнопку \"Личный кабинет\"")
    void loginWithHeaderAccountButton() {
        clickHeaderAccountButton();
        fillLoginForm(testUser.getEmail(), testUser.getPassword());
        verifyLoginSuccess();
    }

    @Test
    @DisplayName("Авторизация пользователя через кнопку \"Войти\" в форме регистрации")
    void loginWithButtonInRegistrationForm() {
        clickHeaderAccountButton();
        clickRegisterLink();
        clickLoginHyperlink();
        fillLoginForm(testUser.getEmail(), testUser.getPassword());
        verifyLoginSuccess();
    }

    @Test
    @DisplayName("Авторизация пользователя через кнопку \"Войти\" в форме восстановления пароля")
    void loginWithButtonInForgotPasswordForm() {
        clickHeaderAccountButton();
        clickForgotPassword();
        clickLoginLinkFromForgotPassword();
        fillLoginForm(testUser.getEmail(), testUser.getPassword());
        verifyLoginSuccess();
    }

    // Шаги теста
    @Step("Клик на кнопку 'Войти в аккаунт'")
    private void clickLoginAccountButton() {
        register.getMainPage().clickLoginAccountButton();
    }

    @Step("Клик на кнопку 'Личный кабинет'")
    private void clickHeaderAccountButton() {
        register.getMainPage().clickHeaderAccountButton();
    }

    @Step("Клик на ссылку 'Зарегистрироваться'")
    private void clickRegisterLink() {
        register.getLoginUser().clickRegisterLink();
    }

    @Step("Клик на ссылку 'Восстановить пароль'")
    private void clickForgotPassword() {
        register.getLoginUser().clickForgotPassword();
    }

    @Step("Клик на ссылку 'Войти' из формы восстановления пароля")
    private void clickLoginLinkFromForgotPassword() {
        register.getForgotPassword().clickloginLinkFromPageForgotPassword();
    }

    @Step("Клик на ссылку 'Войти' из формы регистрации")
    private void clickLoginHyperlink() {
        register.clickLoginHyperlink();
    }

    @Step("Заполнение формы авторизации")
    private void fillLoginForm(String email, String password) {
        register.getLoginUser().loginForm(email, password);
    }

    @Step("Проверка успешной авторизации")
    private void verifyLoginSuccess() {
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