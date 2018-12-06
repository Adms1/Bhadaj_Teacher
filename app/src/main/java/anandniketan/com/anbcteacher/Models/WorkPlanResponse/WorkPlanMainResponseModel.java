package anandniketan.com.anbcteacher.Models.WorkPlanResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/13/2017.
 */

public class WorkPlanMainResponseModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayWorkPlan> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayWorkPlan> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayWorkPlan> finalArray) {
        this.finalArray = finalArray;
    }
}
