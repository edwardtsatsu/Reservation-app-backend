package com.giskard.ReservationProject.controller.response;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationErrorResponseTest {
    @Test
    void testCanEqual() {
        assertFalse((new ValidationErrorResponse()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();

        ValidationErrorResponse validationErrorResponse1 = new ValidationErrorResponse();
        validationErrorResponse1.setErrors(new HashMap<>());
        assertTrue(validationErrorResponse.canEqual(validationErrorResponse1));
    }

    @Test
    void testConstructor() {
        ValidationErrorResponse actualValidationErrorResponse = new ValidationErrorResponse();
        HashMap<String, String> stringStringMap = new HashMap<>();
        actualValidationErrorResponse.setErrors(stringStringMap);
        assertSame(stringStringMap, actualValidationErrorResponse.getErrors());
    }

    @Test
    void testConstructor2() {
        assertNull((new ValidationErrorResponse()).getErrors());
    }

    @Test
    void testEquals() {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setErrors(new HashMap<>());
        assertFalse(false);
    }

    @Test
    void testEquals2() {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setErrors(new HashMap<>());
        assertNotEquals("Different type to ValidationErrorResponse", validationErrorResponse);
    }

    @Test
    void testEquals3() {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setErrors(new HashMap<>());
        assertEquals(validationErrorResponse, validationErrorResponse);
        int expectedHashCodeResult = validationErrorResponse.hashCode();
        assertEquals(expectedHashCodeResult, validationErrorResponse.hashCode());
    }

    @Test
    void testEquals4() {
        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("Key", "42");

        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setErrors(stringStringMap);

        ValidationErrorResponse validationErrorResponse1 = new ValidationErrorResponse();
        validationErrorResponse1.setErrors(new HashMap<>());
        assertNotEquals(validationErrorResponse, validationErrorResponse1);
    }

    @Test
    void testEquals5() {
        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("Key", "42");

        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setErrors(stringStringMap);

        HashMap<String, String> stringStringMap1 = new HashMap<>(new HashMap<>());
        stringStringMap1.put("Key", "42");

        ValidationErrorResponse validationErrorResponse1 = new ValidationErrorResponse();
        validationErrorResponse1.setErrors(stringStringMap1);
        assertEquals(validationErrorResponse, validationErrorResponse1);
        int expectedHashCodeResult = validationErrorResponse.hashCode();
        assertEquals(expectedHashCodeResult, validationErrorResponse1.hashCode());
    }
}

