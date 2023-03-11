package org.cinehub.api.result;

/**
 * Callback to be called on success when no value is returned.
 *
 * @author Jkutkut
 */
@FunctionalInterface
public interface OnSuccessCallback {
    /**
     * Called on success.
     * @implNote It is meant to be implemented with a lambda expression.
     */
    void onSuccess();
}
