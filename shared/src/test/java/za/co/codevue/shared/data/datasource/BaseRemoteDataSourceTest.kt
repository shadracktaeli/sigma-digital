package za.co.codevue.shared.data.datasource

import androidx.annotation.CallSuper
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

abstract class BaseRemoteDataSourceTest {
    val mockWebServer = MockWebServer()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @CallSuper
    @After
    open fun tearDown() {
        mockWebServer.shutdown()
    }

    protected fun <T : Any> createApiService(apiService: KClass<T>): T {
        return retrofit.create(apiService.java)
    }

    protected fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(
            "api-response/$fileName.json"
        )

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse().apply {
                    setResponseCode(code)
                    setBody(source.readString(StandardCharsets.UTF_8))
                }
            )
        }
    }

    protected fun queueActions(times: Int, action: () -> Unit) {
        for (i in 0 until times) {
            action()
        }
    }
}