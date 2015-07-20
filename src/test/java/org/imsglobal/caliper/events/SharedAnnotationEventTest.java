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
import com.google.common.collect.Lists;
import org.imsglobal.caliper.TestAgentEntities;
import org.imsglobal.caliper.TestDates;
import org.imsglobal.caliper.TestEpubEntities;
import org.imsglobal.caliper.TestLisEntities;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.annotation.SharedAnnotation;
import org.imsglobal.caliper.entities.foaf.Agent;
import org.imsglobal.caliper.entities.reading.EpubSubChapter;
import org.imsglobal.caliper.entities.reading.Frame;
import org.imsglobal.caliper.payload.JsonMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class SharedAnnotationEventTest {

    private LearningContext learningContext;
    private Person actor;
    private EpubSubChapter ePub;
    private Frame object;
    private SharedAnnotation generated;
    private AnnotationEvent event;
    private DateTime dateCreated = TestDates.getDefaultDateCreated();
    private DateTime dateModified = TestDates.getDefaultDateModified();
    private DateTime dateStarted = TestDates.getDefaultStartedAtTime();
    // private static final Logger log = LoggerFactory.getLogger(SharedAnnotationEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = LearningContext.builder()
            .edApp(TestAgentEntities.buildEpubViewerApp())
            .group(TestLisEntities.buildGroup())
            .membership(TestLisEntities.buildMembership())
            .build();

        // Build actor
        actor = TestAgentEntities.buildStudent554433();

        // Build object frame
        ePub = TestEpubEntities.buildEpubSubChap433();
        object = Frame.builder()
            .id(ePub.getId())
            .name(ePub.getName())
            .isPartOf(ePub.getIsPartOf())
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .version(ePub.getVersion())
            .index(3)
            .build();

        // Add shared with agents
        List<Agent> shared = Lists.newArrayList();
        shared.add(Person.builder()
            .id("https://example.edu/user/657585")
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .build());
        shared.add(Person.builder()
            .id("https://example.edu/user/667788")
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .build());

        // Build Shared Annotation
        generated = SharedAnnotation.builder()
            .id("https://example.edu/shared/9999")
            .annotated(object)
            .withAgents(shared)
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .build();

        // Build event
        event = buildEvent(Action.SHARED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if Shared Annotation event is serialized to JSON with expected values",
            jsonFixture("fixtures/caliperSharedAnnotationEvent.json"), JsonMapper.serialize(event, JsonInclude.Include.NON_EMPTY));
    }

    @Test(expected=IllegalArgumentException.class)
    public void annotationEventRejectsGradedAction() {
        buildEvent(Action.GRADED);
    }

    /**
     * Build Annotation event.
     * @param action
     * @return event
     */
    private AnnotationEvent buildEvent(Action action) {
        return AnnotationEvent.builder()
            .actor(actor)
            .action(action)
            .object(object)
            .generated(generated)
            .startedAtTime(dateStarted)
            .edApp(learningContext.getEdApp())
            .group(learningContext.getGroup())
            .membership(learningContext.getMembership())
            .build();
    }
}