package cc.itbox.babysay.activities;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.util.CommonUtil;
import cc.itbox.babysay.util.Constants;
import cc.itbox.babysay.util.UIUtil;

public class WeiboOAuthActivity extends BaseActivity {
	private WebView mWebView;
	private MenuItem refreshItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oauth);

		mActionBar.setDisplayHomeAsUpEnabled(true);

		mWebView = UIUtil.getView(this, R.id.webView);
		mWebView.setWebViewClient(new WeiboWebViewClient());

		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSaveFormData(false);
		settings.setSavePassword(false);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.actionbar_oauth, menu);
		refreshItem = menu.findItem(R.id.action_refresh);
		refresh();
		return true;
	}

	public void refresh() {
		mWebView.clearView();
		mWebView.loadUrl("about:blank");
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ImageView iv = (ImageView) inflater.inflate(
				R.layout.refresh_action_view, null);

		Animation rotation = AnimationUtils.loadAnimation(this, R.anim.refresh);
		iv.startAnimation(rotation);

		MenuItemCompat.setActionView(refreshItem, iv);
		mWebView.loadUrl(getWeiboOAuthUrl());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			refresh();
			return true;
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private String getWeiboOAuthUrl() {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("client_id", Constants.WEIBO_KEY);
		parameters.put("response_type", "token");
		parameters.put("redirect_uri", Constants.WEIBO_DIRECT_URL);
		parameters.put("display", "mobile");
		return Constants.WEIBO_OAUTH_URL + "?"
				+ CommonUtil.encodeUrl(parameters)
				+ "&scope=friendships_groups_read,friendships_groups_write";
	}

	private class WeiboWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {

			super.onPageStarted(view, url, favicon);

		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);

		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);

		}
	}
}
