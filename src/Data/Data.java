package Data;

import javax.swing.table.AbstractTableModel;


public class Data extends AbstractTableModel {
    private final int columnRowCount=5;
    private final String[] columnNames = new String[]{"A", "B", "C", "D", "E"};
    private Float[][] data = new Float[columnRowCount][columnRowCount];

    public Data(){
        setZeros();
        this.fireTableDataChanged();
    }

    /**
     * @return <code> data </code>-table containing all data
     */
    public Float[][] getData() {
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
}
