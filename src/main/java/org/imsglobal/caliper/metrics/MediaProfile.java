package org.imsglobal.caliper.metrics;

public class MediaProfile extends BaseProfile {
    private int mediaFrame;
    private long mediaTimestamp;
    private int mediaTargetFrame;
    private long mediaTargetTimestamp;
    private int mediaLength;
    private int resolutionX;
    private int resolutionY;
    private int sizeX;
    private int sizeY;
    private boolean isEmbedded;

    public static abstract class Builder<T extends Builder<T>> extends BaseProfile.Builder<T>  {
        private int mediaFrame;
        private long mediaTimestamp;
        private int mediaTargetFrame;
        private long mediaTargetTimestamp;
        private int mediaLength;
        private int resolutionX;
        private int resolutionY;
        private int sizeX;
        private int sizeY;
        private boolean isEmbedded;

        /**
         * @param mediaFrame
         * @return current frame at time of action.
         */
        public T mediaFrame(int mediaFrame) {
            this.mediaFrame = mediaFrame;
            return self();
        }

        /**
         * @param mediaTimestamp
         * @return media timestamp at moment of action.
         */
        public T mediaTimestamp(long mediaTimestamp) {
            this.mediaTimestamp = mediaTimestamp;
            return self();
        }

        /**
         * @param mediaTargetFrame
         * @return target frame (start, forward/reverse, end).
         */
        public T mediaTargetFrame(int mediaTargetFrame) {
            this.mediaTargetFrame = mediaTargetFrame;
            return self();
        }

        /**
         * @param mediaTargetTimestamp
         * @return target media timestamp at moment of action.
         */
        public T mediaTargetTimestamp(long mediaTargetTimestamp) {
            this.mediaTargetTimestamp = mediaTargetTimestamp;
            return self();
        }

        /**
         * @param mediaLength
         * @return media length.
         */
        public T mediaLength(int mediaLength) {
            this.mediaLength = mediaLength;
            return self();
        }

        /**
         * @param resolutionX
         * @return number of horizontal pixels.
         */
        public T resolutionX(int resolutionX) {
            this.resolutionX = resolutionX;
            return self();
        }

        /**
         * @param resolutionY
         * @return number of vertical pixels.
         */
        public T resolutionY(int resolutionY) {
            this.resolutionY = resolutionY;
            return self();
        }

        /**
         * @param sizeX
         * @return number of horizontal pixels.
         */
        public T sizeX(int sizeX) {
            this.sizeX = sizeX;
            return self();
        }

        /**
         * @param sizeY
         * @return number of vertical pixels.
         */
        public T sizeY(int sizeY) {
            this.sizeY = sizeY;
            return self();
        }

        /**
         * @param isEmbedded
         * @return true or false.
         */
        public T isEmbedded(boolean isEmbedded) {
            this.isEmbedded = isEmbedded;
            return self();
        }

        /**
         * Client invokes the build method sans parameters in order to create an immutable profile object.
         * @return a new instance of MediaProfile.
         */
        public MediaProfile build() {
            return new MediaProfile(this);
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
    protected MediaProfile(Builder<?> builder) {
        super(builder);
        this.mediaFrame = builder.mediaFrame;
        this.mediaTimestamp = builder.mediaTimestamp;
        this.mediaTargetFrame = builder.mediaTargetFrame;
        this.mediaTargetTimestamp = builder.mediaTargetTimestamp;
        this.mediaLength = builder.mediaLength;
        this.resolutionX = builder.resolutionX;
        this.resolutionY = builder.resolutionY;
        this.sizeX = builder.sizeX;
        this.sizeY = builder.sizeY;
        this.isEmbedded = builder.isEmbedded;
    }
}