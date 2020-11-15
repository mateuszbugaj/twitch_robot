package Utils;

/**
 * Custom Function functional interface that throws Exception
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @param <K> the type of the exception thrown by the function that extends Exception class
 */
@FunctionalInterface
public interface FunctionWithException<T, R, K extends Exception>{

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws K thrown exception
     */
    R apply(T t) throws K;
}
