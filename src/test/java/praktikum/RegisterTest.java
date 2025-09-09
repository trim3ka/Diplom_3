package praktikum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import praktikum.api.ApiClient;
import praktikum.model.DriverExtension;
import praktikum.objects.Register;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterTest {

    @RegisterExtension
    private final DriverExtension extension = new DriverExtension();
    private Register register;

    private String lastTestEmail;
    private String lastTestPassword;

    @ParameterizedTest
    @MethodSource("registerParams")
    @DisplayName("Успешная регистрация пользователя с разными данными")
    void testSuccessfulRegistration(String name, String email, String password) {
        WebDriver driver = extension.getDriver();
        driver.get(Constants.BASE_URL);

        register = new Register(driver);

        lastTestEmail = email;
        lastTestPassword = password;

        // Переходим к регистрации
        register.getMainPage().clickHeaderAccountButton();
        register.getLoginUser().clickRegisterLink();

        // Заполняем и отправляем форму регистрации
        register.register(name, email, password);

        // Ждем и проверяем успешную регистрацию
        register.getLoginUser().waitLoginPageVisible();
        assertTrue(register.getLoginUser().isLoginPageVisible(),
                "После регистрации должна открыться страница \"Вход\"");
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
    }

    static Stream<Arguments> registerParams() {
        return Stream.of(
                Arguments.of("d322", "d32@ya.ru", "123456"),
                Arguments.of("Mar ina", "marina_test@yandex.ru", "111111111"),
                Arguments.of("MV", "marina_vovk@yandex.ru", "1234567")
        );
    }
}