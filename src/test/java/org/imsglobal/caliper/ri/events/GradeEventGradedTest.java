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

package org.imsglobal.caliper.ri.events;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Organization;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.agent.Status;
import org.imsglobal.caliper.entities.outcome.Score;
import org.imsglobal.caliper.entities.resource.Assessment;
import org.imsglobal.caliper.entities.resource.Attempt;
import org.imsglobal.caliper.events.GradeEvent;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

@Category(org.imsglobal.caliper.UnitTest.class)
public class GradeEventGradedTest {
    private JsonldContext context;
    private String id;
    private SoftwareApplication actor, edApp;
    private Person learner;
    private Attempt object;
    private Assessment assignable;
    private Score generated;
    private Membership membership;
    private GradeEvent event;

    private static final String BASE_URN = "urn:uuid:";
    private static final String BASE_IRI = "https://www.hmhco.com/";
    private static final String APP_NAME = "ReadingInventory";


    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.getDefault();

        id = "urn:uuid:a50ca17f-5971-47bb-8fca-4e6e6879001d";

        actor = SoftwareApplication.builder().id(BASE_IRI.concat(APP_NAME)).version("v2").build();
        learner = Person.builder().id(BASE_URN.concat("0f4fedbe-2227-415f-8553-40731a627171"))
                    .name("Casandra Rath").build();
        assignable = Assessment.builder().id(BASE_URN.concat("c050e852-5edb-4743-92d1-b53466de3a5f")).build();

        object = Attempt.builder()
            .id(BASE_URN.concat("c35603f7a3434e2cb74ef14d60e70f42"))
            .assignable(assignable)
            .assignee(learner)
            .count(100)
            .dateCreated(new DateTime(2016, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
            .endedAtTime(new DateTime(2016, 11, 15, 10, 55, 12, 0, DateTimeZone.UTC))
            .build();

        generated = Score.builder()
            .id(BASE_URN.concat("c050e852-5edb-4743-92d1-b53466de3a5f"))
            .attempt(Attempt.builder().id(object.getId()).coercedToId(true).build())
            .maxScore(1700)
            .scoreGiven(800)
            .scoredBy(SoftwareApplication.builder().id(BASE_IRI.concat(APP_NAME)).coercedToId(true).build())
            .comment("The lexile score from Test Quiz # c35603f7a3434e2cb74ef14d60e70f42 taken on date/time for student 0f4fedbe-2227-415f-8553-40731a627171")
            .dateCreated(new DateTime(2016, 11, 15, 10, 56, 0, 0, DateTimeZone.UTC))
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI.concat(APP_NAME)).version("v2").build();

        membership = Membership.builder()
                .id(learner.getId())
                .organization(Organization.builder().id(BASE_URN.concat("ab570d90-3791-4048-a5ee-759657ddccef")).type(EntityType.ORGANIZATION)
                    .subOrganizationOf(Organization.builder().id(BASE_URN.concat("ea827de8-0ce9-4fe4-a28c-eb388bd9eb92")).type(EntityType.ORGANIZATION)
                            .build())
                    .build())
                .status(Status.ACTIVE)
                .role(Role.LEARNER)
                .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
                .build();
        
        // Build Outcome Event
        event = buildEvent(Action.GRADED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/hmh-ri/caliperEventGradeGraded.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void gradeEventRejectsHidAction() {
        buildEvent(Action.HID);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build Grade event.
     * @param action
     * @return event
     */
    private GradeEvent buildEvent(Action action) {
        return GradeEvent.builder()
            .context(context)
            .id(id)
            .actor(actor)
            .action(action)
            .object(object)
            .generated(generated)
            .edApp(edApp)
            .membership(membership)
            .eventTime(new DateTime(2016, 11, 15, 10, 57, 6, 0, DateTimeZone.UTC))
            .build();
    }
}