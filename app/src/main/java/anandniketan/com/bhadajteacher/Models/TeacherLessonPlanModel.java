package anandniketan.com.bhadajteacher.Models;

import java.util.ArrayList;

/**
 * Created by admsandroid on 10/13/2017.
 */

public class TeacherLessonPlanModel {

    public TeacherLessonPlanModel() {
    }

    private ArrayList<LessonPlan> getLessonPlan;

    public ArrayList<LessonPlan> getGetLessonPlan() {
        return getLessonPlan;
    }

    public void setGetLessonPlan(ArrayList<LessonPlan> getLessonPlan) {
        this.getLessonPlan = getLessonPlan;
    }

    public class LessonPlan {
        public LessonPlan() {
        }

        private String Standard;
        private String Subject;
        private ArrayList<LessonPlanData> getLessonPlanData;

        public String getStandard() {
            return Standard;
        }

        public void setStandard(String standard) {
            Standard = standard;
        }

        public String getSubject() {
            return Subject;
        }

        public void setSubject(String subject) {
            Subject = subject;
        }

        public ArrayList<LessonPlanData> getGetLessonPlanData() {
            return getLessonPlanData;
        }

        public void setGetLessonPlanData(ArrayList<LessonPlanData> getLessonPlanData) {
            this.getLessonPlanData = getLessonPlanData;
        }

        public class LessonPlanData {

            public LessonPlanData() {
            }

            private String ID;
            private String ChapterNo;
            private String ChapterName;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getChapterNo() {
                return ChapterNo;
            }

            public void setChapterNo(String chapterNo) {
                ChapterNo = chapterNo;
            }

            public String getChapterName() {
                return ChapterName;
            }

            public void setChapterName(String chapterName) {
                ChapterName = chapterName;
            }
        }
    }
}