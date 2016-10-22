package infosys.beans;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.TimesheetRow;

@ApplicationScoped
public class TimeSheetCollector {
    
    List<EditableTimesheet> timesheets = new ArrayList<EditableTimesheet>();
    EditableTimesheet currentTimesheet;
    
    

	TimeSheetCollector() {
    	
    }
        
    public List<EditableTimesheet> getTimesheets() {
        return timesheets;
    }

    public String addTimesheet(Employee e) {
    	EditableTimesheet temp = new EditableTimesheet(e, getNextFriday(), createNewRows());    	
        timesheets.add(temp);
        setCurrentTimesheet(temp);
        return null;
    }
    
    public List<TimesheetRow> createNewRows() {
    	List<TimesheetRow> newRows = new ArrayList<TimesheetRow>();
    	newRows.add(new EditableRow());
    	newRows.add(new EditableRow());
    	newRows.add(new EditableRow());
    	newRows.add(new EditableRow());
    	newRows.add(new EditableRow());
    	return newRows;
    }
    
    public String addTimesheet() {
    	EditableTimesheet temp = new EditableTimesheet();    
		timesheets.add(temp);
        setCurrentTimesheet(temp);
		return null;
	}
    
	
	public EditableTimesheet getCurrentTimesheet() {
		if (currentTimesheet == null) {
			addTimesheet();
		}
		return currentTimesheet;
	}
	
	public EditableTimesheet getCurrentTimesheet(Employee employee) {
		boolean hasTimesheet = false;
		for (EditableTimesheet x : timesheets) {
			if (x.getEmployee().getEmpNumber() == employee.getEmpNumber()) {
				currentTimesheet = x;
				hasTimesheet = true;
			}
		}
		if (currentTimesheet == null || !hasTimesheet) {
			addTimesheet(employee);
		}
		return currentTimesheet;
	}

	public void setCurrentTimesheet(EditableTimesheet currentTimesheet) {
		this.currentTimesheet = currentTimesheet;
	}	
	
	public static Date getNextFriday() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
    	Date now = new Date();
    	cal.setTime(now);
    	int week = cal.get(Calendar.DAY_OF_WEEK);
    	return new Date(now.getTime() - 24 * 60 * 60 * 1000 * (week - 6));
    }

    

}
