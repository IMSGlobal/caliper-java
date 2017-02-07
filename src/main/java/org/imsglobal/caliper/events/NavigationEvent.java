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
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.resource.Resource;
import org.imsglobal.caliper.validators.EventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SupportedActions({ Action.NAVIGATED_TO })
public class NavigationEvent extends AbstractEvent {

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

        EventValidator.checkType(this.getType(), EventType.NAVIGATION);
        EventValidator.checkActorType(this.getActor(), Person.class);
        EventValidator.checkAction(this.getAction(), NavigationEvent.class);
        EventValidator.checkObjectType(this.getObject(), Resource.class);
        if (!(this.getTarget() == null)) {
            EventValidator.checkTargetType(this.getTarget(), Resource.class);
        }
    }

    /**
     * Initialize default parameter values in the builder.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEvent.Builder<T>  {

        /*
         * Constructor
         */
        public Builder() {
            super.type(EventType.NAVIGATION);
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