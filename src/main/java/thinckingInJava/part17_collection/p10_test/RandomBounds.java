package thinckingInJava.part17_collection.p10_test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @date 2020/12/8 23:36
 * @Description  微基准测试的危险性
 *  示例中 Math.random 实际上的取值范围是 [0,1）
 *      但是在该测试中，程序似乎永远都产生不了 0.0 。因为是概率性问题，测试的某段时间内，你真的可能永远都获取不到 0.0
 */

@Slf4j
public class RandomBounds {
    static void usage(){
        log.info("{}","随便打的");
        log.info("{}","\t 随便打的 lower");
        log.info("{}","\t 随便打的 upper");
        // 按照惯例，非零状态表示异常终止
        System.exit(1);
    }

    /**
     *  Math.random() 取值 [0,1)
     */
    public static void main(String[] args) {
        if(args.length!=1)
            usage();
        if(args[0].equals("lower")){
            double d ;
            while ((d = Math.random())!=0.0){
                log.info("产生的随机值：{}",d);
            }
            log.info("产生随机值 0.0");
        }else if(args[0].equals("upper")){
            while (Math.random()!=1.0){}
            log.info("产生随机值 1.0");
        }else
            usage();
    }
}
