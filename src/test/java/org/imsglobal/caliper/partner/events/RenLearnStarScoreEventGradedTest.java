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

package org.imsglobal.caliper.partner.events;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.imsglobal.caliper.partner.events.RenLearnConstants.ACTIVITY_REF_ID;
import static org.imsglobal.caliper.partner.events.RenLearnConstants.APP_REN_LEAN_NAME;
import static org.imsglobal.caliper.partner.events.RenLearnConstants.BASE_IRI;
import static org.imsglobal.caliper.partner.events.RenLearnConstants.BASE_URN;
import static org.imsglobal.caliper.partner.events.RenLearnConstants.DISTRICT_REF_ID;
import static org.imsglobal.caliper.partner.events.RenLearnConstants.OBJECT_ID;
import static org.imsglobal.caliper.partner.events.RenLearnConstants.SCHOOL_REF_ID;
import static org.imsglobal.caliper.partner.events.RenLearnConstants.STUDENT_USER_REF_ID;
import static org.imsglobal.caliper.partner.events.RenLearnConstants.LAST_ATTEMPT_ID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.EntityType;
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
public class RenLearnStarScoreEventGradedTest {
    private JsonldContext context;
    private String id;
    private String member_id;
    private String score_id;
    private String score_name;
    private SoftwareApplication edApp;
    private Person actor;
    private Attempt object;
    private Assessment assignable;
    private Score score;
    private Membership membership;
    private GradeEvent event;

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.getDefault();

        id = "urn:uuid:a50ca17f-5971-47bb-8fca-4e6e6879001d";
        member_id = "urn:uuid:8f4fedbe-2227-415f-8553-40731a627171";
        score_id = "urn:uuid:2050e852-5edb-4743-92d1-b53466de3a5f";
        score_name = "star";

        actor = Person.builder().id(BASE_URN.concat(STUDENT_USER_REF_ID)).build();
        assignable = Assessment.builder().id(BASE_URN.concat(ACTIVITY_REF_ID)).build();

        object = Attempt.builder()
            .id(BASE_URN.concat(LAST_ATTEMPT_ID))
            .assignable(assignable)
            .assignee(Person.builder().id(actor.getId()).coercedToId(true).build())
            .count(35)
            .dateCreated(new DateTime(2016, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
            .endedAtTime(new DateTime(2016, 11, 15, 10, 55, 12, 0, DateTimeZone.UTC))
            .build();

        score = Score.builder()
            .id(score_id)
            .attempt(Attempt.builder().id(BASE_URN.concat(LAST_ATTEMPT_ID)).coercedToId(true).build())
            .maxScore(1400)
            .scoreGiven(500)
            .name(score_name)
            .scoredBy(SoftwareApplication.builder().id(BASE_IRI.concat(APP_REN_LEAN_NAME)).coercedToId(true).build())
            .comment("The Assessment score from RenLearn # "+OBJECT_ID+" taken on date/time for student " + STUDENT_USER_REF_ID)
            .dateCreated(new DateTime(2016, 11, 15, 10, 56, 0, 0, DateTimeZone.UTC))
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI.concat(APP_REN_LEAN_NAME)).coercedToId(true).build();

        membership = Membership.builder()
                .id(member_id)
                .organization(Organization.builder().id(BASE_URN.concat(SCHOOL_REF_ID)).type(EntityType.ORGANIZATION)
                    .subOrganizationOf(Organization.builder().id(BASE_URN.concat(DISTRICT_REF_ID)).type(EntityType.ORGANIZATION)
                            .build())
                    .build())
                .status(Status.ACTIVE)
                .role(Role.LEARNER)
                .build();

        // Build Outcome Event
        event = buildEvent(Action.GRADED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/hmh-renlearn/caliperEventStarScoreGraded.json");
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
            .generated(score)
            .edApp(edApp)
            .membership(membership)
            .eventTime(new DateTime(2016, 11, 15, 10, 57, 6, 0, DateTimeZone.UTC))
            .build();
    }
}