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

import org.imsglobal.caliper.CaliperSendable;
import org.imsglobal.caliper.Envelope;
import org.imsglobal.caliper.Sensor;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.Entity;
import org.imsglobal.caliper.identifiers.SystemIdentifier;
import org.imsglobal.caliper.identifiers.SystemIdentifierType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class EnvelopeTermSystemIdentifierTest {
	
	private Envelope envelope;
	
	private static final String BASE_IRI = "https://example.edu";
	private static final String CASE_URI = "https://salt-demo.edplancms.com/ims/case/v1p0/CFItems/04bc8e7e-36d3-4078-bed5-e33e9de98d5c";
		
	@Before
    public void setUp() throws Exception {
			
		ImmutableList<CaliperSendable> entities = ImmutableList.<CaliperSendable>builder()                
                .add(buildEntity("root", SystemIdentifierType.ACCOUNT_USER_NAME))
                .add(buildEntity(CASE_URI, SystemIdentifierType.CASE_ITEM_URI))
                .add(buildEntity("jane@example.edu", SystemIdentifierType.EMAIL_ADDRESS))
                .add(buildEntity("example.edu:SI182-F16", SystemIdentifierType.LIS_SOURCED_ID))
                .add(buildEntity("example.edu:CI182-S16", SystemIdentifierType.LTI_CONTEXT_ID))
                .add(buildEntity("deployment_id_1", SystemIdentifierType.LTI_DEPLOYMENT_ID))
                .add(buildEntity("platform_id_1", SystemIdentifierType.LTI_PLATFORM_ID))
                .add(buildEntity("tool_id_1", SystemIdentifierType.LTI_TOOL_ID))
                .add(buildEntity("user_id_1", SystemIdentifierType.LTI_USER_ID))
                .add(buildEntity("one_roster_id_1", SystemIdentifierType.ONE_ROSTER_SOURCED_ID))
                .add(buildEntity("other_1", SystemIdentifierType.OTHER))
                .add(buildEntity("sis_sourcedid_1", SystemIdentifierType.SIS_SOURCED_ID))
                .add(buildEntity("system_id_1", SystemIdentifierType.SYSTEM_ID))
                .build();
		
			
		Sensor sensor = Sensor.create(BASE_IRI.concat("/sensors/1"));
		DateTime sendTime = new DateTime(2020, 1, 22, 10, 5, 0, 0, DateTimeZone.UTC);
		
		envelope = sensor.create(sensor.getId(), sendTime, V1P2.value(), entities);
	}
	
	private Entity buildEntity(String identifier, SystemIdentifierType type) {
		return Entity.builder()
				.context(JsonldStringContext.create(V1P2.value()))
				.id("http://purl.imsglobal.org/caliper/Entity")
				.otherIdentifiers(Lists.newArrayList(
						SystemIdentifier.builder()
							.identifier(identifier)
							.identifierType(type)
							.build()
						))
				.build();
	}
	
	@Test
    public void caliperEnvelopeSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(envelope);

        String fixture = jsonFixture("fixtures/v1p2/caliperEnvelopeTermSystemIdentifierType.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }
	
}
