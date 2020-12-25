package com.app.email.adapter;


import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.R;
import com.app.email.adapter.item.MsgItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MsgAdapter extends BaseQuickAdapter<MsgItem, BaseViewHolder> {

    public MsgAdapter(@Nullable List<MsgItem> data) {
        super(R.layout.email_item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgItem item) {
        helper.setText(R.id.email_item_message_nickname, item.getSenderNickname())
                .setText(R.id.email_item_message_subject, item.getSubject())
                .setText(R.id.email_item_message_date, item.getDate());
        ((TextView) helper.getView(R.id.email_item_message_nickname))
                .getPaint().setFakeBoldText(!item.isRead());
        ((TextView) helper.getView(R.id.email_item_message_subject))
                .getPaint().setFakeBoldText(!item.isRead());
        ((TextView) helper.getView(R.id.email_item_message_date))
                .getPaint().setFakeBoldText(!item.isRead());
    }

}
