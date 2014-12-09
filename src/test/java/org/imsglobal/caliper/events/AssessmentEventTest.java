package org.imsglobal.caliper.events;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.assessment.Assessment;
import org.imsglobal.caliper.entities.assignable.Attempt;
import org.imsglobal.caliper.profiles.AssessmentProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static org.imsglobal.caliper.ConformanceUtils.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class AssessmentEventTest {

    private LearningContext learningContext;
    private Assessment assessment;
    private Attempt attempt;
    private String key;
    private AssessmentEvent event;
    private static final Logger LOG = LoggerFactory.getLogger(AssessmentEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = TestUtils.buildAssessmentToolLearningContext();

        // Build assessment
        assessment = TestUtils.buildAssessment();

        // Generate attempt
        attempt = TestUtils.buildAssessmentAttempt(learningContext, assessment);

        // Action
        key = AssessmentProfile.AssessmentActions.STARTED.key();

        // Build event
        event = TestUtils.buildAssessmentEvent(learningContext, assessment, key, attempt);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if Assessment event is serialized to JSON with expected values",
                jsonFixture("fixtures/caliperAssessmentEvent.json"), asJson(event));
    }
}