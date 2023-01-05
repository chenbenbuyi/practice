package wonderful;

import lombok.Data;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author chen
 * @date 2023/1/2 16:49
 * @Description 源自B 站视频
 */

@Data
public class Either<L, R> {

    /**
     * 代表异常情况
     */
    private L left;

    /**
     * 代码正常情况
     */
    private R right;

    public boolean isLeft() {
        return Objects.nonNull(left);
    }

    public boolean isRight() {
        return Objects.nonNull(left);
    }


    public static <L, R> Either<L, R> left(L value) {
        Either<L, R> either = new Either<>();
        either.left = value;
        return either;
    }


    public static <L, R> Either<L, R> right(R value) {
        Either<L, R> either = new Either<>();
        either.right = value;
        return either;
    }


    public <T> Either<L, T> map(Function<R, T> function) {
        if (isLeft()) {
            return left(left);
        }
        return right(function.apply(right));
    }

//    public static <L, R> Either<L, List<R>> sequence(List<Either<L, R>> eitherList, BinaryOperator<L> accumulator) {
//        if (eitherList.stream().allMatch(Either::isRight)) {
//            return right(eitherList.stream().map(Either::getRight).collect(Collectors.toList()));
//        }
//        return left(eitherList.stream().filter(Either::isLeft).map(Either::getLeft).reduce(accumulator).orElseThrow();
//    }

}
