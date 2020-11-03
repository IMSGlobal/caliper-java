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
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseOffering;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.Status;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

public class EnvelopeTermLisRoleTest {
	
	private Envelope envelope;
	
	private static final String BASE_IRI = "https://example.edu";
	private static final String PERSON_IRI = "http://purl.imsglobal.org/caliper/Person";
	private static final String ORG_IRI = "http://purl.imsglobal.org/caliper/CourseOffering";
	private static final String ID = "http://purl.imsglobal.org/caliper/Membership";
	
	@Before
    public void setUp() throws Exception {
		
		List<CaliperSendable> entities = Lists.newArrayList();
				
		for (Role role : Role.values()) {			
			List<Role> roles = Lists.newArrayList();
			String value = role.value();
			if(value.contains("#")) {
				roles.add(Role.fromValue(value.substring(0, value.indexOf('#'))));			
			} 
			roles.add(role);
			
			entities.add(Membership.builder()
					.context(JsonldStringContext.create(V1P2.value()))
					.id(ID)
					.member(Person.builder().id(PERSON_IRI).coercedToId(true).build())
					.organization(CourseOffering.builder().id(ORG_IRI).coercedToId(true).build())
					.status(Status.ACTIVE)
					.roles(roles)
					.build());
		}
			
		Sensor sensor = Sensor.create(BASE_IRI.concat("/sensors/1"));
		DateTime sendTime = new DateTime(2020, 1, 22, 10, 5, 0, 0, DateTimeZone.UTC);
		
		envelope = sensor.create(sensor.getId(), sendTime,
				V1P2.value(), entities);
	}
	
	@Test
    public void caliperEnvelopeSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(envelope);

        String fixture = jsonFixture("fixtures/v1p2/caliperEnvelopeTermLISRole.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }
	
}
