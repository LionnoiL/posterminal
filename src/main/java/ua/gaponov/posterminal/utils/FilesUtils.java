package ua.gaponov.posterminal.utils;

import java.io.*;
import java.nio.file.Files;
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

    public static void deleteFile(String fileName) throws IOException {
        File file = new File(fileName);
        //file.delete();
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

    public static void read(int countdown) throws IOException {
        File f = new File("test.txt");
        if (f.canRead())
        {
            // читаем удобным способом
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            // закрываем поток
            br.close();
            fr.close();
        }
        else
        {
            if (countdown < 1)
                return;
            read(countdown - 1);
        }
    }
}
