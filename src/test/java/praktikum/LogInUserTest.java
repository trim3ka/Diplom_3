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
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogInUserTest {
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
    }

    @Test
    void loginWithLoginAccountButton() {
        //Клик на "Войти в аккаунт" на главной странице
        register.getLoginUser().clickLoginAccountButton();
        //заполнение формы авторизации данными созданного пользователя и клик по "Войти"
        register.getAccount().loginForm(testEmail, testPassword);

        //Проверка видимости кнопки "Оформить заказ", значить авторизация успешна
        assertTrue(register.getLoginUser().isPlaceAnOrderButtonVisible(),
                "После авторизации видна кнопка \"Оформить заказ\"");
    }

    @Test
    void loginWithHeaderAccountButton() {
        //Клик на "Личный кабинет" на главной странице
        register.getAccount().clickHeaderAccountButton();
        //заполнение формы авторизации данными созданного пользователя и клик по "Войти"
        register.getAccount().loginForm(testEmail, testPassword);

        //Проверка видимости кнопки "Оформить заказ", значит авторизация успешна
        assertTrue(register.getLoginUser().isPlaceAnOrderButtonVisible(),
                "После авторизации видна кнопка \"Оформить заказ\"");
    }

    @Test
    void loginWithButtonInRegistrationForm() {
        //Клик на "Личный кабинет" на главной странице
        register.getAccount().clickHeaderAccountButton();

        //Переход по гиперссылке "Зарегистрироваться"
        register.clickRegisterLink();

        //Переход по гиперссылке "Войти" на странице регистрации
        register.getLoginUser().clickLoginHyperlink();

        //заполнение формы авторизации данными созданного пользователя и клик по "Войти"
        register.getAccount().loginForm(testEmail, testPassword);

        //Проверка видимости кнопки "Оформить заказ", значить авторизация успешна
        assertTrue(register.getLoginUser().isPlaceAnOrderButtonVisible(),
                "После авторизации видна кнопка \"Оформить заказ\"");
    }

    @Test
    void loginWithButtonInForgotPasswordForm() {
        //Клик на "Личный кабинет" на главной странице
        register.getAccount().clickHeaderAccountButton();
        //Переход по гиперссылке "Восстановить пароль"
        register.getLoginUser().clickForgotPassword();
        //Переход по гиперссылке "Войти" на странице Восстановления пароля
        register.getLoginUser().clickLoginHyperlink();
        //заполнение формы авторизации данными созданного пользователя и клик по "Войти"
        register.getAccount().loginForm(testEmail, testPassword);

        //Проверка видимости кнопки "Оформить заказ", значит авторизация успешна
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