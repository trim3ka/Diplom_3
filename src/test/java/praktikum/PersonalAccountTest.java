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

public class PersonalAccountTest {
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

        // Клик на "Личный кабинет" в хэдере
        register.getMainPage().clickHeaderAccountButton();
        // Заполнение формы авторизации данными созданного пользователя и клик по "Войти"
        register.getLoginUser().loginForm(testUser.getEmail(), testUser.getPassword());
    }

    @Test
    @DisplayName("Переход по клику в \"Личный кабинет\" для авторизованного пользователя")
    void clickAccountButton() {
        register.getMainPage().clickHeaderAccountButton();

        assertTrue(register.getAccount().isProfileFieldVisible(),
                "После авторизации и входа в Личный кабинет видно поле \"Профиль\"");
    }

    @Test
    @DisplayName("Логаут из \"Личного кабинета\"")
    void clickLogoutButton() {
        register.getMainPage().isAccountButtonVisible();
        register.getMainPage().clickHeaderAccountButton();
        register.getAccount().isLogoutButtonVisible();
        register.getAccount().clickLogoutButton();

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