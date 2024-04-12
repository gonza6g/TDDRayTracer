import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class FileUtils {
    public static void writeBytesToFile(String fileName, byte[] data) {
        try {
            Files.write(Paths.get(fileName), data);
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }
}