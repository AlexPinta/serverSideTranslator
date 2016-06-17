package core;

public interface StreamTaskExecutor {
    void retrieveStreamData();

    void setStreamEnable(boolean streamEnable);

    boolean isStreamEnable();
}
