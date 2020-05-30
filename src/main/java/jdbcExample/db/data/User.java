package jdbcExample.db.data;

import jdbcExample.model.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private String surname;
    private Gender gender;
    private int age;
    private String phone;
    private String password;
}
