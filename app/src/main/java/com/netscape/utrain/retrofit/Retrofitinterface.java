package com.netscape.utrain.retrofit;

import com.netscape.utrain.activities.organization.OrganizationSignUpActivity;
import com.netscape.utrain.response.AthleteSignUpResponse;
import com.netscape.utrain.response.CoachSignUpResponse;
import com.netscape.utrain.response.ForgetPasswordResponse;
import com.netscape.utrain.response.LoginResponse;
import com.netscape.utrain.response.OrgSignUpResponse;
import com.netscape.utrain.response.ServiceListResponse;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface    Retrofitinterface {

    @Multipart
    @POST(Constants.ATHLETE_SIGNUP)
    Call<AthleteSignUpResponse> athleteSignUp(@Part MultipartBody.Part file,
                                              @Query("name") String name,
                                              @Query("email") String email,
                                              @Query("password") String password,
                                              @Query("phone") String phone,
                                              @Query("address") String address,
                                              @Query("latitude") String latitude,
                                              @Query("longitude") String longitude,
                                              @Query("device_type") String device_type,
                                              @Query("device_token") String device_token,
                                              @Header("Content-Type") String contentType);

    @FormUrlEncoded
    @POST(Constants.LOGIN_METHOD)
    Call<LoginResponse> userLogin(@Field("email") String email,
                                      @Field("password") String password,
                                      @Field("device_type") String device_type,
                                      @Field("device_token") String device_token,
                                      @Header("Content-Type") String contentType);
    @FormUrlEncoded
    @POST("reset-password")
    Call<ForgetPasswordResponse> getForgetpassword(@Header("Content-Type") String Content_Type,
                                                   @Field("email") String email);


    @POST(Constants.SERVICES)
    Call<ServiceListResponse> getServiceList(@Header("Content-Type") String Content_Type);






    @FormUrlEncoded
    @POST(Constants.COACH_SIGNUP)
    Call<CoachSignUpResponse> coachSignUp(@Header("Content-Type") String Content_Type,
                                          @Field("name") String name,
                                          @Field("email") String email,
                                          @Field("password") String password,
                                          @Field("phone") String phone,
                                          @Field("location") String location,
                                          @Field("latitude") String latitude,
                                          @Field("longitude") String longitude,
                                          @Field("business_hour_starts") String business_hour_starts,
                                          @Field("business_hour_ends") String business_hour_ends,
                                          @Field("bio") String bio,
                                          @Field("service_ids") String service_ids ,
                                          @Field("expertise_years") String expertise_years,
                                          @Field("hourly_rate") String hourly_rate,
                                          @Field("device_type") String device_type,
                                          @Field("device_token") String device_token);

        @Multipart
        @POST(Constants.ORG_SIGNUP)
        Call<OrgSignUpResponse> orgSignup(@Part MultipartBody.Part profile_image,
                                          @Part MultipartBody.Part portfolio_image_1,
                                          @Part MultipartBody.Part portfolio_image_2,
                                          @Part MultipartBody.Part portfolio_image_3,
                                          @Part MultipartBody.Part portfolio_image_4,
                                          @Query("name") String name,
                                          @Query("email") String email,
                                          @Query("password") String password,
                                          @Query("phone") String phone,
                                          @Query("location") String location,
                                          @Query("latitude") String latitude,
                                          @Query("longitude") String longitude,
                                          @Query("bio") String bio,
                                          @Query("service_ids") JSONArray serviceIds,
                                          @Query("expertise_years") String expertiesYears,
                                          @Query("hourly_rate") String hourlyRate,
                                          @Query("business_hour_starts") String businessStartTime,
                                          @Query("business_hour_ends") String businessEndTime,
                                          @Query("device_type") String device_type,
                                          @Query("device_token") String device_token,
                                          @Header("Content-Type") String contentType);
    @Multipart
    @POST(Constants.COACH_SIGNUP)
    Call<CoachSignUpResponse> coachSignup(@Header("Content-Type") String contentType,
                                      @Query("name") String name,
                                      @Query("email") String email,
                                      @Query("password") String password,
                                      @Query("phone") String phone,
                                      @Query("location") String location,
                                      @Query("latitude") String latitude,
                                      @Query("longitude") String longitude,
                                      @Query("business_hour_starts") String businessStartTime,
                                      @Query("business_hour_ends") String businessEndTime,
                                      @Query("bio") String bio,
                                      @Query("service_ids") String serviceIds,
                                      @Query("expertise_years") String expertiesYears,
                                      @Query("hourly_rate") String hourlyRate,
                                      @Query("device_type") String device_type,
                                      @Query("device_token") String device_token,
                                      @Part MultipartBody.Part profile_image, @Part("name") RequestBody requestBody);

    @Multipart
    @POST(Constants.ATHLETE_SIGNUP)
    Call<AthleteSignUpResponse> registerAthlete(@PartMap Map<String, RequestBody> fields,
                                                 @Part MultipartBody.Part image);
    @Multipart
    @POST(Constants.COACH_SIGNUP)
    Call<CoachSignUpResponse> registerCoach(@PartMap Map<String, RequestBody> fields,
                                                 @Part MultipartBody.Part image);
    @Multipart
    @POST(Constants.ORG_SIGNUP)
    Call<OrgSignUpResponse> registerOrganization(@PartMap Map<String, RequestBody> fields,
                                                 @Part MultipartBody.Part profileImage,
                                                 @Part MultipartBody.Part imageOne,
                                                 @Part MultipartBody.Part imageTwo,
                                                 @Part MultipartBody.Part imageThree,
                                                 @Part MultipartBody.Part imageFour);
}
