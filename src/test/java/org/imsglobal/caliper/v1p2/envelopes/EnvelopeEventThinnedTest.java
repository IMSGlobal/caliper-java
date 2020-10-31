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
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.resource.Page;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.NavigationEvent;
import org.imsglobal.caliper.profiles.Profile;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

public class EnvelopeEventThinnedTest {
	
	private Envelope envelope;
	
	private static final String BASE_IRI = "https://example.edu";
    
	@Before
    public void setUp() throws Exception {
		
		NavigationEvent event = NavigationEvent.builder()
				.context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value()))
				.id("urn:uuid:71657137-8e6e-44f8-8499-e1c3df6810d2")
				.profile(Profile.GENERAL)
				.actor(Person.builder().id(BASE_IRI.concat("/users/554433")).coercedToId(true).build())
				.action(Action.NAVIGATED_TO)
				.object(Page.builder().id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/pages/2")).coercedToId(true).build())
				.eventTime(new DateTime(2017, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
				.referrer(Page.builder().id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/pages/1")).coercedToId(true).build())
				.edApp(SoftwareApplication.builder().id(BASE_IRI).coercedToId(true).build())
				.group(CourseSection.builder().id(BASE_IRI.concat("/terms/201601/courses/7/sections/1")).coercedToId(true).build())
				.membership(Membership.builder().id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/rosters/1")).coercedToId(true).build())
				.session(Session.builder().id(BASE_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259")).coercedToId(true).build())
				.build();
						
		Sensor sensor = Sensor.create(BASE_IRI.concat("/sensors/1"));
		DateTime sendTime = new DateTime(2017, 11, 15, 10, 15, 1, 0, DateTimeZone.UTC);
		
		envelope = sensor.create(sensor.getId(), sendTime,
				V1P2.value(), Lists.newArrayList(event));
	}
	
	@Test
    public void caliperEnvelopeSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(envelope);

        String fixture = jsonFixture("fixtures/v1p2/caliperEnvelopeEventThinned.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

}


