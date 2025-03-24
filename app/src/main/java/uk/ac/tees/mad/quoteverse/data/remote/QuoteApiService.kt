package uk.ac.tees.mad.quoteverse.data.remote

import retrofit2.http.GET
import uk.ac.tees.mad.quoteverse.model.QuoteResponse

interface QuoteApiService {
    @GET("quotes")
    suspend fun getQuotes(): List<QuoteResponse>
}