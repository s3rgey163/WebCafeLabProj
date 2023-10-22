package ru.ssau.webcaffe.repo;

import lombok.Getter;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public class RepositoryResultHandler<T> {
    private T result;

    public RepositoryResultHandler(T result) {
        this.result = result;
    }

    public RepositoryResultHandler<T> doBeforeCloseSession(Consumer<T> action) {
        action.accept(result);
        return this;
    }

    public <R> Optional<R> thenApply(Function<T, R> function) {
        return Optional.of(function.apply(result));
    }

    public Optional<T> thenAccept(Consumer<T> action) {
        action.accept(result);
        return Optional.of(result);
    }

    public Optional<T> getResult() {
        return Optional.of(result);
    }
}
