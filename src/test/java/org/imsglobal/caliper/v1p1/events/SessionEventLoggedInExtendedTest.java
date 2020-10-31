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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.actions.CaliperAction;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
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

import java.util.Map;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class SessionEventLoggedInExtendedTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private SoftwareApplication object, edApp;
    private Session session;
    private Map<String, Object> extensions;
    private SessionEvent event;

    private static final String BASE_IRI = "https://example.edu";

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContextIRI.V1P1.value());

        id = "urn:uuid:4ec2c31e-3ec0-4fe1-a017-b81561b075d7";

        actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        object = SoftwareApplication.builder().id(BASE_IRI).version("v2").build();

        edApp = SoftwareApplication.builder().id(object.getId()).coercedToId(true).build();

        // Create Session extensions
        Request request = Request.create();
        extensions = Maps.newHashMap();
        extensions.put("request", request);

        session = Session.builder()
            .id(BASE_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259"))
            .user(Person.builder().id(actor.getId()).coercedToId(true).build())
            .dateCreated(new DateTime(2016, 11, 15, 20, 11, 15, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 20, 11, 15, 0, DateTimeZone.UTC))
            .extensions(extensions)
            .build();

        // Build event
        event = buildEvent(Action.LOGGED_IN);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p1/caliperEventSessionLoggedInExtended.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void sessionEventRejectsSearchedAction() {
        buildEvent(Action.SEARCHED);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build Session login event.
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
            .edApp(edApp)
            .eventTime(new DateTime(2016, 11, 15, 20, 11, 15, 0, DateTimeZone.UTC))
            .session(session)
            .build();
    }

    /**
     * Request extension
     */
    static class Request {
        private String id;
        private String hostname;
        private String userAgent;

        /**
         * Constructor
         */
        private Request() {
            this.id = "d71016dc-ed2f-46f9-ac2c-b93f15f38fdc";
            this.hostname = "example.com";
            this.userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36";
        }

        /**
         * Get the Job identifier.
         * @return the id
         */
        @JsonProperty("id")
        private String getId() {
            return id;
        }

        /**
         * Get the Hostname.
         * @return the hostname
         */
        @JsonProperty("hostname")
        private String getHostname() {
            return hostname;
        }

        /**
         * Get the User Agent string.
         * @return the userAgent
         */
        @JsonProperty("userAgent")
        private String getUserAgent() {
            return userAgent;
        }

        /**
         * Factory method
         * @return new Job
         */
        private static Request create() {
            return new Request();
        }
    }
}