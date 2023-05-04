package ua.gaponov.posterminal.utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andriy Gaponov
 */
public class FilesUtils {

    public static void saveTextFile(String filePath, String text) {
        checkFileDirAndCreateDir(filePath);
        try (FileWriter file = new FileWriter(filePath)) {
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

    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
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
