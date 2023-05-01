package ua.gaponov.posterminal.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wmcon
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

    public static void deleteFile(String fileName){
        File file = new File(fileName);
        file.delete();
    }

    public static boolean fileExist(String fileName){
        File file = new File(fileName);
        return file.exists();
    }
}
