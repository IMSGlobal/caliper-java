package org.imsglobal.caliper.events;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.annotation.HighlightAnnotation;
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
public class HighlightAnnotationEventTest {

    private LearningContext learningContext;
    private EpubSubChapter epub;
    private HighlightAnnotation highlight;
    private String key;
    private AnnotationEvent event;
    private static final Logger LOG = LoggerFactory.getLogger(HighlightAnnotationEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = TestUtils.buildReadiumLearningContext();

        //Build target reading
        epub = (EpubSubChapter) TestUtils.buildEpubSubChap431();

        // Build Bookmark Annotation
        highlight = TestUtils.buildHighlightAnnotation(epub);

        // Add action
        key = AnnotationProfile.AnnotationActions.HIGHLIGHTED.key();

        // Build event
        event = TestUtils.buildAnnotationEvent(learningContext, highlight, key, epub, 1);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if Highlight Annotation event is serialized to JSON with expected values",
                jsonFixture("fixtures/caliperHighlightAnnotationEvent.json"), asJson(event));
    }
}