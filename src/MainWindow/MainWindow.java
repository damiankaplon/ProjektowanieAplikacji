package MainWindow;

import Data.Data;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainWindow extends JFrame implements ActionListener {

    private static final int WIDTH_FRAME = 800;
    private static final int HEIGHT_FRAME = 600;
    private static final String[] OPERATION_LIST = {"Minimum", "Maximum", "Zeros", "Set", "Randomize table"};

    private JButton toolBarButtonExit, toolBarButtonSave, toolBarMin, toolBarMax, zerosCenter, saveCenter, exitCenter,
            toolBarZeros, toolBarSet, execute;
    private JMenuItem exit, saveToFile, zeros, min, max, setRandom;
    private Icon mSaveIcon16, jtbSaveIcon24, mExitIcon16, jtbExitIcon24, jtbMinIcon24, jtbMaxIcon24, jtbZerosIcon24,
            jtbSetIcon24;
    private Data data;
    private JTextField enteredNumber;
    private JSpinner rowNumber, columnNumber;
    private final JComboBox operationList = new JComboBox(OPERATION_LIST);;
    private JTextArea resultArea;
    private StatusBar statusBar = new StatusBar();

    public MainWindow() {
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
        contentPane.add(createCenterPanel(), BorderLayout.CENTER);
        contentPane.add(this.statusBar, BorderLayout.SOUTH);
    }

    /**
     * Calculates sizes of frame, based on properties of a screen.
     * --------------------------------------------------------------
     * At the beginning default frameSize is set as 800, 600 through static final variables of the class
     * WIDTH_FRAME and HEIGHT_FRAME
     * --------------------------------------------------------------
     *
     * @return Dimension (frameSize) which is, calculated, preferred size for GUI
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
     * Invoked when user try to close the app.
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
        JMenu calculations = createJMenu("Calculations", KeyEvent.VK_C);

        this.exit = createJMenuItem("Exit", this.mExitIcon16, KeyStroke.getKeyStroke(KeyEvent.VK_X,
                KeyEvent.ALT_MASK));
        this.saveToFile = createJMenuItem("Save to file", mSaveIcon16, KeyStroke.getKeyStroke(KeyEvent.VK_S,
                KeyEvent.ALT_MASK));
        file.add(exit);
        file.add(saveToFile);

        this.zeros = createJMenuItem("Fill with zeros", null, KeyStroke.getKeyStroke(KeyEvent.VK_F3,
                KeyEvent.ALT_MASK));
        this.min = createJMenuItem("Minimum Value", null, KeyStroke.getKeyStroke(KeyEvent.VK_F1,
                KeyEvent.ALT_MASK));
        this.max = createJMenuItem("Maximum Value", null, KeyStroke.getKeyStroke(KeyEvent.VK_F2,
                KeyEvent.ALT_MASK));
        this.setRandom = createJMenuItem("Set random numbers in table", null, KeyStroke.getKeyStroke(
                KeyEvent.VK_F4, KeyEvent.ALT_MASK));
        calculations.add(this.max);
        calculations.add(this.min);
        calculations.add(this.zeros);
        calculations.add(this.setRandom);

        menuBar.add(file);
        menuBar.add(calculations);
        menuBar.add(help);

        return menuBar;
    }

    /**
     * @param name     name of Menu
     * @param keyEvent shortcut key
     * @return JMenu
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

    /**
     * @return JToolBar in finished shape
     */
    private  JToolBar createToolBar() {
        JToolBar jToolBar = new JToolBar();
        jToolBar.setFloatable(false);
        jToolBar.add(Box.createHorizontalStrut(5));

        //JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        this.toolBarButtonExit = createJButtonToolBar("Exit", this.jtbExitIcon24);
        this.toolBarButtonSave = createJButtonToolBar("Save to file", this.jtbSaveIcon24);
        this.toolBarMax = createJButtonToolBar("Show max value", this.jtbMaxIcon24);
        this.toolBarMin = createJButtonToolBar("Show min value", this.jtbMinIcon24);
        this.toolBarZeros = createJButtonToolBar("Zeros the table", this.jtbZerosIcon24);
        this.toolBarSet = createJButtonToolBar("Set value", this.jtbSetIcon24);

        jToolBar.add(this.toolBarButtonExit);
        jToolBar.add(this.toolBarButtonSave);
        jToolBar.addSeparator();
        jToolBar.add(this.toolBarMax);
        jToolBar.add(this.toolBarMin);
        jToolBar.add(this.toolBarZeros);
        jToolBar.add(this.toolBarSet);
        jToolBar.addSeparator();

        return jToolBar;
    }

    /**
     * @param tooltip - Description that appears on mouse over event
     * @param icon - buttons icon
     * @return Button
     */
    private  JButton createJButtonToolBar(String tooltip, Icon icon) {
        JButton jb = new JButton("", icon);
        jb.setToolTipText(tooltip);
        jb.addActionListener(this);
        return jb;
    }

    /**
     * Creates main panel of an app. Contains Labels, JSpinners, JTextFields, JTable.
     * @return JPanel
     */
    private JPanel createCenterPanel(){
        JPanel centerPanel = new JPanel();
        FormLayout formLayout = new FormLayout(
                "5dlu,50dlu,50dlu,3dlu,50dlu,3dlu,50dlu,50dlu,3dlu,50dlu,50dlu,50dlu,50dlu,3dlu,50dlu,5dlu",
                "5dlu,30dlu,3dlu,30dlu,3dlu,30dlu,3dlu,30dlu,3dlu,30dlu,3dlu,30dlu,30dlu,30dlu,5dlu"
        );
        centerPanel.setLayout(formLayout);
        CellConstraints cc = new CellConstraints();

        JLabel enterNumberL = new JLabel("Enter the number: ");
        this.enteredNumber = new JTextField("0");
        this.enteredNumber.setHorizontalAlignment(JTextField.RIGHT);
        centerPanel.add(enterNumberL, cc.xyw(2,2,2, CellConstraints.RIGHT, CellConstraints.CENTER));
        centerPanel.add(this.enteredNumber, cc.xy(5,2,CellConstraints.FILL, CellConstraints.CENTER));

        JLabel rowNumberL = new JLabel("Row number: ");
        SpinnerNumberModel spinnerNumberModel1 = new SpinnerNumberModel(1, 1, 5, 1);
        this.rowNumber = new JSpinner(spinnerNumberModel1);
        centerPanel.add(rowNumberL, cc.xyw(7,2,2, CellConstraints.RIGHT, CellConstraints.CENTER));
        centerPanel.add(this.rowNumber, cc.xy(10,2,CellConstraints.FILL, CellConstraints.CENTER));

        JLabel columnNumberL = new JLabel("Column number: ");
        SpinnerNumberModel spinnerNumberModel2 = new SpinnerNumberModel(1, 1, 5, 1);
        this.columnNumber = new JSpinner(spinnerNumberModel2);
        centerPanel.add(columnNumberL, cc.xyw(12,2,2, CellConstraints.RIGHT, CellConstraints.CENTER));
        centerPanel.add(this.columnNumber, cc.xyw(15,2,2, CellConstraints.FILL, CellConstraints.CENTER));

        this.saveCenter = new JButton("Save", this.jtbSaveIcon24);
        this.saveCenter.addActionListener(this);
        this.zerosCenter = new JButton("Reset");
        this.zerosCenter.addActionListener(this);
        this.exitCenter = new JButton("exit", this.jtbExitIcon24);
        this.exitCenter.addActionListener(this);
        centerPanel.add(this.saveCenter, cc.xyw(15,4,2, CellConstraints.FILL, CellConstraints.FILL));
        centerPanel.add(this.zerosCenter, cc.xyw(15,6,2,CellConstraints.FILL, CellConstraints.FILL));
        centerPanel.add(this.exitCenter, cc.xyw(15,8,2,CellConstraints.FILL, CellConstraints.FILL));

        this.data = new Data();
        JTable table = new JTable(this.data);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, cc.xywh(2,3,12,6, CellConstraints.FILL, CellConstraints.FILL));

        JLabel chooseOperation = new JLabel("Choose Operation:");
        centerPanel.add(chooseOperation, cc.xyw(2, 10, 4, CellConstraints.RIGHT, CellConstraints.FILL));
        centerPanel.add(this.operationList, cc.xyw(7, 10, 3, CellConstraints.FILL, CellConstraints.CENTER));
        this.execute = new JButton("Execute");
        this.execute.addActionListener(this);
        centerPanel.add(this.execute, cc.xyw(11, 10, 2, CellConstraints.FILL, CellConstraints.CENTER));


        this.resultArea = new JTextArea();
        this.resultArea.setEditable(false);
        JScrollPane scrollForResultsArea = new JScrollPane(this.resultArea);
        scrollForResultsArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                "Results"));
        centerPanel.add(scrollForResultsArea, cc.xywh(2,12,13, 3, CellConstraints.FILL,
                CellConstraints.FILL));

        return centerPanel;
    }

    /**
     * creates icons. Handles exception while loading
     */
    private void createIcons() {
            this.mExitIcon16 = createMyIcon("exit16.png");
            this.jtbExitIcon24 = createMyIcon("exit24.png");
            this.mSaveIcon16 = createMyIcon("save16.png");
            this.jtbSaveIcon24 = createMyIcon("save24.png");
            this.jtbMaxIcon24 = createMyIcon("max24.png");
            this.jtbMinIcon24 = createMyIcon("min24.png");
            this.jtbSetIcon24 = createMyIcon("set24.png");
            this.jtbZerosIcon24 = createMyIcon("zeros24.png");
    }

    /**
     * @param file name of file containing image, with extension, in resources folder
     * @return icon
     */
    private Icon createMyIcon(String file) {
        Icon icon = null;
        try {
            String name = "/resources/" + file;
            icon = new ImageIcon(getClass().getResource(name));
        }
        catch (Exception e){
            System.out.println("ERROR while creating icon");
        }
        return icon;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.exit || e.getSource()==this.toolBarButtonExit || e.getSource()==this.exitCenter){
            confirmExit();
        }

        if (e.getSource()==this.saveCenter || e.getSource()==this.toolBarButtonSave || e.getSource()==this.saveToFile){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save to file");
            int userSelection = fileChooser.showSaveDialog(this);
        }

        if (e.getSource()==this.setRandom){
            this.data.setRandom();
            this.resultArea.append("Table has been randomized" + '\n');
        }

        if (e.getSource() == this.execute){
            int chosenOperation = this.operationList.getSelectedIndex();
            if (chosenOperation == 0){
                this.resultArea.append("Minimum value: "+ (this.data.getMinimumValue()) + '\n');
            }
            else if (chosenOperation == 1){
                this.resultArea.append("Maximum value: "+ (this.data.getMaximumValue()) + '\n');
            }
            else if (chosenOperation == 2){
                this.data.setZeros();
                this.resultArea.append("Table has been reset" + '\n');
            }
            else if (chosenOperation == 4){
                this.data.setRandom();
                this.resultArea.append("Table has been randomized" + '\n');
            }
            else if (chosenOperation == 3){
                try {
                    Float enteredNumber = Float.parseFloat(this.enteredNumber.getText());
                    int chosenRow = (int) this.rowNumber.getValue() - 1;
                    int chosenCol = (int) this.columnNumber.getValue() - 1;
                    this.data.setValueAt(enteredNumber, chosenRow, chosenCol);
                }
                catch (Exception ArrayIndexOutOfBoundsException) {
                    this.resultArea.append("Array Index out of Bounds!" + '\n');
                    JOptionPane.showMessageDialog(this, "Array Index out of Bounds!",
                            "Alert!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource()==this.toolBarSet){
            try {
                Float enteredNumber = Float.parseFloat(this.enteredNumber.getText());
                int chosenRow = (int) this.rowNumber.getValue() - 1;
                int chosenCol = (int) this.columnNumber.getValue() - 1;
                this.data.setValueAt(enteredNumber, chosenRow, chosenCol);
            }
            catch (Exception ArrayIndexOutOfBoundsException) {
                this.resultArea.append("Array Index out of Bounds!" + '\n');
                JOptionPane.showMessageDialog(this, "Array Index out of Bounds!",
                        "Alert!", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == this.zeros || e.getSource() == this.zerosCenter || e.getSource() == this.toolBarZeros){
            this.data.setZeros();
            this.resultArea.append("Table has been reset" + '\n');
        }

        if (e.getSource() == this.min || e.getSource() == this.toolBarMin){
            this.resultArea.append("Minimum value: "+ (this.data.getMinimumValue()) + '\n');
        }

        if (e.getSource() == this.max || e.getSource() == this.toolBarMax){
            this.resultArea.append("Maximum value: "+ (this.data.getMaximumValue()) + '\n');
        }
    }


    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}
