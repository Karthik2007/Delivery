package com.karthik.delivery.deliverylist.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.karthik.delivery.R
import com.karthik.delivery.base.BaseApplication
import com.karthik.delivery.base.navigation.AppNavigator
import com.karthik.delivery.base.util.OnItemClickListener
import com.karthik.delivery.deliverylist.data.Delivery
import com.karthik.delivery.deliverylist.di.DaggerDeliveryComponent
import com.karthik.delivery.deliverylist.viewmodel.DeliveryListViewModel
import com.karthik.delivery.deliverylist.viewmodel.DeliveryListViewModelFactory
import kotlinx.android.synthetic.main.activity_delivery.*
import javax.inject.Inject

class DeliveryListActivity : AppCompatActivity(), OnItemClickListener {


    private lateinit var deliveryListViewModelFactory: DeliveryListViewModelFactory
    private lateinit var deliveryListViewModel: DeliveryListViewModel
    private lateinit var appNavigator: AppNavigator
    private lateinit var deliveryListAdapter: DeliveryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        initDagger()

        deliveryListViewModel =
            ViewModelProviders.of(this, deliveryListViewModelFactory).get(DeliveryListViewModel::class.java)

        setUpDeliveryList()
        setupObservers()
        setupActionListeners()

    }

    private fun setupActionListeners() {

        swipe_container.isEnabled = false

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
            deliveryListAdapter.loadItems(it)
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

    /**
     * Helper functions
     */
    private fun initDagger() {
        DaggerDeliveryComponent.builder()
            .appComponent((application as BaseApplication).appComponent)
            .build()
            .injectDependencies(this)
    }

    @Inject
    fun setDependencies(deliveryListViewModelFactory: DeliveryListViewModelFactory, appNavigator: AppNavigator) {
        this.deliveryListViewModelFactory = deliveryListViewModelFactory
        this.appNavigator = appNavigator
    }
}
