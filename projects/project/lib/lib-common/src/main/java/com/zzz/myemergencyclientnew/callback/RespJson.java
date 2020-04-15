package com.zzz.myemergencyclientnew.callback;

import java.io.Serializable;

public class RespJson<T> implements Serializable {
    public int code;
    public String msg;
    public T data;
}
