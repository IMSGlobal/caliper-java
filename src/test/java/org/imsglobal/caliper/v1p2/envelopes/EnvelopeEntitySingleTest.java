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
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.resource.DigitalResource;
import org.imsglobal.caliper.entities.resource.DigitalResourceCollection;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

public class EnvelopeEntitySingleTest {
	
	private Envelope envelope;
	
	private static final String BASE_IRI = "https://example.edu";
    
	@Before
    public void setUp() throws Exception {
		
		DigitalResource entity = DigitalResource.builder()
				.context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value()))
				.id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/resources/1/syllabus.pdf"))
				.name("Course Syllabus")
				.storageName("fall-2016-syllabus.pdf")
				.mediaType("application/pdf")				
				.creators(Lists.newArrayList(
						Person.builder().id(BASE_IRI.concat("/users/223344")).build()))
				.isPartOf(DigitalResourceCollection.builder()
						.id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/resources/1"))
						.name("Course Assets")
						.isPartOf(CourseSection.builder()
								.id(BASE_IRI.concat("/terms/201601/courses/7/sections/1"))
								.build())
						.build())
				.dateCreated(new DateTime(2016, 8, 2, 11, 32, 0, 0, DateTimeZone.UTC))
				.build();
						
		Sensor sensor = Sensor.create(BASE_IRI.concat("/sensors/1"));
		DateTime sendTime = new DateTime(2016, 11, 15, 11, 5, 1, 0, DateTimeZone.UTC);
		
		envelope = sensor.create(sensor.getId(), sendTime,
				V1P2.value(), Lists.newArrayList(entity));
	}
	
	@Test
    public void caliperEnvelopeSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(envelope);

        String fixture = jsonFixture("fixtures/v1p2/caliperEnvelopeEntitySingle.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

}


