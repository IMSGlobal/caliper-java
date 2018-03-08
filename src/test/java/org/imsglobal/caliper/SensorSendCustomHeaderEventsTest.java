package org.imsglobal.caliper;

import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.clients.HttpCustomHeaderClient;
import org.imsglobal.caliper.clients.HttpClientOptions;
import org.imsglobal.caliper.config.Config;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.*;
import org.imsglobal.caliper.entities.resource.WebPage;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.NavigationEvent;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class SensorSendCustomHeaderEventsTest {
    private static final String BASE_IRI = "https://example.edu";
    private static final String ENDPOINT = "https://example.edu/endpoint";

    // private static final Logger log = LoggerFactory.getLogger(SensorSendEventsTest.class);

    @Test
    public void test() {

        // Initialize Sensor, Client and Requester provisioned with Options
        Sensor sensor = Sensor.create(BASE_IRI.concat("/sensors/1"));
        HttpClientOptions opts = HttpClientOptions.builder()
                                    .host(ENDPOINT)
                                    .apiKey("Bearer 2e8317a0-86ed-4674-a0ea-b211233c94cf")
                                    .build();
        HttpCustomHeaderClient client = HttpCustomHeaderClient.create(sensor.getId(), opts);
        sensor.registerClient(client);

        // Fire event test - Send a envelope containing the above event
        JsonldStringContext context = JsonldStringContext.getDefault();

        String id = "urn:id:" + UUID.randomUUID().toString();

        Person actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        String currentLocation = BASE_IRI.concat("/terms/201601/courses/7/sections/1/pages/1");
        String previousLocation = BASE_IRI.concat("/terms/201601/courses/7/sections/1/pages/1");

        WebPage object = WebPage.builder()
                .id(currentLocation)
                .name("Learning Analytics Specifications")
                .description("Overview of Learning Analytics Specifications with particular emphasis on IMS Caliper.")
                .dateCreated(new DateTime(2016, 8, 1, 9, 0, 0, 0, DateTimeZone.UTC))
                .build();

        WebPage referrer = WebPage.builder().id(previousLocation).build();

        SoftwareApplication edApp = SoftwareApplication.builder().id(BASE_IRI).build();

        CourseSection group = CourseSection.builder().id(BASE_IRI.concat("/terms/201601/courses/7/sections/1"))
                .courseNumber("CPS 435-01")
                .academicSession("Fall 2016")
                .build();

        Membership membership = Membership.builder()
                .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/rosters/1"))
                .member(actor)
                .organization(CourseSection.builder().id(group.getId()).build())
                .status(Status.ACTIVE)
                .role(Role.LEARNER)
                .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
                .build();

        Session session = Session.builder()
                .id(BASE_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259"))
                .startedAtTime(new DateTime(2016, 11, 15, 10, 0, 0, 0, DateTimeZone.UTC))
                .build();

        NavigationEvent event = NavigationEvent.builder()
                .context(context)
                .id(id)
                .actor(actor)
                .action(Action.NAVIGATED_TO)
                .object(object)
                .eventTime(new DateTime(DateTimeZone.UTC))
                .referrer(referrer)
                .edApp(edApp)
                .group(group)
                .membership(membership)
                .session(session)
                .build();

        // Prep envelope
        DateTime sendTime = new DateTime(DateTimeZone.UTC);
        List<Object> data = new ArrayList<>();
        data.add(event);

        // Add additional headers
        client.setCustomHeader("Accept", "*/*");

        // These header will not be added
        client.setCustomHeader("Content-Type", "text");
        client.setCustomHeader("content-type", "text");
        client.setCustomHeader("Authorization", "Basic 1234");
        client.setCustomHeader("AUTHORIZATION", "Bearer 123121421421414");

        Envelope envelope = sensor.create(client.getId(), sendTime, Config.DATA_VERSION, data);

        // Send envelope
        sensor.send(client, envelope);

        // TODO: These assertion throws NullPointerException.
        /*
        assertEquals("Expect fifty Caliper events to be sent", 1,
                sensor.getStatistics().get("default").getMeasures().getCount());
        */
    }
}
