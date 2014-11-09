package org.imsglobal.caliper.request;

import org.apache.http.entity.StringEntity;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.events.NavigationEvent;
import org.imsglobal.caliper.profiles.ReadingProfile;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class HttpRequestorTest {

    private HttpRequestor httpRequestor;
    private LearningContext learningContext;
    private NavigationEvent event;
    private ReadingProfile profile;
    private String id;
    private DateTime timestamp;
    private String expectedContentType;
    private static final Logger LOG = LoggerFactory.getLogger(HttpRequestorTest.class);

    @Before
    public void setup() {

        httpRequestor = new HttpRequestor(TestUtils.getTestingOptions());
        id = "caliper-java_fccffd9b-68d5-4183-b563-e22136aafaa3";
        timestamp = DateTime.parse("2014-07-01T14:29:29.858-04:00");
        expectedContentType = "Content-Type: application/json";

        // Build the Learning Context
        learningContext = TestUtils.buildLearningContext();

        // Build Reading Profile
        profile = TestUtils.buildReadingProfile(learningContext);

        // Add navigation-related properties to profile
        profile = TestUtils.navigateToReadingTarget(profile);

        // Build event
        event = TestUtils.buildNavigationEvent(profile);
    }

    @Test
    public void testGeneratePayloadJson() throws Exception {

        String jsonPayload;
        jsonPayload = httpRequestor.getPayloadJson(event, id, timestamp);

        LOG.debug("JSON payload: " + jsonPayload);

        assertEquals("Test HTTP Requestor payload JSON",
            jsonFixture("fixtures/eventStorePayload.json"), jsonPayload);

    }

    @Test
    public void testGeneratePayloadContentType() throws Exception {

        StringEntity payload;
        payload = httpRequestor.generatePayload(event, id, timestamp);

        assertEquals(expectedContentType, payload.getContentType().toString());
    }

    @After
    public void teardown() {
        event = null;
    }
}