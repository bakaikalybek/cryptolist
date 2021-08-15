package kg.bakai.cryptolist.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "crypto_table")
data class Crypto(
    @PrimaryKey
    val id: String,
    val symbol: String,
    @SerializedName("current_price")
    val currentPrice: Double,
    val image: String,
    val name: String
)