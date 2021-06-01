package Data;


import javax.swing.table.AbstractTableModel;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class which is actual model of the table in the app. Lets to make basic operations and calculations
 * Represents an employee.
 * @author Damian Kaplon
 * @author kaplon.damian99@gmail.com Date: 22.05.2021
 * @version 1.0.1
 */
public class Data extends AbstractTableModel {
    private final int columnRowCount=5;
    private float[][] data;

    public Data(){
        this.data = new float[columnRowCount][columnRowCount];
        setZeros();
        this.fireTableDataChanged();
    }

    /**
     * @return <code> data </code>-table containing all data
     */
    public float[][] getData() {
        return this.data;
    }


    /**
     * Set all values in the table on 0
     */
    public void setZeros(){
        for (int i = 0; i<columnRowCount; i++){
            for (int j = 0; j<columnRowCount; j++){
                this.data[i][j]= (float) 0;
            }
        }
        this.fireTableDataChanged();
    }

    /**
     * Sets random values in the table
     */
    public void setRandom(){
        for (int i = 0; i<columnRowCount; i++){
            for (int j = 0; j<columnRowCount; j++){
                this.data[i][j]= (float) Math.random() * 10;
            }
        }
        fireTableDataChanged();
    }

    /**
     * @return Minimum Value in the table
     */
    public float getMinimumValue(){
        Float minimum = null;
        for (int i=0; i<columnRowCount; i++){
            for (int j=0; j<columnRowCount; j++){
                if (i == 0 & j == 0) {
                    minimum = this.data[0][0];
                }
                else if (minimum > this.data[i][j]) {
                    minimum = this.data[i][j];
                }
            }
        }
        return (float) minimum;
    }

    /**
     * @return <code>sum </code> of all values in the table
     */
    public float getSum(){
        float sum = (float) 0;
            for (int i=0; i<columnRowCount; i++){
                for (int j=0; j<columnRowCount; j++){
                    sum += this.data[i][j];
                }
            }
            return sum;
    }

    /**
     * @return <code>avg</code> - average value from table
     */
    public float getAvg(){
        float sum = (float) 0;
        for (int i=0; i<columnRowCount; i++){
            for (int j=0; j<columnRowCount; j++){
                sum += this.data[i][j];
            }
        }

        return sum/(this.columnRowCount*this.columnRowCount);
    }

    /**
     * @return Maximum Value in the table
     */
    public float getMaximumValue(){
        float maximum = (float) 0;
        for (int i=0; i<columnRowCount; i++){
            for (int j=0; j<columnRowCount; j++){
                if (maximum < this.data[i][j]) {
                    maximum = this.data[i][j];
                }
            }
        }
        return maximum;
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    @Override
    public int getRowCount() {
        return columnRowCount;
    }

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    @Override
    public int getColumnCount() {
        return columnRowCount;
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex    the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data[rowIndex][columnIndex];
    }

    /**
     *  @param  aValue   value to assign to cell
     *  @param  rowIndex   row of cell
     *  @param  columnIndex  column of cell
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Float Value = (Float) aValue;
            this.data[rowIndex][columnIndex] =Value;
            fireTableDataChanged();
    }

    /**
     * @param out output stream
     */
    public void saveData(PrintWriter out) {
        for (int i=0; i<5; i++){
            for (int j=0;j<5;j++)
                out.print(this.data[i][j] + "\n");
        }
    }

    /**
     * Reads data from input stream and save it to <code>this.data</code>
     * @param in - (Scanner) FileInputStream
     */
    public void readData(Scanner in) {
        for (int i = 0; i<5; i++) {
            for (int j = 0; j < 5; j++) {
                String line = in.nextLine();
                this.data[i][j] = Float.parseFloat(line);
            }
        }
        fireTableDataChanged();
    }

    /**
     * @param number number to check
     * @return occurrences of number
     */
    public int getOccurrences(float number){
        int intNumber = (int) number;
        int occurrences = 0;
        for (int i=0; i<columnRowCount; i++){
            for (int j=0; j<columnRowCount; j++){
            int iteration = (int) this.data[i][j];
                if (iteration == intNumber){
                    occurrences += 1;
                }
            }
        }
        return occurrences;
    }
}