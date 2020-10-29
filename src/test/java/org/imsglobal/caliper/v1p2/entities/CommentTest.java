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
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.resource.DigitalResource;
import org.imsglobal.caliper.entities.resource.DigitalResourceCollection;
import org.imsglobal.caliper.entities.survey.Comment;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;

@Category(org.imsglobal.caliper.UnitTest.class)
public class CommentTest {
	
    private Comment entity;
    private static final String BASE_IRI = "https://example.edu";
    
    @Before
    public void setUp() throws Exception {

        entity = Comment.builder()
            .context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value()))
            .id(BASE_IRI.concat("/terms/201801/courses/7/sections/1/assess/1/items/6/users/665544/responses/1/comment/1"))
            .commenter(Person.builder().id(BASE_IRI.concat("/users/554433")).build())
            .commentedOn(DigitalResource.builder()
            		.id(BASE_IRI.concat("/terms/201801/courses/7/sections/1/resources/1/syllabus.pdf"))
            		.name("Course Syllabus")
            		.mediaType("application/pdf")
            		.isPartOf(DigitalResourceCollection.builder()
            				.id(BASE_IRI.concat("/terms/201801/courses/7/sections/1/resources/1"))
            				.name("Course Assets")
            				.isPartOf(CourseSection.builder()
            						.id(BASE_IRI.concat("/terms/201801/courses/7/sections/1"))
            						.build())
            				.build())
            		.dateCreated(new DateTime(2018, 8, 2, 11, 32, 0, 0, DateTimeZone.UTC))
            		.build())
            .value("I like what you did here but you need to improve on...")
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))            
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p2/caliperEntityComment.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}
