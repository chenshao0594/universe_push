package com.comsince.github.future;


public interface DependentFuture<T> extends Future<T>, DependentCancellable {
}
