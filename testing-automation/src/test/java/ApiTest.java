import com.ApiController;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.junit.Test;

public class ApiTest {
    ApiController api = new ApiController();
    String json = "";

    @Test
    public void testApi() {
        Response response = api.apiUpdateUser(json);
        JSONArray getIndex = new JSONArray(response.asString());
        //System.out.println(response);
    }

    @Test
    public void testTrxApi() {
        Response response = api.apiGetUserHistory();
        System.out.println(response);
    }
}
