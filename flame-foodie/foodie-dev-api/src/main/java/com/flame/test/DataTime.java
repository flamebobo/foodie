package com.flame.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DataTime {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2021, 1, 26);
        LocalDate date1 = LocalDate.parse("2021-01-26");
        System.out.println("date:"+date);
        System.out.println("date1:"+date1);

        LocalDateTime dateTime = LocalDateTime.of(2021, 1, 26, 12, 12, 22);
//        LocalDateTime localDateTime = LocalDateTime.parse("2021-01-26 12:12:22");
        System.out.println("dateTime:"+dateTime);
//        System.out.println("localDateTime:"+localDateTime);

        LocalTime time = LocalTime.of(12, 12, 22);
        LocalTime localTime = LocalTime.parse("12:12:22");
        System.out.println("time:"+time);
        System.out.println("localTime:"+localTime);
    }
}
