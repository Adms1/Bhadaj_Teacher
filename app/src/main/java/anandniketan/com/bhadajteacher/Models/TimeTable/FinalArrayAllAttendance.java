package anandniketan.com.bhadajteacher.Models.TimeTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import anandniketan.com.bhadajteacher.Models.TimeTable.SectionDetailModel;

/**
 * Created by admsandroid on 11/14/2017.
 */

public class FinalArrayAllAttendance {

    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("StandardID")
    @Expose
    private Integer standardID;
    @SerializedName("SectionDetail")
    @Expose
    private List<SectionDetailModel> sectionDetail = null;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Integer getStandardID() {
        return standardID;
    }

    public void setStandardID(Integer standardID) {
        this.standardID = standardID;
    }

    public List<SectionDetailModel> getSectionDetail() {
        return sectionDetail;
    }

    public void setSectionDetail(List<SectionDetailModel> sectionDetail) {
        this.sectionDetail = sectionDetail;
    }


}
