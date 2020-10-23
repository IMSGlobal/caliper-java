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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.imsglobal.caliper.actions.CaliperAction;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.entities.CaliperEntity;
import org.imsglobal.caliper.entities.CaliperGeneratable;
import org.imsglobal.caliper.entities.CaliperReferable;
import org.imsglobal.caliper.entities.CaliperTargetable;
import org.imsglobal.caliper.entities.agent.CaliperAgent;
import org.imsglobal.caliper.entities.agent.CaliperOrganization;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.session.LtiSession;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.profiles.CaliperProfile;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * This class provides a skeletal implementation of the Event interface
 * in order to minimize the effort required to implement the interface.
 */
public abstract class AbstractEvent implements CaliperEvent {

    @JsonProperty("@context")
    private final JsonldContext context;

    @JsonProperty("type")
    private final CaliperEventType type;

    @JsonProperty("profile")
    private final CaliperProfile profile;

    @JsonProperty("id")
    private final String id;

    @JsonProperty("actor")
    private final CaliperAgent actor;

    @JsonProperty("action")
    private final CaliperAction action;

    @JsonProperty("object")
    private final CaliperEntity object;

    @JsonProperty("target")
    private final CaliperTargetable target;

    @JsonProperty("generated")
    private final CaliperGeneratable generated;

    @JsonProperty("referrer")
    private final CaliperReferable referrer;

    @JsonProperty("eventTime")
    private final DateTime eventTime;

    @JsonProperty("edApp")
    private final SoftwareApplication edApp;

    @JsonProperty("group")
    private final CaliperOrganization group;

    @JsonProperty("membership")
    private final Membership membership;

    @JsonProperty("session")
    private final Session session;

    @JsonProperty("federatedSession")
    private final LtiSession federatedSession;

    @JsonProperty("extensions")
    //private final Object extensions;
    private final Map<String, Object> extensions;

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(AbstractEvent.class);

    /**
     * Utilize builder to construct Event.  Validate object copy rather than the
     * builder.  This approach protects the class against parameter changes from another
     * thread during the "window of vulnerability" between the time the parameters are checked
     * until when they are copied.
     *
     * @param builder
     */
    protected AbstractEvent(Builder<?> builder) {
        this.context = builder.context;
        this.type = builder.type;
        this.profile = builder.profile;
        this.id = builder.id;
        this.actor = builder.actor;
        this.action = builder.action;
        this.object = builder.object;
        this.target = builder.target;
        this.generated = builder.generated;
        this.referrer = builder.referrer;
        this.eventTime = builder.eventTime;
        this.edApp = builder.edApp;
        this.group = builder.group;
        this.membership = builder.membership;
        this.session = builder.session;
        this.federatedSession = builder.federatedSession;
        this.extensions = ImmutableMap.copyOf(builder.extensions);
    }

    /**
     * Required.
     * @return the context
     */
    @Nonnull
    public JsonldContext getContext() {
        return context;
    }

    /**
     * Required.
     * @return the type
     */
    @Nonnull
    public CaliperEventType getType() {
        return type;
    }

    /**
     * Optional (though recommended).
     * @return the profile that governs this Event.
     */
    public CaliperProfile getProfile() {
        return profile;
    }

    /**
     * Identifier that must be set either by emitting service or the receiving endpoint.
     * @return the id
     */
    @Nullable
    public String getId() {
        return id;
    }

    /**
     * Required.
     * @return the actor
     */
    @Nonnull
    public CaliperAgent getActor() {
        return actor;
    }

    /**
     * Required.
     * @return the action
     */
    @Nonnull
    public CaliperAction getAction() {
        return action;
    }

    /**
     * Required.
     * @return the object
     */
    @Nonnull
    public CaliperEntity getObject() {
        return object;
    }

    /**
     * Optional.
     * @return the target
     */
    @Nullable
    public CaliperTargetable getTarget() {
        return target;
    }

    /**
     * Optional.
     * @return generated
     */
    @Nullable
    public CaliperGeneratable getGenerated() {
        return generated;
    }

    /**
     * Optional.
     * @return referrer
     */
    @Nullable
    public CaliperReferable getReferrer() {
        return referrer;
    }

    /**
     * Required.
     * @return the startedAt time
     */
    @Nonnull
    public DateTime getEventTime() {
        return eventTime;
    }

