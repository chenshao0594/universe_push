package com.comsince.github.core.callback;


import com.comsince.github.core.ByteBufferList;
import com.comsince.github.core.DataEmitter;

public interface DataCallback {
    public class NullDataCallback implements DataCallback {
        @Override
        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            bb.recycle();
        }
    }

    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb);
}
