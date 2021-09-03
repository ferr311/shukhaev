package com.shukhaev.tinkofftz.repo

import android.content.Context
import com.shukhaev.tinkofftz.R
import com.shukhaev.tinkofftz.model.Post
import com.shukhaev.tinkofftz.network.DevLifeApi
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: DevLifeApi,
    @ApplicationContext val context: Context
) {

    suspend fun getRandomGif(): Resource<Post> {
        return try {
            val response = api.getRandomGif()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(context.getString(R.string.server_error))
            }
        } catch (e: IOException) {
            Resource.Error(e.localizedMessage ?: context.getString(R.string.io_error))
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: context.getString(R.string.http_error))
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: context.getString(R.string.unexpected_error))
        }
    }

    suspend fun getLatestGif(): Resource<List<Post>> {
        return try {
            val response = api.getLatestGif()
            val result = response.result
            if (!result.isNullOrEmpty()) {
                Resource.Success(result)
            } else {
                Resource.Error(context.getString(R.string.server_error))
            }
        } catch (e: IOException) {
            Resource.Error(e.localizedMessage ?: context.getString(R.string.io_error))
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: context.getString(R.string.http_error))
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: context.getString(R.string.unexpected_error))
        }
    }

    suspend fun getBestGif(): Resource<List<Post>> {
        return try {
            val response = api.getBestGif()
            val result = response.result
            if (!result.isNullOrEmpty()) {
                Resource.Success(result)
            } else {
                Resource.Error(context.getString(R.string.server_error))
            }
        } catch (e: IOException) {
            Resource.Error(e.localizedMessage ?: context.getString(R.string.io_error))
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: context.getString(R.string.http_error))
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: context.getString(R.string.unexpected_error))
        }
    }
}