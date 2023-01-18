package effective_java.chapter2.create_and_destroy_objects.builder;

/**
 * @author chen
 * @date 2023/1/18 11:03
 * @Description
 */
public class Calzone extends Pizza {

    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Calzone.Builder> {

        private boolean sauceInside = false; // 默认值

        public Builder sauceInside() {
            sauceInside = true;
            return this;
        }

        /**
         * build 方法都返回正确的子类
         * 子类方法声明返回超类中申明的返回类型的子类，被称为协变返回类型。如此，客户端无须进行类型转换
         */
        @Override
        public Calzone build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public Calzone(Calzone.Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }
}
