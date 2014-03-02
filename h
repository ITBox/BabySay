[1mdiff --cc app/res/menu/global.xml[m
[1mindex 7ab3429,3e4e6d2..0000000[m
[1m--- a/app/res/menu/global.xml[m
[1m+++ b/app/res/menu/global.xml[m
[36m@@@ -1,13 -1,9 +1,21 @@@[m
  <menu xmlns:android="http://schemas.android.com/apk/res/android" >[m
  [m
[32m++<<<<<<< HEAD[m
[32m +	<item[m
[32m +		android:id="@+id/action_settings"[m
[32m +		android:orderInCategory="100"[m
[32m +		android:showAsAction="ifRoom"/>[m
[32m +	<item[m
[32m +		android:id="@+id/action_settings"[m
[32m +		android:orderInCategory="100"[m
[32m +		android:showAsAction="ifRoom"[m
[32m +		android:title="@string/abc_action_bar_home_description"/>[m
[32m++=======[m
[32m+     <item[m
[32m+         android:id="@+id/action_settings"[m
[32m+         android:orderInCategory="100"[m
[32m+         android:showAsAction="ifRoom"[m
[32m+         android:title="@string/abc_action_bar_home_description"/>[m
[32m++>>>>>>> 056e0581fb9797ec9b696e04803f0659e635ca6f[m
  [m
  </menu>[m
