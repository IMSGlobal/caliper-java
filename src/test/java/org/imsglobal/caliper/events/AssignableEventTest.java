package org.imsglobal.caliper.events;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.assessment.Assessment;
import org.imsglobal.caliper.profiles.AssignableProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static org.imsglobal.caliper.ConformanceUtils.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class AssignableEventTest {
    private LearningContext learningContext;
    private Assessment assessment;
    private String key;
    private AssignableEvent event;
    private static final Logger LOG = LoggerFactory.getLogger(AssignableEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = TestUtils.buildAssessmentToolLearningContext();

        // Build assessment
        assessment = TestUtils.buildAssessment();

        // Action
        key = AssignableProfile.AssignableActions.ACTIVATED.key();

        // Build event
        event = TestUtils.buildAssessmentAssignableEvent(learningContext, assessment, key);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if Assignable event is serialized to JSON with expected values",
                jsonFixture("fixtures/caliperAssignableEvent.json"), asJson(event));
    }
}