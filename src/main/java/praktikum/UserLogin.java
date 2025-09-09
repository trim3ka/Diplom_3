package praktikum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogin {

    private String email;
    private String password;

    public static UserLogin from (UserCreated user) {
        return new UserLogin(user.getEmail(), user.getPassword());
    }
}
