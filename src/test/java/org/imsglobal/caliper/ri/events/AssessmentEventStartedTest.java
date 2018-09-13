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
import org.imsglobal.caliper.entities.resource.Assessment;
import org.imsglobal.caliper.entities.resource.Attempt;
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

@Category(org.imsglobal.caliper.UnitTest.class)
public class AssessmentEventStartedTest {
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

    
    private static final String BASE_URN = "urn:uuid:";
    private static final String BASE_IRI = "https://www.hmhco.com/";
    private static final String APP_NAME = "ReadingInventory";

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.getDefault();

        id = BASE_URN + "27734504-068d-4596-861c-2315be33a2a2";

        actor = Person.builder().id(BASE_URN.concat("0f4fedbe-2227-415f-8553-40731a627171"))
                .name("Casandra Rath").build();
        Person assignee = Person.builder().id(actor.getId()).coercedToId(true).build();

        object = Assessment.builder()
            .id(BASE_URN.concat("c050e852-5edb-4743-92d1-b53466de3a5f"))
            .name(APP_NAME)
            .dateToStartOn(new DateTime(2016, 11, 14, 5, 0, 0, 0, DateTimeZone.UTC))
            .dateToSubmit(new DateTime(2016, 11, 18, 11, 59, 59, 0, DateTimeZone.UTC))
            .maxAttempts(1)
            .maxSubmits(1)
            .maxScore(25.0)
            .version("1.0")
            .build();

        generated = Attempt.builder()
            .id(BASE_URN.concat("c35603f7a3434e2cb74ef14d60e70f42"))
            .assignable(Assessment.builder().id(object.getId()).coercedToId(true).build())
            .assignee(assignee)
            .count(1)
            .dateCreated(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI.concat(APP_NAME)).version("v2").build();

        membership = Membership.builder()
            .id(actor.getId())
            .organization(Organization.builder().id(BASE_URN.concat("ab570d90-3791-4048-a5ee-759657ddccef")).type(EntityType.ORGANIZATION)
                .subOrganizationOf(Organization.builder().id(BASE_URN.concat("ea827de8-0ce9-4fe4-a28c-eb388bd9eb92")).type(EntityType.ORGANIZATION)
                        .build())
                .build())
            .status(Status.ACTIVE)
            .role(Role.LEARNER)
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        event = buildEvent(Action.STARTED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/hmh-ri/caliperEventAssessmentStarted.json");
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