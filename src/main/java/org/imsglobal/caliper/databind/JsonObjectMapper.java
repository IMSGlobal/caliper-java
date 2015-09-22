package org.imsglobal.caliper.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

public class JsonObjectMapper extends ObjectMapper {

    /**
     * Constructor
     */
    private JsonObjectMapper() {
        super();
    }

    /**
     * Factory method that returns a Caliper-friendly Jackson ObjectMapper instance.
     * @return
     */
    public static JsonObjectMapper create(Include include) {
        JsonObjectMapper mapper = new JsonObjectMapper();
        mapper.setDateFormat(new ISO8601DateFormat());
        mapper.setSerializationInclusion(include);
        mapper.registerModule(new JodaModule());

        return mapper;
    }

    /**
     * Factory method that returns a Caliper-friendly Jackson ObjectMapper instance.
     * @return
     */
    public static JsonObjectMapper create(Include include, FilterProvider provider) {
        JsonObjectMapper mapper = create(include);
        mapper.setFilterProvider(provider);

        return mapper;
    }
}