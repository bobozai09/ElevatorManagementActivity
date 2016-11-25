package management.elevator.com.elevatormanagementactivity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.utils.SharedPrefUtils;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    public static final String EXTRA_TEXT = "extra_text";

    private static final int MOCK_LOAD_DATA_DELAYED_TIME = 2000;

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    private WeakRunnable mRunnable = new WeakRunnable(this);
    private String mText;

    private TextView tvText;

    private ProgressBar progressBar;

    public static Fragment newInstance(String text) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT, text);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static class WeakRunnable implements Runnable {
        WeakReference<MainFragment> mMainFragmentReference;


        public WeakRunnable(MainFragment mainFragment) {

            this.mMainFragmentReference = new WeakReference<MainFragment>(mainFragment);

        }

        @Override
        public void run() {
            MainFragment mainFragment = mMainFragmentReference.get();
            if (mainFragment != null && !mainFragment.isDetached()) {
                mainFragment.showProgressBar(false);
                mainFragment.bindData();
            }
        }
    }

    public MainFragment() {


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mText = getArguments().getString(EXTRA_TEXT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvText = (TextView) view.findViewById(R.id.tvText);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            loadData();
        } else {
            mText = savedInstanceState.getString(EXTRA_TEXT);
            bindData();
        }
    }

    private void loadData() {
        showProgressBar(true);


    }

    private void showProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        sHandler.postDelayed(mRunnable, MOCK_LOAD_DATA_DELAYED_TIME);

    }

    private void bindData() {
        boolean isLogin = SharedPrefUtils.isLogin(getActivity());
        tvText.setText(mText + "\n" + "Login" + isLogin);

    }

    @Override
    public void onDestroyView() {
        sHandler.removeCallbacks(mRunnable);
        tvText = null;
        progressBar = null;
        super.onDestroyView();
    }
}
