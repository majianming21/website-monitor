package xyz.ewis.websitemonitor.utils;

import java.util.ArrayList;

/**
 * Lists
 *
 * @author MAJANNING
 * @date 2020/3/7
 */
public class Lists {
    public static <E> ArrayList<E> newArrayList(E... elements) {
        return com.google.common.collect.Lists.newArrayList(elements);
    }
}
