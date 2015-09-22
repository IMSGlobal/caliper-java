package org.imsglobal.caliper.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

public class JsonObjectMapper {

    private static ObjectMapper mapper;

    /**
     * Constructor
     */
    private JsonObjectMapper() {

    }

    /**
     * Default factory method that returns a Caliper-friendly Jackson ObjectMapper instance.
     * @param include
     * @return
     */
    public static ObjectMapper create(Include include) {
        mapper = new ObjectMapper();
        mapper.setDateFormat(new ISO8601DateFormat());
        mapper.setSerializationInclusion(include);
        mapper.registerModule(new JodaModule());

        return mapper;
    }

    /**
     * Factory method that returns a Caliper-friendly Jackson ObjectMapper instance.
     * @param include
     * @param provider
     * @return
     */
    public static ObjectMapper create(Include include, FilterProvider provider) {
        mapper = create(include);
        mapper.setFilterProvider(provider);

        return mapper;
    }
}