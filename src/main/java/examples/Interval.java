package examples;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Interval {
	public static long getInterval(String dateTime1,String dateTime2) { //将Zonedatetime之差表示成秒数
		
		long interval=0;
		
		String time1=TransDate.transDate(dateTime1);
		String time2=TransDate.transDate(dateTime2);
		
		Date date1 = new Date();
		Date date2 = new Date();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setLenient(false);
		
		try {
			date1= format.parse(time1);
			date2= format.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		interval=(date1.getTime() - date2.getTime())/1000;
		
		return interval;
	}
	
	public static String prove(String origin) {
		return TransDate.transDate(origin);
	}
}

class TransDate{
	public static String transDate(String origin) {
		String result=new String();
		
		String[] temp1=origin.split("T");
		String dateTime=temp1[0];
		
		String[] temp2=temp1[1].split("\\.");
		String dayTime=temp2[0];
		
		result=dateTime+" "+dayTime;
		
		return result;
	}
}
