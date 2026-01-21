package com.AngularCURD;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class Test1 {

    public static void main(String[] args) {

        String year = String.valueOf(LocalDate.now().getYear());
        for (int i = 0; i < 20; i++) {
            int rand = ThreadLocalRandom.current().nextInt(0, 100000);
            String five = String.format("%05d", rand);
            String candidate = "RMG" + five + year;
            System.out.println(candidate);
        }


    }
}
