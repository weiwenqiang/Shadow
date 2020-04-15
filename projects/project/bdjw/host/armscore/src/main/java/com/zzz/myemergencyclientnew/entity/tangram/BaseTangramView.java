package com.zzz.myemergencyclientnew.entity.tangram;

import java.util.ArrayList;
import java.util.List;

public class BaseTangramView<T> {
    public int type;
    public BaseStyle style = new BaseStyle();
    public List<T> items = new ArrayList<>();

    public BaseTangramView(int type) {
        this.type = type;
    }

    public BaseTangramView() {
    }
}
