package ua.gaponov.posterminal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilesUtils {

    public static void saveTextFile(String filePath, String text) {
        checkFileDirAndCreateDir(filePath);
        try (FileWriter file = new FileWriter(filePath, Charset.forName("UTF-8"))) {
            file.write(text);
        } catch (IOException e) {
            Logger.getLogger(FilesUtils.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void checkFileDirAndCreateDir(String filePath) {
        File dir = new File(new File(filePath).getParent());
        if (!dir.exists() && !dir.mkdirs()) {
            Logger.getLogger(FilesUtils.class.getName()).log(Level.SEVERE, "Error create dir = {}", dir.getAbsolutePath());
        }
    }

    public static void deleteFile(String fileName) throws IOException {
        File file = new File(fileName);
        Files.delete(file.toPath());
    }

    public static boolean fileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public static FileInputStream getFileInputStream(String fileName)
            throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            FilesUtils.checkFileDirAndCreateDir(fileName);
            FilesUtils.saveTextFile(fileName, "");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        return fileInputStream;
    }
}
