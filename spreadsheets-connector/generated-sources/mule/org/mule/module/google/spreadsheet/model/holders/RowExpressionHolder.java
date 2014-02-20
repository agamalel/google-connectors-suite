
package org.mule.module.google.spreadsheet.model.holders;

import java.util.List;
import javax.annotation.Generated;
import org.mule.module.google.spreadsheet.model.Cell;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-20T04:29:29-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class RowExpressionHolder {

    protected Object rowNumber;
    protected int _rowNumberType;
    protected Object cells;
    protected List<Cell> _cellsType;

    /**
     * Sets rowNumber
     * 
     * @param value Value to set
     */
    public void setRowNumber(Object value) {
        this.rowNumber = value;
    }

    /**
     * Retrieves rowNumber
     * 
     */
    public Object getRowNumber() {
        return this.rowNumber;
    }

    /**
     * Sets cells
     * 
     * @param value Value to set
     */
    public void setCells(Object value) {
        this.cells = value;
    }

    /**
     * Retrieves cells
     * 
     */
    public Object getCells() {
        return this.cells;
    }

}
