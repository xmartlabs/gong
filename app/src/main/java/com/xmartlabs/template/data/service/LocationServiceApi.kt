package com.xmartlabs.template.data.service

import com.xmartlabs.template.data.model.Location
import retrofit2.http.GET

/**
 * Created by mirland on 28/04/20.
 */
// API should be declared here
interface LocationServiceApi {
  @GET("/json")
  suspend fun getLocation(): Location
}
