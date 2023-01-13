import lombok.Builder;
import lombok.Data;

/**
 * @author chen
 * @date 2023/1/13 22:14
 * @Description
 */

@Data
@Builder
public class User {

    private String name;

    private String addr;

    public static void main(String[] args) {
        UserBuilder builder = User.builder();
        User build = builder.addr("").name("").build();
    }
}
