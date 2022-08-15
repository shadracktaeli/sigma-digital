package za.co.codevue.shared.network.retrofit.service

import retrofit2.Call
import retrofit2.http.GET
import za.co.codevue.shared.models.network.EventDTO
import za.co.codevue.shared.network.retrofit.ApiConstants

internal interface IEventApiService {
    @GET(value = ApiConstants.EVENTS_PATH)
    fun getEvents(): Call<List<EventDTO>>

    @GET(value = ApiConstants.SCHEDULE_PATH)
    fun getSchedule(): Call<List<EventDTO>>
}