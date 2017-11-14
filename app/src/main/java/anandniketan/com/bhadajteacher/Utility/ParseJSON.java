package anandniketan.com.bhadajteacher.Utility;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import anandniketan.com.bhadajteacher.Models.HomeworkModel;
import anandniketan.com.bhadajteacher.Models.LessonPlanModel;
import anandniketan.com.bhadajteacher.Models.LoginModel;
import anandniketan.com.bhadajteacher.Models.TeacherAssignedSubjectModel;
import anandniketan.com.bhadajteacher.Models.TeacherGetAssignStudentSubjectmModel;
import anandniketan.com.bhadajteacher.Models.TeacherGetTestMarksModel;
import anandniketan.com.bhadajteacher.Models.TeacherGetTestNameModel;
import anandniketan.com.bhadajteacher.Models.TeacherGetTimetableModel;
import anandniketan.com.bhadajteacher.Models.TeacherInsertTestDetailModel;
import anandniketan.com.bhadajteacher.Models.TeacherLessonPlanModel;
import anandniketan.com.bhadajteacher.Models.TeacherTodayScheduleModel;
import anandniketan.com.bhadajteacher.Models.Test_SyllabusModel;
import anandniketan.com.bhadajteacher.Models.UserProfileModel;

/**
 * Created by Megha on 15-Sep-17.
 */
public class ParseJSON {

