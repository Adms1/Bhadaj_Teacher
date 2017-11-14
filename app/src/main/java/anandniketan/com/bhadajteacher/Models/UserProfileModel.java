package anandniketan.com.bhadajteacher.Models;

import java.util.ArrayList;

/**
 * Created by admsandroid on 9/20/2017.
 */


public class UserProfileModel {

    private String StaffID;
    private String Emp_Code;
    private String Emp_Name;
    private String Depratment;
    private String Designation;
    private String EmailID;
    private String Mobile;
    private String Image;
    private ArrayList<ClassDetail> getclassDetailsArrayList;

    public UserProfileModel(){}

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

    public String getDepratment() {
        return Depratment;
    }

    public void setDepratment(String depratment) {
        Depratment = depratment;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public ArrayList<ClassDetail> getGetclassDetailsArrayList() {
        return getclassDetailsArrayList;
    }

    public void setGetclassDetailsArrayList(ArrayList<ClassDetail> getclassDetailsArrayList) {
        this.getclassDetailsArrayList = getclassDetailsArrayList;
    }

    public class ClassDetail{

        public ClassDetail(){

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
