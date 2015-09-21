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

package org.imsglobal.caliper.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.entities.w3c.Membership;
import org.imsglobal.caliper.entities.w3c.Organization;

import javax.annotation.Nullable;

public class LearningContext {

    @JsonProperty("edApp")
    private SoftwareApplication edApp;

    @JsonProperty("group")
    private Organization group;

    @JsonProperty("membership")
    private Membership membership;

    @JsonProperty("federatedSession")
    private Session federatedSession;

    /**
     * @param builder apply builder object properties to the LearningContext object.
     */
    protected LearningContext(Builder<?> builder) {
        this.edApp = builder.edApp;
        this.group = builder.group;
        this.membership = builder.membership;
        this.federatedSession = builder.federatedSession;
    }

    /**
     * The edApp context.
     * @return the educational app.
     */
    @Nullable
    public SoftwareApplication getEdApp() {
        return edApp;
    }

    /**
     * The Group context.
     * @return organizational group.
     */
    @Nullable
    public Organization getGroup() {
        return group;
    }

    /**
     * The Membership context.
     * @return the membership
     */
    @Nullable
    public Membership getMembership() {
        return membership;
    }

    /**
     * LTI consumer session provided as part of the launch context.
     * @return the federated session.
     */
    public Session getFederatedSession() {
        return federatedSession;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> {
        private SoftwareApplication edApp;
        private Organization group;
        private Membership membership;
        private Session federatedSession;

        protected abstract T self();

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
        public T federatedSession(Session federatedSession) {
            this.federatedSession = federatedSession;
            return self();
        }

        /**
         * Client invokes the build method in order to create an immutable LearningContext object.
         * @return the LearningContext.
         */
        public LearningContext build() {
            return new LearningContext(this);
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

    /**
     * Static factory method.
     * @return a new instance of the builder.
     */
    public static Builder<?> builder() {
        return new Builder2();
    }
}