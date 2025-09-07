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
        clickHeaderAccountButtonInSetup();
        //заполнение формы авторизации данными созданного пользователя и клик по "Войти"
        fillLoginFormInSetup(testEmail, testPassword);
    }

    @Test
    @DisplayName("Переход по клику в \"Личный кабинет\" для авторизованного пользователя")
    void clickAccountButton() {
        clickHeaderAccountButton();
        verifyProfileVisible();
    }

    @Test
    @DisplayName("Логаут из \"Личного кабинета\"")
    void clickLogoutButton() {
        waitForAccountButtonVisible();
        clickHeaderAccountButton();
        waitForLogoutButtonVisible();
        clickLogoutButtonStep();
        verifyLoginPageVisibleAfterLogout();
    }

    // Шаги теста
    @Step("Клик на 'Личный кабинет' в хэдере (в setup)")
    private void clickHeaderAccountButtonInSetup() {
        register.getMainPage().clickHeaderAccountButton();
    }

    @Step("Заполнение формы авторизации")
    private void fillLoginFormInSetup(String email, String password) {
        register.getLoginUser().loginForm(email, password);
    }

    @Step("Клик на 'Личный кабинет' в хэдере")
    private void clickHeaderAccountButton() {
        register.getMainPage().clickHeaderAccountButton();
    }

    @Step("Проверка видимости поля 'Профиль'")
    private void verifyProfileVisible() {
        assertTrue(register.getAccount().isProfileFieldVisible(),
                "После авторизации и входа в Личный кабинет видно поле \"Профиль\"");
    }

    @Step("Ожидание видимости кнопки аккаунта")
    private void waitForAccountButtonVisible() {
        register.getMainPage().isAccountButtonVisible();
    }

    @Step("Ожидание видимости кнопки 'Выход'")
    private void waitForLogoutButtonVisible() {
        register.getAccount().isLogoutButtonVisible();
    }

    @Step("Клик на кнопку 'Выход'")
    private void clickLogoutButtonStep() {
        register.getAccount().clickLogoutButton();
    }

    @Step("Проверка страницы авторизации после логаута")
    private void verifyLoginPageVisibleAfterLogout() {
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