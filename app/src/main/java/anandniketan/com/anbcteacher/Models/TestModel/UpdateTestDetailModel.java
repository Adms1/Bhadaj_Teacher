package anandniketan.com.anbcteacher.Models.TestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/6/2017.
 */

public class UpdateTestDetailModel {



        @SerializedName("Success")
        @Expose
        private String success;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("FinalArray")
        @Expose
        private List<Object> finalArray = null;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Object> getFinalArray() {
            return finalArray;
        }

        public void setFinalArray(List<Object> finalArray) {
            this.finalArray = finalArray;
        }


}
