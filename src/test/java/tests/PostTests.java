package tests;

import common.MyLogger;
import components.CommentComponent;
import components.PostComponent;
import components.UserComponent;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import models.Comment;
import models.Post;
import models.User;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class PostTests {

    private Response allCommentsOnUserPostsResponse;
    private int userId;
    private List<Post> allPostsByUser;
    private List<Comment> allCommentsOnUserPosts;
    private Logger log = MyLogger.log;

    @Test
    public void checkGetAllUsers() {
        UserComponent.getAllUsers()
                .then()
                .assertThat()
                .body("size()", greaterThan(0));

        Assert.assertEquals(200, UserComponent.getAllUsers().getStatusCode());
        Assertions.assertEquals(10, UserComponent.getAllUsers().jsonPath().getList("$").size());


    }

    @Test
    @Parameters({"userName"}) // get the userName from testng.xml
    public void checkGetUser(String userName) {
        List<User> users = Arrays.asList(UserComponent.getUser(userName).getBody().as(User[].class));
        if (users.size() > 0) {
            userId = users.get(0).getId();
            log.info("userId for the username " + userName + " = " + userId);
        } else
            Assert.fail("No user found with username = " + userName);

        Assert.assertEquals(200, UserComponent.getUser(userName).getStatusCode());
        Assert.assertEquals("Glenna Reichert", UserComponent.getUser(userName).jsonPath().getString("name[0]"));
        Assert.assertEquals("Chaim_McDermott@dana.io", UserComponent.getUser(userName).jsonPath().getString("email[0]"));

    }

    @Test(dependsOnMethods = "checkGetUser")
    public void checkGetPostsByUserId() {
        allPostsByUser = Arrays.asList(PostComponent.getPosts(userId).getBody().as(Post[].class));
        allPostsByUser.forEach(post -> Assert.assertEquals(post.getUserId(), userId));

        Assert.assertEquals(200, PostComponent.getPosts(userId).getStatusCode());
        Assertions.assertEquals("tempora rem veritatis voluptas quo dolores vero", PostComponent.getPosts(userId).jsonPath().getString("title[0]"));
        Assertions.assertEquals("laudantium voluptate suscipit sunt enim enim", PostComponent.getPosts(userId).jsonPath().getString("title[1]"));
        Assertions.assertEquals(10, PostComponent.getPosts(userId).jsonPath().getList("$").size());
        String posts_title = PostComponent.getPosts(userId).jsonPath().getString("title");
        Assertions.assertEquals( posts_title, PostComponent.getPosts(userId).jsonPath().getString("title"));

    }

    @Test(dependsOnMethods = "checkGetPostsByUserId")
    public void checkPostComments() {
        allPostsByUser.forEach(post -> {
            int postId = post.getId();
            allCommentsOnUserPostsResponse = CommentComponent.getCommentsOnPost(postId);
            allCommentsOnUserPosts = Arrays.asList(allCommentsOnUserPostsResponse.getBody().as(Comment[].class));
            String emailRegex = "^(.+)@(.+)$";
            allCommentsOnUserPosts.forEach(comment -> {
                Assert.assertTrue(comment.getEmail().matches(emailRegex));
            });
            Assert.assertEquals(200, CommentComponent.getCommentsOnPost(postId).getStatusCode());

        });
    }


}

