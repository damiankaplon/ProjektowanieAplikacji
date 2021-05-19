package MainWindow;

import javax.swing.*;
import java.util.Vector;


public class MyComboBoxModel<E> extends AbstractListModel<E> implements javax.swing.ComboBoxModel<E> {

    Vector<E> objects;
    Object selectedObject;

    /**
     * Constructs an object which is a legal ComboBoxModel, containing elements, injected as 1dim array as a param.
     * @param items 1dim array of elements to be contained in ComboBox
     */
    public MyComboBoxModel(E[] items) {
        objects = new Vector<>(items.length);
        int i,c;
        for ( i=0,c=items.length;i<c;i++ )
            objects.addElement(items[i]);
    }

    /**
     * Returns the length of the list.
     *
     * @return the length of the list
     */
    @Override
    public int getSize() {
        return objects.size();
    }

    /**
     * Returns the value at the specified index.
     *
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    @Override
    public E getElementAt(int index) {
        if ( index >= 0 && index < objects.size() )
            return objects.elementAt(index);
        else
            return null;
    }

    /**
     * Set the selected item. The implementation of this  method should notify
     * all registered <code>ListDataListener</code>s that the contents
     * have changed.
     *
     * @param anObject the list object to select or <code>null</code>
     *               to clear the selection
     */
    @Override
    public void setSelectedItem(Object anObject) {
        if ((selectedObject != null && !selectedObject.equals( anObject )) ||
                selectedObject == null && anObject != null) {
            selectedObject = anObject;
            fireContentsChanged(this, -1, -1);
        }
    }

    /**
     * Returns the selected item
     *
     * @return The selected item or <code>null</code> if there is no selection
     */
    @Override
    public Object getSelectedItem() {
        return selectedObject;
    }
}