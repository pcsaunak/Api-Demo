import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Demo {


    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated


    public int countOfSaturdays(String d1, int diff) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date1 = dateFormat.parse(d1);

        calendar.setTime(date1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int noOfSaturdays = 0;
        for (int i = 0; i <= diff; i++) {
            calendar.set(year, month, day + i);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY) {
                noOfSaturdays++;
            }
        }
        return noOfSaturdays;
    }

    public int countOfSundays(String d1, int diff) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date1 = dateFormat.parse(d1);

        System.out.println("Day is: "+simpleDateformat.format(date1));
        calendar.setTime(date1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int noOfSundays = 0;
        for (int i = 0; i <= diff; i++) {
            calendar.set(year, month, day+i);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek == Calendar.SUNDAY) {
                noOfSundays++;
                // Or do whatever you need to with the result.
            }
        }
        return noOfSundays;
    }


    public int countOfSundays_new(String d1, String d2) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date1 = dateFormat.parse(d1);
        Date date2 = dateFormat.parse(d2);
        int sundayCount = 0;
        while (!date1.after(date2)){
            int i = 0;
            calendar.setTime(date1);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if(dayOfWeek == Calendar.SUNDAY){
                sundayCount++;
            }
            calendar.add(Calendar.DATE,1);
            date1 = calendar.getTime();
        }
        return sundayCount;
    }
}
