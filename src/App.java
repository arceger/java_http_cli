import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {
        
        FileReader reader = new FileReader(".env");
        Properties p = new Properties();
        p.load(reader);

        // Defina as credenciais de autenticação
        String username = p.getProperty("username");
        String password = p.getProperty("password");
        
        // Codifique as credenciais em Base64
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        
        // Crie um cliente HTTP
        HttpClient httpClient = HttpClient.newHttpClient();
        
        // Construa a requisição com a autenticação Basic Auth
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/rst"))
                .header("Authorization", "Basic " + encodedCredentials)
                .build();
        
        // Envie a requisição e obtenha a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        // Imprima a resposta
        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response body: " + response.body());
    }
}
