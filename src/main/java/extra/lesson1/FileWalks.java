package extra.lesson1;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileWalks {

    public static void main(String[] args) {
        try {
            Stream<Path> pathStream = Files.walk(Paths.get("."));
            pathStream.filter(Files::isRegularFile)
                    .filter(FileSystems.getDefault().getPathMatcher("glob:**/*.java")::matches)
                    .flatMap(ThrowingFunction.unchecked(path->Files.readAllLines(path).stream()
                            .filter(line-> Pattern.compile("public class").matcher(line).find())
                    .map(line -> path.getFileName() + " >> " + line)))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FunctionalInterface
    public interface ThrowingFunction<T, R, E extends Throwable> {
        static <T, R, E extends Throwable> Function<T, R> unchecked(ThrowingFunction<T, R, E> f) {
            return t -> {
                try {
                    return f.apply(t);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            };
        }

        R apply(T t) throws E;
    }
}
