package ua.gaponov.posterminal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImagesUtils {

    public static Image getImage(String imageName){
        return new ImageIcon(ImagesUtils.class.getClassLoader()
                .getResource("images/"+imageName)).getImage();
    }

    public static ImageIcon getIcon(String iconName){
        return new ImageIcon(ImagesUtils.class.getClassLoader()
                .getResource("images/"+iconName));
    }
}
