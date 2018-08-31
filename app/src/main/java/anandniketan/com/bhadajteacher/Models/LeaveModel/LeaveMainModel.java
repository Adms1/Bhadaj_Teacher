package anandniketan.com.bhadajteacher.Models.LeaveModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import anandniketan.com.bhadajteacher.Models.NewResponse.FinalArray;

public class LeaveMainModel {

    @SerializedName("Success")
    @Expose
    private String success;
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
}
