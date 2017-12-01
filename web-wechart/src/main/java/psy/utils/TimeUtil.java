package psy.utils;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期格式处理
 * @author yuegu
 *
 */
public class TimeUtil {
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public String formatYYYYMMDDHHMMSS(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	
}
