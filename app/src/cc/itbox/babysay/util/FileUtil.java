package cc.itbox.babysay.util;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import cc.itbox.babysay.exception.SDCardNotFoundException;

/**
 * 
 * @author malinkang 2014-3-8
 * 
 */

public class FileUtil {
	// 应用文件夹
	public static final String APP_DIR = "BabySay";
	// 头像
	public static final String AVATAR_PICTURE = "avatar.jpg";

	public enum StorageOption {

		INTERNAL_MEMORY// 手机内存
		, SD_CARD_ROOT// 内存卡
		, SD_CARD_APP_ROOT//

	}

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean isSDCardAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 向SD卡写入文件
	 * 
	 * @param path
	 * @return
	 * @throws SDCardNotFoundException
	 */
	public static File createNewFileInSDCard(StorageOption storageOption,
			String path) throws SDCardNotFoundException {
		if (FileUtil.isSDCardAvailable() == false) {
			throw new SDCardNotFoundException("SDCard is not mounted.");
		}
		if (storageOption == StorageOption.SD_CARD_ROOT) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ path;
		}
		if (!isSDCardAvailable()) {
			return null;
		}
		File file = new File(path);
		if (file.exists()) {
			return file;
		} else {
			File dir = file.getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}
			try {
				if (file.createNewFile()) {
					return file;
				}
			} catch (IOException e) {
				return null;
			}
		}
		return null;

	}

	/**
	 * 获取File对象
	 * 
	 * @param context
	 * @param storageOption
	 * @param path
	 * @return
	 */
	public static File getFile(Context context, StorageOption storageOption,
			String fileName) {
		if (storageOption == StorageOption.SD_CARD_ROOT) {
			return new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + fileName);
		} else if (storageOption == StorageOption.SD_CARD_APP_ROOT) {
			return new File(context.getExternalFilesDir(null).getAbsolutePath()
					+ "/" + fileName);
		} else {
			return new File(context.getFilesDir().getAbsolutePath() + "/"
					+ fileName);
		}
	}

}
