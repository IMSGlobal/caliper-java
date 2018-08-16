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

package org.imsglobal.caliper.events.rimi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.CaliperEntityType;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.agent.*;
import org.imsglobal.caliper.entities.resource.Assessment;
import org.imsglobal.caliper.entities.resource.Attempt;
import org.imsglobal.caliper.entities.resource.MediaLocation;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.AssessmentEvent;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.lang.annotation.Target;
import java.util.UUID;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class AssessmentEventStartedTest {

    private final String userRefId = "0f4fedbe-2227-415f-8553-40731a627171";
    private final String sectionRefId = "128dedbe-9927-415f-8553-40731a627f893";
    private final String districtRefId = "7f7b4763-8b91-45ce-b7a4-e051fd79953c";
    private final String schoolRefId = "fbb79fa9-ce24-4979-beba-66c57395b0a1";

    private JsonldContext context;
    private String id;
    private Person actor;
    private Assessment object;
    private Attempt generated;
    private SoftwareApplication edApp;
    private CourseSection group;
    private Membership membership;
    private Session session;
    private AssessmentEvent event;
    private Target target;

    private static final String BASE_IRI = "https://https://www.hmhco.com/ed";
    private static final String SECTION_IRI = BASE_IRI.concat("/sections/");
    private static final String ORGANIZATION_IRI = BASE_IRI.concat("/organizations/");

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.getDefault();

        id = "urn:uuid:" + UUID.randomUUID();;  // random uuid generated per event I assume

        actor = Person.builder().id(BASE_IRI.concat("/users/" + userRefId)).build();
        //Person assignee = Person.builder().id(actor.getId()).coercedToId(true).build();

        object = Assessment.builder()
            .id("RI\\/HMH-RI") // TODO: This is identifer of RI/MI not of an Assessment
            .name("HMH-RI")
            .type(EntityType.ASSESSMENT) // TODO: This is a proposed correction from RIMI proposed SOFTWARE APPLICTION. Spec: "The string value MUST be set to the Term Assessment."
//            .dateToStartOn(new DateTime(2016, 11, 14, 5, 0, 0, 0, DateTimeZone.UTC))
//            .dateToSubmit(new DateTime(2016, 11, 18, 11, 59, 59, 0, DateTimeZone.UTC))
//            .maxAttempts(2)
//            .maxSubmits(2)
//            .maxScore(25)
//            .version("1.0")

            .build();

        // SPEC: A Caliper Attempt provides a count of the number of times an actor has interacted with an AssignableDigitalResource along with start time, end time and duration information. An Attempt is generated as the result of an action such as starting an Assessment.
//        generated = Attempt.builder()
//            .id(SECTION_IRI.concat("/assess/1/users/554433/attempts/1"))
//            .assignable(Assessment.builder().id(object.getId()).coercedToId(true).build())
//            .assignee(assignee)
//            .count(1)
//            .dateCreated(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
//            .startedAtTime(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
//            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI).version("v2").build();

        // Target SPEC: An Entity that represents a particular segment or location within the object.

//        group = CourseSection.builder()
//            .id(SECTION_IRI)
//            .courseNumber("CPS 435-01")
//            .academicSession("Fall 2016")
//            .build();

        Organization parentOrganization = Organization.builder().id(ORGANIZATION_IRI.concat(districtRefId)).type(EntityType.ORGANIZATION).build();

        membership = Membership.builder()
            .id(SECTION_IRI.concat(sectionRefId))
            //.organization(CourseSection.builder().id(group.getId()).coercedToId(true).build())
            .organization(Organization.builder().id(ORGANIZATION_IRI.concat(schoolRefId)).type(EntityType.ORGANIZATION)
                    .subOrganizationOf(Organization.builder().id(ORGANIZATION_IRI.concat(districtRefId)).type(EntityType.ORGANIZATION).build())
                    .build())
            .role(Role.LEARNER)
            .build();

//        session = Session.builder()
//            .id(BASE_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259"))
//            .startedAtTime(new DateTime(2016, 11, 15, 10, 0, 0, 0, DateTimeZone.UTC))
//            .build();

        event = buildEvent(Action.STARTED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/caliperEventAssessmentStarted.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void assessmentEventRejectsSearchedAction() {
        buildEvent(Action.SEARCHED);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build Assessment event
     * @param action
     * @return event
     */
    private AssessmentEvent buildEvent(Action action) {
        return AssessmentEvent.builder()
            .context(context)
            .id(id)
            .actor(actor)
            .action(action)
            .object(object)
            .generated(generated)
            .eventTime(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .group(group)
            .membership(membership)
            .session(session)
            .build();
    }
}