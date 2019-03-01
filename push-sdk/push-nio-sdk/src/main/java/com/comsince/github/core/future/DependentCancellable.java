package com.comsince.github.core.future;

public interface DependentCancellable extends Cancellable {
    public DependentCancellable setParent(Cancellable parent);
}
