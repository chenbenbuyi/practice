package effective_java.chapter2.create_and_destroy_objects.builder;

import java.util.Objects;

/**
 * @author chen
 * @date 2023/1/18 10:58
 * @Description
 */
public class NyPizza extends Pizza {

    public enum Size {
        SMALL, MEDIUM, LARGE
    }

    private final Size size;


    public static class Builder extends Pizza.Builder<Builder> {

        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public NyPizza(Builder builder) {
        super(builder);
        this.size = builder.size;
    }
}
