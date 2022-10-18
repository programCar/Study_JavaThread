package GuardedSuspension_Study.DecouplingWait_Production;


import lombok.extern.slf4j.Slf4j;


/**
 * 此类用于测试此包下的所有类的联动
 */


@Slf4j
public class A_Housing_Estate {


    public static void main(String[] args) {


        log.debug("欢迎来到惊喜小区，惊喜小区，处处充满惊喜.........");


        ReceiveMail second_Dog = new ReceiveMail("二狗子",3000);


        ReceiveMail big_Cat = new ReceiveMail("大猫咪",2000);


        ReceiveMail old_Wang = new ReceiveMail("老王",5000);


        ReceiveMail old_six = new ReceiveMail("老六",1000);


        ReceiveMail chicken_Brother = new ReceiveMail("鸡哥",300);


        SendMail mouse_Mouse = new SendMail("鼠鼠",second_Dog.getMyId());


        SendMail big_Panda = new SendMail("你知道我这些年怎么过的吗??",big_Cat.getMyId());


        SendMail wei_Wei_Wei = new SendMail("喂喂喂",old_six.getMyId());


        SendMail small_Pig = new SendMail("小猪佩奇",old_Wang.getMyId());


        SendMail small_black = new SendMail("小黑子",chicken_Brother.getMyId());


        second_Dog.start();


        big_Cat.start();


        old_Wang.start();


        old_six.start();


        chicken_Brother.start();


        mouse_Mouse.start();


        big_Panda.start();


        wei_Wei_Wei.start();


        small_Pig.start();


        small_black.start();
    }
}
