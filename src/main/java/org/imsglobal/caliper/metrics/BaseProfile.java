package org.imsglobal.caliper.metrics;

import java.util.ArrayList;
import java.util.List;

public class BaseProfile {
    private final String id;
    private String partOf;
    private List<String> objectType = new ArrayList<String>();
    private final String action;
    private List<String> learningObjectives = new ArrayList<String>();
    private List<String> topics = new ArrayList<String>();

    /**
     * @param builder apply builder object properties to the profile object.
     */
    protected BaseProfile(Builder<?> builder) {
        this.id = builder.id;
        this.partOf = builder.partOf;
        this.objectType = builder.objectType;
        this.action = builder.action;
        this.learningObjectives = builder.learningObjectives;
        this.topics = builder.topics;
    }

    /**
     * @return identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * @return parent identifier.
     */
    public String getPartOf() {
        return partOf;
    }

    /**
     * @return objectType.
     */
    public List<String> getObjectType() {
        return objectType;
    }

    /**
     * @return action.
     */
    public String getAction() {
        return action;
    }

    /**
     * @return learning objectives.
     */
    public List<String> getLearningObjectives() {
        return learningObjectives;
    }

    /**
     * @return topics.
     */
    public List<String> getTopics() {
        return topics;
    }

    /**
     * Initialize default parameter values in the builder (not in the outer profile class).
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> {
        private String id;
        private String partOf;
        private List<String> objectType;
        private String action;
        private List<String> learningObjectives = new ArrayList<String>();
        private List<String> topics = new ArrayList<String>();

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
         * @param partOf
         * @return parent learning activity.
         */
        public T partOf(String partOf) {
            this.partOf = partOf;
            return self();
        }

        /**
         * @param objectType
         * @return objectType
         */
        public T objectType(List<String> objectType) {
            this.objectType = objectType;
            return self();
        }

        /**
         * @param action
         * @return action.
         */
        public T action(String action) {
            this.action = action;
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
         * Client invokes build method sans parameters in order to create an immutable metric profile object.
         * @return a new instance of the BaseProfile.
         */
        public BaseProfile build() {
            return new BaseProfile(this);
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
     * Static factory
     * @return a new instance of the builder.
     */
    public static Builder<?> builder() {
        return new Builder2();
    }
}