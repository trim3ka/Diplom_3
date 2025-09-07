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

public class PersonalAccountTest {
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

        testName = "mv000";
        testEmail = "1000@ya.ru";
        testPassword = "111111";

        newUser = ApiClient.getNewUser(testName, testEmail, testPassword);

        var response = newUser
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .extract().body().jsonPath();

        accessToken = response.getString("accessToken");

        //Клик на "Личный кабинет" в хэдере
        register.getMainPage().clickHeaderAccountButton();
        //заполнение формы авторизации данными созданного пользователя и клик по "Войти"
        register.getLoginUser().loginForm(testEmail, testPassword);
    }

    @Test
    void clickAccountButton() {
        //Клик на "Личный кабинет" в хэдере
        register.getMainPage().clickHeaderAccountButton();
        // Проверяем, что видно поле "Профиль", значит зашли в "Личный кабинет" успешно
        assertTrue(register.getAccount().isProfileFieldVisible(),
                "После авторизации и входа в Личный кабинет видно поле \"Профиль\"");
    }

    @Test
    void clickLogoutButton() {
        //Ждем, чтобы страница загрузилась
        register.getMainPage().isAccountButtonVisible();
        //Клик на "Личный кабинет" в хэдере
        register.getMainPage().clickHeaderAccountButton();
        //Ждем, чтобы страница загрузилась
        register.getAccount().isLogoutButtonVisible();
        //Клик на "Выход" в Личном кабинете
        register.getAccount().clickLogoutButton();
        //Проверка, что логаут прошел успешно, открылась страница "Вход" с формой авторизации
        assertTrue(register.getLoginUser().isLoginPageVisible(),
                "После логаута должна открыться страница авторизации");
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
