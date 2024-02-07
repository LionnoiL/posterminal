package ua.gaponov.posterminal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormsUtils {

    public static java.util.List<JButton> findAllButtons(Container container) {
        java.util.List<JButton> buttons = new ArrayList<>();
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                buttons.add((JButton) component);
            }
            if (component instanceof Container) {
                buttons.addAll(findAllButtons((Container) component));
            }
        }
        return buttons;
    }
}
