package org.imsglobal.caliper.metrics;

import java.util.ArrayList;
import java.util.List;

public class AssessmentProfile extends BaseProfile {
    private int maxSubmissionsAllowed;
    private int submissionCount;
    private int maxTimeAllowed;
    private int timeTaken;
    private List<String> assets = new ArrayList<String>();

    /**
     * @param builder apply builder object properties to the profile object.
     */
    protected AssessmentProfile(Builder<?> builder) {
        super(builder);
        this.maxSubmissionsAllowed = builder.maxSubmissionsAllowed;
        this.submissionCount = builder.submissionCount;
        this.maxTimeAllowed = builder.maxTimeAllowed;
        this.timeTaken = builder.timeTaken;
        this.assets = builder.assets;
    }

    /**
     * @return maximum number of submissions allowed.
     */
    public int getMaxSubmissionsAllowed() {
        return maxSubmissionsAllowed;
    }

    /**
     * @return current submission count.
     */
    public int getSubmissionCount() {
        return submissionCount;
    }

    /**
     * @return maximum time allowed for the assessment.
     */
    public int getMaxTimeAllowed() {
        return maxTimeAllowed;
    }

    /**
     * @return time taken.
     */
    public int getTimeTaken() {
        return timeTaken;
    }

    /**
     * @return list of asset references associated with the assessment.
     */
    public List<String> getAssets() {
        return assets;
    }

    /**
     *
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends BaseProfile.Builder<T>  {
        private int maxSubmissionsAllowed;
        private int submissionCount;
        private int maxTimeAllowed;
        private int timeTaken;
        private List<String> assets = new ArrayList<String>();

        /**
         * @param maxSubmissionsAllowed
         * @return Assessment setting specifying the maximum number of allowed submissions.
         */
        public T maxSubmissionsAllowed(int maxSubmissionsAllowed) {
            this.maxSubmissionsAllowed = maxSubmissionsAllowed;
            return self();
        }

        /**
         * @param submissionCount
         * @return current count of submissions.
         */
        public T submissionCount(int submissionCount) {
            this.submissionCount = submissionCount;
            return self();
        }

        /**
         * TODO time interval: Fractional hours, minutes?
         * @param maxTimeAllowed
         * @return Assessment setting specifying the maximum time allowed to complete the assessment.
         */
        public T maxTimeAllowed(int maxTimeAllowed) {
            this.maxTimeAllowed = maxTimeAllowed;
            return self();
        }

        /**
         * @param timeTaken
         * @return time spent on assessment.
         */
        public T timeTaken(int timeTaken) {
            this.timeTaken = timeTaken;
            return self();
        }

        /**
         * @param assets
         * @return URI list of item assets:, hint, model answer, etc.
         */
        public T assets(List<String> assets) {
            this.assets = assets;
            return self();
        }

        /**
         * Client invokes build method sans parameters in order to create an immutable profile object.
         * @return a new instance of the AssessmentProfile.
         */
        public AssessmentProfile build() {
            return new AssessmentProfile(this);
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
     *
     * @return a new instance of the builder.
     */
    public static Builder<?> builder() {
        return new Builder2();
    }
}