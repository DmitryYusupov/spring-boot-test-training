package ru.yusdm.training.springboottest.common.solutions;

@FunctionalInterface
public interface Converter<From, To> {
    To convert(From from);
}
