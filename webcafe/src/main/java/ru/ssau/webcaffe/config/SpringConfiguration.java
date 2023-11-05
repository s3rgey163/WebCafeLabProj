package ru.ssau.webcaffe.config;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class SpringConfiguration {
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .dateFormat(new StdDateFormat());
    }
}
