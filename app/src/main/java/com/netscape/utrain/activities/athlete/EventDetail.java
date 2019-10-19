package com.netscape.utrain.activities.athlete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.EventBookingActivity;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.adapters.MyCustomPagerAdapter;
import com.netscape.utrain.adapters.ViewPagerAdapter;
import com.netscape.utrain.databinding.ActivityEventDetailBinding;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import me.relex.circleindicator.CircleIndicator;

public class EventDetail extends AppCompatActivity {

    private ActivityEventDetailBinding binding;

    MaterialTextView venueAddress, eventName, eventInstructionsDetailTv, eventTimeDetailTv, eventDateDetailTv;
    MaterialTextView title;
    MaterialTextView endDateTime;

    String eventType = "";

    AppCompatImageView imgBackArrowImage;


    ViewPager viewPager;
    ViewPagerAdapter adapter;
    List<String> imageList = new ArrayList<>();
    MyCustomPagerAdapter pagerAdapter;
    MaterialTextView evntJoinNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_event_detail);

        binding = DataBindingUtil.setContentView(EventDetail.this,R.layout.activity_event_detail);
        binding.eventBookingBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        venueAddress = findViewById(R.id.eventVanueDetailTv);
        eventName = findViewById(R.id.eventMarathonHeaderTv);
        title = findViewById(R.id.title);
        eventTimeDetailTv = findViewById(R.id.eventTimeDetailTv);
        eventDateDetailTv = findViewById(R.id.eventDateDetailTv);
        eventInstructionsDetailTv = findViewById(R.id.eventInstructionsDetailTv);
        evntJoinNow = findViewById(R.id.evntJoinNow);

        eventName.setText(getIntent().getStringExtra("eventName"));
        venueAddress.setText(getIntent().getStringExtra("eventVenue"));//eventEndDateTime
        eventTimeDetailTv.setText(getIntent().getStringExtra("eventTime"));
        eventDateDetailTv.setText(getIntent().getStringExtra("eventDate"));
        eventInstructionsDetailTv.setText(getIntent().getStringExtra("eventDescription"));

        if (getIntent().getStringExtra("from") != null)
            if (getIntent().getStringExtra("from").equalsIgnoreCase("places")) {
                title.setText("Spaces");

                binding.eventNumOfCandidateAttendingTv.setVisibility(View.GONE);
                binding.eventCandidateTv.setVisibility(View.GONE);
                binding.eventNumOfCandidateTv.setVisibility(View.GONE);
                binding.view2.setVisibility(View.GONE);
                eventType = "space";
            }

        if (getIntent().getStringExtra("from") != null)
            if (getIntent().getStringExtra("from").equalsIgnoreCase("events")) {
                title.setText("Events");
                binding.eventNumOfCandidateAttendingTv.setVisibility(View.VISIBLE);
                binding.eventCandidateTv.setVisibility(View.VISIBLE);
                binding.eventNumOfCandidateTv.setVisibility(View.VISIBLE);
                binding.view2.setVisibility(View.VISIBLE);

                binding.eventNumOfCandidateTv.setText(getIntent().getIntExtra("capacity",0)+"");
                eventType = "event";
            }
        if (getIntent().getStringExtra("from") != null)
            if (getIntent().getStringExtra("from").equalsIgnoreCase("sessions")) {
                title.setText("Sessions");
                binding.eventNumOfCandidateAttendingTv.setVisibility(View.GONE);
                binding.eventCandidateTv.setVisibility(View.GONE);
                binding.eventNumOfCandidateTv.setVisibility(View.GONE);
                binding.view2.setVisibility(View.GONE);
                eventType = "session";

            }
        Bundle b = getIntent().getExtras();
        if (b != null) {
            String Array = b.getString("Array");
            try {
                JSONArray jsonArray = new JSONArray(Array);
                for (int i = 0; i < jsonArray.length(); i++) {
                    imageList.add(String.valueOf(getIntent().getStringExtra("image_url") + jsonArray.get(i)));
                }
            } catch (JSONException e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            imgBackArrowImage = findViewById(R.id.eventBookingBackImg);
            imgBackArrowImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, EventDetail.this).equalsIgnoreCase(Constants.Athlete)) {
                        Intent intent = new Intent(EventDetail.this, AthleteHomeScreen.class);
                        startActivity(intent);
                    } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, EventDetail.this).equalsIgnoreCase("Organizer")) {
                        Intent intent = new Intent(EventDetail.this, OrgHomeScreen.class);
                        startActivity(intent);
                    }
                }
            });

            evntJoinNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(EventDetail.this, EventBookingActivity.class);
                    intent.putExtra("event_id", getIntent().getIntExtra("event_id", 0));
                    intent.putExtra("type", eventType);
                    startActivity(intent);

                }
            });
            viewPager = findViewById(R.id.viewPagerImage);
            pagerAdapter = new MyCustomPagerAdapter(this, imageList);
            viewPager.setAdapter(pagerAdapter);
            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
            if (viewPager.getAdapter().getCount() == 1)

                indicator.setViewPager(null);
            else
                indicator.setViewPager(viewPager);
        }
    }
}
