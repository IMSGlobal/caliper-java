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
import org.imsglobal.caliper.entities.resource.Thread;
import org.imsglobal.caliper.validators.EventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SupportedActions({
    Action.MARKED_AS_READ,
    Action.MARKED_AS_UNREAD
})
public class ThreadEvent extends AbstractEvent {

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(ThreadEvent.class);

    /**
     * Utilize builder to construct ThreadEvent.  Validate Thread object copy rather than the
     * Thread builder.  This approach protects the class against parameter changes from another
     * thread during the "window of vulnerability" between the time the parameters are checked
     * until when they are copied.
     *
     * @param builder
     */
    protected ThreadEvent(Builder<?> builder) {
        super(builder);

        EventValidator.checkActorType(this.getActor(), Person.class);
        EventValidator.checkAction(this.getAction(), ThreadEvent.class);
        EventValidator.checkObjectType(this.getObject(), Thread.class);
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
            type(EventType.FORUM);
        }

        /**
         * Client invokes build method in order to create an immutable profile object.
         * @return a new ThreadEvent instance.
         */
        public ThreadEvent build() {
            return new ThreadEvent(this);
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