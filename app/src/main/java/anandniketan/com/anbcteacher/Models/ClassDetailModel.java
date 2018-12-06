package anandniketan.com.anbcteacher.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassDetailModel {
    @SerializedName("StandardID")
    @Expose
    private String standardID;
    @SerializedName("ClassID")
    @Expose
    private String classID;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Class")
    @Expose
    private String _class;

    public String getStandardID() {
        return standardID;
    }

    public void setStandardID(String standardID) {
        this.standardID = standardID;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }
}
