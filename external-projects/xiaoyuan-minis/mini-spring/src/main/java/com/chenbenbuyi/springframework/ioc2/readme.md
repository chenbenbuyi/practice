### 扩展Bean：如何配置constructor、property和init-method
1.增加单例 Bean 的接口定义，然后把所有的 Bean 默认为单例模式。
2.预留事件监听的接口，方便后续进一步解耦代码逻辑。
3.添加实现属性注入