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
import org.imsglobal.caliper.TestAgentEntities;
import org.imsglobal.caliper.TestDates;
import org.imsglobal.caliper.TestLisEntities;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.LearningObjective;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.media.MediaLocation;
import org.imsglobal.caliper.entities.media.VideoObject;
import org.imsglobal.caliper.payload.JsonMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class MediaEventTest {

    private LearningContext learningContext;
    private Person actor;
    private VideoObject object;
    private MediaEvent event;
    private MediaLocation target;
    private DateTime dateCreated = TestDates.getDefaultDateCreated();
    private DateTime dateModified = TestDates.getDefaultDateModified();
    private DateTime eventTime = TestDates.getDefaultEventTime();
    // private static final Logger log = LoggerFactory.getLogger(MediaEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = LearningContext.builder()
            .edApp(TestAgentEntities.buildMediaPlayerApp())
            .group(TestLisEntities.buildGroup())
            .membership(TestLisEntities.buildMembership())
            .build();

        // Build actor
        actor = TestAgentEntities.buildStudent554433();

        // Build video
        object = VideoObject.builder()
            .id("https://example.com/super-media-tool/video/1225")
            .name("American Revolution - Key Figures Video")
            .learningObjective(LearningObjective.builder()
                .id("https://example.edu/american-revolution-101/personalities/learn")
                .dateCreated(dateCreated)
                .build())
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .version("1.0")
            .duration(1420)
            .build();

        // Build media location
        target = MediaLocation.builder()
            .id(object.getId())
            .dateCreated(dateCreated)
            .version(object.getVersion())
            .currentTime(710)
            .build();

        // Build event
        event = buildEvent(Action.PAUSED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        String json = JsonMapper.serialize(event, JsonInclude.Include.NON_EMPTY);
        String fixture = jsonFixture("fixtures/caliperEventMediaPausedVideo.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void mediaEventRejectsSubmittedAction() {
        buildEvent(Action.SUBMITTED);
    }

    /**
     * Build Media event.
     * @param action
     * @return event
     */
    private MediaEvent buildEvent(Action action) {
        return MediaEvent.builder()
            .actor(actor)
            .action(action)
            .object(object)
            .target(target)
            .eventTime(eventTime)
            .edApp(learningContext.getEdApp())
            .group(learningContext.getGroup())
            .membership(learningContext.getMembership())
            .build();
    }
}