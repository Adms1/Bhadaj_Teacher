package anandniketan.com.bhadajteacher.Models;

import java.util.ArrayList;

/**
 * Created by Megha on 9/15/2017.
 */

public class LoginModel {

    private String StaffID;
    private String Emp_Code;
    private String Emp_Name;
    private String DepratmentID;
    private String DesignationID;
    private String DeviceId;
    private ArrayList<ClassDetail> getGetclassDetailsArrayList;

    public LoginModel() {
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        StaffID = staffID;
    }

    public String getEmp_Code() {
        return Emp_Code;
    }

    public void setEmp_Code(String emp_Code) {
        Emp_Code = emp_Code;
    }

    public String getEmp_Name() {
        return Emp_Name;
    }

    public void setEmp_Name(String emp_Name) {
        Emp_Name = emp_Name;
    }

    public String getDepratmentID() {
        return DepratmentID;
    }

    public void setDepratmentID(String depratmentID) {
        DepratmentID = depratmentID;
    }

    public String getDesignationID() {
        return DesignationID;
    }

    public void setDesignationID(String designationID) {
        DesignationID = designationID;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public ArrayList<ClassDetail> getGetGetclassDetailsArrayList() {
        return getGetclassDetailsArrayList;
    }

    public void setGetGetclassDetailsArrayList(ArrayList<ClassDetail> getGetclassDetailsArrayList) {
        this.getGetclassDetailsArrayList = getGetclassDetailsArrayList;
    }

    public class ClassDetail {

        public ClassDetail() {
        }

        private String StandardID;
        private String ClassID;
        private String Standard;
        private String Classes;

        public String getStandardID() {
            return StandardID;
        }

        public void setStandardID(String standardID) {
            StandardID = standardID;
        }

        public String getClassID() {
            return ClassID;
        }

        public void setClassID(String classID) {
            ClassID = classID;
        }

        public String getStandard() {
            return Standard;
        }

        public void setStandard(String standard) {
            Standard = standard;
        }

        public String getClasses() {
            return Classes;
        }

        public void setClasses(String classes) {
            Classes = classes;
        }
    }
}
