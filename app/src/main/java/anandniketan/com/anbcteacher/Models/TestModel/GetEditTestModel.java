package anandniketan.com.anbcteacher.Models.TestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 1/17/2018.
 */

public class GetEditTestModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayTestDataModel> finalArray = new ArrayList<FinalArrayTestDataModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayTestDataModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayTestDataModel> finalArray) {
        this.finalArray = finalArray;
    }

}
