package Data;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private final List<String> columnNames = new ArrayList<>();
    private Float[][] data = new Float[5][5];

    public Data(){
       for (int i=0;i<5;i++){
           int x = 65+i;
           char defaultName = (char) x;
           this.columnNames.add(Character.toString(defaultName));

           for (int j=0;j<5;j++){
               this.data[i][j]= new Float(0);
           }
       }
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public Float[][] getData() {
        return data;
    }


}
