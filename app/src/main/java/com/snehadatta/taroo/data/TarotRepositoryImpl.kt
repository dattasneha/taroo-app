package com.snehadatta.taroo.data

import com.snehadatta.taroo.data.local.TarotDao
import com.snehadatta.taroo.data.local.entity.History
import com.snehadatta.taroo.data.model.GetAllCardsResponse
import com.snehadatta.taroo.network.TarotApi
import com.snehadatta.taroo.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject

class TarotRepositoryImpl @Inject constructor(
    private val tarotApi: TarotApi,
    private val tarotDao: TarotDao
) : TarotRepository {
    override fun getAllCards(): Flow<Resource<GetAllCardsResponse>> = flow {
        emit(Resource.Loading())

        try {
            val response = tarotApi.getAllCards()
            if(response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("No response found"))
            }
            else {
                emit(Resource.Error("API Error: ${response.code()}"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, Something went wrong!"
            ))

        }catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, Check your internet connection."
            ))

        }

    }

    override suspend fun insert(history: History) {
        tarotDao.insert(history)
    }

    override suspend fun delete(histories: List<History>) {
        tarotDao.delete(histories)
    }

    override suspend fun getHistory(): List<History> {
        return tarotDao.getHistory()
    }
}