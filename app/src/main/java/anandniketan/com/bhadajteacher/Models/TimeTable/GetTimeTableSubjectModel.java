package anandniketan.com.bhadajteacher.Models.TimeTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 1/11/2018.
 */

public class GetTimeTableSubjectModel {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayTimeTableSubjectModel> finalArray = new ArrayList<FinalArrayTimeTableSubjectModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayTimeTableSubjectModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayTimeTableSubjectModel> finalArray) {
        this.finalArray = finalArray;
    }

}
