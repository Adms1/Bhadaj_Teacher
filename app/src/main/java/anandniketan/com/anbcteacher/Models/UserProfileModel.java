package anandniketan.com.anbcteacher.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.anbcteacher.Models.NewResponse.FinalArray;

/**
 * Created by admsandroid on 9/20/2017.
 */


public class UserProfileModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<UserFinalArray> finalArray = null;
    @SerializedName("ClassDetail")
    @Expose
    private List<ClassDetailModel> classDetail = null;

    @SerializedName("BirthdayDetail")
    @Expose
    private List<BirthDayDetailModel> birthdayDetail = null;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<UserFinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<UserFinalArray> finalArray) {
        this.finalArray = finalArray;
    }

    public List<ClassDetailModel> getClassDetail() {
        return classDetail;
    }

    public void setClassDetail(List<ClassDetailModel> classDetail) {
        this.classDetail = classDetail;
    }

    public List<BirthDayDetailModel> getBirthdayDetail() {
        return birthdayDetail;
    }

    public void setBirthdayDetail(List<BirthDayDetailModel> birthdayDetail) {
        this.birthdayDetail = birthdayDetail;
    }

}
