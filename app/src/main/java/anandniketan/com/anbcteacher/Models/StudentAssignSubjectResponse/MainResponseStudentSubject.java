package anandniketan.com.anbcteacher.Models.StudentAssignSubjectResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 10/16/2017.
 */

public class MainResponseStudentSubject {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayStudentSubject> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayStudentSubject> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayStudentSubject> finalArray) {
        this.finalArray = finalArray;
    }

}
