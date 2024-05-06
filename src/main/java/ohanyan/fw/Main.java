package ohanyan.fw;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ohanyan.controllers.AuthController;
import ohanyan.controllers.MainController;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class Main extends Application {
    private ConfigurableApplicationContext applicationContext;
    @FXML
    public static Stage authStage;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(SpringApplication.class)
                .run(args);

        initSecurity();
    }

    @Override
    public void start(Stage stage) throws Exception {
        authStage = new Stage();
        FxWeaver fxWeaver0 = applicationContext.getBean(FxWeaver.class);
        Parent auth = fxWeaver0.loadView(AuthController.class);
        authStage.setTitle("Log in");
        authStage.setScene(new Scene(auth));
        authStage.setResizable(false);
        authStage.setOnCloseRequest(arg0 -> System.exit(0));
        authStage.showAndWait();
        fxWeaver0.loadController(MainController.class).show();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    public static void initSecurity() {
        SecurityContextHolder.setStrategyName("MODE_GLOBAL");
    }


}
