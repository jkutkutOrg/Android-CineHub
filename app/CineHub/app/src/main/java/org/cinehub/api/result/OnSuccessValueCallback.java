package org.cinehub.api.result;

/**
 * Listener to be called on success.
 * @param <T> type of the value to be returned
 * @author Jkutkut
 */
public interface OnSuccessValueCallback<T> {
    /**
     * Called on success.
     * @param v value to be returned
     * @implNote It is meant to be implemented with a lambda expression.
     */
    void onSuccess(T v);
}