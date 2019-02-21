package com.comsince.github.callback;


import com.comsince.github.ByteBufferList;
import com.comsince.github.DataEmitter;

public interface DataCallback {
    public class NullDataCallback implements DataCallback {
        @Override
        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            bb.recycle();
        }
    }

    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb);
}
