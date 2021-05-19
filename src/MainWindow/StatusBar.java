package MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for StatusBar shown at the bottom of the window of the app
 */
public class StatusBar extends JPanel {
    private JLabel info;
    public StatusBar(){
        this.setBackground(Color.gray);
        this.info = new JLabel("ON");
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.info);
    }
}
