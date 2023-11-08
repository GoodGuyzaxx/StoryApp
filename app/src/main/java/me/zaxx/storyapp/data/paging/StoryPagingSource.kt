package me.zaxx.storyapp.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.zaxx.storyapp.data.retrofit.ApiService
import me.zaxx.storyapp.data.retrofit.response.ListStoryItem

class StoryPagingSource(private val apiService: ApiService,val token: String) : PagingSource<Int,ListStoryItem>(){
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPostition ->
            val anchorPage = state.closestPageToPosition(anchorPostition)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {

        return try {
            val postion = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStories(token,postion,params.loadSize)
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (postion == INITIAL_PAGE_INDEX) null else postion -1,
                nextKey = if (responseData.listStory.isEmpty()) null else postion +1
            )
        }catch (e: Exception){
            Log.e("TAG", "load: ${e.localizedMessage}")
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}