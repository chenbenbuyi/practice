package skill.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2021/6/23 14:16
 * @author: chen
 */


@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class Users extends ArrayList<User> {

    @XmlElement(name = "user")
    public List<User> users = this;
}
