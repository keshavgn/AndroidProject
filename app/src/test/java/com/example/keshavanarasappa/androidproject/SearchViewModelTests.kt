package com.example.keshavanarasappa.androidproject
import com.example.keshavanarasappa.androidproject.Search.SearchViewModel
import org.junit.Test
import org.junit.Assert.*
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify

/**
 * Created by keshava.narasappa on 02/04/18.
 */
class SearchViewModelTests {

    private lateinit var viewModel: SearchViewModel
    @Test
    fun searchViewModel() {
        viewModel = mock()
        viewModel.queryBooks("")
        verify(viewModel).queryBooks("")
    }

}