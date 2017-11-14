package anandniketan.com.bhadajteacher.Models;

import java.util.ArrayList;

/**
 * Created by admsandroid on 10/13/2017.
 */

public class HomeworkModel {

    public HomeworkModel() {
    }

    private ArrayList<HomeworkData> gethomeworkdata;

    public ArrayList<HomeworkData> getGethomeworkdata() {
        return gethomeworkdata;
    }

    public void setGethomeworkdata(ArrayList<HomeworkData> gethomeworkdata) {
        this.gethomeworkdata = gethomeworkdata;
    }

    public class HomeworkData {
        public HomeworkData() {
        }

        private String Date;
        private String Standard;
        private String StandardID;
        private String ClassName;
        private String ClassID;
        private String Subject;
        private String SubjectID;
        private String TermID;
        private String WorkPlan;
        private String ClassWork;
        private String HomeWork;
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

        public String getStandardID() {
            return StandardID;
        }

        public void setStandardID(String standardID) {
            StandardID = standardID;
        }

        public String getClassName() {
            return ClassName;
        }

        public void setClassName(String className) {
            ClassName = className;
        }

        public String getClassID() {
            return ClassID;
        }

        public void setClassID(String classID) {
            ClassID = classID;
        }

        public String getSubject() {
            return Subject;
        }

        public void setSubject(String subject) {
            Subject = subject;
        }

        public String getSubjectID() {
            return SubjectID;
        }

        public void setSubjectID(String subjectID) {
            SubjectID = subjectID;
        }

        public String getTermID() {
            return TermID;
        }

        public void setTermID(String termID) {
            TermID = termID;
        }

        public String getWorkPlan() {
            return WorkPlan;
        }

        public void setWorkPlan(String workPlan) {
            WorkPlan = workPlan;
        }

        public String getClassWork() {
            return ClassWork;
        }

        public void setClassWork(String classWork) {
            ClassWork = classWork;
        }

        public String getHomeWork() {
            return HomeWork;
        }

        public void setHomeWork(String homeWork) {
            HomeWork = homeWork;
        }

        public String getFont() {
            return Font;
        }

        public void setFont(String font) {
            Font = font;
        }
    }
}
