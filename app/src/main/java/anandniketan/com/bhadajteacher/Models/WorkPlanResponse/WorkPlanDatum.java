package anandniketan.com.bhadajteacher.Models.WorkPlanResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/13/2017.
 */

public class WorkPlanDatum {
    @SerializedName("Work")
    @Expose
    private String work;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("PK_TeacherworkID")
    @Expose
    private Integer pKTeacherworkID;
    @SerializedName("FK_WorkID")
    @Expose
    private Integer fKWorkID;

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getPKTeacherworkID() {
        return pKTeacherworkID;
    }

    public void setPKTeacherworkID(Integer pKTeacherworkID) {
        this.pKTeacherworkID = pKTeacherworkID;
    }

    public Integer getFKWorkID() {
        return fKWorkID;
    }

    public void setFKWorkID(Integer fKWorkID) {
        this.fKWorkID = fKWorkID;
    }

}
