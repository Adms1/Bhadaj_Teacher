package anandniketan.com.bhadajteacher.Models.TimeTable;

import java.util.ArrayList;

/**
 * Created by admsandroid on 9/25/2017.
 */

public class TeacherGetTimetableModel {

    public TeacherGetTimetableModel() {
    }

    private ArrayList<TimeTable> getTimeTable;

    public ArrayList<TimeTable> getGetTimeTable() {
        return getTimeTable;
    }

    public void setGetTimeTable(ArrayList<TimeTable> getTimeTable) {
        this.getTimeTable = getTimeTable;
    }

    public class TimeTable {

        public TimeTable() {
        }

        private String Day;
        private ArrayList<TimeTableData> getTimeTableData;

        public String getDay() {
            return Day;
        }

        public void setDay(String day) {
            Day = day;
        }

        public ArrayList<TimeTableData> getGetTimeTableData() {
            return getTimeTableData;
        }

        public void setGetTimeTableData(ArrayList<TimeTableData> getTimeTableData) {
            this.getTimeTableData = getTimeTableData;
        }


        public class TimeTableData {

            public TimeTableData() {
            }

            private String Lecture;
            private String Subject;
            private String StandardClass;
            private String ProxyStatus;
            private String TimetableID;

            public String getLecture() {
                return Lecture;
            }

            public void setLecture(String lecture) {
                Lecture = lecture;
            }

            public String getSubject() {
                return Subject;
            }

            public void setSubject(String subject) {
                Subject = subject;
            }

            public String getStandardClass() {
                return StandardClass;
            }

            public void setStandardClass(String standardClass) {
                StandardClass = standardClass;
            }

            public String getProxyStatus() {
                return ProxyStatus;
            }

            public void setProxyStatus(String proxyStatus) {
                ProxyStatus = proxyStatus;
            }

            public String getTimetableID() {
                return TimetableID;
            }

            public void setTimetableID(String timetableID) {
                TimetableID = timetableID;
            }
        }
    }
}
