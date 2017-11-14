package anandniketan.com.bhadajteacher.Models.StudentAssignSubjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 10/16/2017.
 */

public class FinalArrayStudentSubject {
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("StudentID")
    @Expose
    private Integer studentID;
    @SerializedName("StudentSubject")
    @Expose
    private List<StudentSubject> studentSubject = null;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public List<StudentSubject> getStudentSubject() {
        return studentSubject;
    }

    public void setStudentSubject(List<StudentSubject> studentSubject) {
        this.studentSubject = studentSubject;
    }

}
