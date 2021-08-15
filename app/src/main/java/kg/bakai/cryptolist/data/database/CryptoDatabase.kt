package kg.bakai.cryptolist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kg.bakai.cryptolist.domain.models.Crypto

@Database(
    entities = [Crypto::class],
    version = 2,
    exportSchema = false
)
abstract class CryptoDatabase: RoomDatabase() {
    abstract val dao: CryptoDao
}