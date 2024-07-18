package de.rieckpil;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

class PostClientTest {


  @RegisterExtension
  static WireMockExtension wireMockServer =
    WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private PostClient cut = new PostClient(WebClient.builder().baseUrl(wireMockServer.baseUrl()).build());

  @Test
  void shouldFetchAllPosts() {
    wireMockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", WireMock.equalTo("30"))
        .withQueryParam("skip", WireMock.equalTo("0"))
        .willReturn(
          aResponse()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBodyFile("dummyjson/get-all-posts-page-one.json")
        )
    );

    wireMockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", WireMock.equalTo("30"))
        .withQueryParam("skip", WireMock.equalTo("30"))
        .willReturn(
          aResponse()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBodyFile("dummyjson/get-all-posts-page-two.json")
        )
    );

    wireMockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", WireMock.equalTo("30"))
        .withQueryParam("skip", WireMock.equalTo("60"))
        .willReturn(
          aResponse()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBodyFile("dummyjson/get-all-posts-page-final.json")
        )
    );

    List<Post> result = cut.fetchAllPosts();
    assertThat(result).hasSize(90);
  }

}
