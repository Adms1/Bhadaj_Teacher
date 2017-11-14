package anandniketan.com.bhadajteacher.Models.NewResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectMark {

@SerializedName("Subject")
@Expose
private String subject;
@SerializedName("Marks")
@Expose
private String marks;

public String getSubject() {
return subject;
}

public void setSubject(String subject) {
this.subject = subject;
}

public String getMarks() {
return marks;
}

public void setMarks(String marks) {
this.marks = marks;
}

}