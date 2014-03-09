package cc.itbox.babysay.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cc.itbox.babysay.AppContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class UIUtil {

	/**
	 * http://android-activities.blogspot.jp/2013/11/cleaner-view-casting-with-
	 * generics.html
	 * 
	 * @param activity
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final static <E extends View> E getView(Activity activity, int id) {
		try {
			return (E) activity.findViewById(id);
		} catch (ClassCastException ex) {
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	public final static <E extends View> E getView(View view, int id) {
		try {
			return (E) view.findViewById(id);
		} catch (ClassCastException ex) {
			throw ex;
		}
	}

	public final static void showLongToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	public final static void showShortToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
    
	 /**
     * 比较本地版本号，跟服务器的版本号
     * @param version
     * @return
     */
	public static boolean compareVersion(String version) {
		if(!version.equals(getApkVersionName())){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取应用版本号
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static int getApkVersionCode() {
		int versionCode = -1;
		try {
			versionCode = AppContext.mContext.getPackageManager()
					.getPackageInfo(AppContext.mContext.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	/**
	 * 获取应用版本NAME
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getApkVersionName() {
		String versionCode = null;
		try {
			versionCode = AppContext.mContext.getPackageManager()
					.getPackageInfo(AppContext.mContext.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	/**
	 * 安装apk
	 * 
	 * @param file
	 */
	public static void installApk(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		AppContext.mContext.startActivity(intent);
	}
	
	/**
	 * 下载APK
	 * @param urlPath
	 * @param savePath
	 * @param pb
	 * @return
	 */
	public static File download(String urlPath, String savePath, ProgressBar pb) {
		FileOutputStream fos = null;
		int count = 0;
		try {
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			int code = conn.getResponseCode();
			if (code == 200) {
				pb.setMax(conn.getContentLength());
				File file = new File(savePath);
				if (file.exists()){
					file.delete();
				}
				file.getParentFile().mkdirs();
				file.createNewFile();
				fos = new FileOutputStream(file);
				InputStream is = conn.getInputStream();
				byte[] buf = new byte[1024];
				int len;
				while ((len = is.read(buf)) != -1) {
					fos.write(buf, 0, len);
					count += len;
					pb.setProgress(count);
				}
				fos.flush();
				fos.close();
				return file;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fos != null){
				try {
					fos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 删除SD卡缓冲目录
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {
        if (path == null || path.trim().length() == 0) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }
}
