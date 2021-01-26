package com.clp3z.vitcord.mvp.model;

import com.clp3z.vitcord.mvp.base.contracts.HomeModelProvided;
import com.clp3z.vitcord.mvp.base.generic.GenericModel;
import com.clp3z.vitcord.mvp.base.interfaces.PresenterMethods;
import com.clp3z.vitcord.mvp.base.manager.SharedPreferenceManager;
import com.clp3z.vitcord.mvp.base.util.Logger;
import com.clp3z.vitcord.mvp.model.response.User;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
  * Created by Clelia López on 4/23/2019
 */
@SuppressWarnings("FieldCanBeLocal")
public class HomeModel
        extends GenericModel<PresenterMethods.Required>
        implements HomeModelProvided {

    /**
     * Attributes
     */
    private String TAG = getClass().getSimpleName();
    protected Logger logger = new Logger(TAG);
    private LinkedHashMap<String,User> followingMap;
    private ArrayList<User> followingList;

    private static final String SAVED_FOLLOWING_KEY = "following";

    // TODO
    // This model is reused, so later is duplicated, which means there are two instances of this
    // This causes issues, since data source should be unique
    // For a future release, separate the data source and place it in a manager

    // TODO
    // There should always be an instance of data / Singleton


    @SuppressWarnings("unchecked")
    @Override
    public void fetch() {
         this.followingList = new ArrayList<>();

        // Try to get persistence data
        followingMap = (LinkedHashMap<String,User>) SharedPreferenceManager
                .getObject(context, SAVED_FOLLOWING_KEY, new TypeToken<LinkedHashMap<String,User>>(){}.getType());

        if (followingMap == null) {
            followingMap = new LinkedHashMap<>();
            SharedPreferenceManager.saveObject(context, SAVED_FOLLOWING_KEY, followingMap);
        } else {
            for (Map.Entry<String,User> entry : followingMap.entrySet())
                followingList.add(entry.getValue());
        }
    }

    @Override
    public void saveFollowing(User user) {
        followingList.add(user);
        followingMap.put(user.getId(), user);
        SharedPreferenceManager.saveObject(context, SAVED_FOLLOWING_KEY, followingMap);
    }

    @Override
    public void removeFollowing(User user) {
        followingList.remove(user);
        followingMap.remove(user.getId());
        SharedPreferenceManager.saveObject(context, SAVED_FOLLOWING_KEY, followingMap);
    }

    @Override
    public ArrayList<User> getFollowings() {
        return followingList;
    }

    @Override
    public int getFollowingsCount() {
        return followingMap.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean doesExist(User user) {
        // Update data source
        followingMap = (LinkedHashMap<String,User>) SharedPreferenceManager
                .getObject(context, SAVED_FOLLOWING_KEY, new TypeToken<LinkedHashMap<String,User>>(){}.getType());

        if (followingMap != null)
            return followingMap.containsKey(user.getId());
        else
            return false;
    }
}
