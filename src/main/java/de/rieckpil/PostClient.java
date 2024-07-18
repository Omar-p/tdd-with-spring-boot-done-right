package de.rieckpil;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Develop an HTTP client that fetch all posts from the post-service and return them as a list. The
 * post-service returns the all posts with pagination. The client should fetch all pages and return
 * the result as a list.
 */
@Component
public class PostClient {

  private final WebClient postWebClient;

  public PostClient(WebClient postWebClient) {
    this.postWebClient = postWebClient;
  }


  public List<Post> fetchAllPosts() {

    int limit = 30;
    int skip = 0;
    int totalResults;

    List<Post> posts = new ArrayList<>();

    do {
      var result = postWebClient.get()
        .uri("/posts?limit={limit}&skip={skip}", limit, skip)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(PostResult.class)
        .block();

      totalResults = result.total();
      skip += limit;
      posts.addAll(result.posts());

    } while (posts.size() < totalResults);

    return posts;
  }

}


// /test/resources/__file.+nameOftheApi/methodName.json
