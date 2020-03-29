import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import model.Post;
import model.User;

public class JerseyClient {
    public static void main(String[] args) {
        try {

            ClientConfig clientConfig = new DefaultClientConfig();

            clientConfig.getFeatures().put(
                    JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

            Client client = Client.create(clientConfig);

            WebResource webResource = client
                    .resource("https://jsonplaceholder.typicode.com/users/1");

            ClientResponse response = webResource.accept("application/json")
                    .type("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            User userResponse = response.getEntity(User.class);

            System.out.println("Server response .... \n");
            System.out.println(userResponse.toString());

            Post newPost = new Post();
            newPost.setUserId(userResponse.getId());
            newPost.setTitle("Teste");
            newPost.setBody("apenas um teste");

            System.out.println(newPost.toString());

            WebResource postResourse = client
                    .resource("https://jsonplaceholder.typicode.com/posts");

            ClientResponse responsePost = postResourse.accept("application/json")
                    .type("application/json").post(ClientResponse.class, newPost);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }


            String output = responsePost.getEntity(String.class);

            System.out.println("Server response .... \n");
            System.out.println(output);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
