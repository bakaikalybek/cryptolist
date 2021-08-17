package kg.bakai.cryptolist.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kg.bakai.cryptolist.domain.models.Crypto
import kg.bakai.cryptolist.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CryptoDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: CryptoDatabase
    private lateinit var dao: CryptoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CryptoDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertNewCryptoToDatabase() = runBlockingTest {
        val newCrypto = Crypto("new", "nwc", 1.999, "", "New Crypto")
        dao.insert(newCrypto)

        val allCryptoList = dao.getCryptoList().asLiveData().getOrAwaitValue()

        assertThat(allCryptoList).contains(newCrypto)
    }

    @Test
    fun deleteAllItemsFromDatabase() = runBlockingTest {
        val newCrypto = Crypto("new", "nwc", 1.999, "", "New Crypto")
        dao.insert(newCrypto)
        dao.deleteAll()

        val allCryptoList = dao.getCryptoList().asLiveData().getOrAwaitValue()

        assertThat(allCryptoList).doesNotContain(newCrypto)
    }
}