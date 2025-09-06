package praktikum;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import praktikum.model.DriverExtension;
import praktikum.objects.Register;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterNegativeTest {

    @RegisterExtension
    private final DriverExtension extension = new DriverExtension();
    private Register register;


    @ParameterizedTest
    @MethodSource("registerNegativePassword")
    void testNegativeRegistrationWithUncorrectPassword(String name, String email, String password) {
        WebDriver driver = extension.getDriver();
        driver.get(Constants.BASE_URL);

        register = new Register(driver);

        //Переходим к регистрации
        register.getAccount().clickHeaderAccountButton();
        register.clickRegisterLink();

        //Заполняем и отправляем форму регистрации
        register.register(name, email, password);

        // Проверяем, что появилась ошибка под полем "Пароль"
        assertEquals(register.getPasswordErrorText(),
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