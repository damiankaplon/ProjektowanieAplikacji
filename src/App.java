import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class App extends JFrame {

    private static final int WIDTH_FRAME = 800;
    private static final int HEIGHT_FRAME = 600;

    public App() {
        super("Zadanie 1 GUI v.1.0.1");
        setSize(setWindowSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
        JPanel contentPane = (JPanel) getContentPane();

        contentPane.setLayout(new BorderLayout());
        contentPane.add(createMenu(),BorderLayout.NORTH);

    }

    /**
     * Calculates sizes of frame, based on properties of a screen.
     * --------------------------------------------------------------
     * At the beginning default frameSize is set as 800, 600 through static final variables of the class
     * WIDTH_FRAME and HEIGHT_FRAME
     * --------------------------------------------------------------
     * @return Dimenosion (frameSize) which is, calculated, preferred size for GUI
     */
    private Dimension setWindowSize(){

        Dimension frameSize = new Dimension(WIDTH_FRAME,HEIGHT_FRAME);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        else if(frameSize.height < screenSize.height/2)
            frameSize.height = screenSize.height/2;
        if(frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        else if(frameSize.width < screenSize.width/2)
            frameSize.width = screenSize.width/2;
        return frameSize;
    }

    /**
     * Invoked when try to close the app.
     */
    private void confirmExit(){

    int decision = JOptionPane.showConfirmDialog(
            null, "Close the app?");
    if (decision == JOptionPane.YES_OPTION){
        System.exit(0);
    }
    }


    private JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu file = createJMenu("File", KeyEvent.VK_F);
        JMenu help = createJMenu("Help", KeyEvent.VK_H);
        JMenuItem exit = createJMenuItem("Exit", null, null);
        file.add(exit);
        menuBar.add(file);
        menuBar.add(help);
        return  menuBar;
    }

    /**
     * @param name name of Menu
     * @param keyEvent shorcut key
     * @return created JMenu
     */
    public JMenu createJMenu(String name, int keyEvent) {
        JMenu jMenu = new JMenu(name);
        jMenu.setMnemonic(keyEvent);
        return jMenu;
    }


    /**
     *
     * @param name of the Item on the "List" of menu
     * @param icon icon
     * @param key access accelerator
     * @return JMenuItem
     */
    public JMenuItem createJMenuItem(String name, Icon icon, KeyStroke key) {
        JMenuItem jMI;
        if(icon != null)
            jMI = new JMenuItem(name,icon);
        else jMI = new JMenuItem(name);
        jMI.setAccelerator(key);
        //jMI.addActionListener(this);
        return jMI;
    }

    public static void main(String[] args) {
	App app = new App();
	app.setVisible(true);
    }
}
