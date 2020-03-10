package xyz.ewis.websitemonitor.utils;

import java.util.function.BinaryOperator;

/**
 * FunctionUtil
 *
 * @author MAJANNING
 * @date 2020/3/9
 */
@FunctionalInterface
public interface BinaryOperatorUtil<T> extends BinaryOperator<T> {

    static <T> BinaryOperator<T> first() {
        return (a, b) -> a;
    }
}
