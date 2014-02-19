package com.zenika.util;

/**
 * An implementation of Try representing a failure.
 * @param <E>
 */
public final class Failure<E> extends Try<E> {

    private final Exception exception;

    /**
     * Wrap the given exception in an instance of Failure.
     * @param exception
     */
    public Failure(Exception exception) {
        assert exception != null;
        this.exception = exception;
    }

    /**
     * Return the exception wrapped by this instance.
     * @return
     */
    public Exception getException() {
        return exception;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Type getType() {
        return Type.FAILURE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Failure failure = (Failure) o;

        if (!exception.equals(failure.exception)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return exception.hashCode();
    }
}
