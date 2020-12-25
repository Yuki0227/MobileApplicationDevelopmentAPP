package com.app.email.adapter;


import androidx.annotation.Nullable;

import com.app.R;
import com.app.email.adapter.item.AttachmentItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AttachmentAdapter extends BaseQuickAdapter<AttachmentItem, BaseViewHolder> {

    public AttachmentAdapter(@Nullable List<AttachmentItem> data) {
        super(R.layout.email_item_attachment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AttachmentItem item) {
        helper.setText(R.id.email_item_attachment_filename, item.getFilename())
                .setText(R.id.email_item_attachment_size, item.getSize())
                .setText(R.id.email_item_attachment_point, item.getPoint())
                .addOnClickListener(R.id.email_item_attachment_rl);
    }

}
