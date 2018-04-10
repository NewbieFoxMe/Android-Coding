package club.playerfox.netpractice;

public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
