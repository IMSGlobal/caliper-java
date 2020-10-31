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

package org.imsglobal.caliper.v1p1.events;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

import java.util.List;
import java.util.Map;

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
import org.imsglobal.caliper.entities.resource.Document;
import org.imsglobal.caliper.entities.resource.LtiMessageType;
import org.imsglobal.caliper.entities.session.LtiSession;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.ViewEvent;
import org.imsglobal.caliper.lti.ContextClaim;
import org.imsglobal.caliper.lti.CustomClaim;
import org.imsglobal.caliper.lti.LaunchPresentationClaim;
import org.imsglobal.caliper.lti.LisClaim;
import org.imsglobal.caliper.lti.MessageParameterSession;
import org.imsglobal.caliper.lti.ResourceLinkClaim;
import org.imsglobal.caliper.lti.ToolPlatformClaim;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Category(org.imsglobal.caliper.UnitTest.class)
public class ViewEventViewedDocumentFedSessionTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private Document object;
    private SoftwareApplication edApp;
    private CourseSection group;
    private Membership membership;
    private LtiSession federatedSession;
    private Session session;
    private ViewEvent event;

    private static final String BASE_IRI_COM = "https://example.com";
    private static final String BASE_IRI_EDU = "https://example.edu";

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContextIRI.V1P1.value());

        id = "urn:uuid:4be6d29d-5728-44cd-8a8f-3d3f07e46b61";

        actor = Person.builder().id(BASE_IRI_EDU.concat("/users/554433")).build();
        Person actorToId = Person.builder().id(actor.getId()).coercedToId(true).build();

        object = Document.builder()
            .id(BASE_IRI_COM.concat("/lti/reader/202.epub"))
            .mediaType("application/epub+zip")
            .name("Caliper Case Studies")
            .dateCreated(new DateTime(2018, 8, 1, 9, 0, 0, 0, DateTimeZone.UTC))
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI_COM).coercedToId(true).build();

        Map<String, Object> groupExtensions = Maps.newHashMap();
        groupExtensions.put("edu_example_course_section_instructor", "https://example.edu/faculty/1234");

        group = CourseSection.builder()
            .id(BASE_IRI_EDU.concat("/terms/201801/courses/7/sections/1"))
            .extensions(groupExtensions)
            .build();

        membership = Membership.builder()
            .id(BASE_IRI_EDU.concat("/terms/201801/courses/7/sections/1/rosters/1"))
            .member(actorToId)
            .organization(CourseSection.builder().id(group.getId()).coercedToId(true).build())
            .status(Status.ACTIVE)
            .role(Role.LEARNER)
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        session = Session.builder()
            .id(BASE_IRI_COM.concat("/sessions/c25fd3da-87fa-45f5-8875-b682113fa5ee"))
            .dateCreated(new DateTime(2018, 11, 15, 10, 20, 0, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2018, 11, 15, 10, 20, 0, 0, DateTimeZone.UTC))
            .build();

        Map<String, Object> messageParameters = Maps.newHashMap();
        messageParameters.putAll(getMessageParameters());

        federatedSession = LtiSession.builder()
            .id(BASE_IRI_EDU.concat("/lti/sessions/b533eb02823f31024e6b7f53436c42fb99b31241"))
            .user(actor)
            .messageParameters(messageParameters)
            .dateCreated(new DateTime(2018, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2018, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .build();

        // Build event
        event = buildEvent(Action.VIEWED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p1/caliperEventViewViewedDocumentFedSession.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void navigationEventRejectsViewedAction() {
        buildEvent(Action.NAVIGATED_TO);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build Navigation event
     *
     * @param action
     * @return event
     */
    private ViewEvent buildEvent(CaliperAction action) {
        return ViewEvent.builder()
            .context(context)
            .id(id)
            .actor(actor)
            .action(action)
            .object(object)
            .eventTime(new DateTime(2018, 11, 15, 10, 20, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .group(group)
            .membership(membership)
            .session(session)
            .federatedSession(federatedSession)
            .build();
    }

    /**
     * LTI message parameters
     *
     * @return messageParameters
     */
    private Map<String, Object> getMessageParameters() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("iss", "https://example.edu");
        params.put("sub", "https://example.edu/users/554433");

        List<String> auds = Lists.newArrayList();
        auds.add("https://example.com/lti/tool");

        params.put("aud", auds);
        params.put("exp", 1510185728);
        params.put("iat", 1510185228);
        params.put("azp", "962fa4d8-bcbf-49a0-94b2-2de05ad274af");
        params.put("nonce", "fc5fdc6d-5dd6-47f4-b2c9-5d1216e9b771");
        params.put("name", "Ms Jane Marie Doe");
        params.put("given_name", "Jane");
        params.put("family_name", "Doe");
        params.put("middle_name", "Marie");
        params.put("picture", "https://example.edu/jane.jpg");
        params.put("email", "jane@example.edu");
        params.put("locale", "en-US");
        params.put("https://purl.imsglobal.org/spec/lti/claim/deployment_id", "07940580-b309-415e-a37c-914d387c1150");
        params.put("https://purl.imsglobal.org/spec/lti/claim/message_type", LtiMessageType.LTI_RESOURCE_LINK_REQUEST);
        params.put("https://purl.imsglobal.org/spec/lti/claim/version", "1.3.0");

        List<String> roles = Lists.newArrayList();
        roles.add("http://purl.imsglobal.org/vocab/lis/v2/institution/person#Student");
        roles.add("http://purl.imsglobal.org/vocab/lis/v2/membership#Learner");
        roles.add("http://purl.imsglobal.org/vocab/lis/v2/membership#Mentor");

        params.put("https://purl.imsglobal.org/spec/lti/claim/roles", roles);

        List<String> mentors = Lists.newArrayList();
        mentors.add("http://purl.imsglobal.org/vocab/lis/v2/institution/person#Administrator");

        params.put("https://purl.imsglobal.org/spec/lti/claim/role_scope_mentor", mentors);

        List<String> types = Lists.newArrayList();
        types.add("http://purl.imsglobal.org/vocab/lis/v2/course#CourseSection");

        ContextClaim contextClaim = ContextClaim.builder()
            .id("https://example.edu/terms/201801/courses/7/sections/1")
            .label("CPS 435-01")
            .title("CPS 435 Learning Analytics, Section 01")
            .type(types)
            .build();
        params.put("https://purl.imsglobal.org/spec/lti/claim/context", contextClaim);

        CustomClaim customClaim = CustomClaim.builder()
            .xstart("2017-04-21T01:00:00Z")
            .requestURL("https://tool.com/link/123")
            .build();
        params.put("https://purl.imsglobal.org/spec/lti/claim/custom", customClaim);

        LaunchPresentationClaim launchPresentationClaim = LaunchPresentationClaim.builder()
            .documentTarget("iframe")
            .height(320)
            .width(240)
            .returnUrl("https://example.edu/terms/201801/courses/7/sections/1/pages/1")
            .build();
        params.put("https://purl.imsglobal.org/spec/lti/claim/launch_presentation", launchPresentationClaim);

        LisClaim lisClaim = LisClaim.builder()
            .personSourcedId("example.edu:71ee7e42-f6d2-414a-80db-b69ac2defd4")
            .courseOfferingSourcedId("example.edu:SI182-F18")
            .courseSectionSourcedId("example.edu:SI182-001-F18")
            .build();
        params.put("https://purl.imsglobal.org/spec/lti/claim/lis", lisClaim);

        ResourceLinkClaim resourceLinkClaim = ResourceLinkClaim.builder()
            .id("200d101f-2c14-434a-a0f3-57c2a42369fd")
            .description("Assignment to introduce who you are")
            .title("Introduction Assignment")
            .build();
        params.put("https://purl.imsglobal.org/spec/lti/claim/resource_link", resourceLinkClaim);

        ToolPlatformClaim toolPlatformClaim = ToolPlatformClaim.builder()
            .guid("https://example.edu")
            .contactEmail("support@example.edu")
            .description("An Example Tool Platform")
            .name("Example Tool Platform")
            .url("https://example.edu")
            .productFamilyCode("ExamplePlatformVendor-Product")
            .version("1.0")
            .build();
        params.put("https://purl.imsglobal.org/spec/lti/claim/tool_platform", toolPlatformClaim);

        MessageParameterSession messageParamSession = MessageParameterSession.builder().id("89023sj890dju080").build();
        params.put("http://www.ExamplePlatformVendor.com/session", messageParamSession);

        return params;
    }
}