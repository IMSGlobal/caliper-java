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

package org.imsglobal.caliper.v1p1;

import org.imsglobal.caliper.CaliperSendable;
import org.imsglobal.caliper.Envelope;
import org.imsglobal.caliper.Sensor;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.clients.HttpClient;
import org.imsglobal.caliper.clients.HttpClientOptions;
import org.imsglobal.caliper.config.Config;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.agent.Status;
import org.imsglobal.caliper.entities.resource.WebPage;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.NavigationEvent;
import org.imsglobal.caliper.statistics.Statistics;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

// @Category(org.imsglobal.caliper.UnitTest.class)
public class SensorSendEventsTest {
    private static final String BASE_IRI = "https://example.edu";

    // private static final Logger log = LoggerFactory.getLogger(SensorSendEventsTest.class);

    @Test
    public void test() {

        // Initialize Sensor, Client and Requestor provisioned with Options
        Sensor sensor = Sensor.create(BASE_IRI.concat("/sensors/1"));
        HttpClientOptions opts = HttpClientOptions.builder().apiKey("869e5ce5-214c-4e85-86c6-b99e8458a592").build();
        HttpClient client = HttpClient.create(sensor.getId(), opts);
        sensor.registerClient(client);

        // Fire event test - Send 50 envelopes containing the above event
        for (int i = 0 ; i < 50 ; i++) {
            JsonldStringContext context = JsonldStringContext.create(CaliperJsonldContextIRI.V1P1.value());

            String id = "urn:id:" + UUID.randomUUID().toString();

            Person actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

            String currentLocation = BASE_IRI.concat("/terms/201601/courses/7/sections/1/pages/" + (i + 2));
            String previousLocation = BASE_IRI.concat("/terms/201601/courses/7/sections/1/pages/" + (i + 1));

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

            NavigationEvent event = NavigationEvent.builder()
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

            // Prep envelope
            DateTime sendTime = new DateTime(DateTimeZone.UTC);
            List<CaliperSendable> data = new ArrayList<>();
            data.add(event);

            Envelope envelope = sensor.create(client.getId(), sendTime, Config.DATA_VERSION, data);

            // Send envelope
            sensor.send(client, envelope);
        }

        // There should be two caliperEvents queued
        assertEquals("Expect fifty Caliper events to be sent", 50,
                sensor.getStatistics().get(sensor.getId()).getMeasures().getCount());

        //Statistic statistics = client.getStatistics().get("default");

        // TODO - Describes test - Send five describes

        // There should be four describes queued assertEquals("Expect five describes to be sent", 5,
        // Caliper.getStatistics().getDescribes().getCount());

        // There should be two message successfully sent
        int successes = sensor.getStatistics().get(sensor.getId()).getSuccessful().getCount();
        assertEquals("Expect fifty messages to be sent successfully", 50, successes);

        // There should be zero failures
        int failures = sensor.getStatistics().get(sensor.getId()).getFailed().getCount();
        assertEquals("Expect zero message failures to be sent", 0, failures);
    }
}