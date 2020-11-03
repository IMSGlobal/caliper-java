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

package org.imsglobal.caliper.v1p2.envelopes;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.imsglobal.caliper.context.CaliperJsonldContextIRI.V1P2;

import org.imsglobal.caliper.Envelope;
import org.imsglobal.caliper.Sensor;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.agent.Status;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.ToolUseEvent;
import org.imsglobal.caliper.profiles.Profile;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

public class EnvelopeToolUseEventTest {
	
	private Envelope envelope;
	
	private static final String BASE_IRI = "https://example.edu";
    
	@Before
    public void setUp() throws Exception {
		
		ToolUseEvent event1 = createToolUseEvent();
								
		Sensor sensor = Sensor.create(BASE_IRI.concat("/sensors/1"));
		DateTime sendTime = new DateTime(2016, 11, 15, 11, 5, 1, 0, DateTimeZone.UTC);
		
		envelope = sensor.create(sensor.getId(), sendTime,
				V1P2.value(), Lists.newArrayList(event1));
	}
	
	@Test
    public void caliperEnvelopeSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(envelope);

        String fixture = jsonFixture("fixtures/v1p2/caliperEnvelopeToolUseEvent.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

	private ToolUseEvent createToolUseEvent() {
		return ToolUseEvent.builder()
				.context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value()))
				.id("urn:uuid:7e10e4f3-a0d8-4430-95bd-783ffae4d916")
				.profile(Profile.TOOL_USE)
				.actor(Person.builder().id(BASE_IRI.concat("/users/554433")).build())
				.action(Action.USED)
				.object(SoftwareApplication.builder().id("https://example.edu").build())
				.eventTime(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
				.edApp(SoftwareApplication.builder()
						.id("https://example.edu")
						.coercedToId(true)
						.build())
				.group(CourseSection.builder()
						.id(BASE_IRI.concat("/terms/201601/courses/7/sections/1"))
						.courseNumber("CPS 435-01")
						.academicSession("Fall 2016")
						.build())
				.membership(Membership.builder()
						.id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/rosters/1"))
						.member(Person.builder()
								.id(BASE_IRI.concat("/users/554433"))
								.coercedToId(true).build())
						.organization(CourseSection.builder()
								.id(BASE_IRI.concat("/terms/201601/courses/7/sections/1"))
								.coercedToId(true).build())
						.roles(Lists.newArrayList(Role.LEARNER))
						.status(Status.ACTIVE)
						.dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
						.build())
				.session(Session.builder()
						.id("https://example.edu/sessions/1f6442a482de72ea6ad134943812bff564a76259")
						.startedAtTime(new DateTime(2016, 11, 15, 10, 0, 0, 0, DateTimeZone.UTC))
						.build())
				.build();
		
	}
	
}
