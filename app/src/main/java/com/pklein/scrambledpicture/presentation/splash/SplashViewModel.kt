package com.pklein.scrambledpicture.presentation.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pklein.scrambledpicture.utils.AccessTokenUtils
import com.pklein.scrambledpicture.utils.SHARED_PREFERENCES_DATA_SAVED
import kotlinx.coroutines.launch

class SplashViewModel(private val splashInteractor: SplashInteractor = SplashInteractor()) :
    ViewModel() {

    fun synchronisedGameData(context: Context) {
        if (!AccessTokenUtils.getSharedPrefBoolean(context, SHARED_PREFERENCES_DATA_SAVED)) {
            splashInteractor.loadJSONFromAsset(context.assets)?.let { listGameData ->
                viewModelScope.launch {
                    splashInteractor.insertGameData(listGameData)
                    // could be improve by waiting end of insert before storing share pref
                    AccessTokenUtils.storeSharedPref(context, SHARED_PREFERENCES_DATA_SAVED, true)
                }
            }
        }
    }
}