package anandniketan.com.anbcteacher.Models.NewResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentDatum {

@SerializedName("StudentName")
@Expose
private String studentName;
@SerializedName("StudentID")
@Expose
private Integer studentID;
@SerializedName("GRNO")
@Expose
private String gRNO;
@SerializedName("TotalMarks")
@Expose
private Double totalMarks;
@SerializedName("TotalGainedMarks")
@Expose
private Double totalGainedMarks;
@SerializedName("Percentage")
@Expose
private String percentage;
@SerializedName("SubjectMarks")
@Expose
private List<SubjectMark> subjectMarks = null;

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

public String getGRNO() {
return gRNO;
}

public void setGRNO(String gRNO) {
this.gRNO = gRNO;
}

public Double getTotalMarks() {
return totalMarks;
}

public void setTotalMarks(Double totalMarks) {
this.totalMarks = totalMarks;
}

public Double getTotalGainedMarks() {
return totalGainedMarks;
}

public void setTotalGainedMarks(Double totalGainedMarks) {
this.totalGainedMarks = totalGainedMarks;
}

public String getPercentage() {
return percentage;
}

public void setPercentage(String percentage) {
this.percentage = percentage;
}

public List<SubjectMark> getSubjectMarks() {
return subjectMarks;
}

public void setSubjectMarks(List<SubjectMark> subjectMarks) {
this.subjectMarks = subjectMarks;
}

}