package com.mmall.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.mmall.concurrency.annoations.NotThreadSafe;
import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * 通过Collections.unmodifiableMap处理的对象，
 * 不可被修改，执行put时会抛出异常，
 * 其实它的原理是将原来的对象里面的值进行复制，
 * 然后将一些要修改对象数据的方法直接抛出异常
 * @author: pinnuli
 * @date: 2019-02-16
 */

@Slf4j
@ThreadSafe
public class ImmutableExample1 {

    private final static Integer a = 1;
    private final static String b = "2";
    // 这里没有用final修饰
    private static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        map.put(1, 3);
        log.info("{}", map.get(1));
    }

}

