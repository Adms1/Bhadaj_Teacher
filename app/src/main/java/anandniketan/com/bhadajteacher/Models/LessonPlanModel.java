package anandniketan.com.bhadajteacher.Models;

import java.util.ArrayList;

/**
 * Created by admsandroid on 10/13/2017.
 */

public class LessonPlanModel {

    public LessonPlanModel() {
    }

    private String FromDate;
    private String ToDate;
    private ArrayList<LessonplanData> gethomeworkdata;

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public ArrayList<LessonplanData> getGethomeworkdata() {
        return gethomeworkdata;
    }

    public void setGethomeworkdata(ArrayList<LessonplanData> gethomeworkdata) {
        this.gethomeworkdata = gethomeworkdata;
    }

    public class LessonplanData {
        public LessonplanData() {
        }

        private String Date;
        private String Standard;
        private String ClassName;
        private String Subject;
        private String HomeWork;
        private String ChapterName;
        private String Objective;
        private String AssessmentQue;
        private String Font;

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }

        public String getStandard() {
            return Standard;
        }

        public void setStandard(String standard) {
            Standard = standard;
        }

        public String getClassName() {
            return ClassName;
        }

        public void setClassName(String className) {
            ClassName = className;
        }

        public String getSubject() {
            return Subject;
        }

        public void setSubject(String subject) {
            Subject = subject;
        }

        public String getHomeWork() {
            return HomeWork;
        }

        public void setHomeWork(String homeWork) {
            HomeWork = homeWork;
        }

        public String getChapterName() {
            return ChapterName;
        }

        public void setChapterName(String chapterName) {
            ChapterName = chapterName;
        }

        public String getObjective() {
            return Objective;
        }

        public void setObjective(String objective) {
            Objective = objective;
        }

        public String getAssessmentQue() {
            return AssessmentQue;
        }

        public void setAssessmentQue(String assessmentQue) {
            AssessmentQue = assessmentQue;
        }

        public String getFont() {
            return Font;
        }

        public void setFont(String font) {
            Font = font;
        }
    }

}
