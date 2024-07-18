package de.rieckpil;

import java.util.List;

public record PostResult(
  List<Post> posts,
  int total,
  int skip,
  int limit
) {
}
