package org.imsglobal.caliper.events;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.assessment.Assessment;
import org.imsglobal.caliper.entities.assignable.Attempt;
import org.imsglobal.caliper.entities.outcome.Result;
import org.imsglobal.caliper.profiles.OutcomeProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static org.imsglobal.caliper.ConformanceUtils.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class AssessmentOutcomeEventTest {

    private LearningContext learningContext;
    private Assessment assessment;
    private String key;
    private Attempt attempt;
    private Result result;
    private OutcomeEvent event;
    private static final Logger LOG = LoggerFactory.getLogger(AssessmentOutcomeEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = TestUtils.buildAssessmentToolLearningContext();

        // Build assessment
        assessment = TestUtils.buildAssessment();

        // Build attempt
        attempt = TestUtils.buildAssessmentAttempt(learningContext, assessment);

        // Build action
        key = OutcomeProfile.OutcomeActions.GRADED.key();

        // Build result
        result = TestUtils.buildAssessmentResult(attempt);

        // Build Outcome Event
        event = TestUtils.buildAssessmentOutcomeEvent(learningContext, attempt, key, result);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if Outcome event is serialized to JSON with expected values",
                jsonFixture("fixtures/caliperAssessmentOutcomeEvent.json"), asJson(event));
    }
}