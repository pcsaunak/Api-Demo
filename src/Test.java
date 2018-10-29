import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String args[]) throws ParseException {
        Demo myDemo = new Demo();

//        System.out.println("Saturday Count: "+myDemo.countOfSaturdays("2018/10/01",13));
        System.out.println("Sunday Count: "+myDemo.countOfSundays("2018/09/29",13));

        System.out.println("New Sunday count: "+myDemo.countOfSundays_new("2018/10/01","2018/10/14"));
    }
}
