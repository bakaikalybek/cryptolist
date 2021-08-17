package kg.bakai.cryptolist.ui.viewmodels

import androidx.lifecycle.*
import kg.bakai.cryptolist.data.repository.MainRepository
import kg.bakai.cryptolist.data.repository.Repository
import kg.bakai.cryptolist.domain.models.Crypto
import kg.bakai.cryptolist.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _status = MutableLiveData<Resource<List<Crypto>>>()
    val status: LiveData<Resource<List<Crypto>>> = _status

    val listInDatabase: LiveData<List<Crypto>> = repository.getListFromDatabase().asLiveData()

    fun fetchList(currency: String, amount: Int) {
        scope.launch {
            _status.postValue(Resource.loading(null))
            val response = repository.fetchList(currency, amount)
            _status.postValue(response)
        }
    }

    fun clearDb() {
        scope.launch {
            repository.clearDb()
        }
    }
}