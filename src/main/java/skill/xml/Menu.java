package skill.xml;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @date: 2021/6/22 16:10
 * @author: chen
 * @desc: xml对应的菜单模型
 */

@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class Menu {

    @XmlAttribute(name = "id")
    private Long menuId;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "url")
    private String url;
    @XmlAttribute(name = "icon")
    private String icon;
    @XmlElement(name = "menu")
    private List<Menu> children;

    @XmlTransient
    private String desc = "Java对象映射XML时，忽略此属性";

    @Override
    public String toString() {
        return "Menu{" +
                "menuId=" + menuId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", desc='" + desc + '\'' +
                ", children=" + children +
                '}';
    }
}
