package com.abin.lee.redis.common.util;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;

/**
 * 类说明: BigDecimal custom property editor<br>
 * 创建时间: 2008-2-26 下午03:15:03<br>
 *
 * @author Seraph<br>
 * @email: seraph@gmail.com<br>
 */
public class CustomBigDecimalEditor extends PropertyEditorSupport {

    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else {
            setValue(NumberUtils.getBigDecimal(text));
        }
    }

    /**
     * 类说明: Number handle utils<br>
     * 创建时间: 2007-10-4 下午05:08:48<br>
     *
     * @author Seraph<br>
     * @email: seraph@gmail.com<br>
     */
    public static class NumberUtils {

        public static int parseInt(long l){
            return BigDecimal.valueOf(l).intValue();
        }

        public static long parseLong(String s) {
            return Long.parseLong(s.trim());
        }

        public static double parseDouble(String s) {
            return Double.parseDouble(s.trim());
        }

        public static BigDecimal getBigDecimal(String s) {
            return BigDecimal.valueOf(Double.parseDouble(s.trim()));
        }

    }

    public static void main(String[] args) {
        BigDecimal result = NumberUtils.getBigDecimal("21");
        System.out.println("result="+result);
    }

}
