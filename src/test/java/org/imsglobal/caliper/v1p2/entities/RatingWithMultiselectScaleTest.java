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

import org.fest.util.Lists;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.question.RatingScaleQuestion;
import org.imsglobal.caliper.entities.resource.DigitalResourceCollection;
import org.imsglobal.caliper.entities.scale.MultiselectScale;
import org.imsglobal.caliper.entities.survey.Rating;
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
public class RatingWithMultiselectScaleTest {
    private Rating entity;

    private static final String BASE_IRI = "https://example.edu";
    @Before
    public void setUp() throws Exception {
       
        entity = Rating.builder()
            .context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value()))
            .id(BASE_IRI.concat("/users/554433/rating/2"))
            .rater(Person.builder().id(BASE_IRI.concat("/users/554433")).build())
            .rated(DigitalResourceCollection.builder()
            		.id(BASE_IRI.concat("/terms/201801/courses/7/sections/1/resources/1"))
            		.name("Course Assets")            		
    				.isPartOf(CourseSection.builder()
    						.id(BASE_IRI.concat("/terms/201801/courses/7/sections/1"))
    						.build())
            		.build())
            .question(RatingScaleQuestion.builder()
            		.id(BASE_IRI.concat("/question/3"))
            		.questionPosed("How do you feel about this content? (select one or more)")
            		.scale(MultiselectScale.builder()
            				.id(BASE_IRI.concat("/scale/3"))
            				.scalePoints(5)
            				.itemLabels(Lists.newArrayList("😁", "😀", "😐", "😕", "😞"))
            				.itemValues(Lists.newArrayList("superhappy", "happy", "indifferent", "unhappy", "disappointed"))
            				.isOrderedSelection(false)
            				.minSelections(1)
            				.maxSelections(5)
            				.build())            		
            		.build())     
            .selections(Lists.newArrayList("superhappy", "disappointed"))
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p2/caliperEntityRatingWithMultiselectScale.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}