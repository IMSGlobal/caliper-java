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

package org.imsglobal.caliper.v1p2.entities;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseOffering;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.resource.Forum;
import org.imsglobal.caliper.entities.resource.Thread;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

@Category(org.imsglobal.caliper.UnitTest.class)
public class ForumTest {
	
    private Forum entity;
    private static final String BASE_IRI = "https://example.edu";
    
    @Before
    public void setUp() throws Exception {

        entity = Forum.builder()
            .context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value()))
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/forums/1"))
            .name("Caliper Forum")
            .items(Lists.newArrayList(
            		Thread.builder()
            			.id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/forums/1/topics/1"))
            			.name("Caliper Information Model")
            			.dateCreated(new DateTime(2016, 11, 1, 9, 30, 0, 0, DateTimeZone.UTC))
            			.build(),
            		Thread.builder()
            			.id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/forums/1/topics/2"))
            			.name("Caliper Sensor API")
            			.dateCreated(new DateTime(2016, 11, 1, 9, 30, 0, 0, DateTimeZone.UTC))
            			.build(),
            		Thread.builder()
            			.id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/forums/1/topics/3"))
            			.name("Caliper Certification")
            			.dateCreated(new DateTime(2016, 11, 1, 9, 30, 0, 0, DateTimeZone.UTC))
            			.build()
            		))
            .isPartOf(CourseSection.builder()
            		.id(BASE_IRI.concat("/terms/201601/courses/7/sections/1"))
            		.subOrganizationOf(CourseOffering.builder()
            				.id(BASE_IRI.concat("/terms/201601/courses/7"))
            				.build())
            		.build())
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .dateModified(new DateTime(2016, 9, 2, 11, 30, 0, 0, DateTimeZone.UTC))            
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p2/caliperEntityForum.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}
