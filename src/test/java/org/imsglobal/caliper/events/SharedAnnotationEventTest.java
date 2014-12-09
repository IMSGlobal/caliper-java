package org.imsglobal.caliper.events;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.annotation.SharedAnnotation;
import org.imsglobal.caliper.entities.reading.EpubSubChapter;
import org.imsglobal.caliper.profiles.AnnotationProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static org.imsglobal.caliper.ConformanceUtils.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class SharedAnnotationEventTest {

    private LearningContext learningContext;
    private EpubSubChapter epub;
    private SharedAnnotation shared;
    private String key;
    private AnnotationEvent event;
    private static final Logger LOG = LoggerFactory.getLogger(SharedAnnotationEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = TestUtils.buildReadiumLearningContext();

        //Build target reading
        epub = (EpubSubChapter) TestUtils.buildEpubSubChap433();

        // Build Bookmark Annotation
        shared = TestUtils.buildSharedAnnotation(epub);

        // Add action
        key = AnnotationProfile.AnnotationActions.SHARED.key();

        // Build event
        event = TestUtils.buildAnnotationEvent(learningContext, shared, key, epub, 3);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if Shared Annotation event is serialized to JSON with expected values",
                jsonFixture("fixtures/caliperSharedAnnotationEvent.json"), asJson(event));
    }
}