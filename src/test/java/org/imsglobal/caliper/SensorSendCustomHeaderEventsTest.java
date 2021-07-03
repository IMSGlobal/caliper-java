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

package org.imsglobal.caliper;

import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.clients.HttpCustomHeader;
import org.imsglobal.caliper.clients.HttpCustomHeaderClient;
import org.imsglobal.caliper.clients.HttpClientOptions;
import org.imsglobal.caliper.config.Config;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.*;
import org.imsglobal.caliper.entities.resource.WebPage;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.NavigationEvent;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SensorSendCustomHeaderEventsTest {
    private static final String BASE_IRI = "https://example.edu";
    private static final String ENDPOINT = "https://caliper.imsglobal.org/caliper/f6c96fef-c3bd-4348-8fad-d41300f350f9/message";

    // Initialize Sensor, Client and Requester provisioned with Options
    private static Sensor sensor = Sensor.create(BASE_IRI.concat("/sensors/1"));
    private static HttpClientOptions opts = HttpClientOptions.builder()
                                                .host(ENDPOINT)
                                                .apiKey("Bearer f6c96fef-c3bd-4348-8fad-d41300f350f9")
                                                .build();
    private static HttpCustomHeaderClient client = HttpCustomHeaderClient.create(sensor.getId(), opts);
    private static JsonldStringContext context = JsonldStringContext.getDefault();

    // Caliper event
    private NavigationEvent event;

    @BeforeClass
    public static void initializeSensorWithClient() {
        sensor.registerClient(client);
    }

    @Before
    public void setUpCaliperEntityAndEvent() {
        String id = "urn:id:" + UUID.randomUUID().toString();

        Person actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        String currentLocation = BASE_IRI.concat("/terms/201601/courses/7/sections/1/pages/1");
        String previousLocation = BASE_IRI.concat("/terms/201601/courses/7/sections/1/pages/1");

        WebPage object = WebPage.builder()
                .id(currentLocation)
                .name("Learning Analytics Specifications")
                .description("Overview of Learning Analytics Specifications with particular emphasis on IMS Caliper.")
                .dateCreated(new DateTime(2016, 8, 1, 9, 0, 0, 0, DateTimeZone.UTC))
                .build();

        WebPage referrer = WebPage.builder().id(previousLocation).build();

        SoftwareApplication edApp = SoftwareApplication.builder().id(BASE_IRI).build();

        CourseSection group = CourseSection.builder().id(BASE_IRI.concat("/terms/201601/courses/7/sections/1"))
                .courseNumber("CPS 435-01")
                .academicSession("Fall 2016")
                .build();

        Membership membership = Membership.builder()
                .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/rosters/1"))
                .member(actor)
                .organization(CourseSection.builder().id(group.getId()).build())
                .status(Status.ACTIVE)
                .role(Role.LEARNER)
                .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
                .build();

        Session session = Session.builder()
                .id(BASE_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259"))
                .startedAtTime(new DateTime(2016, 11, 15, 10, 0, 0, 0, DateTimeZone.UTC))
                .build();

        event = NavigationEvent.builder()
                .context(context)
                .id(id)
                .actor(actor)
                .action(Action.NAVIGATED_TO)
                .object(object)
                .eventTime(new DateTime(DateTimeZone.UTC))
                .referrer(referrer)
                .edApp(edApp)
                .group(group)
                .membership(membership)
                .session(session)
                .build();
    }

    @Test
    public void testSendSingleEvent() {

        // Prep envelope
        DateTime sendTime = new DateTime(DateTimeZone.UTC);
        List<Object> data = new ArrayList<>();
        data.add(event);

        // Add additional headers
        client.setCustomHeader("Accept", "*/*");

        Envelope envelope = sensor.create(client.getId(), sendTime, Config.DATA_VERSION, data);

        // Send envelope
        sensor.send(client, envelope);

        assertEquals("Expect a Caliper event with custom header to be sent",
                client.getStatistics().getMeasures().getCount(),
                client.getStatistics().getSuccessful().getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetContentTypeHeader() {

        // Prep envelope
        DateTime sendTime = new DateTime(DateTimeZone.UTC);
        List<Object> data = new ArrayList<>();
        data.add(event);

        // Add additional headers
        client.setCustomHeader("Content-Type", "text");

        Envelope envelope = sensor.create(client.getId(), sendTime, Config.DATA_VERSION, data);
        sensor.send(client, envelope);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAuthorizationHeader() {

        // Prep envelope
        DateTime sendTime = new DateTime(DateTimeZone.UTC);
        List<Object> data = new ArrayList<>();
        data.add(event);

        // Add additional headers
        client.setCustomHeader("Authorization", "Bearer 123121421421414");

        Envelope envelope = sensor.create(client.getId(), sendTime, Config.DATA_VERSION, data);
        sensor.send(client, envelope);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHeaderBlankString() {
        // Prep envelope
        DateTime sendTime = new DateTime(DateTimeZone.UTC);
        List<Object> data = new ArrayList<>();
        data.add(event);

        // Add additional headers
        client.setCustomHeader("Accept", "");

        Envelope envelope = sensor.create(client.getId(), sendTime, Config.DATA_VERSION, data);
        sensor.send(client, envelope);
    }

    @Test
    public void testSetCustomHeaderList() {
        // Prep envelope
        DateTime sendTime = new DateTime(DateTimeZone.UTC);
        List<Object> data = new ArrayList<>();
        data.add(event);

        List<HttpCustomHeader> headerList = new ArrayList<HttpCustomHeader>() {{
            add(new HttpCustomHeader("accept", "*/*"));
            add(new HttpCustomHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0"));
        }};

        client.setCustomHeaders(headerList);

        assertEquals("Check size of custom header list",
                client.getCustomHeaders().size(),
                2);
        assertNotEquals("Check 'accept' header exists in custom header list",
                client.findCustomHeader("accept"),
                -1);
        assertNotEquals("Check 'user-agent' header exists in custom header list",
                client.findCustomHeader("user-agent"),
                -1);

        Envelope envelope = sensor.create(client.getId(), sendTime, Config.DATA_VERSION, data);
        sensor.send(client, envelope);

        assertEquals("Expect a Caliper event with custom headers to be sent",
                client.getStatistics().getMeasures().getCount(),
                client.getStatistics().getSuccessful().getCount());
    }

    @Test
    public void testRemoveCustomHeaderFromList() {
        // Prep envelope
        DateTime sendTime = new DateTime(DateTimeZone.UTC);
        List<Object> data = new ArrayList<>();
        data.add(event);

        List<HttpCustomHeader> headerList = new ArrayList<HttpCustomHeader>() {{
            add(new HttpCustomHeader("accept", "*/*"));
            add(new HttpCustomHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0"));
        }};

        client.setCustomHeaders(headerList);

        client.removeCustomHeader("user-agent");

        assertEquals("Check size of custom header list",
                client.getCustomHeaders().size(),
                1);
        assertEquals("Check 'user-agent' header deleted from custom header list",
                client.findCustomHeader("user-agent"),
                -1);

        Envelope envelope = sensor.create(client.getId(), sendTime, Config.DATA_VERSION, data);
        sensor.send(client, envelope);

        assertEquals("Expect a Caliper event without 'user-agent' header to be sent",
                client.getStatistics().getMeasures().getCount(),
                client.getStatistics().getSuccessful().getCount());
    }

    @Test
    public void testClearAllCustomHeaders() {
        // Prep envelope
        DateTime sendTime = new DateTime(DateTimeZone.UTC);
        List<Object> data = new ArrayList<>();
        data.add(event);

        List<HttpCustomHeader> headerList = new ArrayList<HttpCustomHeader>() {{
            add(new HttpCustomHeader("accept", "*/*"));
            add(new HttpCustomHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0"));
        }};

        client.setCustomHeaders(headerList);
        client.removeAllCustomHeaders();

        assertEquals("Check size of custom header list",
                client.getCustomHeaders().size(),
                0);

        Envelope envelope = sensor.create(client.getId(), sendTime, Config.DATA_VERSION, data);
        //sensor.send(client, envelope);
    }

    @Test
    public void testUpdateCustomHeader() {
        // Prep envelope
        DateTime sendTime = new DateTime(DateTimeZone.UTC);
        List<Object> data = new ArrayList<>();
        data.add(event);

        // Add additional headers
        client.setCustomHeader("Accept", "*/*");
        assertEquals("", client.getCustomHeaders().get(0).getValue(), "*/*");

        client.setCustomHeader("Accept", "application/json");
        assertEquals("", client.getCustomHeaders().get(0).getValue(), "application/json");

        Envelope envelope = sensor.create(client.getId(), sendTime, Config.DATA_VERSION, data);
        sensor.send(client, envelope);

        assertEquals("Expect a Caliper event with updated custom headers to be sent",
                client.getStatistics().getMeasures().getCount(),
                client.getStatistics().getSuccessful().getCount());
    }
}
