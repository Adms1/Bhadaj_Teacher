package anandniketan.com.anbcteacher.Models.LessonPlanResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 10/16/2017.
 */

public class LessonDatum {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("ChapterNo")
    @Expose
    private Integer chapterNo;
    @SerializedName("ChapterName")
    @Expose
    private String chapterName;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(Integer chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
