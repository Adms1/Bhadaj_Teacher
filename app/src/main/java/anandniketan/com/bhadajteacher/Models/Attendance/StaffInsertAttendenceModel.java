package anandniketan.com.bhadajteacher.Models.Attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/8/2017.
 */

public class StaffInsertAttendenceModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayUpdateAttendace> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayUpdateAttendace> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayUpdateAttendace> finalArray) {
        this.finalArray = finalArray;
    }
}
