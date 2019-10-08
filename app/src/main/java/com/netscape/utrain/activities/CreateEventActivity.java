package com.netscape.utrain.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.PortfolioImagesConstants;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.activities.organization.OrgMapFindAddressActivity;
import com.netscape.utrain.databinding.ActivityCreateEventBinding;
import com.netscape.utrain.databinding.ActivityCreateEventBindingImpl;
import com.netscape.utrain.response.OrgCreateEventResponse;
import com.netscape.utrain.response.OrgCreateEventResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.netscape.utrain.activities.PortfolioActivity.clearFromConstants;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatSpinner spinnerLocation;
    MaterialTextView startBusinessHourTv, endBusinessHourTv, textViewDate, createEventStartDateTv, createEventEndDatetv;
    TextInputEditText tvEnterCapicity;
    private ProgressDialog progressDialog;
    private ActivityCreateEventBinding binding;
    private int ADDRESS_EVENT = 132;
    private int IMAGE_GET = 166;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String locationLat = "", locationLong = "";
    private Retrofitinterface retrofitinterface;
    private String eventName = "", eventDescription = "", eventAddress = "", eventStartDate = "", eventEndDate = "", eventStartTime = "", eventEndtime = "", eventEquipments = "", eventCapacity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_event);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);

        textViewDate = findViewById(R.id.createEventStartDateTv);
        tvEnterCapicity = findViewById(R.id.createEventCapicityEdt);
        createEventStartDateTv = findViewById(R.id.createEventStartDateTv);
        createEventEndDatetv = findViewById(R.id.createEventEndDatetv);
        startBusinessHourTv = findViewById(R.id.createEvtnStartTimeTv);
        endBusinessHourTv = findViewById(R.id.createEventEndTimeTv);
        startBusinessHourTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        startBusinessHourTv.setText(hourOfDay + ":" + minute);
                        startBusinessHourTv.setPadding(20, 0, 70, 0);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        endBusinessHourTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        endBusinessHourTv.setText(hourOfDay + ":" + minute);
                        endBusinessHourTv.setPadding(20, 0, 70, 0);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });


        createEventStartDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        createEventStartDateTv.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        createEventStartDateTv.setPadding(20, 0, 20, 0);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });


        createEventEndDatetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        createEventEndDatetv.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        createEventEndDatetv.setPadding(20, 0, 20, 0);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


//        spinnerLocation = findViewById(R.id.createEvent_LocationSpinner);
//
//        List<String> list = new ArrayList<String>();
//        list.add("Select Location");
//        list.add("Texas");
//        list.add("California");
//        list.add("India");
//        list.add("Canada");
//        list.add("Australia");
//        list.add("Brazil");
//
//        ArrayAdapter adapter = new ArrayAdapter(CreateEventActivity.this, android.R.layout.simple_spinner_item, list);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerLocation.setAdapter(adapter);
//
//        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                spinnerLocation.setSelection(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        init();
    }

    private void init() {
        binding.getAddressTv.setOnClickListener(this);
        binding.createEventBtn.setOnClickListener(this);
        binding.createEventImages.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getAddressTv:
                Intent getAddress = new Intent(CreateEventActivity.this, OrgMapFindAddressActivity.class);
                getAddress.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(getAddress, ADDRESS_EVENT);
                break;
            case R.id.createEventImages:
                Intent getImages = new Intent(CreateEventActivity.this, PortfolioActivity.class);
                getImages.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PortfolioActivity.getImages=true;
                startActivityForResult(getImages, ADDRESS_EVENT);
                break;
            case R.id.createEventBtn:
                getDataFromEdtText();
                break;
        }
    }

    private void getDataFromEdtText() {
        eventName = binding.getAddressTv.getText().toString();
        eventDescription = binding.getAddressTv.getText().toString();
        eventAddress = binding.getAddressTv.getText().toString();
        eventStartDate = binding.createEventStartDateTv.getText().toString();
        eventEndDate = binding.createEventEndDatetv.getText().toString();
        eventStartTime = binding.createEvtnStartTimeTv.getText().toString();
        eventEndtime = binding.createEventEndTimeTv.getText().toString();
        eventEquipments = binding.createEventEquipmentTv.getText().toString();
        eventCapacity = binding.createEventCapicityEdt.getText().toString();


        if (eventName.isEmpty()) {

        } else if (eventDescription.isEmpty()) {

        } else if (eventAddress.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.selecte_event_address), Toast.LENGTH_SHORT).show();
        } else if (eventStartDate.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_start_date), Toast.LENGTH_SHORT).show();
        } else if (eventEndDate.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_end_date), Toast.LENGTH_SHORT).show();

        } else if (eventStartTime.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_start_time), Toast.LENGTH_SHORT).show();

        } else if (eventEndtime.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.select_end_time), Toast.LENGTH_SHORT).show();

