package MainWindow;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {
    private JLabel info;
    public StatusBar(){
        this.setBackground(Color.gray);
        this.info = new JLabel("ON");
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.info);
    }

    public void statusBarSetText(String text){
        this.info.setText(text);
    }

}
