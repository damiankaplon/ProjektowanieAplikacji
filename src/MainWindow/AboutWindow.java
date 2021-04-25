package MainWindow;

import javax.swing.*;
import java.awt.*;


public class AboutWindow extends JDialog {

    JLabel icon, author, version, email;

    public AboutWindow() {
        setSize(setWindowSize());
        setTitle("About");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        try{
           this.icon = new JLabel(new ImageIcon(getClass().getResource("/resources/about.jpg")));
        }
        catch (Exception e){
            this.icon = new JLabel();
        }
        this.author = new JLabel("author: Damian Kapłon");
        this.version = new JLabel("version: V1.0.1");
        this.email = new JLabel("email: kaplon.damian99@gmail.com");
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 4;
       // c.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(this.icon, c);
        c.gridheight = 1;
        c.weighty = 0.3;
        c.gridx = 1;
        c.gridy = 0;
        contentPane.add(this.author, c);
        c.gridx = 1;
        c.gridy = 1;
        contentPane.add(this.version, c);
        c.gridx = 1;
        c.gridy = 2;
        contentPane.add(this.email, c);
    }

    private Dimension setWindowSize() {
        Dimension frameSize = new Dimension(400, 300);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        else if (frameSize.height < screenSize.height / 4)
            frameSize.height = screenSize.height / 4;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        else if (frameSize.width < screenSize.width / 4)
            frameSize.width = screenSize.width / 4;
        return frameSize;
    }

}