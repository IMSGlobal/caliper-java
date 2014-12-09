package org.imsglobal.caliper.events;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.annotation.BookmarkAnnotation;
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
public class BookmarkAnnotationEventTest {

    private LearningContext learningContext;
    private BookmarkAnnotation bookmark;
    private String key;
    private EpubSubChapter target;
    private AnnotationEvent event;
    private static final Logger LOG = LoggerFactory.getLogger(BookmarkAnnotationEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = TestUtils.buildReadiumLearningContext();

        //Build target reading
        target = (EpubSubChapter) TestUtils.buildEpubSubChap432();

        // Build Bookmark Annotation
        bookmark = TestUtils.buildBookmarkAnnotation(target);

        // Add action
        key = AnnotationProfile.AnnotationActions.BOOKMARKED.key();

        // Build event
        event = TestUtils.buildAnnotationEvent(learningContext, bookmark, key, target, 2);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if Bookmark Annotation event is serialized to JSON with expected values",
                jsonFixture("fixtures/caliperBookmarkAnnotationEvent.json"), asJson(event));
    }
}