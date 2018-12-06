package anandniketan.com.anbcteacher.Models.NewResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FinalArray {

    @SerializedName("TestName")
    @Expose
    private String testName;
    @SerializedName("StandardClass")
    @Expose
    private String standardClass;
    @SerializedName("StudentData")
    @Expose
    private List<StudentDatum> studentData = null;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getStandardClass() {
        return standardClass;
    }

    public void setStandardClass(String standardClass) {
        this.standardClass = standardClass;
    }

    public List<StudentDatum> getStudentData() {
        return studentData;
    }

    public void setStudentData(List<StudentDatum> studentData) {
        this.studentData = studentData;
    }

}