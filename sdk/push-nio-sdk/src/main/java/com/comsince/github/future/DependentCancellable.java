package com.comsince.github.future;

public interface DependentCancellable extends Cancellable {
    public DependentCancellable setParent(Cancellable parent);
}
