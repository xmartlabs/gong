package com.xmartlabs.template.data.service

import com.xmartlabs.template.data.model.Location
import retrofit2.http.GET

/**
 * Created by mirland on 28/04/20.
 */
// API should be declared here
interface LocationServiceApi {
  companion object {
    private const val URL_GET_CURRENT_LOCATION = "/json"
  }

  @GET(URL_GET_CURRENT_LOCATION)
  suspend fun getLocation(): Location
}
