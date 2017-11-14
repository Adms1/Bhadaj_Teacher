package anandniketan.com.bhadajteacher.Models;

import java.util.ArrayList;

/**
 * Created by admsandroid on 9/25/2017.
 */

public class TeacherGetAssignStudentSubjectmModel {

    public TeacherGetAssignStudentSubjectmModel() {

    }

    private String StudentName;
    private String StudentID;
    private ArrayList<StudentSubject> dataStudentSubjectArray;

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

    public ArrayList<StudentSubject> getDataStudentSubjectArray() {
        return dataStudentSubjectArray;
    }

    public void setDataStudentSubjectArray(ArrayList<StudentSubject> dataStudentSubjectArray) {
        this.dataStudentSubjectArray = dataStudentSubjectArray;
    }

    public class StudentSubject {
        public StudentSubject() {
        }

        private String SubjectID;
        private String Subject;
        private String CheckedStatus;


        public String getSubjectID() {
            return SubjectID;
        }

        public void setSubjectID(String subjectID) {
            SubjectID = subjectID;
        }

        public String getSubject() {
            return Subject;
        }

        public void setSubject(String subject) {
            Subject = subject;
        }

        public String getCheckedStatus() {
            return CheckedStatus;
        }

        public void setCheckedStatus(String checkedStatus) {
            CheckedStatus = checkedStatus;
        }
    }
}
