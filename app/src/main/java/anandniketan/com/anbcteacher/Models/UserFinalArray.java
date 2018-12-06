package anandniketan.com.anbcteacher.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserFinalArray {
    @SerializedName("StaffID")
    @Expose
    private String staffID;
    @SerializedName("Emp_Code")
    @Expose
    private String empCode;
    @SerializedName("Emp_Name")
    @Expose
    private String empName;
    @SerializedName("Depratment")
    @Expose
    private String depratment;
    @SerializedName("Designation")
    @Expose
    private String designation;
    @SerializedName("EmailID")
    @Expose
    private String emailID;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("DOB")
    @Expose
    private String dob;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDepratment() {
        return depratment;
    }

    public void setDepratment(String depratment) {
        this.depratment = depratment;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
