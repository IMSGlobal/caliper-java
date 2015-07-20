/**
 * This file is part of IMS Caliper Analytics™ and is licensed to
 * IMS Global Learning Consortium, Inc. (http://www.imsglobal.org)
 * under one or more contributor license agreements.  See the NOTICE
 * file distributed with this work for additional information.
 *
 * IMS Caliper is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * IMS Caliper is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.imsglobal.caliper.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.imsglobal.caliper.TestAgentEntities;
import org.imsglobal.caliper.TestDates;
import org.imsglobal.caliper.TestLisEntities;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.payload.JsonMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class SessionTimeoutEventTest {

    private LearningContext learningContext;
    private SoftwareApplication actor;
    private Session object;
    private SessionEvent event;
    private DateTime dateCreated = TestDates.getDefaultDateCreated();
    private DateTime dateModified = TestDates.getDefaultDateModified();
    private DateTime dateStarted = TestDates.getDefaultStartedAtTime();
    private DateTime dateEnded = TestDates.getDefaultEndedAtTime();
    private String duration = TestDates.getDefaultPeriod();
    // private static final Logger log = LoggerFactory.getLogger(SessionLogoutEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = LearningContext.builder()
            .edApp(TestAgentEntities.buildEpubViewerApp())
            .group(TestLisEntities.buildGroup())
            .build();

        // Build actor
        actor = learningContext.getEdApp();

        // Build object
        object = Session.builder()
            .id("https://example.com/viewer/session-123456789")
            .name("session-123456789")
            .actor(TestAgentEntities.buildStudent554433())
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .startedAtTime(dateStarted)
            .endedAtTime(dateEnded)
            .duration(duration)
            .build();

        // Build event
        event = buildEvent(Action.TIMED_OUT);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if timedOut event is serialized to JSON with expected values",
                jsonFixture("fixtures/caliperSessionTimeoutEvent.json"), JsonMapper.serialize(event, JsonInclude.Include.NON_EMPTY));
    }

    @Test(expected=IllegalArgumentException.class)
    public void sessionEventRejectsLikedAction() {
        buildEvent(Action.LIKED);
    }

    /**
     * Build Session Timeout event.  Note that the actor is the edApp and
     * the membership of the actor is not specified.
     * @param action
     * @return event
     */
    private SessionEvent buildEvent(Action action) {
        return SessionEvent.builder()
            .actor(actor)
            .action(action)
            .object(object)
            .startedAtTime(dateStarted)
            .endedAtTime(dateEnded)
            .duration(duration)
            .edApp(learningContext.getEdApp())
            .group(learningContext.getGroup())
            .build();
    }
}