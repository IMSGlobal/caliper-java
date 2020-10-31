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

import org.imsglobal.caliper.CaliperSendable;
import org.imsglobal.caliper.Envelope;
import org.imsglobal.caliper.Sensor;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldArrayContext;
import org.imsglobal.caliper.context.JsonldObjectContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.Entity;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.agent.Status;
import org.imsglobal.caliper.entities.annotation.BookmarkAnnotation;
import org.imsglobal.caliper.entities.resource.DigitalResource;
import org.imsglobal.caliper.entities.resource.Document;
import org.imsglobal.caliper.entities.resource.WebPage;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.AnnotationEvent;
import org.imsglobal.caliper.events.Event;
import org.imsglobal.caliper.events.NavigationEvent;
import org.imsglobal.caliper.events.ViewEvent;
import org.imsglobal.caliper.profiles.Profile;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class EnvelopeTermActionTest {
	
	private Envelope envelope;
	
	private static final String BASE_IRI = "https://example.edu";
	private static final String PERSON_IRI = "http://purl.imsglobal.org/caliper/Person";
	private static final String ENTITY_IRI = "http://purl.imsglobal.org/caliper/Entity";
	private static final String ID_BASE = "urn:uuid:7d757c17-2f2f-4e39-9d50-fd83494f";
	@Before
    public void setUp() throws Exception {
		
		List<CaliperSendable> events = Lists.newArrayList();
		
		int idx = 0;
		
		for (Action action : Action.values()) {
			if(action == Action.REPLIED) {continue;}
			events.add(Event.builder()
					.context(JsonldStringContext.create(V1P2.value()))
					.id(ID_BASE.concat(Strings.padStart(Integer.toString(++idx), 4, '0')))
					.actor(Person.builder().id(PERSON_IRI).coercedToId(true).build())
					.action(action)
					.object(Entity.builder().id(ENTITY_IRI).coercedToId(true).build())
					.eventTime(new DateTime(2020, 1, 22, 10, 5, 0, 0, DateTimeZone.UTC))
					.build());
		}
			
		Sensor sensor = Sensor.create(BASE_IRI.concat("/sensors/1"));
		DateTime sendTime = new DateTime(2020, 1, 22, 10, 5, 0, 0, DateTimeZone.UTC);
		
		envelope = sensor.create(sensor.getId(), sendTime,
				V1P2.value(), events);
	}
	
	@Test
    public void caliperEnvelopeSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(envelope);

        String fixture = jsonFixture("fixtures/v1p2/caliperEnvelopeTermAction.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }
	
}
