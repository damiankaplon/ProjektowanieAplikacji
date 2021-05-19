package MainWindow.ChartWindow;

import Data.Data;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class ChartWindow extends JDialog {
    public ChartWindow(float max, float min, Data data){
        super();
        this.setTitle("Chart");
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        DefaultPieDataset dataset = createDataset(max, min, data);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.white);
        this.add(chartPanel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    /**
     * @param dataset data from which create a Chart
     * @return PieChart
     */
    private JFreeChart createChart(DefaultPieDataset dataset) {
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Number Occurrences",
                dataset,
                false, false, false);

        return pieChart;
    }

    /**
     * @param max   max value form data to create Dataset
     * @param min   start value from data to create Dataset
     * @param data  data to create a dataset
     * @return      Dataset
     */
    private DefaultPieDataset createDataset(float max, float min, Data data) {
        int intMax = (int) max;
        int intMin = (int) min;
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = intMin; i<=intMax; i++){
            int occurrences = data.getOccurrences(i);
            if (occurrences == 0) continue;
            dataset.setValue("" + i, occurrences);
        }
        return dataset;
    }


}
