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
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.TestAgentEntities;
import org.imsglobal.caliper.TestDates;
import org.imsglobal.caliper.TestEpubEntities;
import org.imsglobal.caliper.TestLisEntities;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.reading.EpubSubChapter;
import org.imsglobal.caliper.entities.reading.Frame;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.payload.JsonMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class SessionLoginEventTest {

    private LearningContext learningContext;
    private Person actor;
    private SoftwareApplication object;
    private EpubSubChapter ePub;
    private Frame target;
    private Session generated;
    private SessionEvent event;
    private DateTime dateCreated = TestDates.getDefaultDateCreated();
    private DateTime dateModified = TestDates.getDefaultDateModified();
    private DateTime dateStarted = TestDates.getDefaultStartedAtTime();
    // private static final Logger log = LoggerFactory.getLogger(SessionLoginEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = LearningContext.builder()
            .edApp(TestAgentEntities.buildEpubViewerApp())
            .group(TestLisEntities.buildGroup())
            .membership(TestLisEntities.buildMembership())
            .build();

        // Build actor
        actor = TestAgentEntities.buildStudent554433();

        // Build object
        object = learningContext.getEdApp();

        // Build target
        ePub = TestEpubEntities.buildEpubSubChap431();
        target = Frame.builder()
            .id(ePub.getId())
            .name(ePub.getName())
            .isPartOf(ePub.getIsPartOf())
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .version(ePub.getVersion())
            .index(1)
            .build();

        // Build generated
        generated = Session.builder()
            .id("https://example.com/viewer/session-123456789")
            .name("session-123456789")
            .actor(actor)
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .startedAtTime(dateStarted)
            .build();

        // Build event
        event = buildEvent(Action.LOGGED_IN);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if loggedIn event is serialized to JSON with expected values",
            jsonFixture("fixtures/caliperSessionLoginEvent.json"), JsonMapper.serialize(event, JsonInclude.Include.NON_EMPTY));
    }

    @Test(expected=IllegalArgumentException.class)
    public void sessionEventRejectsSearchedAction() {
        buildEvent(Action.SEARCHED);
    }

    /**
     * Build Session login event.
     * @param action
     * @return event
     */
    private SessionEvent buildEvent(Action action) {
        return SessionEvent.builder()
            .actor(actor)
            .action(action)
            .object(learningContext.getEdApp())
            .target(target)
            .generated(generated)
            .startedAtTime(dateStarted)
            .edApp(learningContext.getEdApp())
            .group(learningContext.getGroup())
            .membership(learningContext.getMembership())
            .build();
    }
}