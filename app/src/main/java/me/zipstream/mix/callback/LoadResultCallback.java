package me.zipstream.mix.callback;

public interface LoadResultCallback {

    int SUCCESS_OK = 1001;
    int SUCCESS_NONE = 1002;
    int ERROR_NET = 1003;

    void onSuccess(int resultCode, Object object);

    void onError(int resultCode, String msg);
}
