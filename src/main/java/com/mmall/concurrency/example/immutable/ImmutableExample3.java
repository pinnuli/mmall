package com.mmall.concurrency.example.immutable;

import com.google.common.collect.ImmutableMap;
import com.mmall.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;


/**
 * 使用Guava创建不可变对象
 * @author: pinnuli
 * @date: 2019-02-16
 */

@Slf4j
@NotThreadSafe
public class ImmutableExample3 {

    private final static Integer a = 1;
    private final static String b = "2";
    private final static ImmutableMap<Integer, Integer> map = ImmutableMap.of(1, 2, 3, 4);

    private final static ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder()
            .put(1, 2).put(3, 4).put(5, 6).build();


    public static void main(String[] args) {

//        map2.put(1, 3);
        log.info("{}", map.get(1));
    }

}

