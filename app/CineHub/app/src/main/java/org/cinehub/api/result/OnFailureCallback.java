package org.cinehub.api.result;

/**
 * Callback to be called on failure.
 * @param <T> type of the value to be passed to the callback
 * @author Jkutkut
 */
public interface OnFailureCallback<T> {
    /**
     * Called on failure.
     * @param t value to be passed to the callback
     * @implNote It is meant to be implemented with a lambda expression.
     */
    void onFailure(T t);
}