    /**
     * The edApp context, part of the Caliper Learning Context.  Optional.
     * @return the edApp
     */
    @Nullable
    public SoftwareApplication getEdApp() {
        return edApp;
    }

    /**
     * The Group context, part of the Caliper Learning Context.  Optional.
     * @return the group
     */
    @Nullable
    public CaliperOrganization getGroup() {
        return group;
    }

    /**
     * The Membership context, part of the Caliper Learning Context.  Optional.
     * @return the membership
     */
    @Nullable
    public Membership getMembership() {
        return membership;
    }

    /**
     * Current session context.  Optional.
     * @return the federated session
     */
    public Session getSession() {
        return session;
    }

    /**
     * Federated Session object, part of the LTI launch context.  Optional.
     * @return the federated session
     */
    public LtiSession getFederatedSession() {
        return federatedSession;
    }

    /**
     * Custom properties.  Optional.
     * @return extensions
     */
    /**
    public Object getExtensions() {
        return extensions;
    }
     */
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> {
        private JsonldContext context;
        private CaliperEventType type;
        private CaliperProfile profile;
        private String id;
        private CaliperAgent actor;
        private CaliperAction action;
        private CaliperEntity object;
        private CaliperTargetable target;
        private CaliperGeneratable generated;
        private CaliperReferable referrer;
        private DateTime eventTime;
        private SoftwareApplication edApp;
        private CaliperOrganization group;
        private Membership membership;
        private Session session;
        private LtiSession federatedSession;
        //private Object extensions;
        private Map<String, Object> extensions = Maps.newHashMap();

        protected abstract T self();

        /**
         * Initialize type with default values.
         */
        public Builder() {
            type(EventType.EVENT);
        }

        /**
         * @param context
         * @return builder.
         */
        public T context(JsonldContext context) {
            this.context = context;
            return self();
        }

        /**
         * @param type
         * @return builder.
         */
        public T type(CaliperEventType type) {
            this.type = type;
            return self();
        }

        /**
         * @param profile
         * @return builder.
         */
        public T profile(CaliperProfile profile) {
            this.profile = profile;
            return self();
        }

        /**
         * @param id
         * @return builder.
         */
        public T id(String id) {
            this.id = id;
            return self();
        }

        /**
         * @param actor
         * @return builder.
         */
        public T actor(CaliperAgent actor) {
            this.actor = actor;
            return self();
        }

        /**
         * @param action
         * @return builder.
         */
        public T action(CaliperAction action) {
            this.action = action;
            return self();
        }

        /**
         * @param object
         * @return builder.
         */
        public T object(CaliperEntity object) {
            this.object = object;
            return self();
        }

        /**
         * @param target
         * @return builder.
         */
        public T target(CaliperTargetable target) {
            this.target = target;
            return self();
        }

        /**
         * @param generated
         * @return builder.
         */
        public T generated(CaliperGeneratable generated) {
            this.generated = generated;
            return self();
        }

        /**
         * @param referrer
         * @return builder.
         */
        public T referrer(CaliperReferable referrer) {
            this.referrer = referrer;
            return self();
        }

        /**
         * @param eventTime
         * @return builder.
         */
        public T eventTime(DateTime eventTime) {
            this.eventTime = eventTime;
            return self();
        }

        /**
         * @param edApp
         * @return builder.
         */
        public T edApp(SoftwareApplication edApp) {
            this.edApp = edApp;
            return self();
        }

        /**
         * @param group
         * @return builder.
         */
        public T group(CaliperOrganization group) {
            this.group = group;
            return self();
        }

        /**
         * @param membership
         * @return builder.
         */
        public T membership(Membership membership) {
            this.membership = membership;
            return self();
        }

        /**
         * @param session
         * @return builder.
         */
        public T session(Session session) {
            this.session = session;
            return self();
        }

        /**
         * @param federatedSession
         * @return builder.
         */
        public T federatedSession(LtiSession federatedSession) {
            this.federatedSession = federatedSession;
            return self();
        }

        /**
         * @param extensions
         * @return builder.
         */
        /**
        public T extensions(Object extensions) {
            this.extensions = extensions;
            return self();
        }
         */

        public T extensions(Map<String, Object> extensions) {
            this.extensions = extensions;
            return self();
        }
    }

    /**
     * Self-reference that permits sub-classing of builder.
     */
    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }
}