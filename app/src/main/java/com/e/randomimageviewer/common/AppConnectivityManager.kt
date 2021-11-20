package com.e.randomimageviewer.common

import android.content.Context
import androidx.annotation.NonNull
import androidx.lifecycle.*
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.error.DefaultErrorHandler
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AppConnectivityManager private constructor() : LifecycleObserver {
    private var networkDisposable: Disposable? = null
    private val connectivity: MutableLiveData<Connectivity> = MutableLiveData()
    private val internetConnectivity: MutableLiveData<InternetConnection> = MutableLiveData()
    private var internetDisposable: Disposable? = null
    private val initialInterval = 1000
    private val interval = 5000
    private val timeout = 5000
    val networkConnectivity: LiveData<Connectivity>
        get() = connectivity

    fun getInternetConnectivity(): LiveData<InternetConnection> {
        return internetConnectivity
    }

    fun init(context: Context?) {
        initNetworkSubscriber(context!!.applicationContext)
        initInternetSubscriber()
    }

    private fun initInternetSubscriber() {
        if (internetDisposable != null) {
            return
        }
        val settings: InternetObservingSettings? = initInternetObservingSettings()
        internetDisposable = startInternetObserving(settings)
    }

    private fun startInternetObserving(settings: InternetObservingSettings?): Disposable? {
        return ReactiveNetwork
            .observeInternetConnectivity(settings)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToInternet: Boolean? ->
                publishInternetStatus(
                    isConnectedToInternet
                )
            }
    }

    private fun publishInternetStatus(isConnectedToInternet: Boolean?) {
        val internetConnection: InternetConnection? =
            internetConnectivity.value
        internetConnection?.hasIternet = isConnectedToInternet ?: false
        internetConnectivity.value = internetConnection
    }

    @NonNull
    private fun initInternetObservingSettings(): InternetObservingSettings? {
        return InternetObservingSettings.builder()
            .initialInterval(initialInterval)
            .interval(interval)
            .host("http://www.google.com")
            .timeout(timeout)
            .errorHandler(DefaultErrorHandler())
            .strategy(SocketInternetObservingStrategy())
            .build()
    }

    private fun initNetworkSubscriber(context: Context?) {
        if (networkDisposable != null) {
            return
        }
        networkDisposable = ReactiveNetwork.observeNetworkConnectivity(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connectivity: com.github.pwittchen.reactivenetwork.library.rx2.Connectivity? ->
                publishNetworkStatus(
                    connectivity
                )
            }
    }

    private fun publishNetworkStatus(connectivity: com.github.pwittchen.reactivenetwork.library.rx2.Connectivity?) {
        val connectionStatus: Connectivity? =
            this.connectivity.value
        connectionStatus?.hasConnection = connectivity?.available() ?: false
        this.connectivity.value = connectionStatus
    }

    private fun safelyDispose(vararg disposables: Disposable?) {
        for (subscription in disposables) {
            if (subscription != null && !subscription.isDisposed) {
                subscription.dispose()
            }
        }
    }

    fun updateObservers() {
        publishInternetStatus(getInternetConnectivity().value?.hasIternet)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeManager() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseManager() {
        safelyDispose(networkDisposable, internetDisposable)
    }

    class Connectivity {
        var hasConnection = false
    }

    class InternetConnection {
        var hasIternet: Boolean? = null
    }

    companion object {
        private val TAG: String? = AppConnectivityManager::class.java.name
        private var appConnectivityManager: AppConnectivityManager? = null
        val instance: AppConnectivityManager
            get() {
                if (appConnectivityManager == null) {
                    appConnectivityManager = AppConnectivityManager()
                }
                return appConnectivityManager!!
            }
    }

    init {
        connectivity.value = Connectivity()
        internetConnectivity.value = InternetConnection()
    }
}