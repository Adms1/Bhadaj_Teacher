package anandniketan.com.anbcteacher.Models.LeaveModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaveDetail {

    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Total")
    @Expose
    private String total;
    @SerializedName("Used")
    @Expose
    private String used;
    @SerializedName("Remaining")
    @Expose
    private String remaining;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }
}
