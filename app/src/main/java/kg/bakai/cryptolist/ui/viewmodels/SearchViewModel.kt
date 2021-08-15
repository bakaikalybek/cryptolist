package kg.bakai.cryptolist.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.bakai.cryptolist.data.repository.MainRepository
import kg.bakai.cryptolist.domain.models.Crypto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: MainRepository): ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _searchResult = MutableLiveData<List<Crypto>>()
    val searchResult: LiveData<List<Crypto>> = _searchResult

    fun searchCrypto(search: String) {
        scope.launch {
            _searchResult.postValue(repository.searchCrypto(search))
        }
    }
}