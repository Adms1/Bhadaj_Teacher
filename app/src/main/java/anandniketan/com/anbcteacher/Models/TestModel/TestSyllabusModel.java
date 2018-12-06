package anandniketan.com.anbcteacher.Models.TestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 1/17/2018.
 */

public class TestSyllabusModel {
    @SerializedName("Syllabus")
    @Expose
    private String syllabus;

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

}
