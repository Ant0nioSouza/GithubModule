package br.com.antoniosouza.githubmodule;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class Main {
    private static URL url;

    public static void initModule(String author, String repoPath) throws MalformedURLException, MalformedURLException {
        url = new URL("https://api.github.com/users/" + author + "/repos");
    }

    public static void loopModule() {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET"); // use o método HTTP apropriado para a sua API

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Parse the JSON response using Gson
            Gson gson = new Gson();
            Repository[] repositories = gson.fromJson(content.toString(), Repository[].class);

            // Sort the repositories by the last update time
            Arrays.sort(repositories, Comparator.comparing(Repository::getUpdatedAt).reversed());

            System.out.println(repositories[0].name); // print the recent update


            // Extrai o dia da data atual
            LocalDate dataAtual = LocalDate.now();
            int diaAtual = dataAtual.getDayOfMonth();

            // Extrai o dia do update mais recente do repositório
            LocalDate dataUpdate = repositories[0].updated_at.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int diaUpdate = dataUpdate.getDayOfMonth();

            // Verifica se o dia atual é igual ao dia do update mais recente do repositório
            if(diaAtual == diaUpdate) {
                System.out.println("Hoje é o dia do último update do repositório " + repositories[0].name);
            } else {
                System.out.println("Hoje não é o dia do último update do repositório " + repositories[0].name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MalformedURLException {
        initModule("ant0niosouza", "s");
        loopModule();
    }

    private static class Repository {
        private String name;
        private Date updated_at;

        public String getName() {
            return name;
        }

        public Date getUpdatedAt() {
            return updated_at;
        }
    }
}
