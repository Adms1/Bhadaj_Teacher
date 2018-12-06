package anandniketan.com.anbcteacher.Models.StudentAssignSubjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 10/16/2017.
 */

public class StudentSubject {
    @SerializedName("SubjectID")
    @Expose
    private Integer subjectID;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("CheckedStatus")
    @Expose
    private String checkedStatus;

    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(String checkedStatus) {
        this.checkedStatus = checkedStatus;
    }

}
