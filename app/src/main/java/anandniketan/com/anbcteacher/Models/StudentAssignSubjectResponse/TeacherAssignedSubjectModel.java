package anandniketan.com.anbcteacher.Models.StudentAssignSubjectResponse;

/**
 * Created by admsandroid on 9/25/2017.
 */

public class TeacherAssignedSubjectModel {

    public TeacherAssignedSubjectModel() {
    }

    private String Standard;
    private String classname;
    private String Subject;
    private String StandardID;
    private String ClassID;
    private String SubjectID;
    private String standardsubject;

    public String getStandard() {
        return Standard;
    }

    public void setStandard(String standard) {
        Standard = standard;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

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

    public String getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(String subjectID) {
        SubjectID = subjectID;
    }

    public String getStandardsubject() {
        return standardsubject;
    }

    public void setStandardsubject(String standardsubject) {
        this.standardsubject = standardsubject;
    }
}
