package anandniketan.com.bhadajteacher.Models.TimeTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/14/2017.
 */

public class GetStandardSectionModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayAllAttendance> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayAllAttendance> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayAllAttendance> finalArray) {
        this.finalArray = finalArray;
    }

}
