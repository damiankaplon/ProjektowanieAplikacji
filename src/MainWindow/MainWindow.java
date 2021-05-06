package MainWindow;

import Data.Data;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.l2fprod.common.swing.JOutlookBar;
import com.l2fprod.common.swing.JTipOfTheDay;
import com.l2fprod.common.swing.tips.DefaultTip;
import com.l2fprod.common.swing.tips.DefaultTipModel;
import javafx.scene.chart.PieChart;
import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.DateListener;
import org.freixas.jcalendar.JCalendarCombo;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static javax.swing.SwingConstants.RIGHT;


public class MainWindow extends JFrame implements ActionListener {

    private static final int WIDTH_FRAME = 800;
    private static final int HEIGHT_FRAME = 600;
    private static final String FILE_WITH_TIPS = "D:\\JavaProjects\\ProjektowanieAplikacji\\out\\production\\" +
            "ProjektowanieAplikacji\\resources\\tips.txt";
    private static final String[] OPERATION_LIST = {"Minimum", "Maximum", "Zeros", "Set", "Randomize table", "AVG",
            "SUM"};

    private JButton toolBarButtonExit, toolBarButtonSave, toolBarMin, toolBarMax, saveOutlook, exitOutlook,
            toolBarZeros, toolBarSet, toolBarAbout, toolBarSum, toolBarAvg, tipOutlook, calendarOutlook, chartOutlook,
            zerosOutlook, sumOutlook, avgOutlook, setOutlook, randomOutlook, execute, toolBarHelp;
    private JMenuItem exit, saveToFile, open, zeros, min, max, setRandom, about, help, set, sum, avg;
    private Icon mSaveIcon16, jtbSaveIcon24, mExitIcon16, jtbExitIcon24, jtbMinIcon24, jtbMaxIcon24, jtbZerosIcon24,
            jtbSetIcon24, jtbAboutIcon24,  jtbSumIcon24, jtbAvgIcon24, mOpenIcon16, jtbHelpIcon24;
    private Data data;
    private JTextField enteredNumber;
    private JSpinner rowNumber, columnNumber;
    private final ComboBoxModel comboBoxModel = new ComboBoxModel(OPERATION_LIST);
    private final JComboBox<String> operationList = new JComboBox<>(comboBoxModel);
    private JTextArea resultArea;
    private JCalendarCombo calendarCombo;
    private final StatusBar statusBar = new StatusBar();

