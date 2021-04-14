import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.sun.xml.internal.fastinfoset.util.StringArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App extends JFrame implements ActionListener {

    private static final int WIDTH_FRAME = 800;
    private static final int HEIGHT_FRAME = 600;

    private JButton toolBarButtonExit, toolBarButtonSave;
    private JMenuItem exit, saveToFile;
    private Icon mSaveIcon16, jtbSaveIcon24, mExitIcon16, jtbExitIcon24;
    private JTable table;


    public App() {
        super("Zadanie 1 GUI v.1.0.1");
        setSize(setWindowSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
        createIcons();
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        setJMenuBar(createMenu());
        contentPane.add(createToolBar(), BorderLayout.NORTH);
        contentPane.add(createCenterPanel(),BorderLayout.CENTER);

    }

    /**
     * Calculates sizes of frame, based on properties of a screen.
     * --------------------------------------------------------------
     * At the beginning default frameSize is set as 800, 600 through static final variables of the class
     * WIDTH_FRAME and HEIGHT_FRAME
     * --------------------------------------------------------------
     *
     * @return Dimenosion (frameSize) which is, calculated, preferred size for GUI
     */
    private Dimension setWindowSize() {

        Dimension frameSize = new Dimension(WIDTH_FRAME, HEIGHT_FRAME);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        else if (frameSize.height < screenSize.height / 2)
            frameSize.height = screenSize.height / 2;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        else if (frameSize.width < screenSize.width / 2)
            frameSize.width = screenSize.width / 2;
        return frameSize;
    }

    /**
     * Invoked when try to close the app.
     */
    private void confirmExit() {

        int decision = JOptionPane.showOptionDialog(this, "Do u want to close the app?",
                "Closing", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,null,
                JOptionPane.YES_OPTION);
        if (decision == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * @return menuBar which is the final shape of Menu Bar in the app
     */
    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = createJMenu("File", KeyEvent.VK_F);
        JMenu help = createJMenu("Help", KeyEvent.VK_H);

        this.exit = createJMenuItem("Exit", this.mExitIcon16, KeyStroke.getKeyStroke(KeyEvent.VK_X,
                KeyEvent.ALT_MASK));
        this.saveToFile = createJMenuItem("Save to file", mSaveIcon16, KeyStroke.getKeyStroke(KeyEvent.VK_S,
                KeyEvent.ALT_MASK));
        file.add(exit);
        file.add(saveToFile);

        menuBar.add(file);
        menuBar.add(help);

        return menuBar;
    }

    /**
     * @param name     name of Menu
     * @param keyEvent shorcut key
     * @return created JMenu
     */
    private JMenu createJMenu(String name, int keyEvent) {
        JMenu jMenu = new JMenu(name);
        jMenu.setMnemonic(keyEvent);
        return jMenu;
    }


    /**
     * @param name of the Item
     * @param icon icon
     * @param key  access accelerator
     * @return JMenuItem
     */
    private JMenuItem createJMenuItem(String name, Icon icon, KeyStroke key) {
        JMenuItem jMI;
        if (icon != null)
            jMI = new JMenuItem(name, icon);
        else jMI = new JMenuItem(name);
        jMI.setAccelerator(key);
        jMI.addActionListener(this);
        return jMI;
    }

    private  JToolBar createToolBar() {
        JToolBar jToolBar = new JToolBar();
        jToolBar.setFloatable(false);
        jToolBar.add(Box.createHorizontalStrut(5));

        this.toolBarButtonExit = createJButtonToolBar("sex", jtbExitIcon24);
        this.toolBarButtonSave = createJButtonToolBar("xd", jtbSaveIcon24);


        jToolBar.add(this.toolBarButtonExit);
        jToolBar.add(this.toolBarButtonSave);


        return jToolBar;
    }

    /**
     * @param tooltip - Description that appears on mouse over
     * @param icon - buttons icon
     * @return Button
     */
    private  JButton createJButtonToolBar(String tooltip, Icon icon) {
        JButton jb = new JButton("", icon);
        jb.setToolTipText(tooltip);
        jb.addActionListener(this);
        return jb;
    }

    private JPanel createCenterPanel(){
        JPanel centerPanel = new JPanel();
        FormLayout formLayout = new FormLayout(
                "5dlu,30dlu,30dlu,3dlu,30dlu,3dlu,30dlu,30dlu,3dlu,30dlu,3dlu,30dlu,30dlu,3dlu,30dlu,5dlu",
                "5dlu,15dlu,3dlu,15dlu,3dlu,15dlu,3dlu,15dlu,15dlu,3dlu,15dlu,3dlu,15dlu,15dlu,15dlu,5dlu"
        );
        centerPanel.setLayout(formLayout);
        String[] columnSpec = {"A", "B", "C", "D"};
        Float[][] data = {
                {new Float(1),new Float(1),new Float(1),new Float(1)},
                {new Float(1),new Float(1),new Float(1),new Float(1)},
                {new Float(1),new Float(1),new Float(1),new Float(1)},
                {new Float(1),new Float(1),new Float(1),new Float(1)},
                {new Float(1),new Float(1),new Float(1),new Float(1)},
                {new Float(1),new Float(1),new Float(1),new Float(1)}
        };
        this.table = new JTable(data,columnSpec);
        CellConstraints cc = new CellConstraints();
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        centerPanel.add(scrollPane, cc.xywh(2,4,9,6, CellConstraints.FILL, CellConstraints.FILL));

        return centerPanel;
    }

    /**
     * creates icons. Handles exception while loading
     */
    private void createIcons() {
        try {
        mExitIcon16 = createMyIcon("exit16.png");
        jtbExitIcon24 = createMyIcon("exit24.png");
        mSaveIcon16 = createMyIcon("save16.png");
        jtbSaveIcon24 = createMyIcon("save24.png");
        }
        catch(Exception e) {
            System.out.println("ERROR - Failure while creating icons");
        }
    }

    /**
     * @param file name of file containing image, with extension, in resources folder
     * @return icon
     */
    private Icon createMyIcon(String file) {
        String name = "/resources/"+file;
        Icon icon = new ImageIcon(getClass().getResource(name));
        return icon;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.exit || e.getSource()==this.toolBarButtonExit){
            confirmExit();
        }

    }


    public static void main(String[] args) {
        App app = new App();
        app.setVisible(true);
    }
}
