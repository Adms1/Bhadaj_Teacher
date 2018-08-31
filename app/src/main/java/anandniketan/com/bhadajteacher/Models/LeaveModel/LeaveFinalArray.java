package anandniketan.com.bhadajteacher.Models.LeaveModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaveFinalArray {
//    ==============Get Head==============
    @SerializedName("EmployeeName")
    @Expose
    private String employeeName;
    @SerializedName("EmployeeID")
    @Expose
    private Integer employeeID;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

//    ================Leave detail============
@SerializedName("LeaveID")
@Expose
private Integer leaveID;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("LeaveStartDate")
    @Expose
    private String leaveStartDate;
    @SerializedName("LeaveEndDate")
    @Expose
    private String leaveEndDate;
    @SerializedName("LeaveDays")
    @Expose
    private String leaveDays;
    @SerializedName("ARStartDate")
    @Expose
    private String aRStartDate;
    @SerializedName("AREndDate")
    @Expose
    private String aREndDate;
    @SerializedName("ARDays")
    @Expose
    private String aRDays;
    @SerializedName("ARBy")
    @Expose
    private String aRBy;
    @SerializedName("CL")
    @Expose
    private String cL;
    @SerializedName("PL")
    @Expose
    private String pL;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("HeadName")
    @Expose
    private String headName;

    public Integer getLeaveID() {
        return leaveID;
    }

    public void setLeaveID(Integer leaveID) {
        this.leaveID = leaveID;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLeaveStartDate() {
        return leaveStartDate;
    }

    public void setLeaveStartDate(String leaveStartDate) {
        this.leaveStartDate = leaveStartDate;
    }

    public String getLeaveEndDate() {
        return leaveEndDate;
    }

    public void setLeaveEndDate(String leaveEndDate) {
        this.leaveEndDate = leaveEndDate;
    }

    public String getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(String leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getARStartDate() {
        return aRStartDate;
    }

    public void setARStartDate(String aRStartDate) {
        this.aRStartDate = aRStartDate;
    }

    public String getAREndDate() {
        return aREndDate;
    }

    public void setAREndDate(String aREndDate) {
        this.aREndDate = aREndDate;
    }

    public String getARDays() {
        return aRDays;
    }

    public void setARDays(String aRDays) {
        this.aRDays = aRDays;
    }

    public String getARBy() {
        return aRBy;
    }

    public void setARBy(String aRBy) {
        this.aRBy = aRBy;
    }

    public String getCL() {
        return cL;
    }

    public void setCL(String cL) {
        this.cL = cL;
    }

    public String getPL() {
        return pL;
    }

    public void setPL(String pL) {
        this.pL = pL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }
}