    public MainWindow() {
        super("Zadanie 1 GUI v.1.0.1");
        setSize(setWindowSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed
             * @param e - Window Event object
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

        JTipOfTheDay jTipOfTheDay = new JTipOfTheDay(createTipModel(FILE_WITH_TIPS));
        jTipOfTheDay.showDialog(this);
    }

    /**
     * Calculates sizes of frame, based on properties of a screen.
     * At the beginning default frameSize is set as 800, 600 through static final variables of the class
     * WIDTH_FRAME and HEIGHT_FRAME
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
        this.saveToFile = createJMenuItem("Save to file", this.mSaveIcon16, KeyStroke.getKeyStroke(KeyEvent.VK_S,
                KeyEvent.ALT_MASK));
        this.open = createJMenuItem("Open from file", this.mOpenIcon16,KeyStroke.getKeyStroke(KeyEvent.VK_O,
                KeyEvent.ALT_MASK));
        file.add(this.exit);
        file.add(this.open);
        file.add(this.saveToFile);

        this.about = createJMenuItem("About",null, null);
        this.help = createJMenuItem("Help", null, KeyStroke.getKeyStroke(
                KeyEvent.VK_H, KeyEvent.ALT_MASK));
        help.add(this.about);
        help.add(this.help);

        this.zeros = createJMenuItem("Fill with zeros", null, KeyStroke.getKeyStroke(KeyEvent.VK_F3,
                KeyEvent.ALT_MASK));
        this.min = createJMenuItem("Minimum Value", null, KeyStroke.getKeyStroke(KeyEvent.VK_F1,
                KeyEvent.ALT_MASK));
        this.max = createJMenuItem("Maximum Value", null, KeyStroke.getKeyStroke(KeyEvent.VK_F2,
                KeyEvent.ALT_MASK));
        this.setRandom = createJMenuItem("Set random numbers in table", null, KeyStroke.getKeyStroke(
                KeyEvent.VK_F6, KeyEvent.ALT_MASK));
        this.set = createJMenuItem("Set value", null, KeyStroke.getKeyStroke(
                KeyEvent.VK_F7, KeyEvent.ALT_MASK));
        this.sum = createJMenuItem("Summary values", null, KeyStroke.getKeyStroke(
                KeyEvent.VK_F8, KeyEvent.ALT_MASK));
        this.avg = createJMenuItem("Average value", null, KeyStroke.getKeyStroke(
                KeyEvent.VK_F9, KeyEvent.ALT_MASK));
        calculations.add(this.max);
        calculations.add(this.min);
        calculations.add(this.zeros);
        calculations.add(this.setRandom);
        calculations.add(this.set);
        calculations.add(this.sum);
        calculations.add(this.avg);

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
        this.toolBarAbout = createJButtonToolBar("Show about", this.jtbAboutIcon24);
        this.toolBarSum = createJButtonToolBar("Sum values", this.jtbSumIcon24);
        this.toolBarAvg = createJButtonToolBar("Avg value", this.jtbAvgIcon24);
        this.toolBarHelp = createJButtonToolBar("Help", this.jtbHelpIcon24);

        jToolBar.add(this.toolBarButtonExit);
        jToolBar.add(this.toolBarButtonSave);
        jToolBar.addSeparator();
        jToolBar.add(this.toolBarMax);
        jToolBar.add(this.toolBarMin);
        jToolBar.add(this.toolBarZeros);
        jToolBar.add(this.toolBarSet);
        jToolBar.add(this.toolBarSum);
        jToolBar.add(this.toolBarAvg);
        jToolBar.addSeparator();
        jToolBar.add(this.toolBarAbout);
        jToolBar.add(this.toolBarHelp);

        return jToolBar;
    }

    /**
     * @param tooltip - Description that appears on mouse over event
     * @param icon - buttons icon
     * @return JButton with set tooltip text, icon and added actionListener
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
        this.enteredNumber.setHorizontalAlignment(RIGHT);
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
        centerPanel.add(columnNumberL, cc.xyw(11,2,2, CellConstraints.RIGHT, CellConstraints.CENTER));
        centerPanel.add(this.columnNumber, cc.xyw(13,2,1, CellConstraints.FILL, CellConstraints.CENTER));

        this.data = new Data();
        JTable table = new JTable(this.data);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        for (int i=0; i<5;i++){
            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, cc.xywh(2,3,12,6, CellConstraints.FILL, CellConstraints.FILL));

        JLabel chooseOperation = new JLabel("Choose Operation:");
        centerPanel.add(chooseOperation, cc.xyw(2, 10, 4, CellConstraints.RIGHT, CellConstraints.FILL));
        centerPanel.add(this.operationList, cc.xyw(7, 10, 3, CellConstraints.FILL, CellConstraints.CENTER));
        this.execute = new JButton("Execute");
        this.execute.addActionListener(this);
        centerPanel.add(this.execute, cc.xyw(10, 10, 1, CellConstraints.RIGHT, CellConstraints.CENTER));


        this.resultArea = new JTextArea();
        this.resultArea.setEditable(false);
        JScrollPane scrollForResultsArea = new JScrollPane(this.resultArea);
        scrollForResultsArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                "Results"));
        centerPanel.add(scrollForResultsArea, cc.xywh(2,12,12, 3, CellConstraints.FILL,
                CellConstraints.FILL));

        centerPanel.add(createJOutlookBar(), cc.xywh(14,2,3,13,CellConstraints.FILL,
                CellConstraints.FILL));

        this.calendarCombo = new JCalendarCombo();
        this.calendarCombo.addDateListener(new DateListener() {
            @Override
            public void dateChanged(DateEvent dateEvent) {
                String date = String.valueOf(calendarCombo.getDate());
                String dateStyled = date.substring(0, 10) + " " + date.substring(25, 29);
                resultArea.append(dateStyled + '\n');
            }
        });
        centerPanel.add(this.calendarCombo, cc.xyw(11, 10, 2, CellConstraints.RIGHT,
                CellConstraints.CENTER));

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
            this.jtbAboutIcon24 = createMyIcon("about.png");
            this.jtbSumIcon24 = createMyIcon("sum24.png");
            this.jtbAvgIcon24 = createMyIcon("avg24.png");
            this.mOpenIcon16 = createMyIcon("open16.png");
            this.jtbHelpIcon24 = createMyIcon("help.png");
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
     * @param e - Action Event object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.exit || e.getSource()==this.toolBarButtonExit || e.getSource()==this.exitOutlook){
            confirmExit();
        }

        if (e.getSource()==this.saveOutlook || e.getSource()==this.toolBarButtonSave || e.getSource()==this.saveToFile){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save to file");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String fileToSave = fileChooser.getSelectedFile().toString();
                try (PrintWriter out = new PrintWriter(fileToSave)){
                    System.out.println(fileToSave);
                    this.data.saveData(out);
                }
                 catch (Exception e1) {
                     this.resultArea.append("Error while saving" + '\n');
                     JOptionPane.showMessageDialog(this, "Error while saving",
                             "Alert!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource()==this.open){
            JFileChooser fileChooser2 = new JFileChooser();
            fileChooser2.setDialogTitle("Open File");
            int userSelection = fileChooser2.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String fileToOpen = fileChooser2.getSelectedFile().toString();
                try {
                    Scanner in = new Scanner(new FileInputStream(fileToOpen));
                    this.data.readData(in);

                }
                catch (FileNotFoundException e1) {
                   this.resultArea.append("File Not Found" + '\n');
                   JOptionPane.showMessageDialog(this, "File Not Found",
                           "Alert!", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception e2){
                    this.resultArea.append("Error while opening the file" + '\n');
                    JOptionPane.showMessageDialog(this, "Error while opening the file",
                            "Alert!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource()==this.setRandom || e.getSource() == this.randomOutlook){
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
                catch (Exception e1) {
                    this.resultArea.append("Not a valid value" + '\n');
                    JOptionPane.showMessageDialog(this, "Not a valid value",
                            "Alert!", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if (chosenOperation == 5){
                this.resultArea.append("Avg value: "+ (this.data.getAvg()) + '\n');
            }
            else if (chosenOperation == 6){
                this.resultArea.append("Sum of values: "+ (this.data.getSum()) + '\n');
            }
        }
        if (e.getSource()==this.toolBarSet || e.getSource()==this.set || e.getSource() == this.setOutlook){
            try {
                Float enteredNumber = Float.parseFloat(this.enteredNumber.getText());
                int chosenRow = (int) this.rowNumber.getValue() - 1;
                int chosenCol = (int) this.columnNumber.getValue() - 1;
                this.data.setValueAt(enteredNumber, chosenRow, chosenCol);
            }
            catch (Exception e1) {
                this.resultArea.append("Not a valid value" + '\n');
                JOptionPane.showMessageDialog(this, "Not a valid value",
                        "Alert!", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == this.zeros || e.getSource() == this.zerosOutlook || e.getSource() == this.toolBarZeros){
            this.data.setZeros();
            this.resultArea.append("Table has been reset" + '\n');
        }

        if (e.getSource() == this.min || e.getSource() == this.toolBarMin){
            this.resultArea.append("Minimum value: "+ (this.data.getMinimumValue()) + '\n');
        }

        if (e.getSource() == this.max || e.getSource() == this.toolBarMax){
            this.resultArea.append("Maximum value: "+ (this.data.getMaximumValue()) + '\n');
        }

        if(e.getSource() == this.about || e.getSource() == this.toolBarAbout){
            AboutWindow aboutWindow = new AboutWindow();
            aboutWindow.setVisible(true);
        }

        if(e.getSource() == this.toolBarSum || e.getSource()==this.sum || e.getSource() == this.sumOutlook){
            this.resultArea.append("Sum of values: "+ (this.data.getSum()) + '\n');
        }

        if(e.getSource()==this.avg || e.getSource() == this.toolBarAvg || e.getSource() == this.avgOutlook){
            this.resultArea.append("Avg value: "+ (this.data.getAvg()) + '\n');
        }

        if (e.getSource()==this.help || e.getSource()==this.toolBarHelp){
            HelpWindow helpWindow = new HelpWindow();
            helpWindow.setVisible(true);
        }

        if (e.getSource() == this.chartOutlook){
            new ChartWindow(this.data.getMaximumValue(), this.data.getMinimumValue(), this.data);
        }

        if (e.getSource() == this.tipOutlook){
            JTipOfTheDay jTipOfTheDay = new JTipOfTheDay(createTipModel(FILE_WITH_TIPS));
            jTipOfTheDay.showDialog(this);
        }

        if (e.getSource() == this.calendarCombo){
            this.resultArea.append(String.valueOf(this.calendarCombo.getDate()) + '\n');
        }
    }

    /**
     * @return JOutlookBar
     */
    private JOutlookBar createJOutlookBar(){
        this.saveOutlook = initializeButton("Save", null);
        this.exitOutlook = initializeButton("Exit", null);
        this.zerosOutlook = initializeButton("Reset", null);
        JOutlookBar jOutlookBar = new JOutlookBar();
        Component[] components = {
                this.saveOutlook, this.zerosOutlook,  this.exitOutlook
        };
        jOutlookBar.add(createJOutlookBarTab(components));

        this.tipOutlook = initializeButton("Tip", null);
        this.calendarOutlook = initializeButton("Calendar", null);
        this.chartOutlook = initializeButton("Chart", null);
        Component[] components2 = {
          this.chartOutlook, this.calendarOutlook, this.tipOutlook
        };
        jOutlookBar.add(createJOutlookBarTab(components2));

        this.avgOutlook = initializeButton("Avg", null);
        this.sumOutlook = initializeButton("Sum", null);
        this.randomOutlook = initializeButton("Rand", null);
        this.setOutlook = initializeButton("Set", null);
        Component[] components3 = {
                this.avgOutlook, this.sumOutlook, this.randomOutlook, this.setOutlook
        };

        jOutlookBar.add(createJOutlookBarTab(components3));
        jOutlookBar.setTitleAt(0, "File");
        jOutlookBar.setTitleAt(1, "Tools");
        jOutlookBar.setTitleAt(2, "Calc");

        return jOutlookBar;
    }

    /**
     * @param components Array of components that will be added to tab tab
     * @return JPanel that is addable to JOutlookBar
     */
    private JPanel createJOutlookBarTab(Component[] components){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
            for (Component component : components){
            jPanel.add(component);
            }
        jPanel.setBackground(Color.LIGHT_GRAY);
        return jPanel;
    }

    /**
     * @param title text to set on button
     * @param icon icon to set on button
     * @return JButton with added action listener, icon and text
     */
    private JButton initializeButton(String title, Icon icon){
        JButton jButton = new JButton(title, icon);
        jButton.addActionListener(this);
        jButton.setPreferredSize(new Dimension(101, 30));
        return jButton;
    }

    /**
     * @param fileSource path to file containing tips. 1 tip 1 row.
     * @return TipModel containing tips from certain file.
     */
    private DefaultTipModel createTipModel(String fileSource) {
        DefaultTipModel tipModel = new DefaultTipModel();
        try {
            Scanner in = new Scanner(new FileInputStream(fileSource));
            while (in.hasNextLine()) {
                DefaultTip tip = new DefaultTip("", in.nextLine());
                tipModel.add(tip);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Error while creating tips");
        }
        return tipModel;
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}
