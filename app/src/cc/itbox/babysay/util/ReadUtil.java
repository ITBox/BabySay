package cc.itbox.babysay.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;

public class ReadUtil {
	public static String readFile(Activity activity, String fileName) {
		AssetManager assets = activity.getAssets();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len;
		try {
			InputStream inputStream = assets.open(fileName);
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			inputStream.close();
			return outputStream.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
