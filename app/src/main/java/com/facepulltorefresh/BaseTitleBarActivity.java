package com.facepulltorefresh;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facepulltorefresh.widget.TitleBar;
import com.facepulltorefresh.widget.TitleBarHelper;

/**
 * @Package com.daoda.aijiacommunity.common.app
 * @作 用:带有titleBar的基本activity
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2016/6/2 0002
 */
public abstract class BaseTitleBarActivity extends AppCompatActivity {
    private TitleBarHelper titleHeaderBarHelper;
    protected TitleBar titleHeaderBar;

    /**
     * 设置带有titleBar
     *
     * @param view
     */
    protected void setContentViewTitleBar(View view) {
        titleHeaderBarHelper = new TitleBarHelper(this, view);
        titleHeaderBar = titleHeaderBarHelper.getTitleHeaderBar();
        setContentView(titleHeaderBarHelper.getContentView());
        /*自定义的一些操作*/
        onCreateCustomTitleBar(titleHeaderBar);
    }

    protected void setContentViewTitleBar(int layoutResID) {
        titleHeaderBarHelper = new TitleBarHelper(this, layoutResID);
        titleHeaderBar = titleHeaderBarHelper.getTitleHeaderBar();
        setContentView(titleHeaderBarHelper.getContentView());
        onCreateCustomTitleBar(titleHeaderBar);
    }

    /**
     * 带有返回按钮的titleBar
     *
     * @param view
     */
    protected void setContentViewTitleBarHaveBack(View view) {
        setContentViewTitleBar(view);
        setLeftBtn(R.drawable.btn_title_back_bg).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 带有返回按钮的titleBar
     *
     * @param resId
     */
    protected void setContentViewTitleBarHaveBack(int resId) {
        setContentViewTitleBar(resId);
        setLeftBtn(R.drawable.btn_title_back_bg).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public abstract void onCreateCustomTitleBar(@NonNull TitleBar titleHeaderBar);

    /**
     * 设置titleBar标题
     *
     * @param title
     * @return
     */
    public TitleBar setTitleText(String title) {
        titleHeaderBar.setTitle(title);
        return titleHeaderBar;
    }

    /**
     * 设置titleBar标题
     *
     * @param resId
     * @return
     */
    public TitleBar setTitleText(int resId) {
        titleHeaderBar.setTitle(getString(resId));
        return titleHeaderBar;
    }

    /**
     * 设置titleBar右边文本
     *
     * @param content
     * @return
     */
    public TitleBar setRightText(String content) {
        View view = getLayoutInflater().inflate(R.layout.titlebar_menu_text_item, null);
        TextView itemView = (TextView) view.findViewById(R.id.title_item);
        itemView.setText(content);
        titleHeaderBar.setCustomizedRightView(view);
        return titleHeaderBar;
    }

    /**
     * 设置titleBar左边文本
     *
     * @param content
     * @return
     */
    public TitleBar setLeftText(String content) {
        View view = getLayoutInflater().inflate(R.layout.titlebar_menu_text_item, null);
        TextView itemView = (TextView) view.findViewById(R.id.title_item);
        itemView.setText(content);
        titleHeaderBar.setCustomizedLeftView(view);
        return titleHeaderBar;
    }

    /**
     * 设置titleBar右边文本
     *
     * @param resId
     * @return
     */
    public TitleBar setRightText(int resId) {
        setRightText(getString(resId));
        return titleHeaderBar;
    }

    /**
     * 设置titleBar左边文本
     *
     * @param resId
     * @return
     */
    public TitleBar setLeftText(int resId) {
        setLeftText(getString(resId));
        return titleHeaderBar;
    }

    /**
     * 设置titleBar左边按钮
     *
     * @param resId
     * @return
     */
    public TitleBar setLeftBtn(int resId) {
        View view = getLayoutInflater().inflate(R.layout.titlebar_menu_icon_item, null);
        ImageView itemView = (ImageView) view.findViewById(R.id.item_icon);
        itemView.setImageResource(resId);
        titleHeaderBar.setCustomizedLeftView(view);
        return titleHeaderBar;
    }

    /**
     * 设置titleBar右边按钮
     *
     * @param resId
     * @return
     */
    public TitleBar setRightBtn(int resId) {
        View view = getLayoutInflater().inflate(R.layout.titlebar_menu_icon_item, null);
        ImageView itemView = (ImageView) view.findViewById(R.id.item_icon);
        itemView.setImageResource(resId);
        titleHeaderBar.setCustomizedRightView(view);
        return titleHeaderBar;
    }

}

