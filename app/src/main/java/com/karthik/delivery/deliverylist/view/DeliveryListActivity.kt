package com.karthik.delivery.deliverylist.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karthik.delivery.R
import com.karthik.delivery.base.navigation.AppNavigator
import com.karthik.delivery.base.util.OnItemClickListener
import com.karthik.delivery.deliverylist.data.Delivery
import com.karthik.delivery.deliverylist.viewmodel.DeliveryListViewModel
import com.karthik.delivery.deliverylist.viewmodel.DeliveryListViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_delivery.*
import javax.inject.Inject

class DeliveryListActivity : DaggerAppCompatActivity(), OnItemClickListener {


    private lateinit var deliveryListViewModelFactory: DeliveryListViewModelFactory
    private lateinit var deliveryListViewModel: DeliveryListViewModel
    private lateinit var appNavigator: AppNavigator
    private lateinit var deliveryListAdapter: DeliveryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        deliveryListViewModel =
            ViewModelProviders.of(this, deliveryListViewModelFactory).get(DeliveryListViewModel::class.java)

        setUpDeliveryList()
        setupObservers()
        setupActionListeners()

    }

    private fun setupActionListeners() {

        swipe_container.setOnRefreshListener {
            deliveryListViewModel.onPullToRefresh()
        }

        setupScrollListener()

        retry_button.setOnClickListener {
            deliveryListViewModel.retryClicked()
        }
    }

    // scroll listener setup to invoke paged results on reaching bottom of recycler view
    private fun setupScrollListener() {

        val layoutManager = delivery_recycler_view.layoutManager as LinearLayoutManager

        delivery_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                deliveryListViewModel.onScrolledToBottom(
                    visibleItemCount,
                    lastVisibleItem,
                    totalItemCount,
                    swipe_container.isRefreshing
                )
            }
        })
    }


    private fun setUpDeliveryList() {

        deliveryListAdapter = DeliveryListAdapter(this)
        //val animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
        delivery_recycler_view.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            //layoutAnimation = animation
            adapter = deliveryListAdapter
        }

    }

    private fun setupObservers() {

        deliveryListViewModel.deliveryResult.observe(this, Observer {

            if (it?.page == 1) {
                deliveryListAdapter.setItems(it.delivery)
            } else {
                deliveryListAdapter.loadMoreItems(it?.delivery)
            }

            delivery_recycler_view.scheduleLayoutAnimation()
        })

        deliveryListViewModel.swipeContainer.observe(this, Observer {
            swipe_container.isRefreshing = it ?: false
        })

        deliveryListViewModel.noInternetOrError.observe(this, Observer {
            broken_view.visibility = if (it!!) View.VISIBLE else View.GONE
        })

        deliveryListViewModel.offline.observe(this, Observer {

            if (it!!) Toast.makeText(applicationContext, getString(R.string.offline_message), Toast.LENGTH_LONG).show()
        })

        deliveryListViewModel.emptyListView.observe(this, Observer {
            empty_view.visibility = if (it!!) View.VISIBLE else View.GONE
        })

    }


    override fun onItemClick(position: Int, data: Any) {

        appNavigator.goToDeliveryMapActivity(this, data as Delivery)
    }


    @Inject
    fun setDependencies(deliveryListViewModelFactory: DeliveryListViewModelFactory, appNavigator: AppNavigator) {
        this.deliveryListViewModelFactory = deliveryListViewModelFactory
        this.appNavigator = appNavigator
    }
}
