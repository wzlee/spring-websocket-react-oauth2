package li.barlog.app.rest

import li.barlog.app.config.ITConfig
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(
	classes = arrayOf(ITConfig::class),
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
class HelloControllerIT {
	@Autowired
	lateinit var restTemplate: TestRestTemplate

	@Value("\${api.prefix}")
	private val apiPrefix = ""

	@Test
	fun greetingsMessage() {
		val request = HttpEntity<Void>(headers())

		val response = restTemplate.exchange("$apiPrefix/home/greeting_message", HttpMethod.GET,
			request, String::class.java)
		Assert.assertEquals(HttpStatus.OK, response.statusCode)
		Assert.assertEquals("Hello", response.body)
	}

	private fun headers() = run {
		val headers = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_JSON_UTF8
		headers.accept = listOf(MediaType.APPLICATION_JSON_UTF8)
		headers
	}

}
