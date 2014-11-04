package com.uk.christo.ciderKit;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

public class ScreenSlidePagerActivity extends FragmentActivity {
	MyPageAdapter pageAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen_slide_pager);
		List<Fragment> fragments = getFragments();
		pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
		ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setAdapter(pageAdapter);
	}

    /*
	public void onRadioButtonClicked(View v){
		Toast.makeText(getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();
	}
	*/

	class MyPageAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragments;

		public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}
		
		public void init(){
			fragments = new ArrayList<Fragment>();
			fragments.add(new AddSugarFragment());
		}

		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
	}

	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();
		Fragment asf = new AddSugarFragment();
		Fragment alc = new SGToAlcFragment();

		fList.add(asf);
		fList.add(alc);
		return fList;
	}
}