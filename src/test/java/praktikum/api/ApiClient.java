package praktikum.api;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import praktikum.Constants;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public static ValidatableResponse loginUser(String email, String password) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URL)
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password))
                .when()
                .post(Constants.LOGIN_USER_API)
                .then().log().all();
    }

    public static ValidatableResponse deleteUser(String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URL)
                .header("Authorization", accessToken)
                .when()
                .delete(Constants.USER_MANAGEMENT_API)
                .then().log().all();
    }

    public static void deleteUser(String email, String password) {
        try {
            // Сначала получаем токен через логин
            ValidatableResponse loginResponse = loginUser(email, password);
            String accessToken = loginResponse.extract().jsonPath().getString("accessToken");

            if (accessToken != null && !accessToken.isEmpty()) {
                // Удаляем пользователя по токену
                deleteUser(accessToken);
                System.out.println("Успешно удален пользователь: " + email);
            } else {
                System.out.println("Не удалось получить токен для пользователя: " + email);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при удалении пользователя " + email + ": " + e.getMessage());
        }
    }
}