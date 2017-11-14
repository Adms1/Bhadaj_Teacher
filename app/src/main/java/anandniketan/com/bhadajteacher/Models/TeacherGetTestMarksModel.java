package anandniketan.com.bhadajteacher.Models;

import java.util.ArrayList;

/**
 * Created by admsandroid on 9/27/2017.
 */

public class TeacherGetTestMarksModel {

    public TeacherGetTestMarksModel() {
    }


    private ArrayList<studentDetail> getstudentDetail;

    public ArrayList<studentDetail> getGetstudentDetail() {
        return getstudentDetail;
    }

    public void setGetstudentDetail(ArrayList<studentDetail> getstudentDetail) {
        this.getstudentDetail = getstudentDetail;
    }

    public class studentDetail {
        public studentDetail() {
        }

        private String TestName;
        private String StandardClass;
        private ArrayList<TestDetail> gettestDetail;

        public String getTestName() {
            return TestName;
        }

        public void setTestName(String testName) {
            TestName = testName;
        }

        public String getStandardClass() {
            return StandardClass;
        }

        public void setStandardClass(String standardClass) {
            StandardClass = standardClass;
        }

        public ArrayList<TestDetail> getGettestDetail() {
            return gettestDetail;
        }

        public void setGettestDetail(ArrayList<TestDetail> gettestDetail) {
            this.gettestDetail = gettestDetail;
        }

        public class TestDetail {
            private String StudentName;
            private String StudentID;
            private String GRNO;
            private String TotalMarks;
            private String TotalGainedMarks;
            private String Percentage;
            private ArrayList<subjectMarks> getsubjectMarks;


            public String getStudentName() {
                return StudentName;
            }

            public void setStudentName(String studentName) {
                StudentName = studentName;
            }

            public String getStudentID() {
                return StudentID;
            }

            public void setStudentID(String studentID) {
                StudentID = studentID;
            }

            public String getGRNO() {
                return GRNO;
            }

            public void setGRNO(String GRNO) {
                this.GRNO = GRNO;
            }

            public String getTotalMarks() {
                return TotalMarks;
            }

            public void setTotalMarks(String totalMarks) {
                TotalMarks = totalMarks;
            }

            public String getTotalGainedMarks() {
                return TotalGainedMarks;
            }

            public void setTotalGainedMarks(String totalGainedMarks) {
                TotalGainedMarks = totalGainedMarks;
            }

            public String getPercentage() {
                return Percentage;
            }

            public void setPercentage(String percentage) {
                Percentage = percentage;
            }

            public ArrayList<subjectMarks> getGetsubjectMarks() {
                return getsubjectMarks;
            }

            public void setGetsubjectMarks(ArrayList<subjectMarks> getsubjectMarks) {
                this.getsubjectMarks = getsubjectMarks;
            }


            public class subjectMarks {
                public subjectMarks() {
                }

                private String Subject;
                private String Marks;

                public String getSubject() {
                    return Subject;
                }

                public void setSubject(String subject) {
                    Subject = subject;
                }

                public String getMarks() {
                    return Marks;
                }

                public void setMarks(String marks) {
                    Marks = marks;
                }
            }

        }

    }
}
