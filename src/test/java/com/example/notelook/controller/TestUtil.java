package com.example.notelook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

public class TestUtil {
    public static byte[] writeObjAsBytes(Object obj) {
        try {
            return new ObjectMapper().writeValueAsBytes(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
