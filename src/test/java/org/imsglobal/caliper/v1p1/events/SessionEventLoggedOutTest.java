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

package org.imsglobal.caliper.v1p1.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.actions.CaliperAction;
import org.imsglobal.caliper.context.CaliperJsonldContext;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.SessionEvent;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class SessionEventLoggedOutTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private SoftwareApplication object, edApp;
    private Session session;
    private SessionEvent event;

    private static final String BASE_IRI = "https://example.edu";

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContext.V1P1.value());

        id = "urn:uuid:a438f8ac-1da3-4d48-8c86-94a1b387e0f6";

        actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        object = SoftwareApplication.builder().id(BASE_IRI).version("v2").build();

        edApp = SoftwareApplication.builder().id(object.getId()).coercedToId(true).build();

        session = Session.builder()
            .id(BASE_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259"))
            .user(Person.builder().id(actor.getId()).coercedToId(true).build())
            .dateCreated(new DateTime(2016, 11, 15, 10, 0, 0, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 0, 0, 0, DateTimeZone.UTC))
            .endedAtTime(new DateTime(2016, 11, 15, 11, 5, 0, 0, DateTimeZone.UTC))
            .duration("PT3000S")
            .build();

        // Build event
        event = buildEvent(Action.LOGGED_OUT);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p1/caliperEventSessionLoggedOut.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void sessionEventRejectsMutedAction() {
        buildEvent(Action.MUTED);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build Session logout event.
     * @param action
     * @return event
     */
    private SessionEvent buildEvent(CaliperAction action) {
        return SessionEvent.builder()
            .context(context)
            .id(id)
            .actor(actor)
            .action(action)
            .object(object)
            .eventTime(new DateTime(2016, 11, 15, 11, 5, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .session(session)
            .build();
    }
}