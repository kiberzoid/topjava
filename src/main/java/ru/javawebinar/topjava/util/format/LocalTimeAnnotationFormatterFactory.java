package ru.javawebinar.topjava.util.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LocalTimeAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalTimeFormat> {
    @Autowired
    private LocalDateFormatter formatter;

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(LocalTimeFormat annotation, Class<?> aClass) {
        return new LocalTimeFormatter();
    }

    @Override
    public Parser<?> getParser(LocalTimeFormat annotation, Class<?> aClass) {
        return new LocalTimeFormatter();
    }
}
