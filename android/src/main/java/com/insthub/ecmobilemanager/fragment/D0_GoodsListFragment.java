package com.insthub.ecmobilemanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.BeeFramework.fragment.BaseFragment;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.activity.D1_FilterActivity;
import com.insthub.ecmobilemanager.activity.D3_GoodsEditActivity;
import com.insthub.ecmobilemanager.adapter.D0_GoodsListAdapter;
import com.insthub.ecmobilemanager.adapter.D0_GoodsListLargeAdapter;
import com.insthub.ecmobilemanager.model.GoodsListModel;
import com.insthub.ecmobilemanager.protocol.ApiInterface;
import com.insthub.ecmobilemanager.protocol.FILTER;
import com.insthub.ecmobilemanager.protocol.PAGINATED;

import org.json.JSONException;
import org.json.JSONObject;

public class D0_GoodsListFragment extends BaseFragment implements BusinessResponse, IXListViewListener,OnClickListener
{
    private View view;

    private ImageView item_grid_button;

	private XListView goodlistView;
	private GoodsListModel dataModel;
	private D0_GoodsListAdapter listAdapter;
    private D0_GoodsListLargeAdapter largeListActivityAdapter;

    private ImageView bg;

    private boolean isSetAdapter = true;

	private View null_pager;


    public static final String KEYWORD = "keyword";
    public static final String CATEGORY_ID = "category_id";
    public static final String TITLE = "title";
    public static final String FILTER = "filter";

    public static final int IS_HOT = 0;
    public static final int PRICE_DESC_INT = 1;
    public static final int PRICE_ASC_INT = 2;

    private int flag = IS_HOT;

    public String predefine_category_id;

	protected class TitleCellHolder
	{
		public ImageView triangleImageView;
		public TextView titleTextView;
		public ImageView orderIconImageView;
        public RelativeLayout tabRelative;
	}

	TitleCellHolder tabOneCellHolder;
	TitleCellHolder tabTwoCellHolder;
	TitleCellHolder tabThreeCellHolder;

    private BeeBaseAdapter currentAdapter;

    FILTER filter = new FILTER();

    private ImageView search;
    private ImageView addButton;
    private EditText input;
    private Button searchFilter;
    private View bottom_line;
	private View top_view;
	public D0_GoodsListFragment()
	{

	}
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.d0_goods_list, null);

        input = (EditText)view.findViewById(R.id.search_input);
        search = (ImageView)view.findViewById(R.id.search_search);
        addButton = (ImageView)view.findViewById(R.id.add_goods);
        searchFilter = (Button)view.findViewById(R.id.search_filter);
        bottom_line = (View)view.findViewById(R.id.bottom_line);
        top_view = view.findViewById(R.id.top_view);
        search.setOnClickListener(this);
        searchFilter.setOnClickListener(this);
        top_view.setOnClickListener(this);

        input.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        input.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = input.getText().toString().toString();
                    if (null != keyword && keyword.length() > 0) {
                        D0_GoodsListFragment.this.filter.keywords = keyword;
                        dataModel.fetchPreSearch(D0_GoodsListFragment.this.filter);
                    } else {
                        D0_GoodsListFragment.this.filter.keywords = null;
                        dataModel.fetchPreSearch(D0_GoodsListFragment.this.filter);
                    }
                    CloseKeyBoard();
                }
                return false;
            }
        });

        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), D3_GoodsEditActivity.class);
                //intent.putExtra("", "");
                startActivityForResult(intent, 2);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });

