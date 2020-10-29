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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.actions.CaliperAction;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.resource.Document;
import org.imsglobal.caliper.events.Event;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class GeneralEventModifiedExtendedTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private Document object;
    private Map<String, Object> extensions;
    private Event event;

    private static final String BASE_IRI = "https://example.edu";
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201601/courses/7/sections/1");

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value());

        DateTime dateCreated = new DateTime(2016, 11, 12, 7, 15, 0, 0, DateTimeZone.UTC);

        id = "urn:uuid:5973dcd9-3126-4dcc-8fd8-8153a155361c";

        actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        object = Document.builder()
            .id(SECTION_IRI.concat("/resources/123?version=3"))
            .name("Course Syllabus")
            .dateCreated(dateCreated)
            .dateModified(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .version("3")
            .build();

        Document versionTwo = Document.builder()
            .id("https://example.edu/terms/201601/courses/7/sections/1/resources/123?version=2")
            .dateCreated(dateCreated)
            .dateModified(new DateTime(2016, 11, 13, 11, 0, 0, 0, DateTimeZone.UTC))
            .version("2")
            .build();

        Document versionOne = Document.builder()
            .id("https://example.edu/terms/201601/courses/7/sections/1/resources/123?version=1")
            .dateCreated(dateCreated)
            .version("1")
            .build();

        Document[] versionArray = {versionOne, versionTwo};
        List<Document> versions = Lists.newArrayList();
        versions.addAll(Arrays.asList(versionArray));
        extensions = Maps.newHashMap();
        extensions.put("archive", versions);

        // Build event
        event = buildEvent(Profile.GENERAL, Action.MODIFIED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p2/caliperEventGeneralModifiedExtended.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build Event.
     * @param profile, action
     * @return event
     */
    private Event buildEvent(CaliperProfile profile, CaliperAction action) {
        return Event.builder()
            .context(context)
            .id(id)
            .profile(profile)
            .actor(actor)
            .action(action)
            .object(object)
            .eventTime(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .extensions(extensions)
            .build();
    }
}