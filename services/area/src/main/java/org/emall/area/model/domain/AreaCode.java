package org.emall.area.model.domain;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.diwayou.core.exception.CustomException;

import java.util.LinkedList;

/**
 * @author gaopeng 2021/2/23
 */
public class AreaCode {

    private static final int SIZE = 12;
    private static final int PROVINCE_START = 0;
    private static final int CITY_START = 2;
    private static final int COUNTY_START = 4;
    private static final int TOWN_START = 6;
    private static final int VILLAGE_START = 9;

    private static final char PAD_CHAR = '0';

    /**
     * 北京市, 天津市, 上海市, 重庆市
     */
    private static final String[] selfCityPrefix = new String[] {"11", "12", "31", "50"};

    public static boolean isValid(String code) {
        return StringUtils.length(code) == SIZE && StringUtils.isNumeric(code);
    }

    /**
     * 是否是直辖市
     */
    public static boolean isSelfCity(String code) {
        for (String prefix : selfCityPrefix) {
            if (code.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }

    public static LinkedList<String> parse(String code) {
        if (!isValid(code)) {
            throw new CustomException("code格式不正确!");
        }

        LinkedList<String> codeList = Lists.newLinkedList();
        boolean hasCode = false;
        boolean isSelfCity = isSelfCity(code);
        for (int i = code.length() - 1; i >= 0; i--) {
            char c = code.charAt(i);
            if (c != '0') {
                hasCode = true;
            }

            if (hasCode) {
                if (i == VILLAGE_START) {
                    codeList.addFirst(code);
                } else if (i == TOWN_START) {
                    codeList.addFirst(getCode(code, VILLAGE_START));
                } else if (i == COUNTY_START) {
                    codeList.addFirst(getCode(code, TOWN_START));
                } else if (!isSelfCity && i == CITY_START) {
                    codeList.addFirst(getCode(code, COUNTY_START));
                } else if (i == PROVINCE_START) {
                    codeList.addFirst(getCode(code, CITY_START));
                }
            }
        }

        if (!hasCode) {
            throw new CustomException("code不合法!");
        }

        return codeList;
    }

    private static String getCode(String code, int start) {
        return StringUtils.rightPad(code.substring(PROVINCE_START, start), SIZE, PAD_CHAR);
    }
}
