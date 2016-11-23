package infosys.beans;


import ca.bcit.infosys.timesheet.TimesheetRow;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Expands on the existing TimesheetRows class to make it interact with
 * the jsfPage Timesheet easier.
 * 
 * @author Albert
 *
 */
@SuppressWarnings("serial")
public class EditableRow extends TimesheetRow implements Serializable {

    private int rowId;

    public EditableRow() {
        super();
    }

    public EditableRow(final int id, final String wp,
            final BigDecimal[] hours, final String comments) {
        super( id, wp, hours, comments);
    }
    
    /**
     * returns the number of hours for Saturday.
     * @return number of hours for Saturday.
     */
    public BigDecimal getSaturday() {   
        return super.getHour(0);
    }

    /**
     * sets the number of hours for Saturday.
     * @param hours number of hours for Saturday.
     */
    public void setSaturday(BigDecimal hours) {
        super.setHour(0, hours);
    }


    /**
     * returns the number of hours for Sunday.
     * @return number of hours for Sunday.
     */
    public BigDecimal getSunday() {
        return super.getHour(1);
    }


    /**
     * sets the number of hours for Sunday.
     * @param hours number of hours for Sunday.
     */
    public void setSunday(BigDecimal hours) {
        super.setHour(1, hours);
    }


    /**
     * returns the number of hours for Monday.
     * @return number of hours for Monday.
     */
    public BigDecimal getMonday() {
        return super.getHour(2);
    }


    /**
     * sets the number of hours for Monday.
     * @param hours number of hours for Monday.
     */
    public void setMonday(BigDecimal hours) {
        super.setHour(2, hours);
    }

    /**
     * returns the number of hours for Tuesday.
     * @return number of hours for Tuesday.
     */
    public BigDecimal getTuesday() {
        return super.getHour(3);
    }


    /**
     * sets the number of hours for Tuesday.
     * @param hours number of hours for Tuesday.
     */
    public void setTuesday(BigDecimal hours) {
        super.setHour(3, hours);
    }


    /**
     * returns the number of hours for Wednesday.
     * @return number of hours for Wednesday.
     */
    public BigDecimal getWednesday() {
        return super.getHour(4);
    }


    /**
     * sets the number of hours for Wednesday.
     * @param hours number of hours for Wednesday.
     */
    public void setWednesday(BigDecimal hours) {
        super.setHour(4, hours);
    }


    /**
     * returns the number of hours for Thursday.
     * @return number of hours for Thursday.
     */
    public BigDecimal getThursday() {
        return super.getHour(5);
    }


    /**
     * sets the number of hours for Thursday.
     * @param hours number of hours for Thursday.
     */
    public void setThursday(BigDecimal hours) {
        super.setHour(5, hours);
    }


    /**
     * returns the number of hours for Friday.
     * @return number of hours for Friday.
     */
    public BigDecimal getFriday() {
        return super.getHour(6);
    }


    /**
     * sets the number of hours for Friday.
     * @param hours number of hours for Friday.
     */
    public void setFriday(BigDecimal hours) {
        super.setHour(6, hours);
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }


}

