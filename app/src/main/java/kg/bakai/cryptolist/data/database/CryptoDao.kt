package kg.bakai.cryptolist.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kg.bakai.cryptolist.domain.models.Crypto
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crypto: Crypto)

    @Query("SELECT * FROM crypto_table")
    fun getCryptoList(): Flow<List<Crypto>>

    @Query("DELETE FROM crypto_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM crypto_table WHERE name || symbol like '%' || :search || '%'")
    fun getSearchResult(search: String): List<Crypto>
}