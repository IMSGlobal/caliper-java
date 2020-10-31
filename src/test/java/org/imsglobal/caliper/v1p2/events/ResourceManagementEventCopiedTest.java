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

package org.imsglobal.caliper.v1p2.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.actions.CaliperAction;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.agent.Status;
import org.imsglobal.caliper.entities.resource.DigitalResource;
import org.imsglobal.caliper.entities.resource.DigitalResourceCollection;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.ResourceManagementEvent;
import org.imsglobal.caliper.profiles.CaliperProfile;
import org.imsglobal.caliper.profiles.Profile;
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
public class ResourceManagementEventCopiedTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private DigitalResource object;
    private DigitalResource generated;
    private DigitalResourceCollection collection;
    private CourseSection group;
    private Membership membership;
    private ResourceManagementEvent event;
    private Session session;
    private SoftwareApplication edApp;

    private static final String BASE_IRI = "https://example.edu";
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201801/courses/7/sections/1");

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value());
        id = "urn:uuid:d3543a73-e307-4190-a755-5ce7b3187bc5";
        actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        collection = DigitalResourceCollection.builder()
            .id(SECTION_IRI.concat("/resources/1"))
            .name("Course Assets")
            .isPartOf(CourseSection.builder().id(SECTION_IRI).build())
            .build();

        object = DigitalResource.builder()
            .id(SECTION_IRI.concat("/resources/1/syllabus.pdf"))
            .name("Course Syllabus")
            .creator(actor)
            .mediaType("application/pdf")
            .isPartOf(collection)
            .dateCreated(new DateTime(2018, 8, 2, 11, 32, 0, 0, DateTimeZone.UTC))
            .build();

        generated = DigitalResource.builder()
            .id(SECTION_IRI.concat("/resources/1/syllabus_copy.pdf"))
            .name("Course Syllabus (copy)")
            .creator(actor)
            .mediaType("application/pdf")
            .isPartOf(collection)
            .dateCreated(new DateTime(2018, 11, 15, 10, 05, 0, 0, DateTimeZone.UTC))
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI).coercedToId(true).build();

        group = CourseSection.builder()
            .id(SECTION_IRI)
            .courseNumber("CPS 435-01")
            .academicSession("Fall 2018")
            .build();

        membership = Membership.builder()
            .id(BASE_IRI.concat("/terms/201801/courses/7/sections/1/rosters/1"))
            .member(Person.builder().id(actor.getId()).coercedToId(true).build())
            .organization(CourseSection.builder().id(group.getId()).coercedToId(true).build())
            .status(Status.ACTIVE)
            .role(Role.INSTRUCTOR)
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        session = Session.builder()
            .id(BASE_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259"))
            .startedAtTime(new DateTime(2018, 11, 15, 10, 0, 0, 0, DateTimeZone.UTC))
            .build();

        // Build event
        event = buildEvent(Profile.RESOURCE_MANAGEMENT, Action.COPIED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p2/caliperEventResourceManagementCopied.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void resourceManagementEventRejectsLaunchedAction() {
        buildEvent(Profile.SURVEY, Action.LAUNCHED);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build ResourceManagementEvent.
     * @param profile, action
     * @return event
     */
    private ResourceManagementEvent buildEvent(CaliperProfile profile, CaliperAction action) {
        return ResourceManagementEvent.builder()
            .context(context)
            .profile(profile)
            .id(id)
            .actor(actor)
            .action(action)
            .object(object)
            .generated(generated)
            .eventTime(new DateTime(2018, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .group(group)
            .membership(membership)
            .session(session)
            .build();
    }
}