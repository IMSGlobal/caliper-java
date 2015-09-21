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
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.entities.DigitalResource;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.validators.EventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SupportedActions({Action.NAVIGATED_TO})
public class NavigationEvent extends Event {

    @JsonProperty("@type")
    private final EventType type;

    @JsonProperty("action")
    private final Action action;

    @JsonProperty("navigatedFrom")
    private final DigitalResource fromResource;

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(NavigationEvent.class);

    /**
     * Utilize builder to construct NavigationEvent.  Validate Navigation object copy rather than the
     * Navigation builder.  This approach protects the class against parameter changes from another
     * thread during the "window of vulnerability" between the time the parameters are checked
     * until when they are copied.
     *
     * @param builder
     */
    protected NavigationEvent(Builder<?> builder) {
        super(builder);

        EventValidator.checkType(builder.type, EventType.NAVIGATION);
        EventValidator.checkActorType(getActor(), Person.class);
        EventValidator.checkAction(builder.action, NavigationEvent.class);
        EventValidator.checkObjectType(getObject(), DigitalResource.class);
        EventValidator.checkTargetType(getTarget(), DigitalResource.class);

        this.type = builder.type;
        this.action = builder.action;
        this.fromResource = builder.fromResource;
    }

    /**
     * Required.
     * @return the type
     */
    @Override
    @Nonnull
    public EventType getType() {
        return type;
    }

    /**
     * Required.
     * @return the action
     */
    @Override
    @Nonnull
    public Action getAction() {
        return action;
    }

    /**
     * Describes the resource from which the navigation commences.
     * @return the fromResource
     */
    @Nullable
    public DigitalResource getFromResource() {
        return fromResource;
    }

    /**
     * Initialize default parameter values in the builder.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Event.Builder<T>  {
        private EventType type;
        private Action action;
        private DigitalResource fromResource;

        /*
         * Constructor
         */
        public Builder() {
            type(EventType.NAVIGATION);
            action(Action.NAVIGATED_TO);
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
         * @param action
         * @return builder.
         */
        @Override
        public T action(Action action) {
            this.action = action;
            return self();
        }

        /**
         * @param fromResource
         * @return builder
         */
        public T fromResource(DigitalResource fromResource) {
            this.fromResource = fromResource;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable profile object.
         * @return a new NavigationEvent instance.
         */
        public NavigationEvent build() {
            return new NavigationEvent(this);
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

    /**
     * Static factory method.
     * @return a new instance of the builder.
     */
    public static Builder<?> builder() {
        return new Builder2();
    }
}