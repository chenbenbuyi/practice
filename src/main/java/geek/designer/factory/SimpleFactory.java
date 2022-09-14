package geek.designer.factory;

import geek.designer.factory.inter.Phone;
import geek.designer.factory.model.AiPhone;
import geek.designer.factory.model.HuaWeiPhone;
import geek.designer.factory.model.XiaoMiPhone;

/**
 * @date: 2020/11/3 11:45
 * @author: chen
 * @desc: 工厂类角色
 */
public class SimpleFactory {

    public Phone produce(Integer type) {
        switch (type) {
            case 0:
                return new HuaWeiPhone();
            case 1:
                return new XiaoMiPhone();
            case 2:
                return new AiPhone();
            default:
                return null;
        }
    }
}
