package com.ebay.app.raptor.dashboard.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    
    private static final String DOT_DATE_FORMAT = "yyyy.MM.dd";
    private static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
    private static final String SLASH_DATE_FORMAT = "yyyy/MM/dd";
    private static final String AMERICAN_DATE_FORMAT = "mm/dd/yyyy";
    private static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String GMT_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String SHOT_DATE_FORMAT="ddMMMyyyy";
    
    // Database time format
 	private static final String TIME_FORMAT = "HH:mm:ss";
    
    public final static TimeZone GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
    public final static TimeZone BEIJING_TIMEZONE = TimeZone.getTimeZone("GMT");
    
    private static ThreadLocal<DateFormat> dotDateFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(DOT_DATE_FORMAT);
		}
    };
    private static ThreadLocal<DateFormat> slashDateFormat = new ThreadLocal<DateFormat>(){
    	@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(SLASH_DATE_FORMAT);
		}
    };
    private static ThreadLocal<DateFormat> isoDateFormat = new ThreadLocal<DateFormat>() {
    	@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(ISO_DATE_FORMAT);
		}
    };
    private static ThreadLocal<DateFormat> americanDateFormat = new ThreadLocal<DateFormat>(){
    	@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(AMERICAN_DATE_FORMAT);
		}
    };
    private static ThreadLocal<DateFormat> isoDateTimeFormat = new ThreadLocal<DateFormat>() {
    	@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(ISO_DATETIME_FORMAT);
		}
    };
    private static ThreadLocal<DateFormat> gmtDateTimeFormat = new ThreadLocal<DateFormat>() {
    	@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(GMT_DATETIME_FORMAT);
		}
    };
    public static ThreadLocal<DateFormat> shortDateFormat = new ThreadLocal<DateFormat>() {
    	@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(SHOT_DATE_FORMAT);
		}
    };
    public static ThreadLocal<DateFormat> timeFormat = new ThreadLocal<DateFormat>() {
    	@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(TIME_FORMAT);
		}
    };
    
    
    /***************************************** american date format: mm/dd/yyyy *********************************************************/
    public static Date parseAmericanDate (String dateStr) throws ParseException {
        return americanDateFormat.get().parse(dateStr);
    }
    
    public static String formatAmericanDate (Date date) {
        return americanDateFormat.get().format(date);
    }
    
    /***************************************** date format: yyyy.MM.dd *********************************************************/
    public static Date parseDotDate (String dateStr) throws ParseException {
        return dotDateFormat.get().parse(dateStr);
    }
    
    public static String formatDotDate (Date date) {
        return dotDateFormat.get().format(date);
    }
    
    /***************************************** date format: yyyy/MM/dd *********************************************************/
    public static Date parseSlashDate (String dateStr) throws ParseException {
        return slashDateFormat.get().parse(dateStr);
    }
    
    public static String formatSlashDate (Date date) {
        return slashDateFormat.get().format(date);
    }
    
    /***************************************** date format: yyyy-MM-dd *********************************************************/
    public static Date parseIsoDate (String dateStr) throws ParseException {
        return isoDateFormat.get().parse(dateStr);
    }
    
    public static String parseDotDateToDash (String dateStr) throws ParseException {
    	Date dotDate = parseDotDate(dateStr);
    	return formatIsoDate(dotDate);
    }
    
    public static String formatIsoDate (Date date) {
        return isoDateFormat.get().format(date);
    }
    
    /**************************************************************************************************/
    public static Date parseDate (String dateStr) throws ParseException {
    	if (dateStr == null || dateStr.isEmpty()) return null;
    	
    	try {
    		return parseIsoDate(dateStr);
    	} catch (Exception e) {
    		try {
    			return parseSlashDate(dateStr);
    		} catch (Exception e2) {
    			return parseDotDate(dateStr);
    		}
    	}
    }
    
    public static Date parseDateInExcel (String dateStr) throws ParseException {
    	if (dateStr == null || dateStr.isEmpty()) return null;
    	
    	Date date = null;
		try {
			date = isoDateTimeFormat.get().parse(dateStr);
		} catch (ParseException e) {
			try {
				date = isoDateFormat.get().parse(dateStr);
			} catch (ParseException e1) {
				try {
					date = slashDateFormat.get().parse(dateStr);
				} catch (ParseException e2) {
					return dotDateFormat.get().parse(dateStr);
				}
			}
		}

		return date;
    }
    /**************************************************************************************************/
    
    // date format: yyyy-MM-dd HH:mm:ss
    public static Date parseIsoDateTime (String dateStr) throws ParseException {
        return isoDateTimeFormat.get().parse(dateStr);
    }

    public static String formatIsoDateTime (Date date) {
        return isoDateTimeFormat.get().format(date);
    }
    
    // date format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
    public static Date parseGMTDateTime (String dateStr) throws ParseException {
        return parsetGMTToOtherTimezone(dateStr, GMT_TIMEZONE);
    }
    
    public static String formatGMTDateTime (Date date) {
        DateFormat format = gmtDateTimeFormat.get();
        return format.format(date);
    }
    
    public static Date parsetGMTToOtherTimezone (String dateStr, TimeZone zone) throws ParseException {
        DateFormat format = gmtDateTimeFormat.get();
        format.setTimeZone(zone);
        return format.parse(dateStr);
    }
    
    public static String formatTime (Date date) {
        DateFormat format = timeFormat.get();
        return format.format(date);
    }
    
    public static Time parseTime(String text) throws ParseException {
		Date date = timeFormat.get().parse(text);
		return new Time(date.getTime());
	}
    
    /**
     * Compare the day of two dates.
     *
     * @param current    the first date
     * @param another    the other date
     * @return
     */
    public static int compareDateDay (Date current, Date another) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(current);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(another);

        int currYear = calendar1.get(Calendar.YEAR);
        int anotherYear = calendar2.get(Calendar.YEAR);
        int currDay = calendar1.get(Calendar.DAY_OF_YEAR);
        int anotherDay = calendar2.get(Calendar.DAY_OF_YEAR);

        if (currYear > anotherYear) {
            return 1;
        } else if (currYear < anotherYear) {
            return -1;
        }

        if (currDay > anotherDay) {
            return 1;
        } else if  (currDay == anotherDay) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Get the beginning datetime of this week.
     *
     * @return
     */
    public static Date getStartDateTimeOfThisWeek (Date today) {
        Calendar cal = Calendar.getInstance();
        if (today != null) {
            cal.setTime(today);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Get the Thursday 10:00 AM time of this week.
     *
     * @return
     */
    public static long getThursdayTimeOfThisWeek () {
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		return calendar.getTimeInMillis();
    }
    
    /**
     *	validate now is appeal time 
     *
     *	start time[WED 12:00:00], end time[THU 10:00:00], TimeZone [GMT+8:00]
     * 
     * @return
     */
    public static boolean isAppealTime (){
		Calendar startcCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		startcCalendar.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
		startcCalendar.set(Calendar.HOUR_OF_DAY, 12);
		startcCalendar.set(Calendar.MINUTE, 0);
		startcCalendar.set(Calendar.SECOND, 0);
		
		Calendar endCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		endCalendar.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
		endCalendar.set(Calendar.HOUR_OF_DAY, 10);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		
		Date nowDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")).getTime();
		
		return (nowDate.before(endCalendar.getTime()) && nowDate.after(startcCalendar.getTime()));
	}
    
    /**
     * Get the end datetime of this week.
     *
     * @return
     */
    public static Date getEndDateTimeOfThisWeek (Date today) {
        Calendar cal = Calendar.getInstance();
        if (today != null) {
            cal.setTime(today);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
    
    /**
     * Get the date seven days ago.
     *
     * @return
     */
    public static Date getSevenDaysAgoDate (Date today) {
        Calendar cal = Calendar.getInstance();
        if (today != null) {
            cal.setTime(today);
        }
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 7);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public static Integer getMonthFromDate(Date date){
    	SimpleDateFormat sdf= new SimpleDateFormat("MM");
    	return Integer.valueOf(sdf.format(date));
    }
    
    /**
	 * Date don't have time zone information. So, it's always a local time. If
	 * you want to convert date from one time zone to another, you have to
	 * provide the source time zone and the destination time zone.
	 * 
	 * 
	 * @param date
	 *            Local time
	 * @param fromZone
	 *            , the original time zone of date
	 * @param toZone
	 *            the time zone will converted to.
	 * @return new date in toZone.
	 */
	public static Date convert(Date date, TimeZone fromZone, TimeZone toZone) {
		DateFormat format = isoDateTimeFormat.get();

		// get the time representation of destination time zone from local time.
		format.setTimeZone(toZone);
		String newDate = format.format(date);
		// convert destination time
		format.setTimeZone(fromZone);
		try {
			return format.parse(newDate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static boolean isCurrentWeekOfPstDate(Date createPst) {
		Date createDate= DateUtil.convert(createPst,TimeZone.getTimeZone("PST"), TimeZone.getDefault() );
		Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(createDate);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        
        return paramWeek == currentWeek;
    }
	
}
