package me.zipstream.mix.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zipstream.mix.R;
import me.zipstream.mix.base.BaseFragment;
import me.zipstream.mix.base.Constants;
import me.zipstream.mix.net.RequestManager;
import me.zipstream.mix.util.LocationUtil;

public class WeatherFragment extends BaseFragment {

    private String mDate;
    private String mCity;
    private String mWeather;
    private String mTemp;
    private String mPM;

    @Bind(R.id.date_text_view)
    TextView mDateTextView;
    @Bind(R.id.city_text_view)
    TextView mCityTextView;
    @Bind(R.id.weather_text_view)
    TextView mWeatherTextView;
    @Bind(R.id.temp_text_view)
    TextView mTempTextView;
    @Bind(R.id.pm_text_view)
    TextView mPMTextView;
    @Bind(R.id.pm_ll_layout)
    LinearLayout mPMLayout;

    private String mWeatherUrl = Constants.WEATHER_URL_BASE
            + LocationUtil.getLocationInfo() + Constants.WEATHER_URL_KEY;

    JsonObjectRequest mJsonObjectRequest =
            new JsonObjectRequest(mWeatherUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                parseWeatherInfo(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            setData();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

    private void parseWeatherInfo(JSONObject response) throws Exception {

        JSONArray resultsArray = response.optJSONArray("results");
        JSONObject resultsObject = resultsArray.optJSONObject(0);
        JSONArray weatherArray = resultsObject.optJSONArray("weather_data");
        JSONObject weatherObject = weatherArray.optJSONObject(0);

        mDate = response.optString("date");
        mCity = resultsObject.optString("currentCity");
        mPM = resultsObject.optString("pm25");
        mWeather = weatherObject.optString("weather");
        mTemp = weatherObject.optString("temperature");
    }

    private void setData() {
        mDateTextView.setText(mDate);
        mCityTextView.setText(mCity);
        mPMTextView.setText(mPM);
        mWeatherTextView.setText(mWeather);
        mTempTextView.setText(mTemp);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RequestManager.addRequest(mJsonObjectRequest, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        ButterKnife.bind(this, view);

        return view;
    }
}
