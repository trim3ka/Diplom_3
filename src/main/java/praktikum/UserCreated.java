package praktikum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreated {
    private String name;
    private String email;
    private String password;

        // Метод для генерации случайного пользователя
        public static UserCreated random() {
            var rnd = new Random();
            return new UserCreated(
                    "m_v_"+ rnd.nextInt(100),
                    "m_v" + rnd.nextInt(100) + "@ya.ru",
                    "123456"
            );
        }
    }