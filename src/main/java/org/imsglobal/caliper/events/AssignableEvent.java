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
import org.imsglobal.caliper.entities.resource.CaliperAssignable;
import org.imsglobal.caliper.validators.EventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

@SupportedActions({
    Action.ACTIVATED,
    Action.COMPLETED,
    Action.DEACTIVATED,
    Action.REVIEWED,
    Action.STARTED,
    Action.SUBMITTED
})
public class AssignableEvent extends AbstractEvent {

    @JsonProperty("object")
    private final CaliperAssignable object;

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(AssignableEvent.class);

    /**
     * Utilize builder to construct AssignableEvent.  Validate Assignable object copy rather than the
     * Assignable builder.  This approach protects the class against parameter changes from another
     * thread during the "window of vulnerability" between the time the parameters are checked
     * until when they are copied.
     *
     * @param builder
     */
    private AssignableEvent(Builder<?> builder) {
        super(builder);

        EventValidator.checkType(this.getType(), EventType.ASSIGNABLE);
        EventValidator.checkAction(this.getAction(), AssignableEvent.class);

        this.object = builder.object;
    }

    /**
     * Required.
     * @return the object
     */
    @Override
    @Nonnull
    public CaliperAssignable getObject() {
        return object;
    }

    /**
     * Initialize default parameter values in the builder.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEvent.Builder<T>  {
        private CaliperAssignable object;

        /*
         * Constructor
         */
        public Builder() {
            super.type(EventType.ASSIGNABLE);
        }

        /**
         * @param object
         * @return builder.
         */
        public T object(CaliperAssignable object) {
            this.object = object;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable profile object.
         * @return a new AssignableEvent instance.
         */
        public AssignableEvent build() {
            return new AssignableEvent(this);
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