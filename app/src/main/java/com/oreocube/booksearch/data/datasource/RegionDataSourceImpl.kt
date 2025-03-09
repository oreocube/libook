package com.oreocube.booksearch.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.toObject
import com.oreocube.booksearch.data.response.CityResponse
import com.oreocube.booksearch.data.response.DistrictResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegionDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
) : RegionDataSource {
    override suspend fun getCities(): List<CityResponse> {
        return db.collection("city")
            .get()
            .await()
            .map(QueryDocumentSnapshot::toObject)
    }

    override suspend fun getDistricts(cityId: Int): List<DistrictResponse> {
        return db.collection("district")
            .whereEqualTo("city_id", cityId)
            .get()
            .await()
            .map(QueryDocumentSnapshot::toObject)
    }
}
