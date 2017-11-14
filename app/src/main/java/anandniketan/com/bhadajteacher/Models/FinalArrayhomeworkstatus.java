package anandniketan.com.bhadajteacher.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/7/2017.
 */

public class FinalArrayhomeworkstatus {
    @SerializedName("StudentID")
    @Expose
    private Integer studentID;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("HomeWorkID")
    @Expose
    private String homeWorkID;
    @SerializedName("HomeWorkDetailID")
    @Expose
    private String homeWorkDetailID;
    @SerializedName("HomeWorkStatus")
    @Expose
    private String homeWorkStatus;

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getHomeWorkID() {
        return homeWorkID;
    }

    public void setHomeWorkID(String homeWorkID) {
        this.homeWorkID = homeWorkID;
    }

    public String getHomeWorkDetailID() {
        return homeWorkDetailID;
    }

    public void setHomeWorkDetailID(String homeWorkDetailID) {
        this.homeWorkDetailID = homeWorkDetailID;
    }

    public String getHomeWorkStatus() {
        return homeWorkStatus;
    }

    public void setHomeWorkStatus(String homeWorkStatus) {
        this.homeWorkStatus = homeWorkStatus;
    }
}
