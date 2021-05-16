package collection.list.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author chen
 * @date 2021/4/3 11:03
 * @Description
 */

@Accessors(chain = true)
@Data
public class Book {
    private String name;
    private String  author;
    private double price;
}
