package MainWindow;

import java.awt.BorderLayout;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;


public class HelpWindow extends JDialog {
    private static final long serialVersionUID = 1L;
    private JEditorPane editorPane;
    private URL url;

    public HelpWindow() {
        this.setTitle("HELP");
        setLocationRelativeTo(null);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        this.editorPane = new JEditorPane();
        this.editorPane.setEditable(false);
        this.url = HelpWindow.class.getResource("/resources/help.html");
        try {
            this.editorPane.setPage(this.url);
        } catch (Exception e) {
            this.editorPane.setText("Couldn't load URL");
        }
        this.add(new JScrollPane(this.editorPane), BorderLayout.CENTER);
    }
}