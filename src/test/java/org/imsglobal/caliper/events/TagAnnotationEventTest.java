package org.imsglobal.caliper.events;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.annotation.TagAnnotation;
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
public class TagAnnotationEventTest {

    private LearningContext learningContext;
    private EpubSubChapter target;
    private TagAnnotation tags;
    private String key;
    private AnnotationEvent event;
    private static final Logger LOG = LoggerFactory.getLogger(TagAnnotationEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = TestUtils.buildReadiumLearningContext();

        //Build target reading
        target = TestUtils.buildEpubSubChap434();

        // Build Bookmark Annotation
        tags = TestUtils.buildTagAnnotation(target);

        // Add action
       key = AnnotationProfile.AnnotationActions.TAGGED.key();

        // Build event
        event = TestUtils.buildAnnotationEvent(learningContext, tags, key, target, 4);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if Tag Annotation event is serialized to JSON with expected values",
                jsonFixture("fixtures/caliperTagAnnotationEvent.json"), asJson(event));
    }
}