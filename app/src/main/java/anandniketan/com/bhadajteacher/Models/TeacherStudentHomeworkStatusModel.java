package anandniketan.com.bhadajteacher.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/7/2017.
 */

public class TeacherStudentHomeworkStatusModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayhomeworkstatus> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayhomeworkstatus> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayhomeworkstatus> finalArray) {
        this.finalArray = finalArray;
    }

}
