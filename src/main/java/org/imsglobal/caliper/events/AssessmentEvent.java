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
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.assessment.Assessment;
import org.imsglobal.caliper.entities.assignable.Attempt;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.validators.EventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

@SupportedActions({
    Action.STARTED,
    Action.PAUSED,
    Action.RESTARTED,
    Action.SUBMITTED
})
public class AssessmentEvent extends Event {
    
    @JsonProperty("@context")
    private final EventContext context;

    @JsonProperty("@type")
    private final EventType type;

    @JsonProperty("action")
    private final Action action;

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(AssessmentEvent.class);

    /**
     * Utilize builder to construct AssessmentEvent.  Validate Assessment object copy rather than the
     * Assessment builder.  This approach protects the class against parameter changes from another
     * thread during the "window of vulnerability" between the time the parameters are checked
     * until when they are copied.
     *
     * @param builder
     */
    protected AssessmentEvent(Builder<?> builder) {
        super(builder);

        EventValidator.checkContext(builder.context, EventContext.ASSESSMENT);
        EventValidator.checkType(builder.type, EventType.ASSESSMENT);
        EventValidator.checkActorType(getActor(), Person.class);
        EventValidator.checkAction(builder.action, AssessmentEvent.class);
        EventValidator.checkObjectType(getObject(), Assessment.class);
        EventValidator.checkGeneratedType(getGenerated(), Attempt.class);

        this.context = builder.context;
        this.type = builder.type;
        this.action = builder.action;
    }

    /**
     * @return the context
     */
    @Override
    @Nonnull
    public EventContext getContext() {
        return context;
    }

    /**
     * @return the type
     */
    @Override
    @Nonnull
    public EventType getType() {
        return type;
    }

    /**
     * @return the action
     */
    @Override
    @Nonnull
    public Action getAction() {
        return action;
    }

    /**
     * Initialize default parameter values in the builder.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Event.Builder<T>  {
        private EventContext context;
        private EventType type;
        private Action action;
        /*
         * Constructor
         */
        public Builder() {
            context(EventContext.ASSESSMENT);
            type(EventType.ASSESSMENT);
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
         * @param action
         * @return builder.
         */
        @Override
        public T action(Action action) {
            this.action = action;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable profile object.
         * @return a new AssessmentEvent instance.
         */
        public AssessmentEvent build() {
            return new AssessmentEvent(this);
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