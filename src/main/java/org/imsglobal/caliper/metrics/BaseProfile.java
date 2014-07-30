package org.imsglobal.caliper.metrics;

import java.util.ArrayList;
import java.util.List;

public class BaseProfile {
    private String name;
    private String partOf;
    private String about;
    private List<String> objectType = new ArrayList<String>();
    private final String action;
    private List<String> alignedLearningObjectives = new ArrayList<String>();
    private List<String> keyword = new ArrayList<String>();
    private String inLanguage;

    /**
     * @param builder apply builder object properties to the profile object.
     */
    protected BaseProfile(Builder<?> builder) {
        this.name = builder.name;
        this.partOf = builder.partOf;
        this.about = builder.about;
        this.objectType = builder.objectType;
        this.action = builder.action;
        this.alignedLearningObjectives = builder.alignedLearningObjectives;
        this.keyword = builder.keyword;
        this.inLanguage = builder.inLanguage;
    }

    /**
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return parent identifier.
     */
    public String getPartOf() {
        return partOf;
    }

    /**
     * @return the subject matter of the content.
     */
    public String about() {
        return about;
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
    public List<String> getalignedLearningObjectives() {
        return alignedLearningObjectives;
    }

    /**
     * @return topics.
     */
    public List<String> getKeyword() {
        return keyword;
    }

    /**
     * @return language code of the content per the IETF BCP 47 standard.
     */
    public String getInLanguage() {
        return inLanguage;
    }

    /**
     * Initialize default parameter values in the builder (not in the outer profile class).
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> {
        private String name;
        private String partOf;
        private String about;
        private List<String> objectType;
        private String action;
        private List<String> alignedLearningObjectives = new ArrayList<String>();
        private List<String> keyword = new ArrayList<String>();
        private String inLanguage;

        protected abstract T self();

        /**
         * @param name
         * @return learning activity name.
         */
        public T name(String name) {
            this.name = name;
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
         * @param about
         * @return the subject matter of the content.
         */
        public T about(String about) {
            this.about = about;
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
         * @param alignedLearningObjectives
         * @return list of learning objectives associated with the profile.
         */
        public T alignedLearningObjectives(List<String> alignedLearningObjectives) {
            this.alignedLearningObjectives = alignedLearningObjectives;
            return self();
        }

        /**
         * @param keyword
         * @return keywords or tags used to describe content.
         */
        public T keyword(List<String> keyword) {
            this.keyword = keyword;
            return self();
        }

        /**
         * @param inLanguage
         * @return language code of the content per the IETF BCP 47 standard.
         */
        public T inLanguage(String inLanguage) {
            this.inLanguage = inLanguage;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable metric profile object.
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