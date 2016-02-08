package org.opendatakit.scan;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.*;
import android.util.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CrashTables {
  private static final int APP_START_TIMEOUT = 10000;
  private static final int APP_INIT_TIMEOUT = 10000;
  private static final int OBJ_WAIT_TIMEOUT = 1000;

  private UiDevice mDevice;

  @Before
  public void setup() {
    mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
  }

  @Test
  public void crashBy_fragmentNullPtr() throws IOException, RemoteException {
    int counter = 1;

    while (true) {
      Log.e("crash_tables", "run: " + counter++);

      startApp(mDevice, "org.opendatakit.tables");

      assertThat(
          "Tables cannot be started or crashed",
          mDevice.wait(Until.hasObject(By.desc("Web View")), APP_START_TIMEOUT + APP_INIT_TIMEOUT),
          is(true));

      closeApp(mDevice, "ODK Tables", "org.opendatakit.tables", 2);
    }
  }

  private static void startApp(UiDevice mDevice, String pkgName) {
    mDevice.pressHome(); //Start from home screen

    //wait for package launcher
    mDevice.wait(Until.hasObject(By.pkg(getLauncherPackageName()).depth(0)), APP_START_TIMEOUT);

    //start app
    Context context = InstrumentationRegistry.getContext();
    final Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkgName);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    context.startActivity(intent);
  }

  /**
   * WARNING:
   * Method 1 might not work on all versions of Android
   *
   * appName is name of app displayed on device, for example "ODK Tables"
   */
  private static void closeApp(UiDevice mDevice, String appName, String pkgName, int method)
      throws RemoteException, IOException {
    if (method == 1) {
      mDevice.pressRecentApps();
      //swipe to kill app
      mDevice.wait(Until.findObject(By.text(appName)), OBJ_WAIT_TIMEOUT)
          .swipe(Direction.RIGHT, 1.0f);
      mDevice.pressHome();
    } else {
      mDevice.executeShellCommand("am force-stop " + pkgName);
    }
  }

  private static String getLauncherPackageName() {
    // Create launcher Intent
    final Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_HOME);

    // Use PackageManager to get the launcher package name
    PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
    ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
    return resolveInfo.activityInfo.packageName;
  }
}
