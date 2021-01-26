package com.clp3z.vitcord.mvp.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.clp3z.vitcord.R;
import com.clp3z.vitcord.mvp.view.custom.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clelia López on 9/14/16
 */
public class ViewPagerAdapter
        extends FragmentPagerAdapter {

    /**
     * Attributes
     */
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();
    private final List<Integer> icons = new ArrayList<>();
    private Context context;


    public ViewPagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragment(Fragment fragment, int title, int icon) {
        fragments.add(fragment);
        titles.add(context.getString(title));
        icons.add(icon);
    }

    @SuppressLint("InflateParams")
    public View getTabView(int position) {
        View tab = LayoutInflater.from(context).inflate(R.layout.item_tab, null);
        CustomTextView tabText = tab.findViewById(R.id.title_text_view);
        ImageView tabImage = tab.findViewById(R.id.icon_image_view);
        tabText.setText(titles.get(position));
        tabImage.setBackgroundResource(icons.get(position));
        if (position == 0)
            tab.setSelected(true);
        return tab;
    }
}