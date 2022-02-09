package com.giskard.ReservationProject.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;

class HelpersTest {
    @Test
    void testGetDayFromDate() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Helpers.getDayFromDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
    }

    @Test
    void testGetDayFromDate2() {
        assertEquals("", Helpers.getDayFromDate(null));
    }
}

