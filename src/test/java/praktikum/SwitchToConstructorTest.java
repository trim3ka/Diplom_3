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

class SwitchToConstructorTest {

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

        clickLoginAccountButtonInSetup(); //Клик на "Войти в аккаунт"
        fillLoginFormInSetup(testEmail, testPassword); //Авторизация
        clickHeaderAccountButtonInSetup(); //Переход в личный кабинет
    }

    @Test
    @DisplayName("Переход из \"Личного кабинета\" в Конструктор через клик на Конструктор")
    void fromAccountToConstructorButton() {
        verifyProfileVisibleInAccount();
        clickConstructorButton();
        verifyPlaceOrderButtonVisible();
    }

    @Test
    @DisplayName("Переход из \"Личного кабинета\" в Конструктор через клик на Лого")
    void fromAccountToLogoButton() {
        verifyProfileVisibleInAccount();
        clickLogoButton();
        verifyPlaceOrderButtonVisible();
    }

    // Шаги теста
    @Step("Клик на 'Войти в аккаунт' (в setup)")
    private void clickLoginAccountButtonInSetup() {
        register.getMainPage().clickLoginAccountButton();
    }

    @Step("Заполнение формы авторизации (в setup)")
    private void fillLoginFormInSetup(String email, String password) {
        register.getLoginUser().loginForm(email, password);
    }

    @Step("Клик на 'Личный кабинет' (в setup)")
    private void clickHeaderAccountButtonInSetup() {
        register.getMainPage().clickHeaderAccountButton();
    }

    @Step("Проверка видимости поля 'Профиль' в личном кабинете")
    private void verifyProfileVisibleInAccount() {
        assertTrue(register.getAccount().isProfileFieldVisible(),
                "После авторизации и входа в Личный кабинет видно поле \"Профиль\"");
    }

    @Step("Клик на 'Конструктор'")
    private void clickConstructorButton() {
        register.getMainPage().clickConstructorButton();
    }

    @Step("Клик на 'Лого'")
    private void clickLogoButton() {
        register.getMainPage().clickLogoButton();
    }

    @Step("Проверка видимости кнопки 'Оформить заказ'")
    private void verifyPlaceOrderButtonVisible() {
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