package org.imsglobal.caliper.databind;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.util.Map;

public class JsonSimpleFilterProvider {
    private static SimpleFilterProvider provider;

    /**
     * Constructor
     */
    private JsonSimpleFilterProvider() {

    }

    /**
     * Factory method that returns a Caliper-friendly SimpleFilterProvider instance.
     * @return SimpleFilterProvider
     */
    private static SimpleFilterProvider create() {
        provider = new SimpleFilterProvider();
        provider.setDefaultFilter(JsonFilters.SERIALIZE_ALL.filter());
        provider.setFailOnUnknownId(true);

        return provider;
    }

    /**
     * Factory method that returns a Caliper-friendly JsonSimpleFilterProvider instance
     * provisioned with a filter.
     * @return JsonSimpleFilterProvider
     */
    public static SimpleFilterProvider create(String id, SimpleBeanPropertyFilter filter) {
        provider = create();
        provider = provider.addFilter(id, filter);

        return provider;
    }

    /**
     * Factory method that returns a Caliper-friendly JsonSimpleFilterProvider instance
     * provisioned with a stock filter.
     * @return JsonSimpleFilterProvider
     */
    public static SimpleFilterProvider create(JsonFilters filter) {
        provider = create();
        provider = provider.addFilter(filter.id(), filter.filter());

        return provider;
    }

    /**
     * Factory method that returns a Caliper-friendly JsonSimpleFilterProvider instance
     * provisioned with multiple filters.
     * @return JsonSimpleFilterProvider
     */
    public static SimpleFilterProvider create(Map<String, SimpleBeanPropertyFilter> filters) {
        provider = create();
        for (Map.Entry<String, SimpleBeanPropertyFilter> entry : filters.entrySet()) {
            provider = provider.addFilter(entry.getKey(), entry.getValue());
        }

        return provider;
    }
}