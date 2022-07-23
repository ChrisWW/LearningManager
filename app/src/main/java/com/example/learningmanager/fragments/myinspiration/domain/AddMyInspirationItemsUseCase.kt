package com.example.learningmanager.fragments.myinspiration.domain

import com.example.learningmanager.fragments.myinspiration.data.MyInspirationData
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationDetailsResponse
import com.example.learningmanager.fragments.myinspiration.data.local.MyInspirationDataDao
import javax.inject.Inject

class AddMyInspirationItemsUseCase @Inject constructor(private val myInspirationDataDao: MyInspirationDataDao) {
    suspend fun create(params: MyInspirationDetailsResponse) {
        val myInspirationData = MyInspirationData(
            params.id,
            params.title,
            params.description,
            params.imageUrl,
            params.date,
            params.authorQuote,
            params.quote
        )
        myInspirationDataDao.addInspiration(myInspirationData)
    }
}