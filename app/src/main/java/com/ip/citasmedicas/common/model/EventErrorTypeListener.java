package com.ip.citasmedicas.common.model;

public interface EventErrorTypeListener {
    void onError(int typeEvent, int resMsg);
    void onOk(int typeEvent, int resMsg);
}