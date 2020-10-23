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
import org.imsglobal.caliper.entities.outcome.Score;
import org.imsglobal.caliper.entities.outcome.Attempt;
import org.imsglobal.caliper.validators.EventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SupportedActions({ Action.GRADED })
public class GradeEvent extends AbstractEvent {

    @JsonProperty("object")
    private final Attempt object;

    @JsonProperty("generated")
    private final Score generated;

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(GradeEvent.class);

    /**
     * Utilize builder to construct OutcomeEvent.  Validate Outcome object copy rather than the
     * Outcome builder.  This approach protects the class against parameter changes from another
     * thread during the "window of vulnerability" between the time the parameters are checked
     * until when they are copied.
     *
     * @param builder
     */
    private GradeEvent(Builder<?> builder) {
        super(builder);

        EventValidator.checkType(this.getType(), EventType.GRADE);
        EventValidator.checkAction(this.getAction(), GradeEvent.class);

        this.object = builder.object;
        this.generated = builder.generated;
    }

    /**
     * Get the Attempt.
     * @return the object
     */
    @Override
    @Nonnull
    public Attempt getObject() {
        return object;
    }

    /**
     * Get the generated Score.
     * @return the generated object
     */
    @Override
    @Nullable
    public Score getGenerated() {
        return generated;
    }

    /**
     * Initialize default parameter values in the builder.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEvent.Builder<T>  {
        private Attempt object;
        private Score generated;

        /*
         * Constructor
         */
        public Builder() {
            super.type(EventType.GRADE);
        }

        /**
         * @param object
         * @return builder.
         */
        public T object(Attempt object) {
            this.object = object;
            return self();
        }

        /**
         * @param generated
         * @return builder.
         */
        public T generated(Score generated) {
            this.generated = generated;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable profile object.
         * @return a new OutcomeEvent instance.
         */
        public GradeEvent build() {
            return new GradeEvent(this);
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