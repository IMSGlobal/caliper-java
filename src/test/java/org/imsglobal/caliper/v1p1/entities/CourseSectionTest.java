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
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseOffering;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class CourseSectionTest {
    private CourseSection entity;
    private CourseOffering courseOffering;

    private static final String BASE_IRI = "https://example.edu";

    @Before
    public void setUp() throws Exception {

        courseOffering = CourseOffering.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7"))
            .courseNumber("CPS 435")
            .build();

        entity = CourseSection.builder()
            .context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P1.value()))
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1"))
            .academicSession("Fall 2016")
            .courseNumber("CPS 435-01")
            .name("CPS 435 Learning Analytics, Section 01")
            .category("seminar")
            .subOrganizationOf(courseOffering)
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p1/caliperEntityCourseSection.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}