package anandniketan.com.bhadajteacher.Models.Attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/8/2017.
 */

public class StaffNewAttendenceModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayAttendance> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<FinalArrayAttendance> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayAttendance> finalArray) {
        this.finalArray = finalArray;
    }
}
