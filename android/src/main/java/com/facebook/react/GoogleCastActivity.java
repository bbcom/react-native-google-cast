/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.react;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;

import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.reactnative.googlecast.GoogleCastModule;

import javax.annotation.Nullable;

/**
 * Google Cast Activity for React Native applications.
 */
public abstract class GoogleCastActivity extends AppCompatActivity
    implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {

  private final ReactActivityDelegate mDelegate;

  protected GoogleCastActivity() { mDelegate = createReactActivityDelegate(); }

  /**
   * Returns the name of the main component registered from JavaScript.
   * This is used to schedule rendering of the component.
   * e.g. "MoviesApp"
   */
  protected @Nullable String getMainComponentName() { return null; }

  /**
   * Called at construction time, override if you have a custom delegate
   * implementation.
   */
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new ReactActivityDelegate(this, getMainComponentName());
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setTheme(android.R.style.Theme_Dark);
    mDelegate.onCreate(savedInstanceState);
    // lazy load Google Cast context
    GoogleCastModule.initializeCast(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mDelegate.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mDelegate.onResume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mDelegate.onDestroy();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    mDelegate.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    return mDelegate.onKeyDown(keyCode, event) ||
        super.onKeyDown(keyCode, event);
  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    return mDelegate.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
  }

  @Override
  public boolean onKeyLongPress(int keyCode, KeyEvent event) {
    return mDelegate.onKeyLongPress(keyCode, event) ||
        super.onKeyLongPress(keyCode, event);
  }

  @Override
  public void onBackPressed() {
    if (!mDelegate.onBackPressed()) {
      super.onBackPressed();
    }
  }

  @Override
  public void invokeDefaultOnBackPressed() {
    super.onBackPressed();
  }

  @Override
  public void onNewIntent(Intent intent) {
    if (!mDelegate.onNewIntent(intent)) {
      super.onNewIntent(intent);
    }
  }

  @Override
  public void requestPermissions(String[] permissions, int requestCode,
                                 PermissionListener listener) {
    mDelegate.requestPermissions(permissions, requestCode, listener);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                         int[] grantResults) {
    mDelegate.onRequestPermissionsResult(requestCode, permissions,
                                         grantResults);
  }

  protected final ReactNativeHost getReactNativeHost() {
    return mDelegate.getReactNativeHost();
  }

  protected final ReactInstanceManager getReactInstanceManager() {
    return mDelegate.getReactInstanceManager();
  }

  protected final void loadApp(String appKey) { mDelegate.loadApp(appKey); }
}
