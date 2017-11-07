package com.abin.lee.redis.common.util;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

	private static final Object object = new Object();

	/**
	 * 获取SimpleDateFormat
	 * 
	 * @param pattern
	 *            日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException
	 *             异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormat(String pattern)
			throws RuntimeException {
		SimpleDateFormat dateFormat = threadLocal.get();
		if (dateFormat == null) {
			synchronized (object) {
				if (dateFormat == null) {
					dateFormat = new SimpleDateFormat(pattern);
					dateFormat.setLenient(false);
					threadLocal.set(dateFormat);
				}
			}
		}
		dateFormat.applyPattern(pattern);
		return dateFormat;
	}

	/**
	 * 获取日期中的某数值。如获取月份
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            日期格式
	 * @return 数值
	 */
	private static int getInteger(Date date, int dateType) {
		int num = 0;
		Calendar calendar = Calendar.getInstance();

		if (date != null) {
			calendar.setTime(date);
			num = calendar.get(dateType);
		}
		return num;
	}

	/**
	 * getYearMonth(获取年月)
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public static String getYearMonth(Date date) {
		SimpleDateFormat sdf = DateUtil.getDateFormat("yyyyMM");
		return sdf.format(date);
	}
	
	public static Integer getYearMonthInt(Date date) {
		SimpleDateFormat sdf = DateUtil.getDateFormat("yyyyMM");
		return Integer.valueOf(sdf.format(date));
	}

    /**
     * 获取日期时间字符串
     * @param date date
     * @return string like 'yyyy-MM-dd HH:mm:ss'
     */
    private static SimpleDateFormat sdfYMDHMS = DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String getDateString(Date date) {
        return sdfYMDHMS.format(date);
    }
    
    private static SimpleDateFormat sdfYMDHMSSimple = new SimpleDateFormat("yyyyMMddHHmmss");
    public static String getYMDHMS(Date date) {
    	return sdfYMDHMSSimple.format(date);
    }
    
    private static SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyyMMdd");
    public static String getYMDByYMDHMS(String yyyyMMddHHmmss){
    	try {
			return sdfYMD.format(sdfYMDHMSSimple.parse(yyyyMMddHHmmss));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    }
    public static String getYMDByDate(Date date){
    	return sdfYMD.format(date);
    }
	/**
	 * getMonth(获得当前月)
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public static int getMonth() {
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.MONTH)+1;
	}

	/**
	 * getDay(获得当前日)
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public static String getDay() {
		DateTime dt = new DateTime();
		return dt.dayOfMonth().getAsText();
	}

	/**
	 * 得到某个日期上月的月份
	 * 
	 * @param date
	 *            格式yyyyMM
	 * @return
	 */
	public static String getLastMonth() {
		DateTime dt = new DateTime();
		return dt.minusMonths(1).toString("yyyyMM");
	}

	/**
	 * yearMonth2DateTime(根据年月获取时间)
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public static long yearMonth2DateTime(int yearMonth) {
		return yearMonth;
	}

	/**
	 * 根据年月获取日期
	 * 
	 * @param date
	 *            格式yyyyMMdd
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Date getDate(String yearMonth){
		if(StringUtils.isNotEmpty(yearMonth)){
		 SimpleDateFormat sdf = DateUtil.getDateFormat("yyyyMM");
		 	try {
				return sdf.parse(yearMonth);
			} catch (ParseException e) {
				logger.warn("解析日期格式失败，日期数据内容为:"+yearMonth);
				return new Date();
			}
		}else{
		    return new Date();
		}
	}

	public static Date getYestoday(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}

    public static Date transferStringToDate(String str){
        SimpleDateFormat sdf = DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            logger.warn("解析日期格式失败，字符串数据内容为:"+str);
            return new Date();
        }
    }

    public static String getDateStrByCustomization(String pattern, Date date){
    	return getDateFormat(pattern).format(date);
    }

    public static Date monthBeforeOrAfter(int month, Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH, month);
    	return calendar.getTime();
    }
    public static Date dayBeforeOrAfter(int day, Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_YEAR, day);
    	return calendar.getTime();
    }

    private static SimpleDateFormat sdfYMD_ = new SimpleDateFormat("yyyy-MM-dd");
    public static Date getDateByYMD(String ymd){
    	try {
			return sdfYMD_.parse(ymd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
    public static String getSimpleYMDByDate(Date date){
    	return sdfYMD_.format(date);
    }
    /**
	 * 返回两个日期相差的天数
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws java.text.ParseException
	 */
	public static int getDistDates(Date startDate, Date endDate) {
		long totalDate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();
		totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
		return (int) totalDate;
	}
	
	/**
	 * 对当前的时间增加x秒
	  * @Title: addSecondForNowDate
	  * @param second
	  * @return
	  * @return Date
	 */
	public static Date addSecondForNowDate(Long second){
		long currentTime = System.currentTimeMillis() + second * 1000;
	    Date date = new Date(currentTime);
	    return date;
	}
	
	/**
	 * 获取后几天的日期 拼接上 00:00:00
	 * 
	 * @param num
	 * @return
	 */
	public static Date getNextDayBegin(int num) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, date.get(Calendar.DATE) + num);
		String nextDate = dft.format(date.getTime()) + " 00:00:00";
		Date ndate = null;
		try {
			ndate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(nextDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ndate;
	}
	
	/**
	 * 获取后几天的日期 拼接上 23:59:59
	  * @Title: getNextDayLastTime
	  * @param num
	  * @return
	  * @return Date
	 */
	public static Date getNextDayEnd(int num) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, date.get(Calendar.DATE) + num);
		String beforeDate = dft.format(date.getTime()) + " 23:59:59";
		Date fdate = null;
		try {
			fdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beforeDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fdate;
	}
	
	/**
	 * 获取前几天的日期 拼接上  00:00:00
	 * 
	 * @param num
	 * @return
	 */
	public static Date getPriorDayBegin(int num) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, date.get(Calendar.DATE) - num);
		String beforeDate = dft.format(date.getTime()) + " 00:00:00";
		Date fdate = null;
		try {
			fdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beforeDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fdate;
	}
	
	/**
	 *  获取前几天的日期 拼接上 23:59:59
	  * @Title: getPriorDayLastTime
	  * @param num
	  * @return
	  * @return Date
	 */
	public static Date getPriorDayEnd(int num) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, date.get(Calendar.DATE) - num);
		String beforeDate = dft.format(date.getTime()) + " 23:59:59";
		Date fdate = null;
		try {
			fdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beforeDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fdate;
	}
	
	/**
	 * 当前年月日拼接上 23:59:59
	  * @Title: getPriorDayEndTime
	  * @param num
	  * @param startTime
	  * @return
	  * @return Date
	 */
	public static Date getPriorDayEndTime(Date startTime,int num) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		if (startTime != null) {
			date.setTime(startTime);
		}
		date.add(Calendar.DATE, -num);
		
		String beforeDate = dft.format(date.getTime()) + " 23:59:59";
		Date fdate = null;
		try {
			fdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beforeDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fdate;
	}
	
	/**
	 * 当前年月日拼接上 00:00:00
	 * 
	 * @param num
	 * @return
	 */
	public static Date getNextDayBeginTime(Date startTime, int num) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		if (startTime != null) {
			date.setTime(startTime);
		}
		date.set(Calendar.DATE, date.get(Calendar.DATE) + num);
		String nextDate = dft.format(date.getTime()) + " 00:00:00";
		Date ndate = null;
		try {
			ndate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(nextDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ndate;
	}
	
	
	/**
	 * 返回两个日期相差的秒数
	  * @Title: getDistSecond
	  * @param startTime
	  * @param endTime
	  * @return
	  * @return int
	 */
	public static int getDistSecond(Date startTime, Date endTime) {
		long num = (endTime.getTime() - startTime.getTime()) / (1000);
		return (int) num;
	}
	
	/**
	 * 返回上个月的第一天+00:00:00
	  * @Title: getLastMonthFirstDay
	  * @return
	  * @return Date
	 */
	public static Date getLastMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String beforeDate = dft.format(calendar.getTime()) + " 23:59:59";
		Date fdate = null;
		try {
			fdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beforeDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fdate;
	}
	
	/**
	 * 返回上个月的第一天或者最后一天，加上时分秒
	  * @Title: getLastMonthDay
	  * @param isFirstDay true代表第一天 false代表最后一天
	  * @return
	  * @return Date
	 */
	public static Date getLastMonthDay(Boolean isFirstDay) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		if(isFirstDay){
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			date = dft.format(calendar.getTime()) + " 00:00:00"; 
		}else{
			calendar.set(Calendar.DAY_OF_MONTH, 1); 
			calendar.add(Calendar.DATE, -1);
			date = dft.format(calendar.getTime()) + " 23:59:59"; 
		}
		Date fdate = null;
		try {
			fdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fdate;
	}
	
	/**
	 * 获取后几天的日期
	 * 
	 * @param num
	 * @return
	 */
	public static Date getNextDayDate(Date cDate, int num) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar date = Calendar.getInstance();
		try {
			date.setTime(cDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		date.set(Calendar.DATE, date.get(Calendar.DATE) + num);
		Date ndate = null;
		try {
			ndate = date.getTime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ndate;
	}
	
	public static void main(String[] args) throws ParseException {
		getLastMonthDay(true);
		System.out.println(sdfYMDHMS.parse("2015-04-07 00:00:00"));
		System.out.println(sdfYMDHMSSimple.parse("20150407000000"));
		System.out.println(monthBeforeOrAfter(3,new Date()));
		Date sds = getYestoday();
		int a =1;
	}
}
