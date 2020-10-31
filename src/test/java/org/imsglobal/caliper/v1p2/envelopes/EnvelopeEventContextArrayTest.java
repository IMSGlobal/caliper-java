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

import java.util.List;

import org.imsglobal.caliper.Envelope;
import org.imsglobal.caliper.Sensor;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldArrayContext;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.resource.Document;
import org.imsglobal.caliper.events.Event;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class EnvelopeEventContextArrayTest {
	
	private Envelope envelope;
	
	private static final String BASE_IRI = "https://example.edu";

	@Before
    public void setUp() throws Exception {
		
		List<Object> contexts = Lists.newArrayList(
			new Object(){@JsonProperty String query = "http://schema.org/query";}, 
			CaliperJsonldContextIRI.V1P2.value());
		
		Event event = Event.builder()
				.context(JsonldArrayContext.create(contexts))
				.id("urn:uuid:3a648e68-f00d-4c08-aa59-8738e1884f2c")
				.actor(Person.builder().id(BASE_IRI.concat("/users/554433")).build())
				.action(Action.SEARCHED)
				.object(Document.builder().id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/resources/123")).build())
				.eventTime(new DateTime(2017, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
				.extensions(ImmutableMap.of("query", "Event or Entity"))
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

        String fixture = jsonFixture("fixtures/v1p2/caliperEnvelopeEventContextArray.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }
	
}
