/*
 * Copyright (C) 2014 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.opendatakit.scan.application;

import org.opendatakit.application.CommonApplication;
import org.opendatakit.scan.R;

public class Scan extends CommonApplication {

  public static final String t = "Scan";

  private static Scan singleton = null;

  public static Scan getInstance() {
    return singleton;
  }

  @Override
  public void onCreate() {
    singleton = this;

    super.onCreate();
  }

  @Override
  public int getApkDisplayNameResourceId() {
    return R.string.app_name;
  }

  @Override
  public int getConfigZipResourceId() {
    return R.raw.configzip;
  }

  @Override
  public int getSystemZipResourceId() {
    return R.raw.systemzip;
  }

}
