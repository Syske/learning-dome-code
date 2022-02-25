/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.PrimitiveSink;

import java.nio.charset.Charset;

/**
 * @author syske
 * @version 1.0
 * @date 2021-12-14 21:49
 */
public class BloomFilterTest {
    public static void main(String[] args) {
        Funnel<CharSequence> charSequenceFunnel = Funnels.stringFunnel(Charset.defaultCharset());
        String test = "hello";
        BloomFilter bloomFilter = BloomFilter.create(charSequenceFunnel, 100);
        for (int i = 0; i < 1000; i++) {

            String object = test + i;
            System.out.println(object);
            bloomFilter.put(object);
        }
        String object = test + -888;
        if (bloomFilter.mightContain(object)) {
            System.out.println("包含" + object);
        } else {
            System.out.println("不包含" + object);
        }
    }
}
