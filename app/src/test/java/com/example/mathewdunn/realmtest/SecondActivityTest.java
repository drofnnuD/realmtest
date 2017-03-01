package com.example.mathewdunn.realmtest;

import android.content.Context;
import android.os.Build;

import com.example.mathewdunn.realmtest.activity.SecondActivity;
import com.example.mathewdunn.realmtest.data.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by mathewdunn on 01/03/2017.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP, manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({Realm.class, RealmConfiguration.class,
        RealmQuery.class, RealmResults.class, RealmCore.class, RealmResults.class, RealmQuery.class})
public class SecondActivityTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private SecondActivity activity;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        // Setup Realm + Mock
        mockStatic(Realm.class);
        mockStatic(RealmConfiguration.class);
        mockStatic(RealmCore.class);
        mockStatic(RealmQuery.class);

        final Realm mockRealm = mock(Realm.class);
        doNothing().when(RealmCore.class);
        RealmCore.loadLibrary(any(Context.class));

        when(Realm.getDefaultInstance()).thenReturn(mockRealm);
        when(mockRealm.createObject(User.class)).thenReturn(new User());

        User userOne = new User("Matt", "Dunn");
        User userTwo = new User("Michael", "Stoddart");

        List<User> userList = Arrays.asList(userOne, userTwo);

        RealmQuery<User> realmQuery = mockRealmQuery();
        RealmResults<User> mockResults = mockRealmResults();

        when(mockResults.iterator()).thenReturn(userList.iterator());
        when(mockResults.size()).thenReturn(userList.size());

        when(mockResults.first()).thenReturn(userList.get(0));

        activity = Robolectric.setupActivity(SecondActivity.class);
    }

    @Test
    public void checkActivityNotNull(){
        Assert.assertNotNull(activity);
    }

    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmQuery<T> mockRealmQuery() {
        return mock(RealmQuery.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmResults<T> mockRealmResults() {
        return mock(RealmResults.class);
    }
}
