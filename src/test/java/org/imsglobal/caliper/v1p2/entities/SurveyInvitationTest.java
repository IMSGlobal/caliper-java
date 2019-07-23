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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.resource.SurveyInvitation;
import org.imsglobal.caliper.entities.survey.Survey;
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
public class SurveyInvitationTest {
    private Person rater;
    private Survey survey;
    private SurveyInvitation entity;

    private static final String BASE_IRI = "https://example.edu";

    @Before
    public void setUp() throws Exception {

        rater = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        survey = Survey.builder().id(BASE_IRI.concat("/survey/1")).build();

        entity = SurveyInvitation.builder()
            .context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value()))
            .id(BASE_IRI.concat("/surveys/100/invitations/users/112233"))
            .sentCount(1)
            .dateSent(new DateTime(2018, 11, 15, 10, 5, 0, DateTimeZone.UTC))
            .rater(rater)
            .survey(survey)
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, DateTimeZone.UTC))
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p2/caliperEntitySurveyInvitation.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}