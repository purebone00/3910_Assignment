package infosys.beans;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetCollection;

public class TimeSheetCollector implements TimesheetCollection{
    
    @Inject EditableTimesheet currentTimesheet;
    List<Timesheet> timesheets = new ArrayList<Timesheet>();
    

    public TimeSheetCollector () {
        addTimesheet();        
    }
    
    public List<Timesheet> getTimesheets() {
        return timesheets;
    }

    public List<Timesheet> getTimesheets(Employee e) {
      List<Timesheet> temp = new ArrayList<Timesheet>();
      for (Timesheet x : timesheets) {
          if (x.getEmployee().equals(e)) {
              temp.add(x);
          }
      }
      return temp;
    }

    public Timesheet getCurrentTimesheet(Employee e) {
        for (Timesheet x : timesheets) {
            if (x.getEmployee().equals(e)) {
                return x;
            }
        }
        return null;
    }
    
    public Timesheet getCurrentTimesheet() {
    	return currentTimesheet;
    }

    public String addTimesheet() {
    	EditableTimesheet temp = new EditableTimesheet();
        timesheets.add(temp);
        currentTimesheet = temp;
        return null;
    }
    

}
