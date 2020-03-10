package xyz.ewis.websitemonitor.utils;

import java.util.Collection;

/**
 * CollectionUtils
 *
 * @author MAJANNING
 * @date 2020/3/7
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }
}
