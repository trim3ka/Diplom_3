package praktikum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import praktikum.api.ApiClient;
import praktikum.model.DriverExtension;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterPageTest {

    @RegisterExtension
    private final DriverExtension extension = new DriverExtension();
    private RegisterPage registerPage;

    private String lastTestEmail;
    private String lastTestPassword;

    @ParameterizedTest
    @MethodSource("registerParams")
    void testSuccessfulRegistrationWithPreFilledFields(String name, String email, String password) {
        WebDriver driver = extension.getDriver();
        driver.get(Constants.BASE_URL);

        registerPage = new RegisterPage(driver);

        lastTestEmail = email;
        lastTestPassword = password;

        //Переходим к регистрации
        registerPage.clickLoginButton();
        registerPage.clickRegisterLink();

        //Заполняем и отправляем форму регистрации
        registerPage.register(name, email, password);

        //Ждем прогрузки страницы "Вход"
        registerPage.waitLoginPageVisible();

        // Проверяем, что после регистрации открылась страница "Вход"
        assertTrue(registerPage.isLoginPageDisplayed(),
                "После регистрации должна открыться страница логина");
    }

    @AfterEach
    void tearDown() {
        if (lastTestEmail != null && lastTestPassword != null) {
            // Удаляем пользователя через API
            ApiClient.deleteUser(lastTestEmail, lastTestPassword);

            // Очищаем данные
            lastTestEmail = null;
            lastTestPassword = null;
        }

        // Закрываем браузер
        if (extension.getDriver() != null) {
            extension.getDriver().quit();
        }
    }

    static Stream<Arguments> registerParams() {
        return Stream.of(
                Arguments.of("d32", "d32@ya.ru", "12345678"),
                Arguments.of("Marina", "marina_test@yandex.ru", "11111111"),
                Arguments.of("MV", "marina_vovk@yandex.ru", "10203040506")
        );
    }
}