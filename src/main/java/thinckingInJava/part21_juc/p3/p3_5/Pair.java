package thinckingInJava.part21_juc.p3.p3_5;

/**
 * @author chen
 * @date 2020/5/17 15:50
 * @Description 非线程安全类
 */
public class Pair {
    private int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pair() {
        this(0, 0);
    }

    public void incrementX() {
        x++;
    }


    public void incrementY() {
        y++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public class PariValNotEqualException extends RuntimeException{
        public PariValNotEqualException() {
            super("pair values not equal :"+Pair.this);
        }
    }

    public void checkState(){
        if(x!=y){
//            throw new PariValNotEqualException();

            System.out.println(this);
        }
    }
}
