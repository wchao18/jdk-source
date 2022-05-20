package com.it;

import java.util.HashMap;
import java.util.Map;

public class TestHashMap {

    public static void main(String[] args) {
        Map<Object, Object> hashMap = new HashMap<>();
        hashMap.put("test","2022");
        System.out.println(hashMap.get("test"));
    }
}
