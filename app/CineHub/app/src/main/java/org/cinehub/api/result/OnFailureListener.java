package org.cinehub.api.result;

public interface OnFailureListener<T> {
    void onFailure(T t);
}
