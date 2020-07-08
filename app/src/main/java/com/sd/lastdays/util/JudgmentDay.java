package com.sd.lastdays.util;

/**
 * 每年月份多少天
 */
public class JudgmentDay {
    public int judgmentDay(int year, int month) {
        int daysnum = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysnum = 31;
                break;
            case 4:
            case 6:
            case 9:
                daysnum = 30;
                break;
            case 2:
                if (year % 4 == 0 && !(year % 100 == 0) || year % 400 == 0) {
                    daysnum = 29;

                } else {
                    daysnum = 28;
                }

                break;
            default:
        }
        return daysnum;
    }
}
