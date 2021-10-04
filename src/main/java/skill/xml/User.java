package skill.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @date: 2021/6/22 16:09
 * @author: chen
 * @desc: xml对应的用户模型
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    @XmlAttribute(name = "name")
    private String name="陈本布衣";
    @XmlAttribute(name = "age")
    private int age =30;
    @XmlAttribute(name = "address")
    private String address="四川*简阳";
    @XmlAttribute(name = "role")
    private Role role = Role.ADMIN;
    @XmlAttribute(name = "lock")
    private boolean isLock = false;
    @XmlAttribute(name = "createTime")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private LocalDateTime createTime ;

    private List<Menu> perms;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", perms=" + perms +
                ", isLock=" + isLock +
                ", createTime=" + createTime +
                '}';
    }
}

enum Role {
    ADMIN("管理员"),
    OPER("操作员");

    Role(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}

