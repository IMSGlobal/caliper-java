package org.imsglobal.caliper.events;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.media.MediaLocation;
import org.imsglobal.caliper.entities.media.VideoObject;
import org.imsglobal.caliper.profiles.MediaProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static org.imsglobal.caliper.ConformanceUtils.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class MediaEventTest {

    private LearningContext learningContext;
    private MediaLocation location;
    private VideoObject video;
    private String key;
    private MediaEvent event;
    private static final Logger LOG = LoggerFactory.getLogger(MediaEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = TestUtils.buildSuperMediaToolLearningContext();

        // Build video
        video = TestUtils.buildVideoWithLearningObjective();

        // Build media location
        location = TestUtils.buildVideoMediaLocation();

        // Action
        key = MediaProfile.MediaActions.PAUSED.key();

        // Build event
        event = TestUtils.buildVideoMediaEvent(learningContext, video, location, key);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if Media event is serialized to JSON with expected values",
                jsonFixture("fixtures/caliperMediaEvent.json"), asJson(event));
    }
}