package anandniketan.com.anbcteacher.Models.LeaveModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import anandniketan.com.anbcteacher.Models.NewResponse.FinalArray;

public class LeaveMainModel {

    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("TermID")
    @Expose
    private Integer termID;
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("TotalMarks")
    @Expose
    private String totalMarks;
    @SerializedName("FinalArray")
    @Expose
    private List<LeaveFinalArray> finalArray = null;
    @SerializedName("LeaveDetails")
    @Expose
    private List<LeaveDetail> leaveDetails = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }



    public List<LeaveFinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<LeaveFinalArray> finalArray) {
        this.finalArray = finalArray;
    }

    public List<LeaveDetail> getLeaveDetails() {
        return leaveDetails;
    }

    public void setLeaveDetails(List<LeaveDetail> leaveDetails) {
        this.leaveDetails = leaveDetails;
    }

    public Integer getTermID() {
        return termID;
    }

    public void setTermID(Integer termID) {
        this.termID = termID;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    //    ========= Forgot Password================
    @SerializedName("Message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
