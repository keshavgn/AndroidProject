package com.example.keshavanarasappa.androidproject

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.example.keshavanarasappa.androidproject.room.*
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CarDaoTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var peopleDao: PeopleDao
    private lateinit var carDao: CarDao

    @Before
    fun setup() {
        val context: Context = InstrumentationRegistry.getTargetContext()
        try {
            database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        } catch (e: Exception) {
            Log.i("test", e.message)
        }
        carDao = database.carDao()
        peopleDao = database.peopleDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testAddingAndRetrievingData() {
        val people = People("Keshav", 9902884924, city = "Bangalore")
        peopleDao.insertPeople(people)

        val person = peopleDao.fetchPeople().first()
        val preInsertRetrievedCategories = carDao.fetchCars(person.id)

        val car1 = Car(name = "BMW1", model = "Q1",year = "2010", cid = person.id)
        val car2 = Car(name = "BMW2", model = "Q1",year = "2010", cid = person.id)
        val car3 = Car(name = "BMW3", model = "Q1",year = "2010", cid = person.id)
        carDao.insertCars(mutableListOf(car1, car2, car3))
        val car4 = Car(name = "BMW3", model = "Q1",year = "2010", cid = 2)
        carDao.insertCar(car4)

        val postInsertRetrievedCategories = carDao.fetchCars(person.id)
        val cars = carDao.fetchAll()
        val sizeDifference = postInsertRetrievedCategories.size - preInsertRetrievedCategories.size
        Assert.assertEquals(3, sizeDifference)
        val retrievedCategory = postInsertRetrievedCategories.last()
        Assert.assertEquals("BMW3", retrievedCategory.name)
    }
}