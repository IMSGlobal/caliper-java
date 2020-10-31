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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldStringContext;
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

import java.util.Map;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class AssessmentItemExtendedTest {
    private AssessmentItem entity;

    private static final String BASE_IRI = "https://example.edu";
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201601/courses/7/sections/1");

    @Before
    public void setUp() throws Exception {

        // Parent
        Assessment assessment = Assessment.builder()
            .id("https://example.edu/terms/201601/courses/7/sections/1/assess/1")
            .build();

        // Add AssessmentItem Extensions
        Question question = Question.create();
        Map<String, Object> extensions = Maps.newHashMap();
        extensions.put("questionType", question.getQuestionType());
        extensions.put("questionText", question.getQuestionText());
        extensions.put("correctResponse", question.getCorrectResponse());

        entity = AssessmentItem.builder()
            .context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P1.value()))
            .id(SECTION_IRI.concat("/assess/1/items/3"))
            .isPartOf(assessment)
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .datePublished(new DateTime(2016, 8, 15, 9, 30, 0, 0, DateTimeZone.UTC))
            .maxScore(1.0)
            .maxSubmits(2)
            .isTimeDependent(false)
            .extensions(extensions)
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p1/caliperEntityAssessmentItemExtended.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }

    /**
     * Question extension
     */
    static class Question {
        private String questionType;
        private String questionText;
        private String correctResponse;

        /**
         * Constructor
         */
        private Question() {
            this.questionType = "Dichotomous";
            this.questionText = "Is a Caliper SoftwareApplication a subtype of Caliper Agent?";
            this.correctResponse = "yes";
        }

        /**
         * Get the question type.
         * @return the questionType
         */
        @JsonProperty("questionType")
        private String getQuestionType() {
            return questionType;
        }

        /**
         * Get the question text.
         * @return the questionText
         */
        @JsonProperty("questionText")
        private String getQuestionText() {
            return questionText;
        }

        /**
         * Get the correct response.
         * @return the correctResponse
         */
        @JsonProperty("correctResponse")
        private String getCorrectResponse() {
            return correctResponse;
        }

        /**
         * Factory method
         * @return new Question
         */
        private static Question create() {
            return new Question();
        }
    }
}
