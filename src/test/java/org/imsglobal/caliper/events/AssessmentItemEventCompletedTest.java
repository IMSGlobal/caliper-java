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

package org.imsglobal.caliper.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.databind.JsonFilters;
import org.imsglobal.caliper.databind.JsonObjectMapper;
import org.imsglobal.caliper.databind.JsonSimpleFilterProvider;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.assessment.Assessment;
import org.imsglobal.caliper.entities.assessment.AssessmentItem;
import org.imsglobal.caliper.entities.assignable.Attempt;
import org.imsglobal.caliper.entities.lis.CourseSection;
import org.imsglobal.caliper.entities.lis.Membership;
import org.imsglobal.caliper.entities.lis.Role;
import org.imsglobal.caliper.entities.lis.Status;
import org.imsglobal.caliper.entities.response.FillinBlankResponse;
import org.imsglobal.caliper.entities.response.Response;
import org.imsglobal.caliper.entities.session.Session;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.ArrayList;
import java.util.List;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class AssessmentItemEventCompletedTest {
    private Person actor;
    private AssessmentItem item;
    private Attempt object;
    private Response generated;
    private List<String> values;
    private SoftwareApplication edApp;
    private CourseSection group;
    private Membership membership;
    private Session session;
    private AssessmentItemEvent event;

    private static final String BASE_IRI = "https://example.edu";

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        item = AssessmentItem.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/assess/1/items/3"))
            .name("Assessment Item 3")
            .isPartOf(Assessment.builder().id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/assess/1")).build())
            .build();

        object = Attempt.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/assess/1/items/3/users/554433/attempts/1"))
            .assignable(item)
            .actor(actor)
            .isPartOf(Attempt.builder().id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/assess/1/users/554433/attempts/1")).build())
            .count(1)
            .dateCreated(new DateTime(2016, 11, 15, 10, 15, 2, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 15, 2, 0, DateTimeZone.UTC))
            .endedAtTime(new DateTime(2016, 11, 15, 10, 15, 12, 0, DateTimeZone.UTC))
            .build();

        values = new ArrayList<String>();
        values.add("subject");
        values.add("object");
        values.add("predicate");

        generated = FillinBlankResponse.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/assess/1/items/3/users/554433/responses/1"))
            .attempt(Attempt.builder().id(object.getId()).build())
            .dateCreated(new DateTime(2016, 11, 15, 10, 15, 12, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 15, 2, 0, DateTimeZone.UTC))
            .endedAtTime(new DateTime(2016, 11, 15, 10, 15, 12, 0, DateTimeZone.UTC))
            .values(values)
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI).version("v2").build();

        group = CourseSection.builder().id(BASE_IRI.concat("/terms/201601/courses/7/sections/1"))
            .courseNumber("CPS 435-01")
            .academicSession("Fall 2016")
            .build();

        membership = Membership.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/rosters/1"))
            .member(actor)
            .organization(CourseSection.builder().id(group.getId()).build())
            .status(Status.ACTIVE)
            .role(Role.LEARNER)
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        session = Session.builder()
            .id(BASE_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259"))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 0, 0, 0, DateTimeZone.UTC))
            .build();

        // Build event
        event = buildEvent(Action.COMPLETED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        SimpleFilterProvider provider = JsonSimpleFilterProvider.create(JsonFilters.EXCLUDE_CONTEXT);
        ObjectMapper mapper = JsonObjectMapper.create(JsonInclude.Include.NON_EMPTY, provider);
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/caliperEventAssessmentItemCompleted.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void assessmentItemEventRejectsChangedSizeAction() {
        buildEvent(Action.CHANGED_SIZE);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build AssessmentItem event.
     *
     * @param action
     * @return event
     */
    private AssessmentItemEvent buildEvent(Action action) {
        return AssessmentItemEvent.builder()
            .actor(actor)
            .action(action.getValue())
            .object(object)
            .generated(generated)
            .eventTime(new DateTime(2016, 11, 15, 10, 15, 12, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .group(group)
            .membership(membership)
            .session(session)
            .build();
    }
}