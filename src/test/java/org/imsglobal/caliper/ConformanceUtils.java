package org.imsglobal.caliper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yammer.dropwizard.json.ObjectMapperFactory;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.net.URL;
import java.io.IOException;
import java.nio.charset.Charset;

public class ConformanceUtils {

    // inspired by com.yammer.dropwizard.testing.JsonHelpers
    private static final ObjectMapper MAPPER = new ObjectMapperFactory().build();

    // inspired by com.yammer.dropwizard.testing.FixtureHelpers
    public static String fixture(String url, Charset charset) throws IOException {
        return Resources.toString(new URL(url), charset).trim();
    }

    // inspired by com.yammer.dropwizard.testing.FixtureHelpers
    public static String fixture(String url) throws IOException {
        return fixture(url, Charsets.UTF_8);
    }

    // inspired by com.yammer.dropwizard.testing.JsonHelpers
    public static String jsonFixture(String filename) throws IOException {
        String url = "http://localhost:1080/" + filename;
        return MAPPER.writeValueAsString(MAPPER.readValue(fixture(url), JsonNode.class));
    }

}