package anandniketan.com.anbcteacher.Models;

/**
 * Created by admsandroid on 9/22/2017.
 */

public class TeacherTodayScheduleModel {

    public TeacherTodayScheduleModel() {
    }

    private String Lecture;
    private String Standard;
    private String classname;
    private String Subject;
    private String Timing;


    public String getLecture() {
        return Lecture;
    }

    public void setLecture(String lecture) {
        Lecture = lecture;
    }

    public String getStandard() {
        return Standard;
    }

    public void setStandard(String standard) {
        Standard = standard;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTiming() {
        return Timing;
    }

    public void setTiming(String timing) {
        Timing = timing;
    }


}
