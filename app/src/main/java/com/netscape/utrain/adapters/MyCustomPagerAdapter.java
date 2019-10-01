package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.netscape.utrain.R;

import java.util.List;

public class MyCustomPagerAdapter extends PagerAdapter {
Context context;
List<Integer> images;
LayoutInflater layoutInflater;


public MyCustomPagerAdapter(Context context, List<Integer> images) {
this.context = context;
this.images = images;
layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}

@Override
public int getCount() {
return images.size();
}

@Override
public boolean isViewFromObject(View view, Object object) {
return view == (object);
}

@Override
public Object instantiateItem(ViewGroup container, final int position) {
View itemView = layoutInflater.inflate(R.layout.coaches_recycler_view, container, false);

ImageView imageView = (ImageView) itemView.findViewById(R.id.coachImageView);
imageView.setImageResource(images.get(position));
container.addView(itemView);
//listening to image click
imageView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
// Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
}
});

return itemView;
}

@Override
public void destroyItem(ViewGroup container, int position, Object object) {
container.removeView((View) object);
}
}