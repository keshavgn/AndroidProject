package com.example.keshavanarasappa.androidproject

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.example.keshavanarasappa.androidproject.room.AppDatabase
import com.example.keshavanarasappa.androidproject.room.People
import com.example.keshavanarasappa.androidproject.room.PeopleDao
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PeopleDaoTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var peopleDao: PeopleDao

    @Before
    fun setup() {
        val context: Context = InstrumentationRegistry.getTargetContext()
        try {
            database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        } catch (e: Exception) {
            Log.i("test", e.message)
        }
        peopleDao = database.peopleDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testAddingAndRetrievingData() {
        val preInsertRetrievedCategories = peopleDao.fetchPeople()

        val people = People("Keshav", 9902884924, city = "Bangalore")
        peopleDao.insertPeople(people)

        val postInsertRetrievedCategories = peopleDao.fetchPeople()
        val sizeDifference = postInsertRetrievedCategories.size - preInsertRetrievedCategories.size
        Assert.assertEquals(1, sizeDifference)
        val retrievedCategory = postInsertRetrievedCategories.last()
        Assert.assertEquals("Keshav", retrievedCategory.name)
    }
}