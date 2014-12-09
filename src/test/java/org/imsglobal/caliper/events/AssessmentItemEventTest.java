package org.imsglobal.caliper.events;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.assessment.Assessment;
import org.imsglobal.caliper.entities.assessment.AssessmentItem;
import org.imsglobal.caliper.profiles.AssessmentItemProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static org.imsglobal.caliper.ConformanceUtils.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class AssessmentItemEventTest {
    private LearningContext learningContext;
    private Assessment assessment;
    private AssessmentItem item;
    private String key;
    private AssessmentItemEvent event;
    private static final Logger LOG = LoggerFactory.getLogger(AssessmentItemEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = TestUtils.buildAssessmentToolLearningContext();

        // Build assessment and get assessment item 1
        item = TestUtils.buildAssessment().getAssessmentItems().get(0);

        // Action
        key = AssessmentItemProfile.AssessmentItemActions.STARTED.key();

        //TODO add response

        // Build event
        event = TestUtils.buildAssessmentItemEvent(learningContext, item, key);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if Assessment Item event is serialized to JSON with expected values",
                jsonFixture("fixtures/caliperAssessmentItemEvent.json"), asJson(event));
    }
}