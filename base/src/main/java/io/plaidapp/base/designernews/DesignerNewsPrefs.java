/*
 *   Copyright 2018 Google LLC
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package io.plaidapp.base.designernews;

import android.content.Context;
import android.support.annotation.NonNull;

import io.plaidapp.base.designernews.data.api.DesignerNewsService;
import io.plaidapp.base.designernews.data.api.model.User;
import io.plaidapp.base.designernews.login.data.DesignerNewsLoginRepository;
import io.plaidapp.base.util.ShortcutHelper;

/**
 * Storing Designer News user state
 */
public class DesignerNewsPrefs {

    private static volatile DesignerNewsPrefs singleton;

    private DesignerNewsLoginRepository loginRepository;

    public static DesignerNewsPrefs get(Context context) {
        if (singleton == null) {
            synchronized (DesignerNewsPrefs.class) {
                singleton = new DesignerNewsPrefs(
                        Injection.provideDesignerNewsLoginRepository(context));
            }
        }
        return singleton;
    }

    private DesignerNewsPrefs(DesignerNewsLoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean isLoggedIn() {
        return loginRepository.isLoggedIn();
    }

    public User getUser() {
        return loginRepository.getUser();
    }

    public void logout(@NonNull Context context) {
        loginRepository.logout();
        ShortcutHelper.disablePostShortcut(context);
    }

    public DesignerNewsService getApi() {
        return loginRepository.getApi();
    }

}
