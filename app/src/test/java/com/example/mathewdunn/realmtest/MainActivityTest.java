package com.example.mathewdunn.realmtest;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.widget.TextView;

import com.example.mathewdunn.realmtest.activity.MainActivity;
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
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by mathewdunn on 28/02/2017.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP, manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({Realm.class, RealmConfiguration.class,
        RealmQuery.class, RealmResults.class, RealmCore.class})
public class MainActivityTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private MainActivity activity;
    private TextView txt_hello_world;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        // Setup Realm + Mock
        mockStatic(Realm.class);
        mockStatic(RealmConfiguration.class);
        mockStatic(RealmCore.class);

        final Realm mockRealm = mock(Realm.class);
        doNothing().when(RealmCore.class);
        RealmCore.loadLibrary(any(Context.class));
        when(Realm.getDefaultInstance()).thenReturn(mockRealm);
        when(mockRealm.createObject(User.class)).thenReturn(new User());

        activity = Robolectric.setupActivity(MainActivity.class);
        txt_hello_world = (TextView)activity.findViewById(R.id.txt_hello_world);
    }

    @Test
    public void testActivityNotNull(){
        Assert.assertNotNull(activity);
    }

    @Test
    public void testTextViewNotNull(){
        Assert.assertNotNull(txt_hello_world);
    }

    @Test
    public void testTextViewText(){
        Assert.assertEquals(txt_hello_world.getText().toString(), "Hello World!");
    }

    @Test
    public void testNextActivity(){

        Class clazz = SecondActivity.class;
        final Intent expectedIntent2 = new Intent(activity, clazz);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ShadowActivity shadowActivity = Shadows.shadowOf(activity);
                Intent actualIntent2 = shadowActivity.getNextStartedActivity();
                assertEquals(expectedIntent2, actualIntent2);
            }
        }, 3000);
    }

}
