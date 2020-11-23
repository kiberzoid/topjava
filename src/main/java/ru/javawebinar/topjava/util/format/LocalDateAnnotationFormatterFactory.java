package ru.javawebinar.topjava.util.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LocalDateAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalDateFormat> {

    @Autowired
    private LocalDateFormatter formatter;

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(LocalDate.class));
    }

    @Override
    public Printer<?> getPrinter(LocalDateFormat annotation, Class<?> aClass) {
        return new LocalDateFormatter();
    }

    @Override
    public Parser<?> getParser(LocalDateFormat annotation, Class<?> aClass) {
        return new LocalDateFormatter();
    }
}
