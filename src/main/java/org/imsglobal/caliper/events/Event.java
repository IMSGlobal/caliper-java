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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.entities.Generatable;
import org.imsglobal.caliper.entities.Targetable;
import org.imsglobal.caliper.entities.foaf.Agent;
import org.imsglobal.caliper.entities.schemadotorg.SoftwareApplication;
import org.imsglobal.caliper.entities.session.FederatedSession;
import org.imsglobal.caliper.entities.w3c.Membership;
import org.imsglobal.caliper.entities.w3c.Organization;
import org.imsglobal.caliper.validators.EventValidator;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@JsonPropertyOrder({
    "@context",
    "@type",
    "actor",
    "action",
    "object",
    "target",
    "generated",
    "startedAtTime",
    "endedAtTime",
    "duration",
    "edApp",
    "group",
    "membership",
    "federatedSession "})
public abstract class Event {

    @JsonProperty("@context")
    private final EventContext context;

    @JsonProperty("@type")
    private final EventType type;

    @JsonProperty("actor")
    private final Agent actor;

    @JsonProperty("action")
    protected final Action action;

    @JsonProperty("object")
    private final Object object;

    @JsonProperty("target")
    private final Targetable target;

    @JsonProperty("generated")
    private final Generatable generated;

    @JsonProperty("startedAtTime")
    private final DateTime startedAtTime;

    @JsonProperty("endedAtTime")
    private final DateTime endedAtTime;

    @JsonProperty("duration")
    private final String duration;

    @JsonProperty("edApp")
    private final SoftwareApplication edApp;

    @JsonProperty("group")
    private final Organization group;

    @JsonProperty("membership")
    private final Membership membership;

    @JsonProperty("federatedSession")
    private final FederatedSession federatedSession;

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(Event.class);

    /**
     * Utilize builder to construct Event.  Validate object copy rather than the
     * builder.  This approach protects the class against parameter changes from another
     * thread during the "window of vulnerability" between the time the parameters are checked
     * until when they are copied.
     *
     * @param builder
     */
    protected Event(Builder<?> builder) {

        // Validator.checkContext(builder.context, Context.EVENT);
        // Validator.checkType(builder.type, Type.EVENT);
        // Validator.checkAction(builder.action, Event.class);
        EventValidator.checkStartTime(builder.startedAtTime, builder.endedAtTime);
        EventValidator.checkDuration(builder.duration);

        this.context = builder.context;
        this.type = builder.type;
        this.actor = builder.actor;
        this.action = builder.action;
        this.object = builder.object;
        this.target = builder.target;
        this.generated = builder.generated;
        this.startedAtTime = builder.startedAtTime;
        this.endedAtTime = builder.endedAtTime;
        this.duration = builder.duration;
        this.edApp = builder.edApp;
        this.group = builder.group;
        this.membership = builder.membership;
        this.federatedSession = builder.federatedSession;
    }

    /**
     * Required.
     * @return the context
     */
    @Nonnull
    public EventContext getContext() {
        return context;
    }

    /**
     * Required.
     * @return the type
     */
    @Nonnull
    public EventType getType() {
        return type;
    }

    /**
     * Required.
     * @return the actor
     */
    @Nonnull
    public Agent getActor() {
        return actor;
    }

    /**
     * Required.
     * @return the action
     */
    @Nonnull
    public Action getAction() {
        return action;
    }

    /**
     * Required.
     * @return the object
     */
    @Nonnull
    public Object getObject() {
        return object;
    }

    /**
     * Optional.
     * @return the target
     */
    @Nullable
    public Targetable getTarget() {
        return target;
    }

    /**
     * Optional.
     * @return generated
     */
    @Nullable
    public Generatable getGenerated() {
        return generated;
    }

    /**
     * Required.
     * @return the startedAt time
     */
    @Nonnull
    public DateTime getStartedAtTime() {
        return startedAtTime;
    }

    /**
     * Optional.
     * @return endedAt time
     */
    @Nullable
    public DateTime getEndedAtTime() {
        return endedAtTime;
    }

    /**
     * Optional. An xsd:duration (http://books.xmlschemata.org/relaxng/ch19-77073.html)
     * The format is expected to be PnYnMnDTnHnMnS
     * Valid values include PT1004199059S, PT130S, PT2M10S, P1DT2S, -P1Y, or P1Y2M3DT5H20M30.123S.
     * The following values are invalid: 1Y (leading P is missing), P1S (T separator is missing),
     * P-1Y (all parts must be positive), P1M2Y (parts order is significant and Y must precede M),
     * or P1Y-1M (all parts must be positive).
     * @return the duration
     */
    @Nullable
    public String getDuration() {
        return duration;
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
    public Organization getGroup() {
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
     * Federated Session object, part of the LTI launch context.  Optional.
     * @return the federated session
     */

    /**
     * Federated Session object, part of the LTI launch context.  Optional.  Serialization of
     * FederatedSession is limited to the identifying URI only.
     * @return the federated session
     */
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "@id")
    @JsonIdentityReference(alwaysAsId = true)
    @Nullable
    public FederatedSession getFederatedSession() {
        return federatedSession;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> {
        private EventContext context;
        private EventType type;
        private Agent actor;
        private Action action;
        private Object object;
        private Targetable target;
        private Generatable generated;
        private DateTime startedAtTime;
        private DateTime endedAtTime;
        private String duration;
        private SoftwareApplication edApp;
        private Organization group;
        private Membership membership;
        private FederatedSession federatedSession;

        protected abstract T self();

        /**
         * Initialize type with default values.
         */
        public Builder() {
            context(EventContext.EVENT);
            type(EventType.EVENT);
        }

        /**
         * @param context
         * @return builder.
         */
        private T context(EventContext context) {
            this.context = context;
            return self();
        }

        /**
         * @param type
         * @return builder.
         */
        private T type(EventType type) {
            this.type = type;
            return self();
        }

        /**
         * @param actor
         * @return builder.
         */
        public T actor(Agent actor) {
            this.actor = actor;
            return self();
        }

        /**
         * @param action
         * @return builder.
         */
        public T action(Action action) {
            this.action = action;
            return self();
        }

        /**
         * @param object
         * @return builder.
         */
        public T object(Object object) {
            this.object = object;
            return self();
        }

        /**
         * @param target
         * @return builder.
         */
        public T target(Targetable target) {
            this.target = target;
            return self();
        }

        /**
         * @param generated
         * @return builder.
         */
        public T generated(Generatable generated) {
            this.generated = generated;
            return self();
        }

        /**
         * @param startedAtTime
         * @return builder.
         */
        public T startedAtTime(DateTime startedAtTime) {
            this.startedAtTime = startedAtTime;
            return self();
        }

        /**
         * @param endedAtTime
         * @return builder.
         */
        public T endedAtTime(DateTime endedAtTime) {
            this.endedAtTime = endedAtTime;
            return self();
        }

        /**
         * @param duration
         * @return builder.
         */
        public T duration(String duration) {
            this.duration = duration;
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
        public T group(Organization group) {
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
         * @param federatedSession
         * @return builder.
         */
        public T federatedSession(FederatedSession federatedSession) {
            this.federatedSession = federatedSession;
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