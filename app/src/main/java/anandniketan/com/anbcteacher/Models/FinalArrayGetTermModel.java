package anandniketan.com.anbcteacher.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinalArrayGetTermModel {

    @SerializedName("TermId")
    @Expose
    private Integer termId;
    @SerializedName("Term")
    @Expose
    private String term;

    public Integer getTermId() {
            return termId;
        }

        public void setTermId(Integer termId) {
            this.termId = termId;
        }
        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }


}
