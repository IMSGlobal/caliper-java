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

package org.imsglobal.caliper.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.imsglobal.caliper.Client;
import org.imsglobal.caliper.Sensor;
import org.imsglobal.caliper.TestAgentEntities;
import org.imsglobal.caliper.TestDates;
import org.imsglobal.caliper.TestEpubEntities;
import org.imsglobal.caliper.TestLisEntities;
import org.imsglobal.caliper.TestSessionEntities;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.databind.JsonFilters;
import org.imsglobal.caliper.databind.JsonObjectMapper;
import org.imsglobal.caliper.databind.JsonSimpleFilterProvider;
import org.imsglobal.caliper.entities.DigitalResource;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.reading.EpubSubChapter;
import org.imsglobal.caliper.entities.reading.EpubVolume;
import org.imsglobal.caliper.entities.reading.Frame;
import org.imsglobal.caliper.entities.reading.WebPage;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.Event;
import org.imsglobal.caliper.events.NavigationEvent;
import org.imsglobal.caliper.payload.Envelope;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class HttpRequestorTest {

    private Sensor<String> sensor ;
    private HttpRequestor<Event> httpRequestor = new HttpRequestor<>(TestUtils.getTestingOptions());
    private Person actor;
    private Session federatedSession;
    private LearningContext learningContext;
    private EpubVolume object;
    private DigitalResource fromResource;
    private EpubSubChapter ePub;
    private Frame target;
    private NavigationEvent event;
    private DateTime dateCreated = TestDates.getDefaultDateCreated();
    private DateTime dateModified = TestDates.getDefaultDateModified();
    private DateTime eventTime = TestDates.getDefaultEventTime();
    private List<Event> data = new ArrayList<>();
    // private static final Logger log = LoggerFactory.getLogger(HttpRequestorTest.class);

    @Before
    public void setup() {

        // Register a Sensor client using the default constructor
        sensor = new Sensor<>("https://example.edu/sensor/001");
        Client client = new Client();
        client.setId(sensor.getId() + "/defaultClient");
        client.setOptions(TestUtils.getTestingOptions());
        sensor.registerClient(client.getId(), client);

        // Build actor
        actor = TestAgentEntities.buildStudent554433();

        // Build the Learning Context
        learningContext = LearningContext.builder()
            .edApp(TestAgentEntities.buildEpubViewerApp())
            .group(TestLisEntities.buildGroup())
            .membership(TestLisEntities.buildMembership())
            .federatedSession(TestSessionEntities.buildFederatedSession(actor))
            .build();

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

        // Build event
        event = buildEvent(Action.NAVIGATED_TO);

        // Add event to data array
        data.add(event);
    }

    @Test
    public void testSerializedEnvelope() throws Exception {

        // Create envelope
        Envelope<Event> envelope = httpRequestor.createEnvelope(sensor, DateTime.now(), data);

        // Serialize envelope, excluding null properties, empty objects, empty arrays and duplicate @context
        SimpleFilterProvider provider = JsonSimpleFilterProvider.create(JsonFilters.EXCLUDE_CONTEXT);
        ObjectMapper mapper = JsonObjectMapper.create(JsonInclude.Include.NON_EMPTY, provider);
        String json = mapper.writeValueAsString(envelope);

        // Swap out sendTime=DateTime.now() in favor of fixture value (or test will most assuredly fail).
        Pattern pattern = Pattern.compile("\"sendTime\":\"[^\"]*\"");
        Matcher matcher = pattern.matcher(json);
        json = matcher.replaceFirst("\"sendTime\":\"" + TestDates.getDefaultSendTime() +"\"");

        String fixture = jsonFixture("fixtures/caliperEnvelopeEventSingle.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testGeneratePayloadContentType() throws Exception {

        // Create envelope
        Envelope<Event> envelope = httpRequestor.createEnvelope(sensor, DateTime.now(), data);

        // Serialize envelope; include null properties, empty objects and empty arrays
        SimpleFilterProvider provider = JsonSimpleFilterProvider.create(JsonFilters.EXCLUDE_CONTEXT);
        ObjectMapper mapper = JsonObjectMapper.create(JsonInclude.Include.NON_EMPTY, provider);
        String json = mapper.writeValueAsString(envelope);

        // Create an HTTP StringEntity payload with the envelope JSON.
        StringEntity payload = httpRequestor.generatePayload(json, ContentType.APPLICATION_JSON);

        //System.out.println("CONTENT-TYPE:" + payload.getContentType().toString());

        assertEquals("Content-Type: application/json; charset=UTF-8", payload.getContentType().toString());
    }

    @After
    public void teardown() {
        event = null;
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
            .federatedSession(learningContext.getFederatedSession())
            .build();
    }
}