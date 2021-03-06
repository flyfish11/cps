package com.cloud.common.utils;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

     private MapUtil() {throw new IllegalStateException("Utility class");}

   /***
    * @author: yuansq
    * @date: 2018-08-09 21:31
    * @param: [args]
    * @desc: 构造HashMap，格式为key,value,key,value...
    * @return: java.util.Map
    */
    public static Map createMap(final Object... args) {
        Map m = new HashMap();
        for (int i = 1; i < args.length; i += 2) {
            m.put(args[i - 1], args[i]);
        }
        return m;
    }

    public static Object getObject(final Map map, final Object key) {
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    public static Timestamp getTimestamp(Map map, Object key) {
        if (map != null) {
            return (Timestamp) map.get(key);
        }
        return null;
    }

    public static String getString(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }
        return null;
    }

    public static Boolean getBoolean(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Boolean) {
                    return (Boolean) answer;

                } else if (answer instanceof String) {
                    return Boolean.parseBoolean((String) answer);

                } else if (answer instanceof Number) {
                    Number n = (Number) answer;
                    return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
                }
            }
        }
        return Boolean.FALSE;
    }

    public static Number getNumber(final Map map, final Object key) throws ParseException {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Number) {
                    return (Number) answer;

                } else if (answer instanceof String) {

                        String text = (String) answer;
                        return NumberFormat.getInstance().parse(text);

                }
            }
        }
        return null;
    }

    public static Byte getByte(final Map map, final Object key) throws ParseException {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Byte) {
            return (Byte) answer;
        }
        return answer.byteValue();
    }

    public static Integer getInteger(final Map map, final Object key) throws ParseException {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Integer) {
            return (Integer) answer;
        }
        return answer.intValue();
    }

    public static Long getLong(final Map map, final Object key) throws ParseException {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Long) {
            return (Long) answer;
        }
        return answer.longValue();
    }

    public static Float getFloat(final Map map, final Object key) throws ParseException {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Float) {
            return (Float) answer;
        }
        return answer.floatValue();
    }

    public static Double getDouble(final Map map, final Object key) throws ParseException {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Double) {
            return (Double) answer;
        }
        return answer.doubleValue();
    }

    public static Object getObject( Object defaultValue) {
        return defaultValue;
    }

    public static String getString(Map map, Object key, String defaultValue) {
        String answer = getString(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static Boolean getBoolean(Map map, Object key, Boolean defaultValue) {
        Boolean answer = getBoolean(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static Number getNumber(Map map, Object key, Number defaultValue) throws ParseException {
        Number answer = getNumber(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static Byte getByte(Map map, Object key, Byte defaultValue) throws ParseException {
        Byte answer = getByte(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static Integer getInteger(Map map, Object key, Integer defaultValue) throws ParseException {
        Integer answer = getInteger(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static Long getLong(Map map, Object key, Long defaultValue) throws ParseException {
        Long answer = getLong(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static Float getFloat(Map map, Object key, Float defaultValue) throws ParseException {
        Float answer = getFloat(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static Double getDouble(Map map, Object key, Double defaultValue) throws ParseException {
        Double answer = getDouble(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static boolean isNotNull(Map map, Object key) {
        if (map.containsKey(key)) {
            Object answer = getObject(map, key);
            if (answer == null) {
                return false;
            }
            return true;
        } else {
            return true;
        }
    }
}