//        } else if (eventEquipments.isEmpty()) {
//            Toast.makeText(this, getResources().getString(R.string.enter_equipments_required), Toast.LENGTH_SHORT).show();


        } else if (eventCapacity.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.enter_numbe_of_guest_allowed), Toast.LENGTH_SHORT).show();
        } else {
            hitCreateEventApi();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADDRESS_EVENT) {
                if (data != null && data.hasExtra(Constants.ADDRESS)) {
                    binding.getAddressTv.setText(data.getStringExtra(Constants.ADDRESS));
                    binding.getAddressTv.setError(null);
                    locationLat = data.getStringExtra(Constants.LOCATION_LAT);
                    locationLong = data.getStringExtra(Constants.LOCATION_LONG);
                }
            } else if (requestCode == IMAGE_GET) {
                if (data != null && data.hasExtra(Constants.ADDRESS)) {
                    Toast.makeText(CreateEventActivity.this, "Images Imported", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(CreateEventActivity.this, "Unable to get Address", Toast.LENGTH_SHORT).show();
        }
    }

    private void hitCreateEventApi() {
        progressDialog.show();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), eventName));
        requestBodyMap.put("description", RequestBody.create(MediaType.parse("multipart/form-data"), eventDescription));
        requestBodyMap.put("start_date", RequestBody.create(MediaType.parse("multipart/form-data"), eventStartDate));
        requestBodyMap.put("start_time", RequestBody.create(MediaType.parse("multipart/form-data"), eventStartTime));
        requestBodyMap.put("end_date", RequestBody.create(MediaType.parse("multipart/form-data"), eventEndDate));
        requestBodyMap.put("end_time", RequestBody.create(MediaType.parse("multipart/form-data"), eventEndtime));
        requestBodyMap.put("location", RequestBody.create(MediaType.parse("multipart/form-data"), eventAddress));
        requestBodyMap.put("latitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLat));
        requestBodyMap.put("longitude", RequestBody.create(MediaType.parse("multipart/form-data"), locationLong));
        requestBodyMap.put("service_id", RequestBody.create(MediaType.parse("multipart/form-data"), "1"));
        requestBodyMap.put("guest_allowed", RequestBody.create(MediaType.parse("multipart/form-data"), eventCapacity));
        requestBodyMap.put("equipment_required", RequestBody.create(MediaType.parse("multipart/form-data"), eventEquipments));
        requestBodyMap.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TYPE));
        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TOKEN));
        requestBodyMap.put("price", RequestBody.create(MediaType.parse("multipart/form-data"), CommonMethods.getPrefData(PrefrenceConstant.PRICE, CreateEventActivity.this)));

        //        requestBodyMap.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.DEVICE_TOKEN));
        Call<OrgCreateEventResponse> signUpAthlete = retrofitinterface.createEvent(requestBodyMap, PortfolioImagesConstants.partOne, PortfolioImagesConstants.partTwo, PortfolioImagesConstants.partThree, PortfolioImagesConstants.partFour);
        signUpAthlete.enqueue(new Callback<OrgCreateEventResponse>() {
            @Override
            public void onResponse(Call<OrgCreateEventResponse> call, Response<OrgCreateEventResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            PortfolioActivity.clearFromConstants();
                            Constants.CHECKBOX_IS_CHECKED = 0;
                            Toast.makeText(CreateEventActivity.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Snackbar.make(binding.createEventLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.createEventLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.createEventLayout, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgCreateEventResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.createEventLayout, "" + t, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }
}
