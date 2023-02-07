package effective.chapter2.create_and_destroy_objects.builder;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author chen
 * @date 2023/1/18 10:50
 * @Description
 */
public abstract class Pizza {

    public enum Topping {
        HAM, MUSHROOM, ONION, PEPPER, SAUSAGE
    }

    final Set<Topping> toppings;

    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T add(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build();

        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }
}
