package com.avoscloud.leanchatlib_demo;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import com.avos.avoscloud.AVOSCloud;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.controller.UserInfoFactory;
import com.avoscloud.leanchatlib.model.UserInfo;
import com.avoscloud.leanchatlib.utils.Logger;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;

/**
 * Created by lzw on 15/4/27.
 */
public class App extends Application {
  public static void initImageLoader(Context context) {
    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
        context)
        .threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
            //.memoryCache(new WeakMemoryCache())
        .denyCacheImageMultipleSizesInMemory()
        .tasksProcessingOrder(QueueProcessingType.LIFO)
        .build();
    ImageLoader.getInstance().init(config);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    AVOSCloud.initialize(this, "xcalhck83o10dntwh8ft3z5kvv0xc25p6t3jqbe5zlkkdsib",
        "m9fzwse7od89gvcnk1dmdq4huprjvghjtiug1u2zu073zn99");
    Logger.level = Logger.VERBOSE; // set Logger.NONE when release
    AVOSCloud.setDebugLogEnabled(true);  // set false when release
    final ChatManager chatManager = ChatManager.getInstance();
    chatManager.init(this);
    chatManager.setUserInfoFactory(new UserInfoFactory() {
      @Override
      public UserInfo getUserInfoById(String userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userId);
        userInfo.setAvatarUrl("http://ac-x3o016bx.clouddn.com/86O7RAPx2BtTW5zgZTPGNwH9RZD5vNDtPm1YbIcu");
        return userInfo;
      }

      @Override
      public void cacheUserInfoByIdsInBackground(List<String> userIds) throws Exception {
      }

      @Override
      public boolean showNotificationWhenNewMessageCome(String selfId) {
        return true;
      }

      @Override
      public void configureNotification(Notification notification) {
        notification.defaults |= Notification.DEFAULT_ALL;
      }
    });
    initImageLoader(this);
  }
}
