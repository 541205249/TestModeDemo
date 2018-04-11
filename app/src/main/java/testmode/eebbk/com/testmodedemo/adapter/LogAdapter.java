package testmode.eebbk.com.testmodedemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/8
 */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {
    private LinkedList<LogEntity> mLogEntities;
    private OnLogChangeListener mOnLogChangeListener;

    public LogAdapter() {
        mLogEntities = new LinkedList<>();
    }

    @Override
    public void onBindViewHolder(final LogViewHolder holder, int position) {
        if (mLogEntities == null) {
            return;
        }

        final LogEntity logEntity = mLogEntities.get(position);

        if (logEntity == null) {
            return;
        }

        String log = holder.itemView.getContext().getResources().getString(R.string.log,
                logEntity.getTarget(),
                logEntity.getMethodName(),
                logEntity.getSuccessCount(),
                logEntity.getFailCount(),
                logEntity.getSpentTime(),
                logEntity.getValue(),
                logEntity.getDescription(),
                logEntity.getDate());
        holder.mLogTv.setText(log);
    }

    public int insertData(LogEntity logEntity) {
        if (mLogEntities == null || logEntity == null) {
            return -1;
        }

        mLogEntities.addFirst(logEntity);
        return 0;
    }

    public int[] insertData(List<LogEntity> logEntities) {
        if (mLogEntities == null || logEntities == null) {
            return null;
        }

        int count = 0;
        for (int i = logEntities.size() - 1; i >= 0; i--) {
            LogEntity logEntity = logEntities.get(i);
            if (logEntity != null) {
                mLogEntities.addFirst(logEntity);
                count++;
            }
        }

        return new int[]{0, count};
    }

    public int removeData(LogEntity logEntity) {
        if (mLogEntities == null || logEntity == null) {
            return -1;
        }

        for (int i = 0; i < mLogEntities.size(); i++) {
            if (logEntity.equals(mLogEntities.get(i))) {
                mLogEntities.remove(i);
                return i;
            }
        }

        return -1;
    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LogViewHolder logViewHolder = new LogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, null));
        logViewHolder.mDeleteBtn.setOnClickListener(v -> {
            int position = logViewHolder.getAdapterPosition();
            LogEntity logEntity = mLogEntities.remove(position);
            DataRepository.getInstance().removeData(logEntity);
            notifyItemRemoved(position);
            if (mOnLogChangeListener != null) {
                mOnLogChangeListener.onDeleteLog(logEntity);
            }
        });
        return logViewHolder;
    }

    @Override
    public int getItemCount() {
        return mLogEntities == null ? 0 : mLogEntities.size();
    }

    public void setOnLogChangeListener(OnLogChangeListener onLogChangeListener) {
        mOnLogChangeListener = onLogChangeListener;
    }

    static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView mLogTv;
        ImageButton mDeleteBtn;

        public LogViewHolder(View itemView) {
            super(itemView);

            mLogTv = itemView.findViewById(R.id.log_tv);
            mDeleteBtn = itemView.findViewById(R.id.log_delete_btn);
        }
    }

    public interface OnLogChangeListener {
        void onDeleteLog(LogEntity logEntity);
    }
}
