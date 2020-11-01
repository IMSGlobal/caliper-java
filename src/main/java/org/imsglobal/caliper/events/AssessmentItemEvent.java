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
import org.imsglobal.caliper.entities.resource.AssessmentItem;
import org.imsglobal.caliper.entities.outcome.Attempt;
import org.imsglobal.caliper.entities.response.CaliperResponse;
import org.imsglobal.caliper.validators.EventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

@SupportedActions({
    Action.STARTED,
    Action.COMPLETED,
    Action.SKIPPED
})
public class AssessmentItemEvent extends AbstractEvent {

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(AssessmentItemEvent.class);

    /**
     * Utilize builder to construct AssessmentItemEvent.  Validate AssessmentItem object copy rather than the
     * AssessmentItem builder.  This approach protects the class against parameter changes from another
     * thread during the "window of vulnerability" between the time the parameters are checked
     * until when they are copied.
     *
     * @param builder
     */
    protected AssessmentItemEvent(Builder<?> builder) {
        super(builder);

        EventValidator.checkType(this.getType(), EventType.ASSESSMENT_ITEM);
        EventValidator.checkAction(this.getAction(), AssessmentItemEvent.class);

        if (this.getAction().equals(Action.STARTED) && !(this.getGenerated() == null)) {
            EventValidator.checkGeneratedType(this.getGenerated(), Attempt.class);
        }

        if (this.getAction().equals(Action.COMPLETED) && !(this.getGenerated() == null)) {
            EventValidator.checkGeneratedType(this.getGenerated(), CaliperResponse.class);
        }

    }

    /**
     * Required.
     * @return the actor
     */
    @Override
    @Nonnull
    public Person getActor() {
    	return (Person) super.getActor();
    }

    /**
     * Required.
     * @return the object
     */
    @Override
    @Nonnull
    public AssessmentItem getObject() {
        return (AssessmentItem) super.getObject();
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
            super.type(EventType.ASSESSMENT_ITEM);
        }

        /**
         * @param actor
         * @return builder.
         */
        public T actor(Person actor) {
        	super.actor(actor);
            return self();
        }

        /**
         * @param object
         * @return builder.
         */
        public T object(AssessmentItem object) {
        	super.object(object);
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable profile object.
         * @return a new AssessmentItemEvent instance.
         */
        public AssessmentItemEvent build() {
            return new AssessmentItemEvent(this);
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
