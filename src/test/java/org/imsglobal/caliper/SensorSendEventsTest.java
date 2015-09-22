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

package org.imsglobal.caliper;

import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.entities.DigitalResource;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.reading.EpubSubChapter;
import org.imsglobal.caliper.entities.reading.EpubVolume;
import org.imsglobal.caliper.entities.reading.Frame;
import org.imsglobal.caliper.entities.reading.WebPage;
import org.imsglobal.caliper.events.Event;
import org.imsglobal.caliper.events.NavigationEvent;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// @Category(org.imsglobal.caliper.UnitTest.class)
public class SensorSendEventsTest {

    private LearningContext learningContext;
    private Person actor;
    private EpubVolume object;
    private DigitalResource fromResource;
    private EpubSubChapter ePub;
    private Frame target;
    private DateTime dateCreated = TestDates.getDefaultDateCreated();
    private DateTime dateModified = TestDates.getDefaultDateModified();
    private DateTime eventTime = TestDates.getDefaultEventTime();

    @Test
    public void test() {

        // Create Sensor, create client, register client with sensor.
        Sensor<String> sensor = new Sensor<>("https://example.edu/sensor/001");
        Client client = new Client(sensor.getId() + "/defaultClient", TestUtils.getTestingOptions());
        sensor.registerClient(client.getId(), client);

        // Build the Learning Context
        learningContext = LearningContext.builder()
            .edApp(TestAgentEntities.buildEpubViewerApp())
            .group(TestLisEntities.buildGroup())
            .membership(TestLisEntities.buildMembership())
            .build();

        // Build actor
        actor = TestAgentEntities.buildStudent554433();

        // Build object
        object = TestEpubEntities.buildEpubVolume43();

        // Build previous location
        fromResource = WebPage.builder()
            .id("https://example.edu/politicalScience/2015/american-revolution-101/index.html")
            .name("American Revolution 101 Landing Page")
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .version("1.0")
            .build();

        // Build target frame
        ePub = TestEpubEntities.buildEpubSubChap431();
        target = Frame.builder()
            .id(ePub.getId())
            .name(ePub.getName())
            .isPartOf(ePub.getIsPartOf())
            .dateCreated(dateCreated)
            .dateModified(dateModified)
            .version(ePub.getVersion())
            .index(1)
            .build();

        // Fire event test - Send 50 events
        for (int i = 0 ; i < 50 ; i++) {
            Event event = buildEvent(Action.NAVIGATED_TO);
            sensor.send(sensor, event);
        }

        // There should be two caliperEvents queued
        assertEquals("Expect fifty Caliper events to be sent", 50,
                sensor.getStatistics().get("default").getMeasures().getCount());

        // TODO - Describes test - Send five describes

        // There should be four describes queued assertEquals("Expect five describes to be sent", 5,
        // Caliper.getStatistics().getDescribes().getCount());

        // There should be two message successfully sent
        int successes = sensor.getStatistics().get("default").getSuccessful().getCount();
        assertEquals("Expect fifty messages to be sent successfully", 50, successes);

        // There should be zero failures
        int failures = sensor.getStatistics().get("default").getFailed().getCount();
        assertEquals("Expect zero message failures to be sent", 0, failures);
    }

    /**
     * Build Navigation event
     * @param action
     * @return event
     */
    private NavigationEvent buildEvent(Action action) {
        return NavigationEvent.builder()
            .actor(actor)
            .action(action)
            .object(object)
            .target(target)
            .fromResource(fromResource)
            .eventTime(eventTime)
            .edApp(learningContext.getEdApp())
            .group(learningContext.getGroup())
            .membership(learningContext.getMembership())
            .build();
    }
}