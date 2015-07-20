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
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.TestAgentEntities;
import org.imsglobal.caliper.TestDates;
import org.imsglobal.caliper.TestEpubEntities;
import org.imsglobal.caliper.TestLisEntities;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.annotation.BookmarkAnnotation;
import org.imsglobal.caliper.entities.reading.EpubSubChapter;
import org.imsglobal.caliper.entities.reading.Frame;
import org.imsglobal.caliper.payload.JsonMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class BookmarkAnnotationEventTest {

    private LearningContext learningContext;
    private Person actor;
    private EpubSubChapter ePub;
    private Frame object;
    private BookmarkAnnotation generated;
    private AnnotationEvent event;
    private DateTime dateCreated = TestDates.getDefaultDateCreated();
    private DateTime dateModified = TestDates.getDefaultDateModified();
    private DateTime dateStarted = TestDates.getDefaultStartedAtTime();
    // private static final Logger log = LoggerFactory.getLogger(BookmarkAnnotationEventTest.class);

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

        // Build Frame
        ePub = TestEpubEntities.buildEpubSubChap432();
        object = Frame.builder()
            .id(ePub.getId())
            .name(ePub.getName())
            .isPartOf(ePub.getIsPartOf())
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .version(ePub.getVersion())
            .index(2)
            .build();

        // Build Bookmark Annotation
        generated = BookmarkAnnotation.builder()
            .id("https://example.edu/bookmarks/00001")
            .annotated(object)
            .bookmarkNotes("The Intolerable Acts (1774)--bad idea Lord North")
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .build();

        // Build event
        event = buildEvent(Action.BOOKMARKED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if Bookmark Annotation event is serialized to JSON with expected values",
            jsonFixture("fixtures/caliperBookmarkAnnotationEvent.json"), JsonMapper.serialize(event, JsonInclude.Include.NON_EMPTY));
    }

    @Test(expected=IllegalArgumentException.class)
    public void annotationEventRejectsCompletedAction() {
        buildEvent(Action.COMPLETED);
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