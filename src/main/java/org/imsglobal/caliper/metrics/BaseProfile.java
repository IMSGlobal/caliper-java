package org.imsglobal.caliper.metrics;

import java.util.ArrayList;
import java.util.List;

public class BaseProfile {
    private final String id;
    private String parentId;
    private String referrerId;
    private String title;
    private List<String> learningObjectives = new ArrayList<String>();
    private List<String> topics = new ArrayList<String>();
    private boolean isGradable;
    private boolean isAssignable;
    private boolean isShareable;

    public static abstract class Builder<T extends Builder<T>> {
        private String id;
        private String parentId;
        private String referrerId;
        private String title;
        private List<String> learningObjectives = new ArrayList<String>();
        private List<String> topics = new ArrayList<String>();
        private boolean isAssignable = false;
        private boolean isGradable = false;
        private boolean isShareable = false;

        protected abstract T self();

        /**
         * @param id
         * @return identifier.
         */
        public T id(String id) {
            this.id = id;
            return self();
        }

        /**
         * @param parentId
         * @return parent learning activity.
         */
        public T parentId(String parentId) {
            this.parentId = parentId;
            return self();
        }

        /**
         * @param referrerId
         * @return source/provider of learning activity.
         */
        public T referrerId(String referrerId) {
            this.referrerId = referrerId;
            return self();
        }

        /**
         * @param title
         * @return title or label.
         */
        public T title(String title) {
            this.title = title;
            return self();
        }

        /**
         * @param learningObjectives
         * @return list of learning objectives associated with the profile.
         */
        public T learningObjectives(List<String> learningObjectives) {
            this.learningObjectives = learningObjectives;
            return self();
        }

        /**
         * @param topics
         * @return list of learning objectives associated with the profile.
         */
        public T topics(List<String> topics) {
            this.topics = topics;
            return self();
        }

        /**
         *
         * @param isAssignable
         * @return the activity is/is not assignable.
         */
        public T isAssignable(boolean isAssignable) {
            this.isAssignable = isAssignable;
            return self();
        }

        /**
         *
         * @param isGradable
         * @return the activity is/is not gradable.
         */
        public T isGradable(boolean isGradable) {
            this.isGradable = isGradable;
            return self();
        }

        /**
         *
         * @param isShareable
         * @return the activity is/is not shareable.
         */
        public T isShareable(boolean isShareable) {
            this.isShareable = isShareable;
            return self();
        }

        /**
         * Client invokes a build method sans parameters in order to create an immutable metric profile object.
         * @return a new instance of BaseProfile.
         */
        public BaseProfile build() {
            return new BaseProfile(this);
        }
    }

    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }

    public static Builder<?> builder() {
        return new Builder2();
    }

    /**
     * Copy parameter settings from the builder to the profile object.
     * @param builder
     */
    protected BaseProfile(Builder<?> builder) {
        this.id = builder.id;
        this.parentId = builder.parentId;
        this.referrerId = builder.referrerId;
        this.title = builder.title;
        this.learningObjectives = builder.learningObjectives;
        this.topics = builder.topics;
        this.isAssignable = builder.isAssignable;
        this.isGradable = builder.isGradable;
        this.isShareable = builder.isShareable;
    }
}