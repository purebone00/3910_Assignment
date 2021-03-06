package ca.bcit.infosys.employee;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class representing a single Employee.
 *
 * @author Bruce Link
 *
 */
@XmlRootElement(name = "Employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    /** The employee's name. */
    private String name;
    /** The employee's employee number. */
    private int empNumber;
    /** The employee's login ID. */
    private String userName;
    /** The employee's password. */
    private String password;


    /**
     * The no-argument constructor. Used to create new employees from within the
     * application.
     */
    public Employee() {
    }

    /**
     * The argument-containing constructor. Used to create the initial employees
     * who have access as well as the administrator.
     *
     * @param empName the name of the employee.
     * @param number the empNumber of the user.
     * @param id the loginID of the user.
     */
    public Employee(final int number, final String id, final String empName,final String password) {
        name = empName;
        empNumber = number;
        userName = id;
        this.password = password;
    }

    /**
     * @return the name
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * @param empName the name to set
     */
    public void setName(final String empName) {
        name = empName;
    }

    /**
     * @return the empNumber
     */
    @XmlElement
    public int getEmpNumber() {
        return empNumber;
    }

    /**
     * @param number the empNumber to set
     */
    public void setEmpNumber(final int number) {
        empNumber = number;
    }

    /**
     * @return the userName
     */
    @XmlElement
    public String getUserName() {
        return userName;
    }

    /**
     * @param id the userName to set
     */
    public void setUserName(final String id) {
        userName = id;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