    public static ArrayList<LoginModel> parseLoginJson(String responseString) {
        ArrayList<LoginModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            LoginModel loginModel = null;

            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                for (int a = 0; a < jsonMainNode.length(); a++) {
                    loginModel = new LoginModel();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
                    loginModel.setStaffID(jsonChildNode.getString("StaffID"));
                    loginModel.setEmp_Name(jsonChildNode.getString("Emp_Name"));
                    loginModel.setEmp_Code(jsonChildNode.getString("Emp_Code"));
                    loginModel.setDeviceId(jsonChildNode.getString("DeviceId"));
                    loginModel.setDesignationID(jsonChildNode.getString("DesignationID"));
                    loginModel.setDepratmentID(jsonChildNode.getString("DepratmentID"));

                    LoginModel.ClassDetail data = null;
                    ArrayList<LoginModel.ClassDetail> dataArrayList = new ArrayList<>();
                    JSONArray jsonChildMainNode = jsonChildNode.optJSONArray("ClassDetail");
                    for (int i = 0; i < jsonChildMainNode.length(); i++) {
                        data = loginModel.new ClassDetail();
                        JSONObject jsonChildNode1 = jsonChildMainNode.getJSONObject(i);
                        data.setClassID(jsonChildNode1.getString("ClassID"));
                        data.setStandardID(jsonChildNode1.getString("StandardID"));
                        data.setStandard(jsonChildNode1.getString("Standard"));
                        data.setClasses(jsonChildNode1.getString("Class"));
                        dataArrayList.add(data);
                    }
                    loginModel.setGetGetclassDetailsArrayList(dataArrayList);

                    result.add(loginModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<UserProfileModel> parseUserProfileJson(String responseString) {
        ArrayList<UserProfileModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            UserProfileModel userProfileModel = null;

            if (data_load_basket.toString().equals("True")) {


                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                for (int a = 0; a < jsonMainNode.length(); a++) {
                    userProfileModel = new UserProfileModel();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
                    userProfileModel.setStaffID(jsonChildNode.getString("StaffID"));
                    userProfileModel.setEmp_Name(jsonChildNode.getString("Emp_Name"));
                    userProfileModel.setEmp_Code(jsonChildNode.getString("Emp_Code"));
                    userProfileModel.setDepratment(jsonChildNode.getString("Depratment"));
                    userProfileModel.setDesignation(jsonChildNode.getString("Designation"));
                    userProfileModel.setEmailID(jsonChildNode.getString("EmailID"));
                    userProfileModel.setMobile(jsonChildNode.getString("Mobile"));
                    userProfileModel.setImage(jsonChildNode.getString("Image"));

                    UserProfileModel.ClassDetail data = null;
                    ArrayList<UserProfileModel.ClassDetail> dataArrayList = new ArrayList<>();
                    JSONArray jsonChildMainNode = jsonChildNode.optJSONArray("ClassDetail");
                    for (int i = 0; i < jsonChildMainNode.length(); i++) {
                        data = userProfileModel.new ClassDetail();
                        JSONObject jsonChildNode1 = jsonChildMainNode.getJSONObject(i);
                        data.setClassID(jsonChildNode1.getString("ClassID"));
                        data.setStandardID(jsonChildNode1.getString("StandardID"));
                        data.setStandard(jsonChildNode1.getString("Standard"));
                        data.setClasses(jsonChildNode1.getString("Class"));
                        dataArrayList.add(data);
                    }
                    userProfileModel.setGetclassDetailsArrayList(dataArrayList);

                    result.add(userProfileModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static ArrayList<TeacherTodayScheduleModel> parseTeacherTodayscheduleJson(String responseString) {
        ArrayList<TeacherTodayScheduleModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            TeacherTodayScheduleModel teacherTodayScheduleModel = null;

            if (data_load_basket.toString().equals("True")) {

                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                for (int a = 0; a < jsonMainNode.length(); a++) {
                    teacherTodayScheduleModel = new TeacherTodayScheduleModel();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
                    teacherTodayScheduleModel.setLecture(jsonChildNode.getString("Lecture"));
                    teacherTodayScheduleModel.setStandard(jsonChildNode.getString("Standard"));
                    teacherTodayScheduleModel.setClassname(jsonChildNode.getString("classname"));
                    teacherTodayScheduleModel.setSubject(jsonChildNode.getString("Subject"));
                    teacherTodayScheduleModel.setTiming(jsonChildNode.getString("Timing"));

                    result.add(teacherTodayScheduleModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    public static ArrayList<TeacherAssignedSubjectModel> parseTeacherAssignedSubjectJson(String responseString) {
        ArrayList<TeacherAssignedSubjectModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            TeacherAssignedSubjectModel teacherAssignedSubjectModel = null;

            if (data_load_basket.toString().equals("True")) {

                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                for (int a = 0; a < jsonMainNode.length(); a++) {
                    teacherAssignedSubjectModel = new TeacherAssignedSubjectModel();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
                    teacherAssignedSubjectModel.setSubject(jsonChildNode.getString("Subject"));
                    teacherAssignedSubjectModel.setStandard(jsonChildNode.getString("Standard"));
                    teacherAssignedSubjectModel.setClassname(jsonChildNode.getString("classname"));
                    teacherAssignedSubjectModel.setSubjectID(jsonChildNode.getString("SubjectID"));
                    teacherAssignedSubjectModel.setClassID(jsonChildNode.getString("ClassID"));
                    teacherAssignedSubjectModel.setStandardID(jsonChildNode.getString("StandardID"));


                    result.add(teacherAssignedSubjectModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    public static ArrayList<TeacherGetAssignStudentSubjectmModel> parseStudentAssignedSubjectJson(String responseString) {
        ArrayList<TeacherGetAssignStudentSubjectmModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            TeacherGetAssignStudentSubjectmModel teacherGetAssignStudentSubjectmModel = null;

            if (data_load_basket.toString().equals("True")) {


                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                for (int a = 0; a < jsonMainNode.length(); a++) {
                    teacherGetAssignStudentSubjectmModel = new TeacherGetAssignStudentSubjectmModel();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
                    teacherGetAssignStudentSubjectmModel.setStudentID(jsonChildNode.getString("StudentID"));
                    teacherGetAssignStudentSubjectmModel.setStudentName(jsonChildNode.getString("StudentName"));

                    TeacherGetAssignStudentSubjectmModel.StudentSubject data = null;
                    ArrayList<TeacherGetAssignStudentSubjectmModel.StudentSubject> dataArrayList = new ArrayList<>();
                    JSONArray jsonChildMainNode = jsonChildNode.optJSONArray("StudentSubject");
                    for (int i = 0; i < jsonChildMainNode.length(); i++) {
                        data = teacherGetAssignStudentSubjectmModel.new StudentSubject();
                        JSONObject jsonChildNode1 = jsonChildMainNode.getJSONObject(i);
                        data.setSubject(jsonChildNode1.getString("Subject"));
                        data.setSubjectID(jsonChildNode1.getString("SubjectID"));
                        data.setCheckedStatus(jsonChildNode1.getString("CheckedStatus"));
                        dataArrayList.add(data);
                    }
                    teacherGetAssignStudentSubjectmModel.setDataStudentSubjectArray(dataArrayList);

                    result.add(teacherGetAssignStudentSubjectmModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<TeacherGetTimetableModel> parseTeachertTimetableJson(String responseString) {
        ArrayList<TeacherGetTimetableModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            TeacherGetTimetableModel teacherGetTimetableModel = new TeacherGetTimetableModel();

            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                TeacherGetTimetableModel.TimeTable timetable = null;
                ArrayList<TeacherGetTimetableModel.TimeTable> timetables = new ArrayList<>();
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    timetable = teacherGetTimetableModel.new TimeTable();
                    timetable.setDay(jsonChildNode.getString("Day"));

                    JSONArray jsonMainNode1 = jsonChildNode.optJSONArray("Data");
                    TeacherGetTimetableModel.TimeTable.TimeTableData timetableData = null;
                    ArrayList<TeacherGetTimetableModel.TimeTable.TimeTableData> timetablesData = new ArrayList<>();
                    for (int j = 0; j < jsonMainNode1.length(); j++) {
                        JSONObject jsonChildNode1 = jsonMainNode1.getJSONObject(j);
                        timetableData = timetable.new TimeTableData();
                        timetableData.setLecture(jsonChildNode1.getString("Lecture"));
                        timetableData.setSubject(jsonChildNode1.getString("Subject"));
                        timetableData.setStandardClass(jsonChildNode1.getString("StandardClass"));
                        timetableData.setProxyStatus(jsonChildNode1.getString("ProxyStatus"));
                        timetableData.setTimetableID(jsonChildNode1.getString("TimetableID"));

                        timetablesData.add(timetableData);
                    }
                    timetable.setGetTimeTableData(timetablesData);
                    timetables.add(timetable);
                }
                teacherGetTimetableModel.setGetTimeTable(timetables);
                result.add(teacherGetTimetableModel);
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<Test_SyllabusModel> parseTestSyllabusJson(String responseString) {
        ArrayList<Test_SyllabusModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            Test_SyllabusModel test_syllabusModel = null;

            if (data_load_basket.toString().equals("True")) {

                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                for (int a = 0; a < jsonMainNode.length(); a++) {
                    test_syllabusModel = new Test_SyllabusModel();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
                    test_syllabusModel.setTestID(jsonChildNode.getString("TestID"));
                    test_syllabusModel.setTestName(jsonChildNode.getString("TestName"));
                    test_syllabusModel.setTestDate(jsonChildNode.getString("TestDate"));
                    test_syllabusModel.setSubject(jsonChildNode.getString("Subject"));
                    test_syllabusModel.setStandardClass(jsonChildNode.getString("StandardClass"));
                    test_syllabusModel.setTestID(jsonChildNode.getString("TestID"));
                    test_syllabusModel.setTSMasterID(jsonChildNode.getString("TSMasterID"));
                    test_syllabusModel.setSectionID(jsonChildNode.getString("SectionID"));
                    test_syllabusModel.setSubjectID(jsonChildNode.getString("SubjectID"));

                    Test_SyllabusModel.TestSyllabus data = null;
                    ArrayList<Test_SyllabusModel.TestSyllabus> dataArrayList = new ArrayList<>();
                    JSONArray jsonChildMainNode = jsonChildNode.optJSONArray("TestSyllabus");
                    for (int i = 0; i < jsonChildMainNode.length(); i++) {
                        data = test_syllabusModel.new TestSyllabus();
                        JSONObject jsonChildNode1 = jsonChildMainNode.getJSONObject(i);
                        data.setSyllabus(jsonChildNode1.getString("Syllabus"));
                        dataArrayList.add(data);
                    }
                    test_syllabusModel.setGetSyllabusData(dataArrayList);

                    result.add(test_syllabusModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<TeacherGetTestMarksModel> parseTeacherGetTestMarksJson(String responseString) {
        ArrayList<TeacherGetTestMarksModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            TeacherGetTestMarksModel teacherGetTestMarksModel = new TeacherGetTestMarksModel();

            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                TeacherGetTestMarksModel.studentDetail studentDetail = null;
                ArrayList<TeacherGetTestMarksModel.studentDetail> studentDetails = new ArrayList<>();
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    studentDetail = teacherGetTestMarksModel.new studentDetail();
                    studentDetail.setTestName(jsonChildNode.getString("TestName"));
                    studentDetail.setStandardClass(jsonChildNode.getString("StandardClass"));

                    JSONArray jsonMainchildArray = jsonChildNode.optJSONArray("StudentData");
                    TeacherGetTestMarksModel.studentDetail.TestDetail testDetail = null;
                    ArrayList<TeacherGetTestMarksModel.studentDetail.TestDetail> testDetails = new ArrayList<>();
                    for (int j = 0; j < jsonMainchildArray.length(); j++) {
                        JSONObject jsonMainchildObject = jsonMainchildArray.getJSONObject(j);
                        testDetail = studentDetail.new TestDetail();
                        testDetail.setStudentName(jsonMainchildObject.getString("StudentName"));
                        testDetail.setStudentID(jsonMainchildObject.getString("StudentID"));
                        testDetail.setGRNO(jsonMainchildObject.getString("GRNO"));
                        testDetail.setTotalMarks(jsonMainchildObject.getString("TotalMarks"));
                        testDetail.setTotalGainedMarks(jsonMainchildObject.getString("TotalGainedMarks"));
                        testDetail.setPercentage(jsonMainchildObject.getString("Percentage"));

                        JSONArray jsonchildArray = jsonMainchildObject.optJSONArray("SubjectMarks");
                        TeacherGetTestMarksModel.studentDetail.TestDetail.subjectMarks subjectMarks = null;
                        ArrayList<TeacherGetTestMarksModel.studentDetail.TestDetail.subjectMarks> subjectMarkses = new ArrayList<>();
                        for (int k = 0; k < jsonchildArray.length(); k++) {
                            JSONObject jsonchildObject = jsonchildArray.getJSONObject(k);
                            subjectMarks = testDetail.new subjectMarks();
                            subjectMarks.setSubject(jsonchildObject.getString("Subject"));
                            subjectMarks.setMarks(jsonchildObject.getString("Marks"));

                            subjectMarkses.add(subjectMarks);
                        }
                        testDetail.setGetsubjectMarks(subjectMarkses);
                        testDetails.add(testDetail);
                    }
                    studentDetail.setGettestDetail(testDetails);
                    studentDetails.add(studentDetail);
                }
                teacherGetTestMarksModel.setGetstudentDetail(studentDetails);
                result.add(teacherGetTestMarksModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<TeacherInsertTestDetailModel> parseTeacherInsertTestDetailJson(String responseString) {
        ArrayList<TeacherInsertTestDetailModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");

            if (data_load_basket.toString().equals("True")) {

                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<TeacherGetTestNameModel> parseTeacherGetTestNameJson(String responseString) {
        ArrayList<TeacherGetTestNameModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            TeacherGetTestNameModel teacherGetTestNameModel = null;

            if (data_load_basket.toString().equals("True")) {

                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                for (int a = 0; a < jsonMainNode.length(); a++) {
                    teacherGetTestNameModel = new TeacherGetTestNameModel();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
                    teacherGetTestNameModel.setTestID(jsonChildNode.getString("TestID"));
                    teacherGetTestNameModel.setTestName(jsonChildNode.getString("TestName"));

                    result.add(teacherGetTestNameModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<HomeworkModel> parseTeacherHomeworkJson(String responseString) {
        ArrayList<HomeworkModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            HomeworkModel homeworkModel = new HomeworkModel();
            if (data_load_basket.toString().equals("True")) {

                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");

                HomeworkModel.HomeworkData homeWorkData = null;
                ArrayList<HomeworkModel.HomeworkData> homeWorkDatas = new ArrayList<>();

                for (int j = 0; j < jsonMainNode.length(); j++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                    homeWorkData = homeworkModel.new HomeworkData();
                    homeWorkData.setDate(jsonChildNode.getString("Date"));
                    homeWorkData.setStandard(jsonChildNode.getString("Standard"));
                    homeWorkData.setStandardID(jsonChildNode.getString("StandardId"));
                    homeWorkData.setClassName(jsonChildNode.getString("Class"));
                    homeWorkData.setClassID(jsonChildNode.getString("ClassId"));
                    homeWorkData.setSubject(jsonChildNode.getString("Subject"));
                    homeWorkData.setSubjectID(jsonChildNode.getString("SubjectId"));
                    homeWorkData.setTermID(jsonChildNode.getString("TermId"));
                    homeWorkData.setWorkPlan(jsonChildNode.getString("WorkPlan"));
                    homeWorkData.setClassWork(jsonChildNode.getString("ClassWork"));
                    homeWorkData.setHomeWork(jsonChildNode.getString("HomeWork"));
//                    homeWorkData.setFont(jsonChildNode.getString(""));

                    homeWorkDatas.add(homeWorkData);
                }
                homeworkModel.setGethomeworkdata(homeWorkDatas);
                result.add(homeworkModel);


            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<LessonPlanModel> parseTeacherLessonPlanScheduleJson(String responseString) {
        ArrayList<LessonPlanModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            LessonPlanModel lessonPlanModel = new LessonPlanModel();
            if (data_load_basket.toString().equals("True")) {
                lessonPlanModel.setFromDate(reader.getString("FromDate"));
                lessonPlanModel.setToDate(reader.getString("ToDate"));

                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");

                LessonPlanModel.LessonplanData lessonplanData = null;
                ArrayList<LessonPlanModel.LessonplanData> lessonplanDatas = new ArrayList<>();

                for (int j = 0; j < jsonMainNode.length(); j++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                    lessonplanData = lessonPlanModel.new LessonplanData();
                    lessonplanData.setDate(jsonChildNode.getString("Date"));
                    lessonplanData.setStandard(jsonChildNode.getString("Standard"));
                    lessonplanData.setClassName(jsonChildNode.getString("ClassName"));
                    lessonplanData.setSubject(jsonChildNode.getString("Subject"));
                    lessonplanData.setHomeWork(jsonChildNode.getString("HomeWork"));
                    lessonplanData.setChapterName(jsonChildNode.getString("ChapterName"));
                    lessonplanData.setObjective(jsonChildNode.getString("Objective"));
                    lessonplanData.setAssessmentQue(jsonChildNode.getString("AssessmentQue"));
                    lessonplanData.setFont(jsonChildNode.getString("Font"));

                    lessonplanDatas.add(lessonplanData);
                }
                lessonPlanModel.setGethomeworkdata(lessonplanDatas);
                result.add(lessonPlanModel);


            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<TeacherLessonPlanModel> parseTeacherLessonPlanJson(String responseString) {
        ArrayList<TeacherLessonPlanModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            TeacherLessonPlanModel teacherLessonPlanModel = new TeacherLessonPlanModel();

            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                TeacherLessonPlanModel.LessonPlan lessonPlan = null;
                ArrayList<TeacherLessonPlanModel.LessonPlan> lessonPlanArrayList = new ArrayList<>();
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    lessonPlan = teacherLessonPlanModel.new LessonPlan();
                    lessonPlan.setSubject(jsonChildNode.getString("Subject"));
                    lessonPlan.setStandard(jsonChildNode.getString("Standard"));

                    JSONArray jsonMainNode1 = jsonChildNode.optJSONArray("Data");
                    TeacherLessonPlanModel.LessonPlan.LessonPlanData lessonPlanData = null;
                    ArrayList<TeacherLessonPlanModel.LessonPlan.LessonPlanData> lessonPlanDatas = new ArrayList<>();
                    for (int j = 0; j < jsonMainNode1.length(); j++) {
                        JSONObject jsonChildNode1 = jsonMainNode1.getJSONObject(j);
                        lessonPlanData = lessonPlan.new LessonPlanData();
                        lessonPlanData.setID(jsonChildNode1.getString("ID"));
                        lessonPlanData.setChapterNo(jsonChildNode1.getString("ChapterNo"));
                        lessonPlanData.setChapterName(jsonChildNode1.getString("ChapterName"));


                        lessonPlanDatas.add(lessonPlanData);
                    }
                    lessonPlan.setGetLessonPlanData(lessonPlanDatas);
                    lessonPlanArrayList.add(lessonPlan);
                }
                teacherLessonPlanModel.setGetLessonPlan(lessonPlanArrayList);
                result.add(teacherLessonPlanModel);
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
