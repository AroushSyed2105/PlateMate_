package view;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A panel containing a label and a text field.
 */
class LabelTextPanel extends JPanel {
    LabelTextPanel(JLabel label, Component inputField) {
        this.add(label);
        this.add(inputField);
    }
}
