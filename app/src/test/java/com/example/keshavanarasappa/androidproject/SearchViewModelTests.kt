package com.example.keshavanarasappa.androidproject
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.example.keshavanarasappa.androidproject.Search.SearchViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`

/**
 * Created by keshava.narasappa on 02/04/18.
 */
@RunWith(JUnit4::class)
class SearchViewModelTests {

    private lateinit var viewModel: SearchViewModel
    @Test
    fun searchViewModel() {
        viewModel = mock()
//        doNothing().`when`(viewModel.queryBooks(""))
        viewModel.queryBooks("")
        verify(viewModel).queryBooks("")
    }

}