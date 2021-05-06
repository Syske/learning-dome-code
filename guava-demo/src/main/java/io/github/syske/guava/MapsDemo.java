package io.github.syske.guava;

import com.google.common.base.Function;
import com.google.common.collect.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: guava-demo
 * @description: maps的guava示例
 * @author: syske
 * @date: 2021-05-01 15:36
 */
public class MapsDemo {

    public static void main(String[] args) {
        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
        objectObjectHashMap.put("k1", "v1");
        objectObjectHashMap.put("k2", "v2");
        objectObjectHashMap.put("k3", "v3");

        HashMap<Object, Object> objectObjectHashMap1 = Maps.newHashMap(objectObjectHashMap);
        objectObjectHashMap.put("k4", "v4");
        objectObjectHashMap1.put("k5", "v5");

        MapDifference<Object, Object> difference = Maps.difference(objectObjectHashMap, objectObjectHashMap1);
        Map<Object, Object> objectObjectMapCommon = difference.entriesInCommon();
        Map<Object, Object> objectObjectMap1Left = difference.entriesOnlyOnLeft();
        Map<Object, Object> objectObjectMap2Right = difference.entriesOnlyOnRight();
        System.out.println(objectObjectMapCommon);
        System.out.println(objectObjectMap1Left);
        System.out.println(objectObjectMap2Right);

        List<String> stringList = Lists.newArrayList("123123", "3534534", "425345");
        ImmutableMap<String, String> stringStringImmutableMap2 = Maps.toMap(stringList, s -> s);
        ImmutableMap<String, Object> stringObjectImmutableMap = Maps.toMap(stringList, new Function<String, Object>() {

            @Override
            public @Nullable Object apply(@Nullable String input) {
                return input;
            }
        });
        System.out.println(stringObjectImmutableMap);
        System.out.println(stringStringImmutableMap2);

        ImmutableMap<Map.Entry<String, Object>, Boolean> entryBooleanImmutableMap = Maps.toMap(stringObjectImmutableMap.entrySet(), k -> "123123".equals(k));
        System.out.println(entryBooleanImmutableMap);
        ImmutableSet<Map.Entry<String, Object>> entries = entryBooleanImmutableMap.keySet();
        ImmutableSet<Map.Entry<Map.Entry<String, Object>, Boolean>> entries1 = entryBooleanImmutableMap.entrySet();

    }
}
