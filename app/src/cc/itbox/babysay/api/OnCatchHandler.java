package cc.itbox.babysay.api;

import cc.itbox.babysay.CatchInfo;
import cc.itbox.babysay.ErrorInfo;

/**
 * 
 * @author youzh
 * 2014-3-9 下午4:04:22
 */
public interface OnCatchHandler {
	void onCatchSucc(int type, CatchInfo ci, Object result);
	void onCatchFail(int type, CatchInfo ci, ErrorInfo ei);
}
