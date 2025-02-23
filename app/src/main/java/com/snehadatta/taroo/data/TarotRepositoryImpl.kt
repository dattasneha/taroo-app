package com.snehadatta.taroo.data

import com.snehadatta.taroo.data.model.GetAllCardsResponse
import com.snehadatta.taroo.network.TarotApi
import com.snehadatta.taroo.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject

class TarotRepositoryImpl @Inject constructor(
    private val tarotApi: TarotApi
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
}