//        final LinearLayout mainView = (LinearLayout)view;//(LinearLayout) view.findViewById(R.id.keyboardLayout1);
//        ViewTreeObserver mainViewObserver =  mainView.getViewTreeObserver();
//        if (null != mainViewObserver)
//        {
//            mainViewObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
//            {
//                @Override
//                public void onGlobalLayout()
//                {
//                    int heightDiff = mainView.getRootView().getHeight() - mainView.getHeight();
//                    if (heightDiff > 100)
//                    { // if more than 100 pixels, its probably a keyboard...
//                        input.setCursorVisible(true);
//                        top_view.setVisibility(View.VISIBLE);
//                        searchFilter.setVisibility(View.GONE);
//                    }
//                    else
//                    {
//                        input.setCursorVisible(false);
//                        top_view.setVisibility(View.INVISIBLE);
//                        searchFilter.setVisibility(View.VISIBLE);
//                    }
//                }
//            });
//        }

        bg = (ImageView) view.findViewById(R.id.goodslist_bg);
        null_pager = view.findViewById(R.id.null_pager);

		goodlistView = (XListView)view.findViewById(R.id.goods_listview);

        goodlistView.setPullLoadEnable(true);
        goodlistView.setRefreshTime();
        goodlistView.setXListViewListener(this, 1);

		dataModel = new GoodsListModel(getActivity());
        dataModel.addResponseListener(this);

        largeListActivityAdapter = new D0_GoodsListLargeAdapter(getActivity(), dataModel.goodsList);

        tabOneCellHolder = new TitleCellHolder();
        tabTwoCellHolder = new TitleCellHolder();
        tabThreeCellHolder = new TitleCellHolder();

        tabOneCellHolder.titleTextView = (TextView)view.findViewById(R.id.filter_title_tabone);
        tabOneCellHolder.orderIconImageView = (ImageView)view.findViewById(R.id.filter_order_tabone);
        tabOneCellHolder.triangleImageView = (ImageView)view.findViewById(R.id.filter_triangle_tabone);
        tabOneCellHolder.tabRelative        = (RelativeLayout)view.findViewById(R.id.tabOne);
        tabOneCellHolder.tabRelative.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectedTab(IS_HOT);
            }
        });

        tabTwoCellHolder.titleTextView = (TextView)view.findViewById(R.id.filter_title_tabtwo);
        tabTwoCellHolder.orderIconImageView = (ImageView)view.findViewById(R.id.filter_order_tabtwo);
        tabTwoCellHolder.triangleImageView = (ImageView)view.findViewById(R.id.filter_triangle_tabtwo);
        tabTwoCellHolder.tabRelative        = (RelativeLayout)view.findViewById(R.id.tabTwo);
        tabTwoCellHolder.tabRelative.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                flag = PRICE_DESC_INT;
                selectedTab(PRICE_DESC_INT);
            }
        });

        tabThreeCellHolder.titleTextView = (TextView)view.findViewById(R.id.filter_title_tabthree);
        tabThreeCellHolder.orderIconImageView = (ImageView)view.findViewById(R.id.filter_order_tabthree);
        tabThreeCellHolder.triangleImageView = (ImageView)view.findViewById(R.id.filter_triangle_tabthree);
        tabThreeCellHolder.tabRelative        = (RelativeLayout)view.findViewById(R.id.tabThree);
        tabThreeCellHolder.tabRelative.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = PRICE_ASC_INT;
                selectedTab(PRICE_ASC_INT);
            }
        });
        selectedTab(PRICE_ASC_INT);
        flag = PRICE_ASC_INT;

        return view;
	}

    void selectedTab(int index)
    {
    	isSetAdapter = true;
        Resources resource = (Resources) getActivity().getBaseContext().getResources();
        ColorStateList selectedTextColor = (ColorStateList) resource.getColorStateList(R.color.filter_text_color);

        if (index == IS_HOT)
        {
            tabOneCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_active_arrow);
            tabOneCellHolder.orderIconImageView.setWillNotCacheDrawing(true);
            tabOneCellHolder.triangleImageView.setVisibility(View.VISIBLE);
            tabOneCellHolder.titleTextView.setTextColor(Color.WHITE);

            tabTwoCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_arrow);
            tabTwoCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabTwoCellHolder.titleTextView.setTextColor(selectedTextColor);

            tabThreeCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabThreeCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_up_arrow);
            tabThreeCellHolder.titleTextView.setTextColor(selectedTextColor);

            filter.sort_by = dataModel.IS_HOT;
            dataModel.fetchPreSearch(filter);
        }
        else if (index == PRICE_DESC_INT)
        {
            tabTwoCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_active_arrow);
            tabTwoCellHolder.triangleImageView.setVisibility(View.VISIBLE);
            tabTwoCellHolder.titleTextView.setTextColor(Color.WHITE);

            tabOneCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_arrow);
            tabOneCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabOneCellHolder.titleTextView.setTextColor(selectedTextColor);

            tabThreeCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabThreeCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_up_arrow);
            tabThreeCellHolder.titleTextView.setTextColor(selectedTextColor);

            filter.sort_by = dataModel.PRICE_DESC;
            dataModel.fetchPreSearch(filter);;
        }
        else
        {

            tabThreeCellHolder.triangleImageView.setVisibility(View.VISIBLE);
            tabThreeCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_up_active_arrow);
            tabThreeCellHolder.titleTextView.setTextColor(Color.WHITE);

            tabOneCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_arrow);
            tabOneCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabOneCellHolder.titleTextView.setTextColor(selectedTextColor);

            tabTwoCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_arrow);
            tabTwoCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabTwoCellHolder.titleTextView.setTextColor(selectedTextColor);

            filter.sort_by = dataModel.PRICE_ASC;
            dataModel.fetchPreSearch(filter);

        }
    }

    public void setContent()
    {
    	if(listAdapter == null)
        {
    		if(dataModel.goodsList.size() == 0)
            {
    			bg.setVisibility(View.GONE);
    			null_pager.setVisibility(View.VISIBLE);
                bottom_line.setBackgroundColor(Color.parseColor("#FFFFFF"));
    		}
            else
            {
    			bg.setVisibility(View.GONE);
    			null_pager.setVisibility(View.GONE);
                bottom_line.setBackgroundColor(Color.parseColor("#000000"));
    			listAdapter = new D0_GoodsListAdapter(getActivity(), dataModel.goodsList);
                goodlistView.setAdapter(listAdapter);
    		}
    	}
        else
        {
    		if(isSetAdapter == true)
            {
    			if (currentAdapter == largeListActivityAdapter)
                {
                    goodlistView.setAdapter(largeListActivityAdapter);
                }
                else
                {
                	listAdapter = new D0_GoodsListAdapter(getActivity(), dataModel.goodsList);
                    goodlistView.setAdapter(listAdapter);
                }
    		}
            else
            {
    			listAdapter.dataList = dataModel.goodsList;
        		listAdapter.notifyDataSetChanged();
    		}
    	}



    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {
    	if(url.endsWith(ApiInterface.GOODS_LIST))
        {
    		goodlistView.stopRefresh();
    		goodlistView.stopLoadMore();
    		goodlistView.setRefreshTime();

    		setContent();
            PAGINATED paginated = new PAGINATED();
            paginated.fromJson(jo.optJSONObject("data").optJSONObject("paginated"));
            if (0 == paginated.more)
            {
                goodlistView.setPullLoadEnable(false);
            }
            else
            {
                goodlistView.setPullLoadEnable(true);
            }
    	}
    }

    @Override
    public void onClick(View v) {
        String tag;
        Intent intent;
        switch(v.getId())
        {
            case R.id.search_search:

                break;
            case R.id.top_view:
                CloseKeyBoard();
                input.setText("");
                break;
            case R.id.search_voice:
            {
                //showRecognizerDialog(); //弹出语音搜索框
                break;
            }
            case R.id.search_filter:
            {
                Intent it = new Intent(getActivity(), D1_FilterActivity.class);
                try
                {
                    it.putExtra("filter",filter.toJson().toString());
                    if (null != predefine_category_id)
                    {
                        it.putExtra("predefine_category_id",predefine_category_id);
                    }
                }
                catch (JSONException e)
                {
                }
                startActivityForResult(it, 1);
                break;
            }
        }
    }

	@Override
	public void onRefresh(int id)
    {
		isSetAdapter = true;
        dataModel.fetchPreSearch(filter);
	}
	@Override
	public void onLoadMore(int id)
    {
		isSetAdapter = false;
        dataModel.fetchPreSearchMore(filter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode){
            case 1:
            {
                if(null != data)
                {
                    String filter_string =  data.getStringExtra("filter");
                    if (null != filter_string)
                    {
                        try
                        {
                            JSONObject filterJSONObject = new JSONObject(filter_string);
                            FILTER filter = new FILTER();
                            filter.fromJson(filterJSONObject);
                            this.filter.category_id = filter.category_id;
                            this.filter.price_range = filter.price_range;
                            this.filter.brand_id = filter.brand_id;
                            dataModel.fetchPreSearch(this.filter);
                            input.clearFocus();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }

	@Override
	public void onResume()
    {
		super.onResume();
        //MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this), "");
        //MobclickAgent.onPageStart("FilterPage");
	}

    // 关闭键盘
    public void CloseKeyBoard()
    {
        input.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        //MobclickAgent.onPageEnd("FilterPage");
        //MobclickAgent.onPause(this);
    }
}
