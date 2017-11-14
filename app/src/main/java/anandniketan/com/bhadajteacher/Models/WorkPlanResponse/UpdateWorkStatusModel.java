package anandniketan.com.bhadajteacher.Models.WorkPlanResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/13/2017.
 */

public class UpdateWorkStatusModel {
    @SerializedName("Success")
    @Expose
    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
