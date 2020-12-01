package thinckingInJava.part17_collection.p10_test;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author chen
 * @date 2020/12/1 23:24
 * @Description
 */
@Slf4j
public class Tester<C> {
    public static int fieldWildth = 0;
    public static TestParam[] defaultParams = TestParam.array(10, 200, 4300, 4349, 98, 666, 2, 32388, 77);
    protected C container;

    protected C initialize(int size) {
        return container;
    }

    private String headline = "";
    private List<Test<C>> tests;

    private static String stringField() {
        return "%" + fieldWildth + "s";
    }

    private static String numberField() {
        return "%" + fieldWildth + "d";
    }

    private static int sizeWidth = 5;
    private static String sizeField = "%" + sizeWidth + "s";
    private TestParam[] paramList = defaultParams;

    public Tester(C container, List<Test<C>> tests) {
        this.container = container;
        this.tests = tests;
        if (container != null)
            headline = container.getClass().getSimpleName();
    }

    public Tester(C container, List<Test<C>> tests, TestParam[] paramList) {
        this(container, tests);
        this.paramList = paramList;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     *  没有返回值类型的静态泛型方法，其方法体虽然没有返回值，但方法上也需要尖括号标注泛型类型
     *
     */
    public static <C> void run(C c, List<Test<C>> tests) {
        new Tester<>(c, tests).timedTest();
    }

    public static <C> void run(C c, List<Test<C>> tests, TestParam[] paramList) {
        new Tester<>(c, tests, paramList).timedTest();
    }

    private void displayHeader() {
        int width = fieldWildth * tests.size() + sizeWidth;
        int dashLength = width - headline.length() - 1;
        StringBuilder head = new StringBuilder(width);
        for (int i = 0; i < dashLength / 2; i++) {
            head.append("-");
        }
        /**
         *  StringBuilder 的append 方法，是可以追加基本数据类型的
         */
        head.append(' ').append(headline).append(' ');
        for (int i = 0; i < dashLength / 2; i++) {
            head.append("-");
        }
        log.info("{}", head);
        System.out.format(sizeField, "size");
        for (Test<C> test : tests) {
            System.out.format(stringField(), test.name);
        }
        System.out.println();
    }

    public void timedTest() {
        displayHeader();
        for (TestParam testParam : paramList) {
            System.out.format(sizeField, testParam.size);
            for (Test<C> test : tests) {
                C kontainer = initialize(testParam.size);
                long start = System.nanoTime();
                int reps = test.test(kontainer, testParam);
                long duration = System.nanoTime() - start;
                long timePerRep = duration / reps;
                System.out.format(numberField(), timePerRep);
            }
            System.out.println();
        }

    }


}

