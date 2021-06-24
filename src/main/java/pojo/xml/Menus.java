package pojo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2021/6/22 16:10
 * @author: chen
 * @desc: xml对应的菜单模型
 */

@XmlRootElement(name = "menus")
@XmlAccessorType(XmlAccessType.FIELD)
public class Menus extends ArrayList<Menu> {

    @XmlElement(name = "menu")
    public List<Menu> menus = this;

}
