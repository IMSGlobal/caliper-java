package org.imsglobal.caliper.metrics;

import java.util.ArrayList;
import java.util.List;

public class BaseProfile {
    private final String id;
    private final String type;
    private final String action;
    private String parentId;
    private String referrerId;
    private String title;
    private List<String> learningObjectives = new ArrayList<String>();
    private List<String> topics = new ArrayList<String>();
    private boolean isGradable;
    private boolean isAssignable;
    private boolean isShareable;
    private String status;

    /**
     * @param builder apply builder object properties to the profile object.
     */
    protected BaseProfile(Builder<?> builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.action = builder.action;
        this.parentId = builder.parentId;
        this.referrerId = builder.referrerId;
        this.title = builder.title;
        this.learningObjectives = builder.learningObjectives;
        this.topics = builder.topics;
        this.isAssignable = builder.isAssignable;
        this.isGradable = builder.isGradable;
        this.isShareable = builder.isShareable;
        this.status = builder.status;
    }

    /**
     * @return identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * @return type.
     */
    public String getType() {
        return type;
    }

    /**
     * @return action.
     */
    public String getAction() {
        return action;
    }

    /**
     * @return parent identifier.
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @return referrer.
     */
    public String getReferrerId() {
        return referrerId;
    }

    /**
     * @return title.
     */
    public String getTitle() {
        return title;
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
     * @return gradable.
     */
    public boolean getIsGradable() {
        return isGradable;
    }

    /**
     * @return isAssignable.
     */
    public boolean getIsAssignable() {
        return isAssignable;
    }

    /**
     * @return isShareable.
     */
    public boolean getIsShareable() {
        return isShareable;
    }

    /**
     * @return status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Initialize default parameter values in the builder (not in the outer profile class).
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> {
        private String id;
        private String type;
        private String action;
        private String parentId;
        private String referrerId;
        private String title;
        private List<String> learningObjectives = new ArrayList<String>();
        private List<String> topics = new ArrayList<String>();
        private boolean isAssignable = false;
        private boolean isGradable = false;
        private boolean isShareable = false;
        private String status;

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
         * @param type
         * @return type
         */
        public T type(String type) {
            this.type = type;
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
         * @param status
         * @return current status of activity.
         */
        public T status(String status) {
            this.status = status;
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