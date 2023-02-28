import android.content.ClipData
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inventory.data.Customer
import com.example.inventory.data.CustomerDao
import com.example.inventory.data.CustomerDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class CustomerDaoTest {
    private lateinit var customerDao: CustomerDao
    private lateinit var inventoryDatabase: CustomerDatabase
    private var customer1 = Customer(id= 1, name ="Apples", phone = 8032458214)
    private var customer2 = Customer(id= 2, name ="Oranges", phone = 8032458214)

    private suspend fun addOneCustomerToDb() {
        customerDao.insert(customer1)
    }

    private suspend fun addTwoCustomersToDb() {
        customerDao.insert(customer1)
        customerDao.insert(customer2)
    }
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, CustomerDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        customerDao = inventoryDatabase.customerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        inventoryDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsCustomerIntoDB() = runBlocking {
        addOneCustomerToDb()
        val allItems = customerDao.getAllCustomers().first()
        assertEquals(allItems[0], customer1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllCustomers_returnsAllCustomersFromDB() = runBlocking {
        addTwoCustomersToDb()
        val allItems = customerDao.getAllCustomers().first()
        assertEquals(allItems[0], customer1)
        assertEquals(allItems[1], customer2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateCustomers_updatesItemCustomersInDB() = runBlocking {
        addTwoCustomersToDb()
        customerDao.update(Customer(id= 1, name ="Oranges", phone = 803353214))
        customerDao.update(Customer(id= 1, name ="Guava", phone = 8032452904))

        val allCustomers = customerDao.getAllCustomers().first()
        assertEquals(allCustomers[0], Customer(id= 1, name ="Oranges", phone = 803353214))
        assertEquals(allCustomers[1], Customer(id= 1, name ="Guava", phone = 8032452904))
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteCustomers_deletesAllCustomersFromDB() = runBlocking {
        addTwoCustomersToDb()
        customerDao.delete(customer1)
        customerDao.delete(customer2)
        val allCustomers = customerDao.getAllCustomers().first()
        assertTrue(allCustomers.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoGetCustomer_returnsCustomerFromDB() = runBlocking {
        addOneCustomerToDb()
        val customer = customerDao.getCustomer(1)
        assertEquals(customer.first(), customer1)
    }
}