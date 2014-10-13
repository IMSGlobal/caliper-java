package org.imsglobal.caliper.entities.assignable;

import org.imsglobal.caliper.entities.CaliperDigitalResource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Assignable Digital Resource
 */
@JsonInclude(Include.NON_NULL)
public class CaliperAssignableDigitalResource extends CaliperDigitalResource implements Assignable {

    public enum Type {
        ASSESSMENT("http://purl.imsglobal.org/caliper/v1/Assessment"),
        ASSESSMENT_ITEM("http://purl.imsglobal.org/caliper/v1/AssessmentItem");

        private final String uri;

        /**
         * Private constructor
         * @param uri
         */
        private Type(final String uri) {
            this.uri = uri;
        }

        /**
         * @return URI string
         */
        public String uri() {
            return uri;
        }
    }

    private long dateCreated, datePublished, dateToActivate, dateToShow, dateToStartOn, dateToSubmit;
    private int maxAttempts, maxSubmits;
    private double maxScore;

    /**
     * @param builder apply builder object properties to the Target object.
     */
    protected CaliperAssignableDigitalResource(Builder<?> builder) {
        super(builder);
        this.dateCreated = builder.dateCreated;
        this.datePublished = builder.datePublished;
        this.dateToActivate = builder.dateToActivate;
        this.dateToShow = builder.dateToShow;
        this.dateToStartOn = builder.dateToStartOn;
        this.dateToSubmit = builder.dateToSubmit;
        this.maxAttempts = builder.maxAttempts;
        this.maxSubmits = builder.maxSubmits;
        this.maxScore = builder.maxScore;
    }

    /**
     * @return the dateCreated
     */
    public long getDateCreated() {
        return dateCreated;
    }

    /**
     * @return the datePublished
     */
    public long getDatePublished() {
        return datePublished;
    }

    /**
     * @return the dateToActivate
     */
    public long getDateToActivate() {
        return dateToActivate;
    }

    /**
     * @return the dateToShow
     */
    public long getDateToShow() {
        return dateToShow;
    }

    /**
     * @return the dateToStartOn
     */
    public long getDateToStartOn() {
        return dateToStartOn;
    }

    /**
     * @return the dateToSubmit
     */
    public long getDateToSubmit() {
        return dateToSubmit;
    }

    /**
     * @return the maxAttempts
     */
    public int getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * @return the maxSubmits
     */
    public int getMaxSubmits() {
        return maxSubmits;
    }

    /**
     * @return the maxScore
     */
    public double getMaxScore() {
        return maxScore;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends CaliperDigitalResource.Builder<T>  {
        private long dateCreated, datePublished, dateToActivate, dateToShow, dateToStartOn, dateToSubmit;
        private int maxAttempts, maxSubmits;
        private double maxScore;

        /**
         * Initialize type with default value.
         */
        public Builder() {
            type(CaliperDigitalResource.Type.CALIPER_ASSIGNABLE_DIGITAL_RESOURCE.uri());
        }

        /**
         * @param dateCreated
         * @return builder
         */
        public T dateCreated(long dateCreated) {
            this.dateCreated = dateCreated;
            return self();
        }

        /**
         * @param datePublished
         * @return builder
         */
        public T datePublished(long datePublished) {
            this.datePublished = datePublished;
            return self();
        }

        /**
         * @param dateToActivate
         * @return builder
         */
        public T dateToActivate(long dateToActivate) {
            this.dateToActivate = dateToActivate;
            return self();
        }

        /**
         * @param dateToShow
         * @return builder
         */
        public T dateToShow(long dateToShow) {
            this.dateToShow = dateToShow;
            return self();
        }

        /**
         * @param dateToStartOn
         * @return builder
         */
        public T dateToStartOn(long dateToStartOn) {
            this.dateToStartOn = dateToStartOn;
            return self();
        }

        /**
         * @param dateToSubmit
         * @return builder
         */
        public T dateToSubmit(long dateToSubmit) {
            this.dateToSubmit = dateToSubmit;
            return self();
        }

        /**
         * @param maxAttempts
         * @return builder
         */
        public T maxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
            return self();
        }

        /**
         * @param maxSubmits
         * @return builder
         */
        public T maxSubmits(int maxSubmits) {
            this.maxSubmits = maxSubmits;
            return self();
        }

        /**
         * @param maxScore
         * @return builder
         */
        public T maxScore(int maxScore) {
            this.maxScore = maxScore;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of Target.
         */
        public CaliperAssignableDigitalResource build() {
            return new CaliperAssignableDigitalResource(this);
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