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

package org.imsglobal.caliper.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.databind.JxnCoercibleSimpleModule;
import org.imsglobal.caliper.entities.resource.Assessment;
import org.imsglobal.caliper.entities.resource.AssessmentItem;
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
public class AssessmentTest {
    private Assessment entity;

    private static final String BASE_IRI = "https://example.edu";
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201601/courses/7/sections/1");

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        List<AssessmentItem> items = Lists.newArrayList();
        items.add(AssessmentItem.builder().id(SECTION_IRI.concat("/assess/1/items/1")).build());
        items.add(AssessmentItem.builder().id(SECTION_IRI.concat("/assess/1/items/2")).build());
        items.add(AssessmentItem.builder().id(SECTION_IRI.concat("/assess/1/items/3")).build());

        entity = Assessment.builder()
            .context(JsonldStringContext.getDefault())
            .id(SECTION_IRI.concat("/assess/1"))
            .name("Quiz One")
            .items(items)
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .dateModified(new DateTime(2016, 9, 2, 11, 30, 0, 0, DateTimeZone.UTC))
            .datePublished(new DateTime(2016, 8, 15, 9, 30, 0, 0, DateTimeZone.UTC))
            .dateToActivate(new DateTime(2016, 8, 16, 5, 0, 0, 0, DateTimeZone.UTC))
            .dateToShow(new DateTime(2016, 8, 16, 5, 0, 0, 0, DateTimeZone.UTC))
            .dateToStartOn(new DateTime(2016, 8, 16, 5, 0, 0, 0, DateTimeZone.UTC))
            .dateToSubmit(new DateTime(2016, 9, 28, 11, 59, 59, 0, DateTimeZone.UTC))
            .maxAttempts(2)
            .maxSubmits(2)
            .maxScore(15.0)
            .version("1.0")
            .build();
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        SimpleFilterProvider provider = new SimpleFilterProvider()
            .setFailOnUnknownId(true);

        ObjectMapper mapper = new ObjectMapper()
            .setDateFormat(new ISO8601DateFormat())
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .setFilterProvider(provider)
            .registerModules(new JodaModule(), new JxnCoercibleSimpleModule());

        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/caliperEntityAssessment.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}

/**
{
    "@context": "http://purl.imsglobal.org/ctx/caliper/v1p1",
    "id": "https://example.edu/terms/201601/courses/7/sections/1/assess/1",
    "type": "Assessment",
    "name": "Quiz One",
    "items": [
    {
    "id": "https://example.edu/terms/201601/courses/7/sections/1/assess/1/items/1",
    "type": "AssessmentItem"
    },
    {
    "id": "https://example.edu/terms/201601/courses/7/sections/1/assess/1/items/2",
    "type": "AssessmentItem"
    },
    {
    "id": "https://example.edu/terms/201601/courses/7/sections/1/assess/1/items/3",
    "type": "AssessmentItem"
    }
    ],
    "dateCreated": "2016-08-01T06:00:00.000Z",
    "dateModified": "2016-09-02T11:30:00.000Z",
    "datePublished": "2016-08-15T09:30:00.000Z",
    "dateToActivate": "2016-08-16T05:00:00.000Z",
    "dateToShow": "2016-08-16T05:00:00.000Z",
    "dateToStartOn": "2016-08-16T05:00:00.000Z",
    "dateToSubmit": "2016-09-28T11:59:59.000Z",
    "maxAttempts": 2,
    "maxScore": 15.0,
    "maxSubmits": 2,
    "version": "1.0"
    }
*/