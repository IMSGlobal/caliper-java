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

package org.imsglobal.caliper.v1p1.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.CaliperJsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CaliperAgent;
import org.imsglobal.caliper.entities.agent.CourseOffering;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.resource.CaliperDigitalResource;
import org.imsglobal.caliper.entities.resource.DigitalResourceCollection;
import org.imsglobal.caliper.entities.resource.VideoObject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class DigitalResourceCollectionTest {
    private DigitalResourceCollection collection;
    private CourseOffering course;
    private CourseSection section;
    private Person actor;
    private List<CaliperAgent> creators;
    private List<CaliperDigitalResource> resources;
    private List<String> keywords;
    private DigitalResourceCollection entity;
    private VideoObject video1;
    private VideoObject video2;

    private static final String BASE_IRI = "https://example.edu";
    private static final String COURSE_IRI = BASE_IRI.concat("/terms/201601/courses/7");
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201601/courses/7/sections/1");

    @Before
    public void setUp() throws Exception {
        
        keywords = Lists.newArrayList();
        keywords.add("collection");
        keywords.add("videos");

        course = CourseOffering.builder()
            .id(COURSE_IRI)
            .build();

        section = CourseSection.builder()
            .id(SECTION_IRI)
            .subOrganizationOf(course)
            .build();

        video1 = VideoObject.builder()
            .id(BASE_IRI.concat("/videos/1225"))
            .mediaType("video/ogg")
            .name("Introduction to IMS Caliper")
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .duration("PT1H12M27S")
            .version("1.1")
            .build();

        video2 = VideoObject.builder()
            .id(BASE_IRI.concat("/videos/5629"))
            .mediaType("video/ogg")
            .name("IMS Caliper Activity Profiles")
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .duration("PT55M13S")
            .version("1.1.1")
            .build();

        resources = Lists.newArrayList();
        resources.add(video1);
        resources.add(video2);

        entity = DigitalResourceCollection.builder()
            .context(JsonldStringContext.create(CaliperJsonldContext.V1P1.value()))
            .id(SECTION_IRI.concat("/resources/2"))
            .name("Video Collection")
            .keywords(keywords)
            .items(resources)
            .isPartOf(section)
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .dateModified(new DateTime(2016, 9, 2, 11, 30, 0, 0, DateTimeZone.UTC))
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p1/caliperEntityDigitalResourceCollection.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}