package anandniketan.com.anbcteacher.Utility;

import com.squareup.okhttp.ResponseBody;

import java.util.Map;

import anandniketan.com.anbcteacher.Models.NewResponse.MainResponse;
import anandniketan.com.anbcteacher.Models.TermModel;
import okhttp3.MultipartBody;
import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

/**
 * Created by admsandroid on 11/20/2017.
 */

public interface WebServices {

    @FormUrlEncoded
    @POST("/TeacherGetTestMarks")
    public void GetTeacherTestMarks(@FieldMap Map<String,String> map,Callback<MainResponse> callback);


    @FormUrlEncoded
    @POST("/GetTerm")
    public void getTerm(@FieldMap Map<String, String> map, Callback<TermModel> callback);




}
