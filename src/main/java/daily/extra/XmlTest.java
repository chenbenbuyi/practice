package daily.extra;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;
import pojo.xml.Menu;
import pojo.xml.Menus;
import util.XMLUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @date: 2021/6/22 16:19
 * @author: chen
 */
public class XmlTest {

    private static final String PATH = System.getProperty("user.dir") + File.separator + "extra" + File.separator + "xml" + File.separator;

    static Menu menu;
    static Menu menu2;
    static {
        menu = new Menu().setMenuId(1L).setIcon("图标").setName("系统管理").setUrl("/user/list");
        List<Menu> children = Arrays.asList(new Menu().setMenuId(11L).setIcon("图标").setName("参数配置").setUrl("/params/conf"), new Menu().setMenuId(12L).setIcon("图标").setName("时钟配置").setUrl("/params/timer"));
        menu.setChildren(children);

        menu2 = new Menu().setMenuId(2L).setIcon("图标").setName("日志管理").setUrl("/log/list");
        List<Menu> children2 = Arrays.asList(new Menu().setMenuId(21L).setIcon("图标2").setName("操作日志").setUrl("/log"), new Menu().setMenuId(22L).setIcon("图标2").setName("系统日志").setUrl("/syslog"));
        menu2.setChildren(children2);
    }


    /**
     * 踩坑指南：
     *  javax.xml.bind.DataBindingException: com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationsException: 5 counts of IllegalAnnotationExceptions类的两个属性具有相同名称 "children"
     * 原因：因为使用了 lombok 的 @Data 注解，导致序列化器序列化为xml文件时同时绑定了Java类中的属性和方法，通过 @XmlAccessorType(XmlAccessType.FIELD) 注解指定只访问字段即可
     *
     *  疑惑：对象属性上不添加@XmlAttribute注解，则字段会生成单个的xml节点，别人的明明也没有该注解标注啊何解？
     */
    @Test
    public void testCreate() throws JAXBException, IOException {
        Menus menus = new Menus();
        menus.add(menu);
        menus.add(menu2);
        String fileName = "menu.xml";
        XMLUtil.beanToXml(menus,Menus.class,FileUtil.newFile(PATH + fileName));
    }


    /**
     * 踩坑指南： StackOverflowError
     * lombok 默认的toString方法很容易导致递归调用
     */
    @Test
    public void testGet() throws IOException, JAXBException {
        String fileName = "menu.xml";
        Menus menus = XMLUtil.xmlToBean(Menus.class, new File(PATH + fileName));
        System.out.println(menus);
    }
}
