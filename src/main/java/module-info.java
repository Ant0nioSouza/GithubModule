module br.com.antoniosouza.githubmodule {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens br.com.antoniosouza.githubmodule to javafx.fxml;
    exports br.com.antoniosouza.githubmodule;
}