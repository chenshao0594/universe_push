package com.comsince.github.core.future;


public interface DependentFuture<T> extends Future<T>, DependentCancellable {
}
