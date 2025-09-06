package praktikum;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import praktikum.model.DriverExtension;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterNegativeTest {

    @RegisterExtension
    private final DriverExtension extension = new DriverExtension();
    private RegisterPage registerPage;


    @ParameterizedTest
    @MethodSource("registerNegativePassword")
    void testNegativeRegistrationWithUncorrectPassword(String name, String email, String password) {
        WebDriver driver = extension.getDriver();
        driver.get(Constants.BASE_URL);

        registerPage = new RegisterPage(driver);

        //Переходим к регистрации
        registerPage.clickLoginButton();
        registerPage.clickRegisterLink();

        //Заполняем и отправляем форму регистрации
        registerPage.register(name, email, password);

        // Проверяем, что появилась ошибка под полем "Пароль"
        assertEquals(registerPage.getPasswordErrorText(),
                "Некорректный пароль");
    }

    static Stream<Arguments> registerNegativePassword() {
        return Stream.of(
                Arguments.of("neg32", "d32@ya.ru", "12345"),
                Arguments.of("NE_G", "marina_test@yandex.ru", "1"),
                Arguments.of("NG", "marina_vovk@yandex.ru", " ")
        );
    }
}