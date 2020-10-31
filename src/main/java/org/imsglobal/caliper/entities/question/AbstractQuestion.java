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

package org.imsglobal.caliper.entities.question;

import javax.annotation.Nullable;

import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.resource.AbstractDigitalResource;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class provides a skeletal implementation of the Question interface
 * in order to minimize the effort required to implement the interface.
 */
public abstract class AbstractQuestion extends AbstractDigitalResource implements CaliperQuestion {

    @JsonProperty("questionPosed")
    private final String questionPosed;

    /**
     * @param builder apply builder object properties to the object.
     */
    protected AbstractQuestion(Builder<?> builder) {
        super(builder);
        this.questionPosed = builder.questionPosed;
    }

    /**
     * @return the questionPosed
     */
    @Nullable
    public String getQuestionPosed() {
        return questionPosed;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractDigitalResource.Builder<T>  {
        private String questionPosed;

        /*
         * Constructor
         */
        public Builder() {
            super.type(EntityType.QUESTION);
        }

        /**
         * @param questionPosed
         * @return builder.
         */
        public T questionPosed(String questionPosed) {
            this.questionPosed = questionPosed;
            return self();
        }
    }

    /**
     *
     */
    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }
}