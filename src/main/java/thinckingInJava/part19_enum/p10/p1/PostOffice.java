package thinckingInJava.part19_enum.p10.p1;


import thinckingInJava.part19_enum.p7.Enums;

import java.util.Iterator;

/**
 * @author chen
 * @date 2020/9/18 23:44
 * @Description 通过枚举实现责任链模式
 */
public class PostOffice {
    enum MailHandler {
        GENERAL_DELIVER {
            boolean handle(Mail m) {
                switch (m.generalDelivery) {
                    case YES:
                        System.out.println("投递："+m);
                        return true;
                    default:
                        return false;
                }
            }
        },
        MACHINE_SCAN {
            boolean handle(Mail m) {
                switch (m.scannability) {
                    case UNSCANNABLE:
                        return false;
                    default:
                        switch (m.address) {
                            case INCORRECT:
                                return false;
                            default:
                                System.out.println("自动投递：" + m);
                                return true;
                        }
                }
            }
        },
        VISUAL_INSPECTION {
            boolean handle(Mail m) {
                switch (m.readability) {
                    case ILLEGIBLE:
                        return false;
                    default:
                        switch (m.address) {
                            case INCORRECT:
                                return false;
                            default:
                                System.out.println("自动投递：" + m);
                                return true;
                        }
                }
            }
        },
        RETUNT_TO_SENDER {
            boolean handle(Mail m) {
                switch (m.returnAddress) {
                    case MISSING:
                        return false;
                    default:
                        switch (m.address) {
                            case INCORRECT:
                                return false;
                            default:
                                System.out.println("返回到原始投递处：" + m);
                                return true;
                        }
                }
            }
        };
        abstract boolean handle(Mail mail);
    }
    static void handle(Mail mail){
        for (MailHandler mailHandler : MailHandler.values()) {
            if(mailHandler.handle(mail)){
                return;
            }
        }
        System.out.println(mail+" 成为了死信");
    }

    public static void main(String[] args) {
        for (Mail mail : Mail.generator(10)) {
            System.out.println(mail.details());
            handle(mail);
            System.out.println("**************");
        }
    }
}

class Mail {
    // 普通投递
    enum GerneralDelivery {
        YES, NO1, NO2, NO3, NO4, NO5
    }

    // 可扫描的
    enum Scannability {
        UNSCANNABLE, YES1, YES2, YES3, YES4
    }

    // 可读的  ILLEGIBLE 难以辨认的
    enum Readability {
        ILLEGIBLE, YES1, YES2, YES3, YES4
    }

    enum Address {
        INCORRECT, OK1, OK2, OK3, OK4, OK5, OK6
    }

    enum ReturnAddress {
        MISSING, OK1, OK2, OK3, OK4, OK5
    }

    GerneralDelivery generalDelivery;
    Scannability scannability;
    Readability readability;
    Address address;
    ReturnAddress returnAddress;
    static long counter = 0;
    long id = counter++;

    @Override
    public String toString() {
        return "邮件ID:" + id;
    }

    public String details() {
        return toString() +
                ",Gerneral Delivery:" + generalDelivery +
                ",Address Scannability:" + scannability +
                ",Address Readability:" + readability +
                ",Address Address:" + address +
                ",Return Address:" + returnAddress;
    }

    public static Mail randomMail() {
        Mail mail = new Mail();
        mail.generalDelivery = Enums.random(GerneralDelivery.class);
        mail.scannability = Enums.random(Scannability.class);
        mail.readability = Enums.random(Readability.class);
        mail.address = Enums.random(Address.class);
        mail.returnAddress = Enums.random(ReturnAddress.class);
        return mail;
    }

    // 返回 Iterable便于进行foreach遍历 这里使用了两层嵌套的内部对象
    public static Iterable<Mail> generator(final int count) {
        return new Iterable<Mail>() {
            int n = count; // 变量转换 引入入参是final,不能直接进行增减操作
            @Override
            public Iterator<Mail> iterator() {
                return new Iterator<Mail>() {
                    @Override
                    public boolean hasNext() {
                        return n-- > 0;
                    }

                    @Override
                    public Mail next() {
                        return randomMail();
                    }
                };
            }
        };
    }
}
