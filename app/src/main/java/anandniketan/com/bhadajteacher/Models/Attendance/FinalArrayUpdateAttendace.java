package anandniketan.com.bhadajteacher.Models.Attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/8/2017.
 */

public class FinalArrayUpdateAttendace {
    @SerializedName("Total")
    @Expose
    private Integer total;
    @SerializedName("TotalPresent")
    @Expose
    private Integer totalPresent;
    @SerializedName("TotalAbsent")
    @Expose
    private Integer totalAbsent;
    @SerializedName("TotalLeave")
    @Expose
    private Integer totalLeave;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(Integer totalPresent) {
        this.totalPresent = totalPresent;
    }

    public Integer getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(Integer totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public Integer getTotalLeave() {
        return totalLeave;
    }

    public void setTotalLeave(Integer totalLeave) {
        this.totalLeave = totalLeave;
    }
}
