package kg.bakai.cryptolist.data.repository

import kg.bakai.cryptolist.domain.models.Crypto
import kg.bakai.cryptolist.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun fetchList(currency: String, amount: Int): Resource<List<Crypto>>
    fun getListFromDatabase(): Flow<List<Crypto>>
    fun searchCrypto(search: String): List<Crypto>
    suspend fun clearDb()
}