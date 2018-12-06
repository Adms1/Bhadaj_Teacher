package anandniketan.com.anbcteacher.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BirthDayDetailModel {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Department")
    @Expose
    private String department;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Flag")
    @Expose
    private String flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
