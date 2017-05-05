package algroup.com.mx.homedelivery.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Util {
    private static final String CLASSNAME = Util.class.getSimpleName();

    public static int getIntValueOfMap(Map<String, String> dataUser,
            String nameValue) {
        String valueString = dataUser.get(nameValue);
        int intValue = 0;

        if (valueString != null && valueString.trim().length() > 0) {
            try {
                intValue = Integer.parseInt(valueString);
            } catch (Exception e) {
                Log.e("Util", "getIntValueOfMap() error = " + e.getMessage());
            }
        }

        return intValue;
    }

    public static String getDateWithFormat(int day, int month, int year) {
        String dayString = String.valueOf(day);
        String mothString = String.valueOf(month + 1);
        if (day < Const.POSICION_DIEZ) {
            dayString = "0" + day;
        }
        if ((month + 1) < Const.POSICION_DIEZ) {
            mothString = "0" + mothString;
        }
        return new StringBuilder().append(year).append("-")
                .append(mothString).append("-").append(dayString).toString();
    }


    public static String getTimeWithFormat(int hour, int min) {
        String hourString = String.valueOf(hour);
        String minString = String.valueOf(min);
        if (hour < Const.POSICION_DIEZ) {
            hourString = "0" + hour;
        }
        if (min < Const.POSICION_DIEZ) {
            minString = "0" + min;
        }
        return new StringBuilder().append(hourString).append(":")
                .append(minString).toString();
    }

    public static String getTimeWithFormat(int hour, int min , int sec) {
        String hourString = String.valueOf(hour);
        String minString = String.valueOf(min);
        String secString = String.valueOf(sec);
        if (hour < Const.POSICION_DIEZ) {
            hourString = "0" + hour;
        }
        if (min < Const.POSICION_DIEZ) {
            minString = "0" + min;
        }
        if (sec < Const.POSICION_DIEZ) {
            secString = "0" + sec;
        }
        return new StringBuilder().append(hourString).append(":")
                .append(minString).append(":").append(secString).toString();
    }


    public static String getDateFromMilis(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        return getDateWithFormat(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
    }

    public static String getDateTimeFromMilis(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        String dayFormat = getDateWithFormat(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        String timeFormat = getTimeWithFormat(calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
        return dayFormat + " " + timeFormat;
    }

    public static String getDateTimeFromMilis_hastaSegundos(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        String dayFormat = getDateWithFormat(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        String timeFormat = getTimeWithFormat(calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get( Calendar.SECOND));
        return dayFormat + " " + timeFormat;
    }

    public static Date getDateFromString(String dateString) {
        String[] dateArray = dateString.split("/");
        int day = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]) -1;
        int year = Integer.parseInt(dateArray[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public static boolean containsElements(List<?> list) {
        boolean result = false;
        if (list.size() > 0) {
            result = true;
        }
        return result;
    }
    
    public static boolean validateFormat(String stringToValid, String regExpression) {
        Pattern pattern = Pattern.compile(regExpression);
        Matcher matcher = pattern.matcher(stringToValid);
        return matcher.matches();
    }


    public static byte[] getArrayByteDeBitMap( Bitmap bitmap ){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }

    public static <U> String getStringJSON(U object) throws IOException {
        long tiempo = System.currentTimeMillis();
        ObjectMapper objectMapper = new ObjectMapper();
        Writer strWriter = new StringWriter();
        objectMapper.writeValue(strWriter, object);
        String json = strWriter.toString();
        if (Log.isLoggable(CLASSNAME, Log.DEBUG)) {
            tiempo = System.currentTimeMillis() - tiempo;
            LogUtil.printLog(CLASSNAME, "getStringJSON() t = " + tiempo + "object Class ="
                    + object.getClass().getName() + "object json = " + json);
        }
        return json;
    }

    public static <U> U parseJson(String jsonLoginRequest, Class<U> classType)
            throws IOException {
        LogUtil.printLog(CLASSNAME, "parseJson() jsonLoginRequest = " + jsonLoginRequest + "object Class ="
                + classType.getClass().getName() );
        U classResult = null;
        ObjectMapper mapper = new ObjectMapper();
        classResult = mapper.readValue(jsonLoginRequest, classType);
        return classResult;
    }


}
