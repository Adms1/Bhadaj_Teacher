package anandniketan.com.bhadajteacher.Models.Attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/8/2017.
 */

public class StudentDetailAttedance {
    @SerializedName("StudentID")
    @Expose
    private Integer studentID;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("StudentImage")
    @Expose
    private String studentImage;
    @SerializedName("AttendanceID")
    @Expose
    private Integer attendanceID;
    @SerializedName("AttendenceStatus")
    @Expose
    private String attendenceStatus;
    @SerializedName("Comment")
    @Expose
    private String comment;

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

    public String getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
    }

    public Integer getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(Integer attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getAttendenceStatus() {
        return attendenceStatus;
    }

    public void setAttendenceStatus(String attendenceStatus) {
        this.attendenceStatus = attendenceStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
