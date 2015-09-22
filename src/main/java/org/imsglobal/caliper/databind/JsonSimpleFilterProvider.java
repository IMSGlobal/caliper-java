package org.imsglobal.caliper.databind;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.util.Map;

public class JsonSimpleFilterProvider extends SimpleFilterProvider {

    /**
     * Constructor
     */
    private JsonSimpleFilterProvider() {
        super();
    }

    /**
     * Factory method that returns a Caliper-friendly JsonSimpleFilterProvider instance.
     * @return JsonSimpleFilterProvider
     */
    private static JsonSimpleFilterProvider create() {
        JsonSimpleFilterProvider provider = new JsonSimpleFilterProvider();
        provider.setDefaultFilter(JsonFilters.SERIALIZE_ALL.filter());
        provider.setFailOnUnknownId(true);

        return provider;
    }

    /**
     * Factory method that returns a Caliper-friendly JsonSimpleFilterProvider instance
     * provisioned with a filter.
     * @return JsonSimpleFilterProvider
     */
    public static JsonSimpleFilterProvider create(String id, SimpleBeanPropertyFilter filter) {
        JsonSimpleFilterProvider provider = create();
        provider = (JsonSimpleFilterProvider) provider.addFilter(id, filter);

        return provider;
    }

    /**
     * Factory method that returns a Caliper-friendly JsonSimpleFilterProvider instance
     * provisioned with a stock filter.
     * @return JsonSimpleFilterProvider
     */
    public static JsonSimpleFilterProvider create(JsonFilters filter) {
        JsonSimpleFilterProvider provider = create();
        provider = (JsonSimpleFilterProvider) provider.addFilter(filter.id(), filter.filter());

        return provider;
    }

    /**
     * Factory method that returns a Caliper-friendly JsonSimpleFilterProvider instance
     * provisioned with multiple filters.
     * @return JsonSimpleFilterProvider
     */
    public static JsonSimpleFilterProvider create(Map<String, SimpleBeanPropertyFilter> filters) {
        JsonSimpleFilterProvider provider = create();
        for (Map.Entry<String, SimpleBeanPropertyFilter> entry : filters.entrySet()) {
            provider = (JsonSimpleFilterProvider) provider.addFilter(entry.getKey(), entry.getValue());
        }

        return provider;
    }
}