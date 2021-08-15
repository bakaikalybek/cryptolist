package kg.bakai.cryptolist.data.repository

import android.content.Context
import kg.bakai.cryptolist.utils.Result
import kg.bakai.cryptolist.data.database.CryptoDao
import kg.bakai.cryptolist.data.network.ApiService
import kg.bakai.cryptolist.domain.models.Crypto
import kg.bakai.cryptolist.utils.Resource
import kotlinx.coroutines.flow.Flow
import kg.bakai.cryptolist.utils.awaitResult

class MainRepository(
    private val apiService: ApiService,
    private val dao: CryptoDao,
    private val context: Context) {

    suspend fun fetchList(currency: String, amount: Int): Resource<List<Crypto>> {
        return when(val response = apiService.fetchList(currency, amount).awaitResult()) {
            is Result.Ok -> {
                val data = response.value
                data.forEach {
                    dao.insert(it)
                }
                Resource.success(data)
            }
            is Result.Error -> Resource.error(null, "ERROR")
            is Result.Exception -> {
                val text = response.getExceptionMessage(context)
                println(text)
                Resource.error(null, text)
            }
        }
    }

    val listInDatabase: Flow<List<Crypto>> = dao.getCryptoList()

    fun searchCrypto(search: String): List<Crypto> {
        return dao.getSearchResult(search)
    }

}