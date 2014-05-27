package com.zenika.util;

/**
 * An implementation of Try representing a success.
 * @param <E>
 */
public final class Success<E> extends Try<E> {

    private final E result;

    /**
     * Wrap the given value in an instance of Success.
     * @param result
     */
    public Success(E result) {
        this.result = result;
    }

    /**
     * Return the result wrapped by this instance.
     * @return
     */
    public E getResult() {
        return result;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Type getType() {
        return Type.SUCCESS;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        Success success = (Success) that;

        if (result != null ? !result.equals(success.result) : success.result != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return result != null ? result.hashCode() : 0;
    }
}
