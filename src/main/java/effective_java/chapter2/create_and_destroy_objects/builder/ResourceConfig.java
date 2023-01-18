package effective_java.chapter2.create_and_destroy_objects.builder;

import cn.hutool.json.JSONObject;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chen
 * @date 2021/9/12 8:29
 * @Description 构造参数过多时，考虑使用构建器(constructor)
 * 多构造参数时，可以通过重叠构造器(就是根据字段个数和业务需求新建多个不同长度参数的构造器，示例如：{@link JSONObject#JSONObject(int, boolean, boolean)})或者JavaBeans模式(构造对象后通过setter方法设值)进行对象的构建；
 * 但是前者代码不易编写和阅读，后者在对象构造过程中会有线程安全问题，因为对象的状态在构建过程中处于不一致的状态，而且也无法将类做成不可变类
 * <p>
 * 此示例没有采用书本示例代码，而是采用极客时间中的一段示例代码，稍有改动
 */

// 此时就不需setter方法了
@Getter
public class ResourceConfig {

    private String name;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;

    private ResourceConfig(Builder builder) {
        this.name = builder.name;
        this.maxTotal = builder.maxTotal;
        this.maxIdle = builder.maxIdle;
        this.minIdle = builder.minIdle;
    }

    // 给定一个静态方法来获取内部的构建类实例
    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private static final int DEFAULT_MAX_TOTAL = 8;
        private static final int DEFAULT_MAX_IDLE = 8;
        private static final int DEFAULT_MIN_IDLE = 0;
        private String name;
        private int maxTotal = DEFAULT_MAX_TOTAL;
        private int maxIdle = DEFAULT_MAX_IDLE;
        private int minIdle = DEFAULT_MIN_IDLE;


        public ResourceConfig build() {
            // 校验逻辑放到这里来做，包括必填项校验、依赖关系校验、约束条件校验等
            if (StringUtils.isBlank(name)) {
                throw new IllegalArgumentException("...");
            }
            if (maxIdle > maxTotal) {
                throw new IllegalArgumentException("...");
            }
            if (minIdle > maxTotal || minIdle > maxIdle) {
                throw new IllegalArgumentException("...");
            }
            return new ResourceConfig(this);
        }


        public Builder setName(String name) {
            if (StringUtils.isBlank(name)) {
                throw new IllegalArgumentException("...");
            }
            this.name = name;
            // 构建器的设置返回构建器本身，便于把调用链接起来，形成流式的API
            return this;
        }

        public Builder setMaxTotal(int maxTotal) {
            if (maxTotal <= 0) {
                throw new IllegalArgumentException("...");
            }
            this.maxTotal = maxTotal;
            return this;
        }

        public Builder setMaxIdle(int maxIdle) {
            if (maxIdle < 0) {
                throw new IllegalArgumentException("...");
            }
            this.maxIdle = maxIdle;
            return this;
        }

        public Builder setMinIdle(int minIdle) {
            if (minIdle < 0) {
                throw new IllegalArgumentException("...");
            }
            this.minIdle = minIdle;
            return this;
        }
    }
}