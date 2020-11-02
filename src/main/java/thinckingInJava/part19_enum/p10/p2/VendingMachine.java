package thinckingInJava.part19_enum.p10.p2;


import lombok.extern.slf4j.Slf4j;
import thinckingInJava.part15_genericity.p3.Generator;

import java.util.EnumMap;

import static thinckingInJava.part19_enum.p10.p2.Input.*;

/**
 * @author chen
 * @date 2020/9/20 23:01
 * @Description 该示例描述枚举用来表示单个的和分组的状态之间的组合使用——
 *  每个基本操作分类的State枚举中，又分组了不同的情况：不同面值钞票、不同种类货物等，而这些分类，又都取之于一个定义好所有类型的输入枚举
 */
enum Category {
    // 一个枚举的元素也可以作为另一个枚举中的描述信息存在
    MONEY(NICKEL, DIME, QUARTER, DOLLAR),  // 定义不同的输入货币
    ITEM_SELECTION(TOOTHPASTE, CHIPS, SODA, SOAP), // 商品选择
    QUIT_TRANSACTIOIN(ABORT_TRANSACTION), // 放弃交易
    SHUT_DOWN(STOP);  // 取消
    private Input[] values;

    private static EnumMap<Input, Category> categories = new EnumMap<>(Input.class);

    static {
        for (Category category : Category.class.getEnumConstants()) {
            for (Input value : category.values) {
                categories.put(value, category);
            }
        }
    }

    public static Category categories(Input input) {
        return categories.get(input);
    }

    Category(Input... values) {
        this.values = values;
    }
}

@Slf4j
public class VendingMachine { // 自动售货机
    private static State state = State.RESTING;
    private static int amount = 0;
    private static Input selection = null;

    enum StateDuration {TRANSIENT}

    enum State {
        RESTING {
            @Override
            void next(Input input) {
                switch (Category.categories(input)) {
                    case MONEY:
                        amount += input.amount();
                        state = ADDING_MONEY;
                        break;
                    case SHUT_DOWN:
                        state = TERMINAL;
                    default:
                }
            }
        },
        ADDING_MONEY {
            void next(Input input) {
                switch (Category.categories(input)) {
                    case MONEY:
                        amount += input.amount();
                        break;
                    case ITEM_SELECTION:
                        selection = input;
                        if (amount < selection.amount()) {
                            log.debug("余额不足：" + selection);
                        } else
                            state = DISPENSING;
                        break;
                    case QUIT_TRANSACTIOIN:
                        state = GIVING_CHANGE;
                        break;
                    case SHUT_DOWN:
                        state = TERMINAL;
                    default:
                }
            }
        },
        DISPENSING(StateDuration.TRANSIENT) {
            @Override
            void next() {
                log.info("你最正确的选择：" + selection);
                amount -= selection.amount();
                state = GIVING_CHANGE;
            }
        },
        GIVING_CHANGE(StateDuration.TRANSIENT) {
            @Override
            void next() {
                if (amount > 0) {
                    log.debug("修改金额：" + amount);
                    amount = 0;
                }
                state = RESTING;
            }
        },
        TERMINAL {
            @Override
            void output() {
                log.error("停止");
            }
        };

        private boolean isTransent = false;

        State() {
        }

        State(StateDuration duration) {
            isTransent = true;
        }


        void next(Input input) {
            throw new RuntimeException("调用next(Input input) 异常抛出");
        }

        void next() {
            throw new RuntimeException("调用StateDuration 异常抛出");
        }

        void output() {
            log.debug(amount + "");
        }
    }

    static void run(Generator<Input> gen) {
        while (state != State.TERMINAL) {
            state.next(gen.next());
            while (state.isTransent) {
                state.next();
            }
            state.output();
        }
    }

    public static void main(String[] args) {
        Generator<Input> gen = new RandomInputGenerator();
        run(gen);
    }
}

class RandomInputGenerator implements Generator<Input> {

    @Override
    public Input next() {
        return Input.randomSelection();
    }
}
