package anandniketan.com.anbcteacher.Models.TestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 1/17/2018.
 */

public class FinalArrayTestDataModel {
    @SerializedName("TestName")
    @Expose
    private String testName;
    @SerializedName("StandardClass")
    @Expose
    private String standardClass;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("TestDate")
    @Expose
    private String testDate;
    @SerializedName("TSMasterID")
    @Expose
    private Integer tSMasterID;
    @SerializedName("TestID")
    @Expose
    private Integer testID;
    @SerializedName("SubjectID")
    @Expose
    private Integer subjectID;
    @SerializedName("StandardID")
    @Expose
    private Integer standardID;
    @SerializedName("SectionID")
    @Expose
    private Integer sectionID;
    @SerializedName("TestSyllabus")
    @Expose
    private List<TestSyllabusModel> testSyllabus = new ArrayList<TestSyllabusModel>();

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public Integer getTSMasterID() {
        return tSMasterID;
    }

    public void setTSMasterID(Integer tSMasterID) {
        this.tSMasterID = tSMasterID;
    }

    public Integer getTestID() {
        return testID;
    }

    public void setTestID(Integer testID) {
        this.testID = testID;
    }

    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }

    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
        this.sectionID = sectionID;
    }

    public List<TestSyllabusModel> getTestSyllabus() {
        return testSyllabus;
    }

    public void setTestSyllabus(List<TestSyllabusModel> testSyllabus) {
        this.testSyllabus = testSyllabus;
    }

    public Integer gettSMasterID() {
        return tSMasterID;
    }

    public void settSMasterID(Integer tSMasterID) {
        this.tSMasterID = tSMasterID;
    }

    public Integer getStandardID() {
        return standardID;
    }

    public void setStandardID(Integer standardID) {
        this.standardID = standardID;
    }
}
