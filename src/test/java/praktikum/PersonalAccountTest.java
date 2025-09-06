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

        testName = "mv00";
        testEmail = "100@ya.ru";
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
    void clickAccountButton() {
        //Клик на "Личный кабинет" в хэдере
        register.getAccount().clickHeaderAccountButton();
        //заполнение формы авторизации данными созданного пользователя и клик по "Войти"
        register.getAccount().loginForm(testEmail, testPassword);
        //Клик на "Личный кабинет" в хэдере
        register.getAccount().clickHeaderAccountButton();
        // Проверяем, что видно поле "Профиль", значит зашли в "Личный кабинет" успешно
        assertTrue(register.getAccount().isProfileFieldVisible(),
                "После авторизации и входа в Личный кабинет видно поле \"Профиль\"");
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
