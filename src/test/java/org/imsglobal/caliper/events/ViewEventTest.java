package org.imsglobal.caliper.events;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.reading.EpubSubChapter;
import org.imsglobal.caliper.entities.reading.EpubVolume;
import org.imsglobal.caliper.profiles.ReadingProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static org.imsglobal.caliper.ConformanceUtils.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class ViewEventTest {

    private LearningContext learningContext;
    private EpubVolume epub;
    private String key;
    private EpubSubChapter target;
    private ViewEvent event;
    private static final Logger LOG = LoggerFactory.getLogger(ViewEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = TestUtils.buildReadiumLearningContext();

        // Build epub
        epub = TestUtils.buildEpubVolume43();

        // Build target
        target = TestUtils.buildEpubSubChap431();

        // Action
       key = ReadingProfile.ReadingActions.VIEWED.key();

        // Build event
        event = TestUtils.buildEpubViewEvent(learningContext, epub, key, target);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if View event is serialized to JSON with expected values",
                jsonFixture("fixtures/caliperViewEvent.json"), asJson(event));
    }
}